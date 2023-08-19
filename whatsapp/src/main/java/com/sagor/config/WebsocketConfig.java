package com.sagor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
//eikhane duita method kei manually override korte hobe 

	// ws endpoint client site e access kore
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
	}

	// kon end point theke message jabe r kon endpoint theke message receive korbe
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");// ei app er sahajje client site theke
															// message send korbe and server side
															// receive korbe

		registry.enableSimpleBroker("/group", "/user");// eitar sahajje message server theke
														// client site e diye dibe

		registry.setUserDestinationPrefix("/user");//

	}

}
