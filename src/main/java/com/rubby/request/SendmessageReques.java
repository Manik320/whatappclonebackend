package com.rubby.request;

import lombok.Data;

@Data
public class SendmessageReques {

	private Integer userId;
	private Integer chatId;
	private String content;
	
	public SendmessageReques() {
		
	}

	public SendmessageReques(Integer userId, Integer chatId, String content) {
	
		this.userId = userId;
		this.chatId = chatId;
		this.content = content;
	}
	
	
	
}
