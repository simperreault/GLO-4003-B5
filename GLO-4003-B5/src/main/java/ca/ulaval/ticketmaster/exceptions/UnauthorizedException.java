package ca.ulaval.ticketmaster.exceptions;

public class UnauthorizedException extends DAException {

    private static final long serialVersionUID = -1370773613146187183L;

    public UnauthorizedException() {
	super("Vous n'avez pas les autorisations nécessaires pour accéder à cette page");
    }

    public UnauthorizedException(String errorMsg) {
	super(errorMsg);
    }

}
