package exceptions;

public class UnauthorizedException extends DAException{

	public UnauthorizedException(){
		super("Vous n'avez pas les autorisations n�cessaires pour acc�der � cette page");
	}

	public UnauthorizedException(String errorMsg){
		super(errorMsg);
	}

}
