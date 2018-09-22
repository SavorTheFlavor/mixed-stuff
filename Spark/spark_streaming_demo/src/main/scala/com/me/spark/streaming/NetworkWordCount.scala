package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
// a very simple demo
object NetworkWordCount {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[4]").setAppName("NetworkWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(5))
        // Input Stream data from ncat....
        val lines = ssc.socketTextStream("localhost", 9999)
        // Input Stream data from hdfs
        // monitors a Hadoop-compatible filesystem for new files and reads them as text files
        //  Files must be written to the
        //  * monitored directory by "moving" them from another location within the same
        //  * file system. File names starting with . are ignored.
        // val lines2 = ssc.textFileStream("hdfs://namenode:8040/logs/2017/")
        val result = lines.flatMap(_.split(" "))
                       .map((_, 1))
                       .reduceByKey(_ + _)
        result.print()

        ssc.start()
        ssc.awaitTermination()
    }
}
