package com.sagor.contrller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.dto.LikeDto;
import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.mapper.LikeDtoMapper;
import com.sagor.model.Like;
import com.sagor.model.User;
import com.sagor.service.LikeService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {

	private final UserService userService;
	private final LikeService likeService;

	public LikeController(UserService userService, LikeService likeService) {
		this.userService = userService;
		this.likeService = likeService;
	}

	@PostMapping("/{twitId}/like")
	public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);
		Like like = likeService.likeTwit(twitId, user);
		LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);

		return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);
	}

	@GetMapping("/twit/{twitId}")
	public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId,
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);
		List<Like> likes = likeService.getAllLikes(twitId);
		List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);

		return new ResponseEntity<List<LikeDto>>(likeDtos, HttpStatus.OK);

	}

}
