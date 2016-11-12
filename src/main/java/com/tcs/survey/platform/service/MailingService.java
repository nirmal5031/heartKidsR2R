package com.tcs.survey.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import java.util.Properties;

@Service
public class MailingService {

	@Value("${mailfromId}")
	public String fromMailId;
	@Value("${mail.smtp.host}")
	public String Host;
	@Value("${mail.smtp.port}")
	public String Port;
	@Value("${mail.username}")
	public String username;
	@Value("${mail.password}")
	public String password;
	@Value("${mail.auth}")
	public String auth;
	@Value("${mail.AccessKey}")
	public String accessKey;

	public String mailingservice(String to, String fullname, String subjectMess, String htmlText) {

		SendGrid sendgrid = new SendGrid(accessKey);
		SendGrid.Email MessageMail = new SendGrid.Email();
		MessageMail.addTo(to);
		MessageMail.setFrom(fromMailId);
		MessageMail.setSubject(subjectMess);
		MessageMail.setHtml(htmlText);

		try {
		    SendGrid.Response response = sendgrid.send(MessageMail);
		    System.out.println(response.getMessage());
			System.out.println("Sent message successfully....");
		}   catch (SendGridException sge) {
		    sge.printStackTrace();
		}
	
	return null;
	}
}
