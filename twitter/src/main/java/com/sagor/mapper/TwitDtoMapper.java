package com.sagor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sagor.dto.TwitDto;
import com.sagor.dto.UserDto;
import com.sagor.model.Twit;
import com.sagor.model.User;
import com.sagor.util.TwitUtil;

public class TwitDtoMapper {

	public static TwitDto toTwitDto(Twit twit, User reqUser) {

		UserDto user = UserDtoMapper.toUserDto(twit.getUser());

		boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, twit);
		boolean isReTwited = TwitUtil.isReTwitedByReqUser(reqUser, twit);

		List<Long> reTwitUserId = new ArrayList<>();

		for (User user1 : twit.getReTwitsUser()) {
			reTwitUserId.add(user1.getId());
		}

		TwitDto twitDto = new TwitDto();

		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setImage(twit.getImage());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwit().size());
		twitDto.setTotalReTweets(twit.getReTwitsUser().size());
		twitDto.setUser(user);
		twitDto.setLiked(isLiked);
		twitDto.setRetwit(isReTwited);
		twitDto.setRetwitUsersId(reTwitUserId);
		twitDto.setReplyTwits(toTwitDtos(twit.getReplyTwit(), reqUser));
		twitDto.setVideo(twit.getVideo());

		return twitDto;
	}

	public static List<TwitDto> toTwitDtos(List<Twit> twits, User reqUser) {
		List<TwitDto> twitDtos = new ArrayList<>();

		for (Twit twit : twits) {
			TwitDto twitDto = toReplyTwitDto(twit, reqUser);
			twitDtos.add(twitDto);
		}
		return twitDtos;
	}

	private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {
		UserDto user = UserDtoMapper.toUserDto(twit.getUser());

		boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, twit);
		boolean isReTwited = TwitUtil.isReTwitedByReqUser(reqUser, twit);

		List<Long> reTwitUserId = new ArrayList<>();

		for (User user1 : twit.getReTwitsUser()) {
			reTwitUserId.add(user1.getId());
		}

		TwitDto twitDto = new TwitDto();

		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setImage(twit.getImage());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwit().size());
		twitDto.setTotalReTweets(twit.getReTwitsUser().size());
		twitDto.setUser(user);
		twitDto.setLiked(isLiked);
		twitDto.setRetwit(isReTwited);
		twitDto.setRetwitUsersId(reTwitUserId);
		twitDto.setVideo(twit.getVideo());

		return twitDto;
	}
}
