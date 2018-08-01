package com.me.spark.log.util

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
  * Created by Administrator on 2018/6/10.
  */
object AccessLogConvertUtil extends Serializable{

  val schema = StructType(Array(
    StructField("url", StringType, nullable = true),
    StructField("cmsType", StringType, nullable = true),
    StructField("cmsId", LongType, nullable = true),
    StructField("traffic", LongType, nullable = true),
    StructField("ip", StringType, nullable = true),
    StructField("city", StringType, nullable = true),
    StructField("time", StringType, nullable = true),
    StructField("day", StringType, nullable = true)))

  def parseLog(line: String): Row = {
    try{
      val splits = line.split("\t")
      val url = splits(1)
      val domain = "www.imooc.com"
      // http://www.imooc.com/video/4500
      val cmsTypeAndId = url.substring(url.indexOf(domain) + domain.length + 1)
      val cmsType = cmsTypeAndId.split("/")(0)
      val cmsId = cmsTypeAndId.split("/")(1).toLong
      val traffic = splits(2).toLong
      val ip = splits(3)
      val city = ""
      val time = splits(0)
      val day = time.substring(0,10).replaceAll("-", "")
      Row(url, cmsType, cmsId, traffic, ip, city, time, day)
    }catch {
      case e:Exception => {Row(0)}
    }
  }

}
