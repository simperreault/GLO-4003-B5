package exceptions;

public class UnauthenticatedException extends DAException{

	public UnauthenticatedException(){
		super("Vous devez �tre authentifi� pour acc�der � cette page");
	}

	public UnauthenticatedException(String errorMsg){
		super(errorMsg);
	}

}
