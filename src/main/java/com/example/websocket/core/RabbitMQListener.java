package com.example.websocket.core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@RabbitListener(queues = RabbitMQConstants.taskQueue, concurrency="2")
	public void listen(String in) throws InterruptedException {
		LOGGER.info("Message read from taskQueue: {}", in);
		JSONObject root = new JSONObject(in);
		String principal = root.getString("principal");
		String content = root.getString("content");
		root.put("content", "Hello " + content);
		LOGGER.info("principal: {}", principal);
		Thread.sleep(5000);
		this.template.convertAndSendToUser(principal, "/exchange/amq.direct/greeting", root.toString());
	}

}
