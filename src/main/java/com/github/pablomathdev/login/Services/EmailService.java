package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.github.pablomathdev.login.Domain.Entities.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Value("${base.url}")
	private String baseUrl;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSenderImpl mailSender;

	public void createResetPasswordEmail(User user, String token) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

		String body = "Para redefinir sua senha, clique no link abaixo:";

		String linkResetPassword = baseUrl + "/user/changePassword?token=" + token;

		Context context = new Context();
		context.setVariable("message", body);
		context.setVariable("link", linkResetPassword);

		String htmlContent = templateEngine.process("resetPasswordEmailTemplate", context);

		helper.setTo(user.getUsername());
		helper.setFrom("noreply@springlogin.com");
		helper.setSubject("Redefinição de Senha");
		helper.setText(htmlContent, true);

		mailSender.send(mimeMessage);

	}
}
