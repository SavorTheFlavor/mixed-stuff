package com.me.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2018/5/31.
  */
object SparkSessionApp {
  def main(args: Array[String]): Unit = {
    //val path = args(0)
    val path = "file:///home/hadoop/source/spark-2.1.0/examples/src/main/resources/people.json"

    val spark = SparkSession.builder().appName("SparkSessionApp").getOrCreate()

    //data handling
    //val people = spark.read.format("json").load(path)
    val people = spark.read.json(path)
    people.printSchema()
    people.show()

    spark.stop()
  }
}
