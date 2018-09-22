package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming整合Flume的第二种方式(Spark Streaming pulls data from the buffered sink...)
  * Flume pushes data into the sink, and the data stays buffered.
  * Spark Streaming uses a reliable Flume receiver and transactions to pull data from the sink.
  * Transactions succeed only after data is received and replicated by Spark Streaming.
  *
  * flume_pull_streaming.conf
    my-agent.sources = netcat-source
    my-agent.channels = memory-channel
    my-agent.sinks = spark-sink

    my-agent.sources.netcat-source.type = netcat
    my-agent.sources.netcat-source.bind = 192.168.....
    my-agent.sources.netcat-source.port = 42312

    my-agent.sinks.spark-sink.type = org.apache.spark.streaming.flume.sink.SparkSink
    my-agent.sinks.spark-sink.hostname = 192.168.....
    my-agent.sinks.spark-sink.port = 44444

    my-agent.channels.memory-channel.type = memory

    my-agent.sources.netcat-source.channels = memory-channel
    my-agent.sinks.spark-sink.channel = memory-channel
  */
object FlumePullWordCount {

  def main(args: Array[String]): Unit = {

    if(args.length != 2) {
      System.err.println("Usage: FlumePullWordCount <hostname> <port>")
      System.exit(1)
    }

    val Array(hostname, port) = args

    val sparkConf = new SparkConf() //.setMaster("local[2]").setAppName("FlumePullWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val flumeStream = FlumeUtils.createPollingStream(ssc, hostname, port.toInt)

    flumeStream.map(x=> new String(x.event.getBody.array()).trim)
      .flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
