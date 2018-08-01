package com.me.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaConsumer extends Thread{
    private String topic;

    public KafkaConsumer(String topic){
        this.topic = topic;
    }

    private ConsumerConnector createConnector(){
        Properties properties = new Properties();
        properties.put("zookeeper.connect", KafkaProperties.ZK);
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }

    @Override
    public void run() {
        ConsumerConnector consumerConnector = createConnector();
        HashMap<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //@param topicCountMap  a map of (topic, #streams) pair
        topicCountMap.put(topic, 1);
        // String: topic
        // List<KafkaStream<byte[], byte[]>> 对应的数据量
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStream = consumerConnector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = messageStream.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while(iterator.hasNext()){
            String message = new String(iterator.next().message());
            System.out.println("received:" + message);
        }
    }
}
