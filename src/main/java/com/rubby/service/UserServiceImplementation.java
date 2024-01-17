package com.rubby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.rubby.config.TokenProvider;
import com.rubby.exception.UserException;
import com.rubby.modal.User;
import com.rubby.repository.UserRepository;
import com.rubby.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	 @Autowired
	private TokenProvider tokenProvider;
	
	public UserServiceImplementation(UserRepository userRepository,TokenProvider req) {
		this.userRepository=userRepository;
		this.tokenProvider=tokenProvider;
	}


	@Override
	public User findUserById(Integer id) throws UserException{
		Optional<User>opt=userRepository.findById(id);
		 if(opt.isPresent()) {
			 return opt.get();
		 }
		 throw new UserException("user not found with id"+id);
	}


	@Override
	public User findUserByProfile(String jwt) throws UserException {
		
		String email=tokenProvider.getEmailFromToken(jwt);
		
		if(email==null) {
			throw new BadCredentialsException("recieved invalid token.....");
		}
		User user=userRepository.findByEmail(email);
         
		if(user==null) {
        	  throw new UserException("user not found with email"+email);
        	  
          }
		return user;
	}


	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
	     User user=findUserById(userId);
		
		if(req.getFull_name()==null) {
	    	   user.setFull_name(req.getFull_name());
	    	   
	       }
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		return userRepository.save(user);
	}


	@Override
	public List<User> searchUser(String query) {
		List<User>users=userRepository.searchUser(query); 
		return users;
	}
	
}