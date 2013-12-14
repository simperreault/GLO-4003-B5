package ca.ulaval.ticketmaster.dao.util;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.users.model.User;

public class MailUtil {

    public static String TRANSACTION_SUBJECT = "Votre confirmation de commande de billets";
    public static String TRANSACTION_FROM = "confirmationAchat@ulaval.ca";
    public static String TRANSACTION_HELP = "AssistanceAchat@ulaval.ca";
    public static String HOST = "smtp.gmail.com";
    public static int PORT = 587;
    public static String MAIL = "ulavalachatbillets@gmail.com";
    public static String PASS = "lolulaval";

    private JavaMailSenderImpl mailSender;

    public MailUtil() {
	mailSender = new JavaMailSenderImpl();
	mailSender.setHost(HOST);
	mailSender.setPort(PORT);
	mailSender.setPassword(PASS);
	mailSender.setUsername(MAIL);
	Properties javaMailProperties = new Properties();
	javaMailProperties.put("mail.smtp.auth", "true");
	javaMailProperties.put("mail.smtp.starttls.enable", "true");
	javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	javaMailProperties.put("mail.debug", "true");
	mailSender.setJavaMailProperties(javaMailProperties);
    }

    public void sendTransactionMail(User _receiver, UUID _transactionId, List<Ticket> _buyList) {
	SimpleMailMessage msg = new SimpleMailMessage();
	setupMailInfos(msg);
	msg.setTo(_receiver.getEmail());
	msg.setText("Cher " + _receiver.getFirstName() + " " + _receiver.getLastName()
		+ ",\r\n merci d'avoir passé votre commande. Votre numéro de confirmation est le "
		+ _transactionId.toString() + ". \r\n" + getInvoiceString(_buyList)
		+ "Au plaisir de vous revoir!");
	try {
	    mailSender.send(msg);
	} catch (MailException ex) {
	    // simply log it and go on...
	    System.err.println(ex.getMessage());
	}
    }

    private void setupMailInfos(SimpleMailMessage _msg) {
	_msg.setSubject(TRANSACTION_SUBJECT);
	_msg.setFrom(TRANSACTION_FROM);
	_msg.setSentDate(new Date());
	_msg.setReplyTo(TRANSACTION_HELP);
    }

    private String getInvoiceString(List<Ticket> _buyList) {
	String returnedString = "Votre commande : \r\n";
	for (Iterator<Ticket> it = _buyList.iterator(); it.hasNext();) {
	    Ticket t = it.next();
	    String ticketString = "Billet " + t.getType().toString() + "   " + t.getId() + "   Prix : "
		    + t.getPrice() + "$ \r\n";
	    returnedString += ticketString;
	}
	return returnedString;
    }
}
