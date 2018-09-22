package com.me.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TransformApp {
    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val blackList = List("bacon", "bacon2", "bacon22b")
        val blackListRDD = ssc.sparkContext.parallelize(blackList).map(x => (x, true))

        val lines = ssc.socketTextStream("localhost", 6789)
        // 20180610,bacon
        // 20180610,lily
        val clicklog = lines.map(x => (x.split(",")(1), x)).transform(//perform operations on each RDD to generate new RDD
            rdd => {
                rdd.leftOuterJoin(blackListRDD) // bacon, (20180610,bacon), option[boolean]
                        .filter(x => !x._2._2.getOrElse(false))
                        .map(x => x._2._1)
            }
        )
        clicklog.print()

        ssc.start()
        ssc.awaitTermination()
    }

}
