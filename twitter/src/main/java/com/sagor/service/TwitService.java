package com.sagor.service;

import java.util.List;

import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.model.Twit;
import com.sagor.model.User;
import com.sagor.request.TwitReplyRequest;

public interface TwitService {

	public Twit createTwit(Twit req, User user) throws UserException;

	public List<Twit> findAllTwit();

	public Twit reTwit(Long twitId, User user) throws UserException, TwitException;

	public Twit findById(Long twitId) throws TwitException;

	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException;

	public Twit removeFromReTwit(Long twitId, Long userId) throws TwitException, UserException;

	public Twit createdReply(TwitReplyRequest req, User user) throws TwitException, UserException;

	public List<Twit> getUserTwit(User user);

	public List<Twit> findByLikesContainsUser(User user);

}
