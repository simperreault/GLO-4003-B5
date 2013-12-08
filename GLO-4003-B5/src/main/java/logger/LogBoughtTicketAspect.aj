package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.purchase.model.PurchaseViewModel;
import ca.ulaval.ticketmaster.users.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public aspect LogBoughtTicketAspect {

	private static final String LOG_FILE = "src/main/resources/log.log";
	private static void log( String str )
	{
		FileWriter fwLog = null;
		
		try {
			fwLog = new FileWriter(LOG_FILE, true);
			fwLog.append(str);
			fwLog.flush();
			fwLog.close();
		} catch (IOException e) {
			System.out.println("Ne peut pas logger :");
			e.printStackTrace();
		}
		
	}
	
	//Purchase
	pointcut logPurchase() :
		execution( 
				public String ca.ulaval.ticketmaster.purchase.PurchaseController.purchase(PurchaseViewModel, Model, HttpSession,
					    BindingResult, HttpServletRequest) );
	
	before() : logPurchase()
	{
		System.out.println("About to log");
		
		System.out.println(thisJoinPoint.getSignature());
		System.out.println(thisJoinPoint.getArgs().length);
		
		System.out.println(thisJoinPoint.getArgs()[0] instanceof PurchaseViewModel);
		
		if ( thisJoinPoint.getArgs()[0] instanceof PurchaseViewModel )
		{
			PurchaseViewModel pModel = (PurchaseViewModel)thisJoinPoint.getArgs()[0];
			
			System.out.println(pModel.toString());
		}
		//pModel.
		//System.out.println(model.);
				//thisJoinPoint().getArgs();
	}
	
	pointcut logConfirmation() :
		execution(
				private void ca.ulaval.ticketmaster.dao.util.DataManager.sendConfirmationMail(User, UUID, List<Ticket>) );
	
	@SuppressWarnings("unchecked")
	Object around() : logConfirmation()
	{
		System.out.println("around");

		User user = (User)thisJoinPoint.getArgs()[0];
		UUID transactionId = (UUID)thisJoinPoint.getArgs()[1];
		ArrayList<Ticket> ticketList = (ArrayList<Ticket>)thisJoinPoint.getArgs()[2];

		LogBoughtTicketAspect.log("Envoi du courriel de confirmation à l'utilisateur '" + user.getUsername()
				+ "' avec transactionId = '" + transactionId 
				+ "', les billets :\n" + Arrays.toString(ticketList.toArray()));
		
		Object retval = proceed();
		
		return retval;
	}

}
