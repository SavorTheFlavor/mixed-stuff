package com.me.spark.streaming

import java.sql.DriverManager

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ForeachRDDApp {

    def createNewConnection() = {
        Class.forName("com.mysql.jdbc.Driver")
        DriverManager.getConnection("jdbc:mysql://localhost:3306/sparktest", "root", "23456")
    }

    def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setMaster("local[4]").setAppName("NetworkWordCount")
        val ssc = new StreamingContext(sparkConf, Seconds(5))

        val lines = ssc.socketTextStream("localhost", 6789)
        val res = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
        /*-- save data to MySQL --*/
        /* bad example 1 */
//        dstream.foreachRDD { rdd =>
//            val connection = createNewConnection()  // executed at the driver
//            rdd.foreach { record =>
//                connection.send(record) // executed at the worker
//            }
//        }
        //This is incorrect as this requires the connection object to be serialized and sent from the driver to the worker.
        // Such connection objects are rarely transferable across machines. This error may manifest as serialization errors (connection object not serializable)

        /* bad example 2 */
//        dstream.foreachRDD { rdd =>
//            rdd.foreach { record =>
//                val connection = createNewConnection()
//                connection.send(record)
//                connection.close()
//            }
//        }
        // creating and destroying a connection object for each record can incur unnecessarily high overheads and can significantly reduce the overall throughput of the system.

        /* not too bad example 3 */
        res.foreachRDD { rdd =>
            rdd.foreachPartition { partitionOfRecords =>
                    if(partitionOfRecords.size > 0){
                        val connection = createNewConnection()
                        partitionOfRecords.foreach(record => {
                            val sql = s"insert into word_count(word, wcount) values(${record._1}, ${record._2})"
                            connection.createStatement().execute(sql)
                           }
                        )
                        connection.close()
                    }
            }
        }

        /* optimized example 4 */
//        res.foreachRDD { rdd =>
//            rdd.foreachPartition { partitionOfRecords =>
//                // ConnectionPool is a static, lazily initialized pool of connections
//                val connection = ConnectionPool.getConnection()
//                partitionOfRecords.foreach(record => connection.send(record))
//                ConnectionPool.returnConnection(connection)  // return to the pool for future reuse
//            }
//        }
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
