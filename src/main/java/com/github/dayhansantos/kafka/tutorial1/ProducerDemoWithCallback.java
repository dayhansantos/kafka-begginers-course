package com.github.dayhansantos.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerDemoWithCallback {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);


        // create Producer properties
        var properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        var producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 10; i++) {

            // create a producer record
            var record = new ProducerRecord<String, String>("first_topic", "hello world " + i);

            // send data
            producer.send(record, (metadata, e) -> {
                // execute every time a record is succesfully sent or an exception is throw
                if (e == null) {
                    // the record was successfuly sent
                    logger.info("Received new metadata: \n" +
                                    "Topic: {}\n" +
                                    "Partition: {}\n" +
                                    "Offset: {}\n" +
                                    "Timestamp: {}",
                            metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
                } else {
                    logger.error("Error while producing", e);
                }
            });

            Thread.sleep(2000L);
        }

        producer.flush();
        producer.close();
    }
}
