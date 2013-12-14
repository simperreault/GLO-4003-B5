package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.users.model.User;

public aspect LogBoughtTicketAspect {

	private static final String LOG_FILE = "src/main/resources/log.log";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static void log( String str )
	{
		FileWriter fwLog = null;
		
		try {
			fwLog = new FileWriter(LOG_FILE, true);
			fwLog.append("\n");
			fwLog.append(dateFormat.format(new Date()));
			fwLog.append(" :\n");
			fwLog.append(str);
			fwLog.flush();
			fwLog.close();
		} catch (IOException e) {
			System.out.println("Ne peut pas logger :");
			e.printStackTrace();
		}
		
	}
	
	pointcut logConfirmation() :
		execution(
				private void ca.ulaval.ticketmaster.dao.util.DataManager.sendConfirmationMail(User, UUID, List<Ticket>) );
	
	@SuppressWarnings("unchecked")
	before() : logConfirmation()
	{
		System.out.println("around");

		User user = (User)thisJoinPoint.getArgs()[0];
		UUID transactionId = (UUID)thisJoinPoint.getArgs()[1];
		ArrayList<Ticket> ticketList = (ArrayList<Ticket>)thisJoinPoint.getArgs()[2];

		LogBoughtTicketAspect.log("Envoi du courriel de confirmation à l'utilisateur '" + user.getUsername()
				+ "' (email='" + user.getEmail() + "') avec transactionId = '" + transactionId 
				+ "', les billets :\n" + Arrays.toString(ticketList.toArray()));
	}

}
