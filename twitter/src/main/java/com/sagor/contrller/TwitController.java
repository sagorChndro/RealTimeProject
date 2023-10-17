package com.sagor.contrller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.dto.TwitDto;
import com.sagor.exception.TwitException;
import com.sagor.exception.UserException;
import com.sagor.mapper.TwitDtoMapper;
import com.sagor.model.Twit;
import com.sagor.model.User;
import com.sagor.request.TwitReplyRequest;
import com.sagor.response.ApiResponse;
import com.sagor.service.TwitService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/auth/twits")
public class TwitController {

	private final TwitService twitService;
	private final UserService userService;

	public TwitController(TwitService twitService, UserService userService) {
		this.twitService = twitService;
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req, @RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);
		Twit twit = twitService.createTwit(req, user);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

		return new ResponseEntity<TwitDto>(twitDto, HttpStatus.CREATED);

	}

	@PostMapping("/reply")
	public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);
		Twit twit = twitService.createdReply(req, user);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

		return new ResponseEntity<TwitDto>(twitDto, HttpStatus.CREATED);
	}

	@PutMapping("/{twitId}/retwit")
	public ResponseEntity<TwitDto> retwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {
		User user = userService.findUserByProfileByJwt(jwt);

		Twit twit = twitService.reTwit(twitId, user);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

		return new ResponseEntity<TwitDto>(twitDto, HttpStatus.OK);
	}

	@GetMapping("/{twitId}")
	public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);
		Twit twit = twitService.findById(twitId);

		TwitDto twitDto = TwitDtoMapper.toTwitDto(twit, user);

		return new ResponseEntity<TwitDto>(twitDto, HttpStatus.OK);
	}

	@DeleteMapping("/{twitId}")
	public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId, @RequestHeader("Atuhorization") String jwt)
			throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);

		twitService.deleteTwitById(twitId, user.getId());

		ApiResponse response = new ApiResponse("Twit deleted successfully", true);

		// or
//		ApiResponse res = new ApiResponse();
//		res.setMessage("Twit deleted successfully");
//		res.setStatus(true);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

	}

	@GetMapping("/")
	public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization") String jwt)
			throws UserException, TwitException {
		User user = userService.findUserByProfileByJwt(jwt);

		List<Twit> twit = twitService.findAllTwit();

		List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twit, user);

		return new ResponseEntity<List<TwitDto>>(twitDto, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TwitDto>> getUserAllTwit(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);

		List<Twit> twit = twitService.getUserTwit(user);

		List<TwitDto> twitDtos = TwitDtoMapper.toTwitDtos(twit, user);

		return new ResponseEntity<List<TwitDto>>(twitDtos, HttpStatus.OK);

	}

	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TwitDto>> findTwitByLikesContains(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException {

		User user = userService.findUserByProfileByJwt(jwt);

		List<Twit> twit = twitService.findByLikesContainsUser(user);

		List<TwitDto> twitDtos = TwitDtoMapper.toTwitDtos(twit, user);

		return new ResponseEntity<List<TwitDto>>(twitDtos, HttpStatus.OK);

	}

}
