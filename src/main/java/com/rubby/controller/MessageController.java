package com.rubby.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubby.exception.ChatException;
import com.rubby.exception.UserException;
import com.rubby.modal.Message;
import com.rubby.modal.User;
import com.rubby.request.SendmessageReques;
import com.rubby.response.ApiResponse;
import com.rubby.service.MessageService;
import com.rubby.service.UserService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

	private MessageService messageService;
	private UserService userService;
	
	public  MessageController(MessageService messageService, UserService userService) {
		
		this.messageService =messageService;
		this.userService = userService;
	}
	 
	@PostMapping("/create")
	public ResponseEntity<Message>sendMessageHandler(@RequestBody SendmessageReques req,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user=userService.findUserByProfile(jwt);
		req.setUserId(user.getId());
		Message message=messageService.sendMessage(req);
	
	  return new ResponseEntity<Message>(message,HttpStatus.OK);
	}
	@GetMapping("/chat/{chatid}")
	public ResponseEntity<List<Message>>getChatsMessageHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user=userService.findUserByProfile(jwt);

		List<Message> message=messageService.getChatsMessage(chatId, user);
	
	  return new ResponseEntity<List<Message>>(message,HttpStatus.OK);
	}
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse>deleteMessageHandler(@PathVariable Integer messageId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		
		User user=userService.findUserByProfile(jwt);

		messageService.getChatsMessage(messageId, user);
	     ApiResponse res=new ApiResponse("message deleted sucessfully", false);
	  return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
