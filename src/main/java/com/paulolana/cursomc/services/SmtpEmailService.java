package com.paulolana.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	MailSender mailSender; // Será instanciado com todos os dados contidos em application.properties (string.mail...)

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		LOG.info(msg.toString());
		mailSender.send(msg);
		LOG.info("Email enviado");
		
	}

}
