package com.rubby.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDetail {
	
	public ErrorDetail() {
		
	}

	public ErrorDetail(String message, String description, LocalDateTime now) {
		
	}
	private String error;
	private String message;
	private LocalDateTime timeStamp;
	
	
}
