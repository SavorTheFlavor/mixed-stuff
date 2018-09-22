package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Spark Streaming整合Flume的第一种方式: push streaming(flume(avro) push data to spark streaming directly)
  * flume_push_streaming.conf
    my-agent.sources = netcat-source
    my-agent.channels = memory-channel
    my-agent.sinks = avro-sink

    my-agent.sources.netcat-source.type = netcat
    my-agent.sources.netcat-source.bind = 192.168.....
    my-agent.sources.netcat-source.port = 42312

    my-agent.sinks.avro-sink.type = avro
    my-agent.sinks.avro-sink.hostname = 192.168..... # stream receiver
    my-agent.sinks.avro-sink.port = 44444 # stream receiver listening

    my-agent.channels.memory-channel.type = memory

    my-agent.sources.netcat-source.channels = memory-channel
    my-agent.sinks.avro-sink.channel = memory-channel
  */
object FlumePushWordCount {

  def main(args: Array[String]): Unit = {

    if(args.length != 2) {
      System.err.println("Usage: FlumePushWordCount <hostname> <port>")
      System.exit(1)
    }

    val Array(hostname, port) = args

    val sparkConf = new SparkConf() //.setMaster("local[2]").setAppName("FlumePushWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val flumeStream = FlumeUtils.createStream(ssc, hostname, port.toInt)

    flumeStream.map(x=> new String(x.event.getBody.array()).trim)
      .flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
