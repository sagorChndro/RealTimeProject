package com.sagor.service;

import java.util.List;

import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.model.Like;
import com.sagor.model.User;

public interface LikeService {

	public Like likeTwit(Long twitId, User user) throws UserException, TwitException;

	public List<Like> getAllLikes(Long twitId) throws TwitException;

}
