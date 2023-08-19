package com.sagor.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequest {

	private List<Integer> userIds;
	private String chatName;
	private String chatImage;

}
