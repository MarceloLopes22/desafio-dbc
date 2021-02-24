package com.api.votos.dbc;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.api.votos.dbc"})
@EntityScan(basePackages = {"com.api.votos.dbc.basicas"})  // scan JPA entities
@EnableAutoConfiguration
@EnableScheduling
@EnableRabbit
public class DbcApplication {

	@Value("${queue.order.name}")
	private String orderQueue;

	public static void main(String[] args) {
		SpringApplication.run(DbcApplication.class, args);
	}

	@Bean
	public Queue queue() {
		return new Queue(orderQueue, true);
	}
}
