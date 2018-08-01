package com.me.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2018/5/31.
  */
object HiveContextApp {
  def main(args: Array[String]): Unit = {

    // create the sparkContext
    val sparkConf = new SparkConf();
    // 在测试或生产中应使用脚本指定AppName和Master
    // runtestdemo.sh  spark-submit --class com.me... --master spark://hadoop001:7077 --name SQLContext sql-1.0.jar
    //sparkConf.setAppName("SQLContextApp").setMaster("local[2]")

    val sc = new SparkContext(sparkConf)
    val hiveContext = new HiveContext(sc) //已过时，建议使用sparkSession

    //data handling
    hiveContext.table("emp").show

    sc.stop()
  }
}
