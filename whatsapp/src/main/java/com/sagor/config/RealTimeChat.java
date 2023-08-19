package com.sagor.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.sagor.model.Message;

public class RealTimeChat {

	private SimpMessagingTemplate simpMessagingTemplate;

	// methode ei endpoint e prothome client site theke jei message ashbe seikhan
	// theke server message receive korbe
//@Payload message receive korbe

	@MessageMapping("/message")
	@SendTo("/group/public")
	public Message receiveMessage(@Payload Message message) {
		simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId().toString(), message);
		return message;
	}

}
