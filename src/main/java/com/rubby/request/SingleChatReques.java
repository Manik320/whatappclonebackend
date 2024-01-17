package com.rubby.request;

import lombok.Data;

@Data
public class SingleChatReques {

	private Integer userId;

	public SingleChatReques(Integer userId) {
		
		this.userId = userId;
	}

	public SingleChatReques() {
		
	}
	
	
}
