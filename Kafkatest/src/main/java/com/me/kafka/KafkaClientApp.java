package com.me.kafka;

public class KafkaClientApp {
    public static void main(String[] args) {
        new KafkaProducer(KafkaProperties.TOPIC).start();
    }
}
