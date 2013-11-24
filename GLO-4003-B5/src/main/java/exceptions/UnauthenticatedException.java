package exceptions;

public class UnauthenticatedException extends DAException{

	public UnauthenticatedException(){
		super("Vous devez être authentifié pour accéder à cette page");
	}

	public UnauthenticatedException(String errorMsg){
		super(errorMsg);
	}

}
