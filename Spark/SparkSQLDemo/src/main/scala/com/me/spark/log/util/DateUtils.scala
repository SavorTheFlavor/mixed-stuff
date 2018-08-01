package com.me.spark.log.util

import java.text.SimpleDateFormat
import java.util.Locale

import org.apache.commons.lang3.time.FastDateFormat


/**
  * Created by Administrator on 2018/6/10.
  */
object DateUtils {

  val DDMMYYHHMM_TIME_FORMAT = FastDateFormat.getInstance("[dd/MMM/yyyy:HH:mm:ss Z]", Locale.ENGLISH)

  val TARGET_TIME_FORMAT = FastDateFormat.getInstance("yyyy:MM:dd HH:mm:ss")

  /* input date format: [10/Nov/2016:00:01:02 +0800] */
  def parse(datestr: String) = {
    val date = DDMMYYHHMM_TIME_FORMAT.parse(datestr)
    TARGET_TIME_FORMAT.format(date)
  }

}
