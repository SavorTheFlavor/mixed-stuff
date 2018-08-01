package com.me.spark.log

import com.me.spark.log.util.DateUtils
import org.apache.spark.sql.SparkSession

/**
  * Created by Administrator on 2018/6/10.
  */
object SparkStatFormatJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkStatFormatJob").master("local[2]").getOrCreate()

    //val access = spark.sparkContext.textFile("file:///D://logs/100000-access.log")
    val access = spark.sparkContext.textFile("file:///home/hadoop/data/100000-access.log")
    access.map(line => {
      val splits = line.split(" ")
      val ip = splits(0)
      //日志文件中的时间格式：[10/Nov/2016:00:01:02 +0800] //时区
      val time = splits(3) + " " + splits(4)
      val url = splits(11).replace("\"", "")
      val traffic = splits(9)
      DateUtils.parse(time) + "\t" + url + "\t" + traffic + "\t" +ip
    }).saveAsTextFile("file:///home/hadoop/data/output/")

    spark.stop()
  }

}
