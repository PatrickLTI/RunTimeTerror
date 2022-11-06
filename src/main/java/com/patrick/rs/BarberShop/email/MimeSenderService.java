package com.patrick.rs.BarberShop.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MimeSenderService {

	@Autowired
	private JavaMailSender emailSender;

	private void sendMessageWithAttachment(String to, String subject, String body)
			throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("noreply@baeldung.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);

		/*
		 * FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		 * helper.addAttachment("Invoice", file); , String pathToAttachment
		 */

		emailSender.send(message);

	}

	public void confirmAppointment(String toEmail, String userName, Long appointmentId) throws MessagingException {
		
		String body = "Thank you for booking an appointment at Xpression Kutz!<br><br>"
				+ "If you wish to cancel, please click the following link:<br>"
				+ "<a href=\"http://localhost:8081/appointment/delete/?id=" + appointmentId + 
				"&email=" + toEmail +"\">"
						+ "Cancel Appointment</a>";
		sendMessageWithAttachment(toEmail, "Appointment Confirmation for " + userName + " at Xpression Kutz", body);
		
	}
	
	public void confirmRegistration(String toEmail, String userName) throws MessagingException {
		String body = "Thank you for registering at Xpression Kutz!<br> "
				+ "Click <a href=\"http://localhost:8081/userdashboard\">here</a> to access your dashboard.";
		sendMessageWithAttachment(toEmail, "Welcome " + userName, body);
	}

}
