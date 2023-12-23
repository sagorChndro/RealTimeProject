package com.sagor.utility;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

@Configuration
public class EmailConstraint {
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private String port;
	@Value("${spring.mail.isAuth}")
	private String isAuth;
	@Value("${spring.mail.tls}")
	private String isTls;

	private Properties getPropertiesInstance() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.isAuth", isAuth);
		properties.put("mail.smtp.isTls", isTls);
		return properties;
	}

	public Session getSessionInstance() {
		return Session.getInstance(getPropertiesInstance(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

}
