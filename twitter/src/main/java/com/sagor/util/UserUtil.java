package com.sagor.util;

import com.sagor.model.User;

public class UserUtil {

	public static final boolean isReqUser(User reqUser, User user2) {
		return reqUser.getId().equals(user2.getId());
	}

	public static final boolean isfollowedByReqUser(User reqUser, User user2) {
		return reqUser.getFollowings().contains(user2);
	}
}
