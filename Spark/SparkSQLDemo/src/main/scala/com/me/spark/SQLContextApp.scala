package com.me.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2018/5/31.
  */
object SQLContextApp {
  def main(args: Array[String]): Unit = {
    //val path = args(0)
    val path = "/home/hadoop/source/spark-2.1.0/examples/src/main/resources/people.json"

    // create the sparkContext
    val sparkConf = new SparkConf();
    // 在测试或生产中应使用脚本指定AppName和Master
    // runtestdemo.sh  spark-submit --class com.me... --master spark://hadoop001:7077 --name SQLContext sql-1.0.jar
    //sparkConf.setAppName("SQLContextApp").setMaster("local[2]")

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc) //过时的写法，建议使用sparkSession

    //data handling
    val people = sqlContext.read.format("json").load(path)
    people.printSchema()
    people.show()

    sc.stop()
  }
}
