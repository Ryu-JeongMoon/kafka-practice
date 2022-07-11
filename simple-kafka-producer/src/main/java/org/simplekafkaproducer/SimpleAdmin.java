package org.simplekafkaproducer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.ConfigResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleAdmin {

	private static final String BOOTSTRAP_SERVERS = "localhost:9092";

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Properties configs = new Properties();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

		try (AdminClient admin = AdminClient.create(configs)) {
			log.info("GET BROKER INFORMATION");
			admin.describeCluster()
				.nodes()
				.get()
				.forEach(node -> {
					ConfigResource configResource = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
					try {
						admin.describeConfigs(Collections.singleton(configResource))
							.all()
							.get()
							.forEach((broker, config) -> config.entries().forEach(configEntry -> log.info("name = {}, value = {}", configEntry.name(), configEntry.value())));
					} catch (InterruptedException | ExecutionException e) {
						throw new RuntimeException(e);
					}
				});
		}
	}
}
