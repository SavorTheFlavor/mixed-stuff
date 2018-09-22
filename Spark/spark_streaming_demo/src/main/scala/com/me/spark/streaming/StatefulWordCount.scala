package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StatefulWordCount {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        // 如果使用了stateful，必须要设置checkpoint来存储state信息
        // 把checkpoint存储目录设置为当前目录
        // 生产环境中建议把checkpoint目录设置到HDFS之类的可靠文件系统中
        ssc.checkpoint(".")

        val lines = ssc.socketTextStream("localhost", 6789)
        val wordMap = lines.flatMap(_.split(" ")).map((_, 1))

        // Return a new "state" DStream where the state for each key is updated by applying
        // the given function on the previous state of the key and the new values of each key.
        // If `this` function returns None, then corresponding state key-value pair will be eliminated.
        val result = wordMap.updateStateByKey[Int](updateFunction _)

        /* Window Operations */
        // Reduce last 30 seconds of data, every 10 seconds
        // val windowedWordCounts = pairs.reduceByKeyAndWindow((a:Int,b:Int) => (a + b), Seconds(30), Seconds(10))

        result.print()

        ssc.start()
        ssc.awaitTermination()
    }

    /**
      * @param curValues the current key's value seq. eg. word_a: [1, 1, 1, 1, 1, 1,...]
      * @param preValues the previous key's value. eg. word_a: pre_count
      * @return
      */
    def updateFunction(curValues: Seq[Int], preValues: Option[Int]): Option[Int] = {
        Some(curValues.sum + preValues.getOrElse(0))
    }

}
