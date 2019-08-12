package com.example.websocket.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.websocket.core.HelloMessage;
import com.example.websocket.core.RabbitMQSender;
import com.example.websocket.core.WebSocketService;

@RestController
public class WebSocketController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private WebSocketService webSocketService;
	
    @MessageMapping("/hello")
    public void greeting(HelloMessage message, Principal principal) {
    	webSocketService.service(message, principal);
    }
	
	@RequestMapping(value = "/sendMessage/{message}", method = RequestMethod.GET)
	public String sendMessage(@PathVariable String message) {
		rabbitMQSender.sendMessage(message);
		return "";
	}

}
