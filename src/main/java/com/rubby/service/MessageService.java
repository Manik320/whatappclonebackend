package com.rubby.service;

import java.util.List;

import com.rubby.exception.ChatException;
import com.rubby.exception.MessageException;
import com.rubby.exception.UserException;
import com.rubby.modal.Message;
import com.rubby.modal.User;
import com.rubby.request.SendmessageReques;

public interface MessageService  {
	
	public Message sendMessage(SendmessageReques req)throws UserException,ChatException;

	public List<Message>getChatsMessage(Integer chatId,User reqUser)throws ChatException, UserException;
	
	public Message  findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messgeId,User reqUser) throws MessageException, UserException;
	
	
}
