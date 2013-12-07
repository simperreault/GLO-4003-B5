package logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.purchase.model.PurchaseViewModel;

public aspect LogBoughtTicketAspect {

	//Purchase
	pointcut logPurchase() :
		execution( 
				//Ne peut pas utiliser .. pour l'argument en raison du GET
				public String ca.ulaval.ticketmaster.purchase.PurchaseController.purchase(PurchaseViewModel, Model, HttpSession,
					    BindingResult, HttpServletRequest) );
		//execution( * *(..) );
	
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

}
