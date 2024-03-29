package com.rubby.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubby.config.TokenProvider;
import com.rubby.exception.UserException;
import com.rubby.modal.User;
import com.rubby.repository.UserRepository;
import com.rubby.request.LoginRequest;
import com.rubby.response.AuthResponse;
import com.rubby.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private TokenProvider tokenProvider;
    private CustomUserService customUserService;
    
	public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,TokenProvider tokenProvider,CustomUserService customUserService) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.tokenProvider=tokenProvider;
		this.customUserService=customUserService;
	}
	  @PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{
		
		String email=user.getEmail();
	      String full_name=user.getFull_name();
		 String password=user.getPassword();
		 
		 User isUser=userRepository.findByEmail(email);
		 if(isUser!=null) {
			 throw new UserException("Email is used with another account"+email);
		   
		 }
		User createdUser =new User();
		createdUser.setEmail(email);
		createdUser.setFull_name(full_name);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=tokenProvider.generateToken(authentication);
		 
		AuthResponse res =new AuthResponse(jwt,true);
		 return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
	}
     
	   public ResponseEntity<AuthResponse>loginHandler(@RequestBody LoginRequest req){
		   
		   String email=req.getEmail();
		   String password=req.getPassword();
		   
		   Authentication authentication=authenticate(email, password);
		   SecurityContextHolder.getContext().setAuthentication(authentication);
		   
		   String jwt=tokenProvider.generateToken(authentication);
			
		   AuthResponse res =new AuthResponse(jwt,true);
			 return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
		
	   }
	   public Authentication  authenticate(String Username,String password) {
		   UserDetails userDetails =customUserService.loadUserByUsername(Username);
		   
		   if(userDetails==null) {
			   throw new BadCredentialsException("invalid username");
		   }
		    if(!passwordEncoder.matches(password, userDetails.getPassword())) {
		    throw new BadCredentialsException("invalid password or username ");
	   }
		    return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	   }
}
