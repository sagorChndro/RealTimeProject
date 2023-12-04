package com.sagor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.model.Like;
import com.sagor.model.Twit;
import com.sagor.model.User;
import com.sagor.repository.LikeRepository;
import com.sagor.repository.TwitRepository;
import com.sagor.service.LikeService;
import com.sagor.service.TwitService;

@Service
public class LikeServiceImplementation implements LikeService {

	private final LikeRepository likeRepository;
	private final TwitService twitService;
	private final TwitRepository twitRepository;

	public LikeServiceImplementation(LikeRepository likeRepository, TwitService twitService,
			TwitRepository twitRepository) {
		this.likeRepository = likeRepository;
		this.twitService = twitService;
		this.twitRepository = twitRepository;
	}

	@Override
	public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
		Like isLikeExist = likeRepository.isLikeExist(user.getId(), twitId);
		if (isLikeExist == null) {
			Twit twit = twitService.findById(twitId);

			Like like = new Like();

			like.setTwit(twit);
			like.setUser(user);

			Like savedLike = likeRepository.save(like);
			twit.getLikes().add(savedLike);
			twitRepository.save(twit);
			return savedLike;
		}
		likeRepository.deleteById(twitId);
		return isLikeExist;

	}

	@Override
	public List<Like> getAllLikes(Long twitId) throws TwitException {
		Twit twit = twitService.findById(twitId);

		List<Like> likes = likeRepository.findByTwitId(twitId);
		return likes;
	}

}
