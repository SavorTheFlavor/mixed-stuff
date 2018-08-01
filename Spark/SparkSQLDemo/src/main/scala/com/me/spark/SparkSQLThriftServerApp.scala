package com.me.spark

import java.sql.DriverManager

/**
  * Created by Administrator on 2018/6/1.
  */
object SparkSQLThriftServerApp {
  def main(args: Array[String]): Unit = {

    Class.forName("org.apache.hive.jdbc.HiveDriver")

    val conn = DriverManager.getConnection("jdbc:hive2://47.106.115.184:10000", "root", "WQcS232DATR2")
    val pstmt = conn.prepareStatement("select empno, ename, sal from emp")
    val rs = pstmt.executeQuery()
    while(rs.next()){
      println("empno:"+rs.getInt("empno"
        +", ename:"+rs.getString("ename")
      +", sal:"+rs.getDouble("sal")))
    }

    rs.close()
    pstmt.close()
    conn.close()
  }
}
