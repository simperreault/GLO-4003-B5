package exceptions;

public class UnauthorizedException extends DAException{

	public UnauthorizedException(){
		super("Vous n'avez pas acc�s � cette page","Home");
	}

	public UnauthorizedException(String errorMsg, String errorPage){
		super(errorMsg, errorPage);
	}

}
