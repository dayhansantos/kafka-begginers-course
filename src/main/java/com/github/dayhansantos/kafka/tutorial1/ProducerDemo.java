package com.github.dayhansantos.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerDemo {
    public static void main(String[] args) {
        // create Producer properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create a producer record
        var record = new ProducerRecord<String, String>("first_topic", "hello world");

        // create the producer
        var producer = new KafkaProducer<String, String>(properties);

        // send data
        producer.send(record);

        producer.flush();
        producer.close();
    }
}
