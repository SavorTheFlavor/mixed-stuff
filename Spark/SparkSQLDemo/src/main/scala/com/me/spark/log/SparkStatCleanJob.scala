package com.me.spark.log

import com.me.spark.log.util.AccessLogConvertUtil
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2018/6/10.
  */
object SparkStatCleanJob {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("SparkStatFormatJob").master("local[2]").getOrCreate()

    val accessRDD = spark.sparkContext.textFile("file:///home/hadoop/data/access.log")

    val accessDF = spark.createDataFrame(accessRDD.map(line => AccessLogConvertUtil.parseLog(line)), AccessLogConvertUtil.schema)
    accessDF.printSchema()
    accessDF.show(10)

    spark.stop()
  }

}
