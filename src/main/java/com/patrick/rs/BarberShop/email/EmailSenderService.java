package com.patrick.rs.BarberShop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;

	private void sendEmail(String toEmail, String subject, String body) {
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom("kutzxpression@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);;
		
		
	}
	
	public void confirmRegistration(String toEmail, String userName) {
		String body = "Thank you for registering with us!\n www.reuters.com";
		sendEmail(toEmail, "Welcome " + userName, body);
	}
								
	
}