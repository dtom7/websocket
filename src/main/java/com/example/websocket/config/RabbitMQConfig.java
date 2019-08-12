package com.example.websocket.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.websocket.core.RabbitMQConstants;

@Configuration
public class RabbitMQConfig {
	
	@Bean
	public Queue taskQueue() {
	    return new Queue(RabbitMQConstants.taskQueue, false);
	}

}
