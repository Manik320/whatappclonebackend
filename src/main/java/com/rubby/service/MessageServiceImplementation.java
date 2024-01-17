package com.rubby.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rubby.exception.ChatException;
import com.rubby.exception.MessageException;
import com.rubby.exception.UserException;
import com.rubby.modal.Chat;
import com.rubby.modal.Message;
import com.rubby.modal.User;
import com.rubby.repository.MessageRepository;
import com.rubby.request.SendmessageReques;

@Service
public class MessageServiceImplementation implements MessageService{

	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	
	
	
	public MessageServiceImplementation(MessageRepository messageRepository, UserService userService,
			ChatService chatService) {
		
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}

	@Override
	public Message sendMessage(SendmessageReques req) throws UserException, ChatException {
		
		User user=userService.findUserById(req.getUserId());
		Chat chat= chatService.findChatById(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
    
		return message;
		
	}

	@Override
	public List<Message> getChatsMessage(Integer chatId,User reqUser) throws ChatException, UserException {
  		Chat chat=chatService.findChatById(chatId);
		
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("you are not related to this chat"+chat.getId());
		}
		List<Message>messages=messageRepository.findByChatId(chat.getId());
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		
		Optional<Message> opt=messageRepository.findById(messageId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new MessageException("message not found with id"+messageId);
	}

	@Override
	public void deleteMessage(Integer messgeId,User reUser ) throws MessageException, UserException {
		Message message =findMessageById(messgeId);
		
		if(message.getUser().getId().equals(reUser.getId())) {
			messageRepository.deleteById(messgeId);
		}
		
          throw new UserException("you can't delete another user's message"+reUser.getFull_name());	
	}

}
