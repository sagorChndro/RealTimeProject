package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sagor.annotation.ApiController;
import com.sagor.exception.ChatException;
import com.sagor.exception.MessageException;
import com.sagor.exception.UserException;
import com.sagor.model.Message;
import com.sagor.model.User;
import com.sagor.request.SendMessageRequest;
import com.sagor.response.ApiResponse;
import com.sagor.service.MessageService;
import com.sagor.service.UserService;

@ApiController
@RequestMapping("api/messages")
public class MessageController {

	private final UserService userService;
	private final MessageService messageService;

	public MessageController(UserService userService, MessageService messageService) {
		this.userService = userService;
		this.messageService = messageService;
	}

	@PostMapping("/createMessage")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest request,
			@RequestHeader("Authrization") String jwt) throws UserException, ChatException {

		User user = userService.findUserProfile(jwt);
		request.setUserId(user.getId());
		Message message = messageService.sendMessage(request);

		return new ResponseEntity<Message>(message, HttpStatus.OK);

	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable("chatId") Integer chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User user = userService.findUserProfile(jwt);
		List<Message> messages = messageService.getChatsMessages(chatId, user);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);

	}

	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable("messageId") Integer messageId,
			@RequestHeader("Authorization") String jwt) throws UserException, MessageException {
		User user = userService.findUserProfile(jwt);

		messageService.deleteMessage(messageId, user);
		ApiResponse response = new ApiResponse("Message deleted successfully", false);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// npm install -g npm@9.8.0

}
