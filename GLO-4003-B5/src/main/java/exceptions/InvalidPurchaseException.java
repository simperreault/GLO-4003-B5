package exceptions;

public class InvalidPurchaseException extends DAException{

	public InvalidPurchaseException(){
		super("Un ou plusieurs billets sont invalides","Purchase");
	}

	public InvalidPurchaseException(String errorMsg, String errorPage){
		super(errorMsg, errorPage);
	}

}
