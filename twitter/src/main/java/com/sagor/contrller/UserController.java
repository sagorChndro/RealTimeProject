package com.sagor.contrller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.dto.UserDto;
import com.sagor.exception.UserException;
import com.sagor.mapper.UserDtoMapper;
import com.sagor.model.User;
import com.sagor.service.UserService;
import com.sagor.util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {

		User user = userService.findUserByProfileByJwt(jwt);

		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(true);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserByProfileByJwt(jwt);
		User user = userService.findUserById(userId);

		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
		userDto.setFollowed(UserUtil.isfollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);

	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User reqUser = userService.findUserByProfileByJwt(jwt);
		List<User> users = userService.searchUser(query);
		List<UserDto> userDtos = UserDtoMapper.toUserDtos(users);
		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.ACCEPTED);

	}

	@PutMapping("/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody User req, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserByProfileByJwt(jwt);
		User user = userService.updateUser(reqUser.getId(), req);

		UserDto userDto = UserDtoMapper.toUserDto(user);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{userId}/follow")
	public ResponseEntity<UserDto> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt)
			throws UserException {
		User reqUser = userService.findUserByProfileByJwt(jwt);
		User user = userService.followUser(userId, reqUser);
		UserDto userDto = UserDtoMapper.toUserDto(user);
		userDto.setFollowed(UserUtil.isfollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
	}

}
