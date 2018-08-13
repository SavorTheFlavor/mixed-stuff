package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.producer.Producer
/**
  * Spark Streaming(consumer)对接Kafka的方式二
  * createDirectStream (No Receivers)
  * 这是工作中最常用的方式
  * 相比receiver方式来说，有以下好处：
  * Simplified Parallelism: No need to create multiple input Kafka streams and union them.....
  * Efficiency: Achieving zero-data loss in the first approach required the data to be stored in a Write Ahead Log....
  * Exactly-once semantics: The first approach uses Kafka’s high level API to store consumed offsets in Zookeeper....
  * Note that one disadvantage of this approach is that it does not update offsets in Zookeeper, hence Zookeeper-based Kafka monitoring tools will not show progress. However, you can access the offsets processed by this approach in each batch and update Zookeeper yourself
  * ---- http://spark.apache.org/docs/latest/streaming-kafka-0-8-integration.html
  */
object KafkaDirectWordCount {

  def main(args: Array[String]): Unit = {

    if(args.length != 2){
      System.err.println("Usage: KafkaDirectWordCount <brokers> <topics>")
      System.exit(1)
    }

    val Array(brokers, topics) = args

    val sparkConf = new SparkConf()

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

    // TODO... 为什么要取第二个?
    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}