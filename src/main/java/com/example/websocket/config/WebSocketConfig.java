package com.example.websocket.config;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
	    config
	      .setApplicationDestinationPrefixes("/app")
	      .enableStompBrokerRelay("/exchange/amq.direct")
	      .setRelayHost("localhost")
	      .setRelayPort(61613)
	      .setClientLogin("guest")
	      .setClientPasscode("guest");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp-websocket").setHandshakeHandler(new CustomHandshakeHandler()).withSockJS();
	}

}

class CustomHandshakeHandler extends DefaultHandshakeHandler {
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		return new StompPrincipal(UUID.randomUUID().toString());
	}
}

class StompPrincipal implements Principal {
	String name;

	StompPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
