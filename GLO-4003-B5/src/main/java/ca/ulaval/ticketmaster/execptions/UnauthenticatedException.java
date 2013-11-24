package ca.ulaval.ticketmaster.execptions;

public class UnauthenticatedException extends DAException {

    private static final long serialVersionUID = -557385663710855116L;

    public UnauthenticatedException() {
	super("Vous devez �tre authentifi� pour acc�der � cette page");
    }

    public UnauthenticatedException(String errorMsg) {
	super(errorMsg);
    }

}
