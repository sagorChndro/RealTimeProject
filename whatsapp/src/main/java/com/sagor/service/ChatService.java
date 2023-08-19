package com.sagor.service;

import java.util.List;

import com.sagor.exception.ChatException;
import com.sagor.exception.UserException;
import com.sagor.model.Chat;
import com.sagor.model.User;

public interface ChatService {

	public Chat createChat(User reqUser, Integer userId2) throws UserException;

	public Chat findChatById(Integer chatId) throws ChatException;

	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

	public Chat addUserToGroup(Integer userId, Integer ChatId, User reqUser) throws UserException, ChatException;

	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException;

	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException;

	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}
