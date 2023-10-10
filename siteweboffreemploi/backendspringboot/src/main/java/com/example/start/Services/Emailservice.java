package com.example.start.Services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Emailservice {
	@Autowired
	private  JavaMailSender mailSender;
	@Async
	public void send(String to,String email) throws MessagingException {
	try {
		System.out.println("dral");
		 MimeMessage message =mailSender.createMimeMessage() ;
		 System.out.println("dral2");
		 MimeMessageHelper helper=new MimeMessageHelper(message, true);
		 System.out.println("dral3");
		 helper.setText(email, true); 
		 helper.setTo(to);
		 helper.setSubject("Resto Job");
		 helper.setFrom("mehriadem309@gmail.com");
		 mailSender.send(message);
	}catch(Exception e) {
		System.out.println(e);
	}
	}

}
