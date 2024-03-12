package com.github.pablomathdev.login.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.User;

@Service
public class EmailService {

	

    public SimpleMailMessage createResetPasswordEmail(String contextPath, String token, User user) {
		
    	
    	String url = contextPath + "/user/changePassword?token=" + token;
    	
    	
    	
    	String text = "Para redefinir sua senha, clique no link abaixo:" + "\r\n" + url;
    	
    	
    	
    	return createEmail(user,"Reset Password",text);
    	
	}
	
	
	
	
	
	
	private SimpleMailMessage createEmail(User user, String subject, String text) {
		
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setFrom("noreply@springlogin.com");
		email.setTo(user.getUsername());
		email.setSubject(subject);
		email.setText(text);
		
		return email; 
		
		
	}
}
