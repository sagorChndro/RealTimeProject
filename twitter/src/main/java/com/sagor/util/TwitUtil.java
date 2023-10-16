package com.sagor.util;

import com.sagor.model.Like;
import com.sagor.model.Twit;
import com.sagor.model.User;

public class TwitUtil {

	public final static boolean isLikedByReqUser(User reqUser, Twit twit) {

		for (Like like : twit.getLikes()) {
			if (like.getUser().getId().equals(reqUser.getId())) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isReTwitedByReqUser(User reqUser, Twit twit) {
		for (User user : twit.getReTwitsUser()) {
			if (user.getId().equals(reqUser.getId())) {
				return true;
			}
		}
		return false;
	}

}
