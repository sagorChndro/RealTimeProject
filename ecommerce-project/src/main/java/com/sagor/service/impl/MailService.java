package com.sagor.service.impl;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sagor.utility.EmailConstraint;
import com.sagor.utility.EmailStatus;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

@Configuration
public class MailService {

	private static final Logger logger = LogManager.getLogger(MailService.class);

	private final JavaMailSender mailSender;

	private final EmailConstraint emailConstraint;

	@Value("${spring.mail.username}")
	private String username;

	public MailService(JavaMailSender mailSender, EmailConstraint emailConstraint) {
		this.mailSender = mailSender;
		this.emailConstraint = emailConstraint;
	}

	public EmailStatus sendMimeMail(String[] to, String subject, String body, Boolean isHtml, List<File> attachment) {
		try {
			Session session = emailConstraint.getSessionInstance();
			MimeMessage message = new MimeMessage(session);
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(username);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, isHtml);
			if (attachment != null && attachment.size() > 0) {
				attachment.forEach(file -> {
					try {
						helper.addAttachment(file.getName(), file);
					} catch (MessagingException e) {
						logger.error("Error is attachment" + e.getMessage());
					}
				});
			}
			Transport.send(message);
			return new EmailStatus(to, subject, body).success();
		} catch (Exception e) {
			logger.error("Error is sending mail to" + to[0] + "message" + e.getMessage());
			return new EmailStatus(to, subject, body).error();
		}
	}

	public EmailStatus sendHtmlMail(String[] to, String subject, String body) {
		return sendMimeMail(to, subject, body, true, null);
	}

	public EmailStatus sendHtmlMail(String[] to, String subject, String body, List<File> attachment) {
		return sendMimeMail(to, subject, body, true, attachment);
	}

	public EmailStatus sendNonHtmlMail(String[] to, String subject, String body) {
		return sendMimeMail(to, subject, body, false, null);
	}

	public EmailStatus sendNonHtmlMail(String[] to, String subject, String body, List<File> attachment) {
		return sendMimeMail(to, subject, body, false, attachment);
	}
}
