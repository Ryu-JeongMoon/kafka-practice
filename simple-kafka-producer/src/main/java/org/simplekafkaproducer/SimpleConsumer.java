package org.simplekafkaproducer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleConsumer {

	private static final String TOPIC_NAME = "test";
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";
	private static final String GROUP_ID = "test-group";

	public static void main(String[] args) {
		Properties configs = new Properties();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs)) {
			consumer.subscribe(List.of(TOPIC_NAME));

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
				for (ConsumerRecord<String, String> record : records) {
					log.info("record = {}", record);
				}
			}
		}
	}
}
