package com.rubby.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {
   SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	public String generateToken(Authentication authentication) {
		
     String jwt=Jwts.builder().setIssuer("code with whatsapp")
     		 .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+864000000))
             .claim("email", authentication.getName())
             .signWith(null)
             .compact();
     return jwt;
 	}
	
	public String getEmailFromToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claim=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
		
		String email=String .valueOf(claim.get("email"));
		return email;
		
	}
	
	
}
