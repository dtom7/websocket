package com.example.websocket.core;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketService.class);
	
/*	@Autowired
	private SimpMessagingTemplate template;
	
	public void service(HelloMessage message, Principal principal) {
		String text = "{\"content\":\"Hello, "+message.getName()+ " -- "+ principal.getName()+" from Q\"}";
		this.template.convertAndSendToUser(principal.getName(), "/exchange/amq.direct/greeting", text);
	}*/
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void service(HelloMessage message, Principal principal) {
		String text = "{\"content\": \"" + message.getName() + "\",\"principal\": \""+ principal.getName() + "\"}";
		rabbitMQSender.sendMessage(text);
	}

}
