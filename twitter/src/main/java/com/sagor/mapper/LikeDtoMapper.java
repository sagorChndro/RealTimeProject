package com.sagor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sagor.dto.LikeDto;
import com.sagor.dto.TwitDto;
import com.sagor.dto.UserDto;
import com.sagor.model.Like;
import com.sagor.model.User;

public class LikeDtoMapper {

	public static LikeDto toLikeDto(Like like, User reqUser) {

		UserDto user = UserDtoMapper.toUserDto(like.getUser());
		UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
		TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

		LikeDto likeDto = new LikeDto();
		likeDto.setId(like.getId());
		likeDto.setTwit(twit);
		likeDto.setUser(user);

		return likeDto;
	}

	public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser) {
		List<LikeDto> likeDtos = new ArrayList<>();

		for (Like like : likes) {
			UserDto user = UserDtoMapper.toUserDto(like.getUser());
			TwitDto twit = TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);

			LikeDto likeDto = new LikeDto();
			likeDto.setId(like.getId());
			likeDto.setTwit(twit);
			likeDto.setUser(user);
			likeDtos.add(likeDto);
		}
		return likeDtos;
	}
}
