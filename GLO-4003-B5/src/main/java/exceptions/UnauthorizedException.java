package exceptions;

public class UnauthorizedException extends DAException{

	public UnauthorizedException(){
		super("Vous n'avez pas les autorisations nécessaires pour accéder à cette page");
	}

	public UnauthorizedException(String errorMsg){
		super(errorMsg);
	}

}
