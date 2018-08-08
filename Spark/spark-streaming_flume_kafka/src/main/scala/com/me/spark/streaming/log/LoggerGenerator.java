package com.me.spark.streaming.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LoggerGenerator {

    private static Logger logger = LoggerFactory.getLogger(LoggerGenerator.class);

    public static void main(String[] args) throws InterruptedException {
        long i = 0;
        while (true){
            Thread.sleep(2500);
            logger.info("counting: " + i++ + "....");
        }
    }


}
