package com.me.kafka;

import kafka.producer.KeyedMessage;
import kafka.producer.Producer;
import kafka.producer.ProducerConfig;
import java.util.Properties;

public class KafkaProducer extends Thread{
    private String topic;

    private Producer<Integer, String> producer;

    public KafkaProducer(String topic){
        this.topic = topic;

        Properties properties = new Properties();
        properties.put("metadata.broker.list", KafkaProperties.BROKER_LIST);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        /*
         * request.required.acks = 0 - means the producer will not wait for any acknowledgement from the leader.
         * request.required.acks = 1 - means the leader will write the message to its local log and immediately acknowledge
         * request.required.acks = -1 - means the leader will wait for acknowledgement from all in-sync replicas before acknowledging the write
         */
        properties.put("request.required.acks", "1");
        this.producer = new Producer<Integer, String>(new ProducerConfig(properties));
    }

    @Override
    public void run() {
        int messageNo = 0;
        while(true){
            String exclamations = "";
            for(int i=1; i<=messageNo; i++){
                exclamations += "!";
            }
            String message = "message: what the hell is going on? " + exclamations;
            producer.send(new KeyedMessage<Integer, String>(topic, message));
            System.out.println("Sent:" + message);
            messageNo++;

            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }
}
