package com.sagor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sagor.exception.ChatException;
import com.sagor.exception.UserException;
import com.sagor.model.Chat;
import com.sagor.model.User;
import com.sagor.repository.ChatRepository;
import com.sagor.service.ChatService;
import com.sagor.service.GroupChatRequest;
import com.sagor.service.UserService;

@Service
public class ChatServiceImplement implements ChatService {

	private final ChatRepository chatRepository;
	private final UserService userService;

	public ChatServiceImplement(ChatRepository chatRepository, UserService userService) {
		this.chatRepository = chatRepository;
		this.userService = userService;
	}

	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
		User user = userService.findUserById(userId2);
		Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
		if (isChatExist != null) {
			return isChatExist;
		}
		Chat chat = new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);

		return chat;
	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);
		System.out.println("find chat " + chat);
		if (chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not found with id " + chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserById(userId);
		System.out.println("user Foud" + user);
		if (user == null) {
			throw new UserException("User not found");
		}
		List<Chat> chats = chatRepository.findChatByUserId(user.getId());
		System.out.println("Chat retrieve " + chats);
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		Chat group = new Chat();
		group.setChatImage(req.getChatImage());
		group.setChatName(req.getChatName());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);

		for (Integer userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}

		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer ChatId, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(ChatId);

		User user = userService.findUserById(userId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chatRepository.save(chat);
			} else {
				throw new UserException("You are not admin");
			}

		}
		throw new ChatException("Chat not found with id " + ChatId);
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getUsers().contains(reqUser)) {
				chat.setChatName(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("You are not a member of this group");
		}
		throw new ChatException("Chat not found with id " + chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);

		User user = userService.findUserById(userId);

		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);
				return chatRepository.save(chat);
			} else if (chat.getAdmins().contains(reqUser)) {
				if (user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);
					return chatRepository.save(chat);
				}
			}
			throw new UserException("You can't remove another user ");
		}
		throw new ChatException("Chat not found with id " + chatId);
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			chatRepository.deleteById(chat.getId());
		}

	}

}
