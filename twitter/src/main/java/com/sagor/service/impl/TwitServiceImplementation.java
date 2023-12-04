package com.sagor.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.model.Twit;
import com.sagor.model.User;
import com.sagor.repository.TwitRepository;
import com.sagor.request.TwitReplyRequest;
import com.sagor.service.TwitService;

@Service
public class TwitServiceImplementation implements TwitService {

	private final TwitRepository twitRepository;
	// private final UserRepository userRepository;

	public TwitServiceImplementation(TwitRepository twitRepository) {
		this.twitRepository = twitRepository;
		// this.userRepository = userRepository;
	}

	@Override
	public Twit createTwit(Twit req, User user) throws UserException {

		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setVideo(req.getVideo());

		Twit savedTwit = twitRepository.save(twit);
		System.out.println("create twit from service " + savedTwit);

		return savedTwit;
	}

	@Override
	public List<Twit> findAllTwit() {
		return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public Twit reTwit(Long twitId, User user) throws UserException, TwitException {
		Twit twit = findById(twitId);
		if (twit.getReTwitsUser().contains(user)) {
			twit.getReTwitsUser().remove(user);
		} else {
			twit.getReTwitsUser().add(user);
		}
		return twitRepository.save(twit);
	}

	@Override
	public Twit findById(Long twitId) throws TwitException {
		Twit twit = twitRepository.findById(twitId)
				.orElseThrow(() -> new TwitException("Twit not found with id " + twitId));
		return twit;
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
		Twit twit = findById(twitId);

		if (!userId.equals(twit.getUser().getId())) {
			throw new UserException("You can't delete another user's twit");
		}

		twitRepository.deleteById(twit.getId());

	}

	// removeFromReTwit kaj retwit e kora ache
	@Override
	public Twit removeFromReTwit(Long twitId, Long userId) throws TwitException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Twit createdReply(TwitReplyRequest req, User user) throws TwitException, UserException {

		Twit replyFor = findById(req.getTwitId());

		Twit twit = new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(req.getCreatedAt());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(true);
		twit.setTwit(false);
		twit.setReplyFor(replyFor);

		Twit savedReply = twitRepository.save(twit);

		twit.getReplyTwit().add(savedReply);
		twitRepository.save(replyFor);

		return replyFor;
	}

	@Override
	public List<Twit> getUserTwit(User user) {
		return twitRepository.findByReTwitsUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAt(user, user.getId());
	}

	@Override
	public List<Twit> findByLikesContainsUser(User user) {
		return twitRepository.findByLikesUser_id(user.getId());
	}

}
