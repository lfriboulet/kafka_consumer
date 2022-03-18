package io.lfr;

import io.lfr.models.Employee;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        final Properties properties = loadConfig();
        final String topic = properties.getProperty("consumer.topic");
        final int duration = Integer.parseInt(properties.getProperty("duration.ms"));

        try (KafkaConsumer<String, Employee> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList(topic));

            while (true) {
                ConsumerRecords<String, Employee> records = consumer.poll(Duration.ofMillis(duration));
                // here process logic
                records.forEach(record -> LOG.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value()));
                // here commit offset  ("at least once" delivery strategy)
                consumer.commitAsync();
            }
        }
    }

    // prévoir de passer la config en paramètre
    private static Properties loadConfig() {
        final Properties properties = new Properties();
        try {
            InputStream producerConfigFile = Consumer.class.getClassLoader().getResourceAsStream("consumer.properties");
            properties.load(producerConfigFile);
        } catch (IOException e) {
            LOG.error("Error : " + e.getMessage());
        }
        return properties;
    }

}
