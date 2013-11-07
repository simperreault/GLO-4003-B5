package ca.ulaval.ticketmaster.dao.util;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.stereotype.Service;

import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;

@Service("MailService")
public class MailUtil {

	public static String TRANSACTION_SUBJECT = "Votre confirmation de commande de billets";
	public static String TRANSACTION_FROM = "confirmationAchat@ulaval.ca";
	public static String TRANSACTION_HELP = "AssistanceAchat@ulaval.ca";
	@Autowired
	private MailSender mailSender;
	
	public void sendTransactionMail(User _receiver , UUID _transactionId , List<Ticket> _buyList){
		SimpleMailMessage msg = new SimpleMailMessage();
		setupMailInfos(msg);
        msg.setTo(_receiver.getEmail());
        msg.setText(
            "Cher " + _receiver.getFirstName() + " " + _receiver.getLastName()
                + ",\r\n merci d'avoir passé votre commande. Votre numéro de confirmation est le "
                + _transactionId.toString() + ". \r\n" + getInvoiceString(_buyList) + "Au plaisir de vous revoir!");
        try{
            mailSender.send(msg);
        }
        catch(MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());            
        }
	}
	
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	private void setupMailInfos(SimpleMailMessage _msg){
		_msg.setSubject(TRANSACTION_SUBJECT);
		_msg.setFrom(TRANSACTION_FROM);
		_msg.setSentDate(new Date());
		_msg.setReplyTo(TRANSACTION_HELP);
	}
	
	private String getInvoiceString(List<Ticket> _buyList){
		String returnedString = "Votre commande : \r\n";
		for (Iterator<Ticket> it = _buyList.iterator(); it.hasNext();) {
			Ticket t = it.next();
			String ticketString = "Billet " +
					t.getType().toString() +"   "+ t.getId() +"   Prix : " + t.getPrice() + "$ \r\n";
			returnedString += ticketString;
		}
		return returnedString;
	}
}
