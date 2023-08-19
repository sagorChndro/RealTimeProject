package com.sagor.service;

import java.util.List;

import com.sagor.exception.ChatException;
import com.sagor.exception.MessageException;
import com.sagor.exception.UserException;
import com.sagor.model.Message;
import com.sagor.model.User;
import com.sagor.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest request) throws UserException, ChatException;

	public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;

	public Message findMessageById(Integer messageId) throws MessageException;

	public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}
