package com.rubby.service;

import java.util.List;

import lombok.Data;

@Data
public class GroupChatRequest {

	private List<Integer>userIds;
	private String chat_name;
	private String chat_image;
	
	
	public GroupChatRequest() {
		
	}
	
	public GroupChatRequest(List<Integer> userIds, String chat_name, String chat_image) {
		super();
		this.userIds = userIds;
		this.chat_name = chat_name;
		this.chat_image = chat_image;
	}



}
