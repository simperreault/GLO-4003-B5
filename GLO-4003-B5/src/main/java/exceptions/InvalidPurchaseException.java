package exceptions;

public class InvalidPurchaseException extends DAException{

	public InvalidPurchaseException(){
		super("Un ou plusieurs billets sont invalides");
	}

	public InvalidPurchaseException(String errorMsg){
		super(errorMsg);
	}

}
