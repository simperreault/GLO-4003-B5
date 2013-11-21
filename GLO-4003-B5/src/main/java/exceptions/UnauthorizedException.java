package exceptions;

public class UnauthorizedException extends DAException{

	public UnauthorizedException(){
		super("Vous n'avez pas accès à cette page","Home");
	}

	public UnauthorizedException(String errorMsg, String errorPage){
		super(errorMsg, errorPage);
	}

}
