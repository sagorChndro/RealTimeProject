package com.sagor.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sagor.exception.ChatException;
import com.sagor.exception.MessageException;
import com.sagor.exception.UserException;
import com.sagor.model.Chat;
import com.sagor.model.Message;
import com.sagor.model.User;
import com.sagor.repository.MessageRepository;
import com.sagor.request.SendMessageRequest;
import com.sagor.service.ChatService;
import com.sagor.service.MessageService;
import com.sagor.service.UserService;

@Service
public class MessageServiceImplement implements MessageService {

	private final MessageRepository messageRepository;
	private final UserService userService;
	private final ChatService chatService;

	public MessageServiceImplement(MessageRepository messageRepository, UserService userService,
			ChatService chatService) {
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendMessageRequest request) throws UserException, ChatException {
		User user = userService.findUserById(request.getUserId());
		Chat chat = chatService.findChatById(request.getChatId());

		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(request.getContent());
		message.setTimestamp(LocalDateTime.now());

		return message;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		Chat chat = chatService.findChatById(chatId);
		if (!chat.getUsers().contains(reqUser)) {
			throw new UserException("You are not related to this chat");
		}
		List<Message> messages = messageRepository.findByChatId(chatId);
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> opt = messageRepository.findById(messageId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("Message not found with id " + messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
		Message message = findMessageById(messageId);

		if (message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You can't delete another user's message " + messageId);

	}

}
