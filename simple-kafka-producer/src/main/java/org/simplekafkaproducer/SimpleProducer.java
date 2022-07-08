package org.simplekafkaproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleProducer {

	private static final String TOPIC_NAME = "test";
	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	public static void main(String[] args) {
		Properties configs = new Properties();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		try (KafkaProducer<String, String> producer = new KafkaProducer<>(configs)) {
			// String key = "tom-misch";
			String messageValue = "movie";
			ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);
			log.info("record = {}", record);

			producer.send(record, (metadata, e) -> {
				if (e != null) {
					log.error(e.getMessage(), e);
				} else {
					log.info("metadata = {}", metadata.toString());
				}
			});
		}
	}
}
