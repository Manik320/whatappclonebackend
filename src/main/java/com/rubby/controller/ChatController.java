package com.rubby.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubby.exception.ChatException;
import com.rubby.exception.UserException;
import com.rubby.modal.Chat;
import com.rubby.modal.User;
import com.rubby.request.SingleChatReques;
import com.rubby.response.ApiResponse;
import com.rubby.service.ChatService;
import com.rubby.service.GroupChatRequest;
import com.rubby.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	private ChatService chatService;
	private UserService userService;
	
	public ChatController(ChatService chatService, UserService userService) {
		
		this.chatService = chatService;
		this.userService = userService;
	}
	
	@PostMapping("/single")
	public ResponseEntity<Chat>createChatHandler(@RequestBody SingleChatReques singleChatRequest,@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser=userService.findUserByProfile(jwt);
		Chat chat=chatService.createchat(reqUser,singleChatRequest.getUserId());
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	@PostMapping("/group")
	public ResponseEntity<Chat>createGroupHandler(@RequestBody GroupChatRequest req,@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser=userService.findUserByProfile(jwt);
		Chat chat=chatService.createGroup(req, reqUser);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@GetMapping("/{chatId}")
	public ResponseEntity<Chat>findchatByIdHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		
		Chat chat=chatService.findChatById(chatId);
		
		return new ResponseEntity<Chat>(chat,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Chat>>findAllChatByUserIdGroupHandler(@RequestHeader("Authorization")String jwt ) throws UserException{
		
		User reqUser=userService.findUserByProfile(jwt);
		List<Chat> chats=chatService.findAllChatByUserId(reqUser.getId());
		
		return new ResponseEntity<List<Chat>>(chats,HttpStatus.OK);
		
	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat>addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer uerId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser=userService.findUserByProfile(jwt);
		
		 Chat chat=chatService.addUserToGroup(uerId, chatId, reqUser);
		
		return new ResponseEntity<>(chat,HttpStatus.OK);
		
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat>removeUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer uerId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser=userService.findUserByProfile(jwt);
		
		 Chat chat=chatService.removeFromGroup(chatId, uerId, reqUser);
		
		return new ResponseEntity<>(chat,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse>deleteChatHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt ) throws UserException, ChatException{
		
		User reqUser=userService.findUserByProfile(jwt);
		
   chatService.deleteChat(chatId, reqUser.getId());
		
   ApiResponse res=new ApiResponse("chat is deleted sucessfully", true);
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	
}
