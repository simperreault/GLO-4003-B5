package ca.ulaval.ticketmaster.exceptions;

public class InvalidFormExceptions extends DAException {

    private static final long serialVersionUID = 8686160430017327468L;

    public InvalidFormExceptions() {
	super("Il existe des erreurs sur la page");
    }

    public InvalidFormExceptions(String errorMsg) {
	super(errorMsg);
    }

}
