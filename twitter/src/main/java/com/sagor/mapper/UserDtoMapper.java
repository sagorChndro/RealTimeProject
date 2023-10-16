package com.sagor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sagor.dto.UserDto;
import com.sagor.model.User;

public class UserDtoMapper {

	public static UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getFullName());
		userDto.setFullName(user.getFullName());
		userDto.setImage(user.getImage());
		userDto.setBackgroundImage(user.getBackgroudImage());
		userDto.setBio(user.getBirthDate());
		userDto.setBirthDate(user.getBirthDate());
		userDto.setFollowers(toUserDtos(user.getFollowers()));
		userDto.setFollowing(toUserDtos(user.getFollowings()));
		userDto.setLogin_with_google(user.isLogin_with_google());
		userDto.setLocation(user.getLocation());
//		userDto.setVerified(false);

		return userDto;
	}

	private static List<UserDto> toUserDtos(List<User> followers) {
		List<UserDto> userDtos = new ArrayList<>();

		for (User user : followers) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setImage(user.getImage());
			userDtos.add(userDto);
		}
		return userDtos;
	}

}
