package com.github.pablomathdev.login.infra.ConfigMailSender;

import org.springframework.mail.javamail.JavaMailSenderImpl;


public class MailSender {

	
	JavaMailSenderImpl javaMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		return mailSender;


	}

}
