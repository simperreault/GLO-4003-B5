package ca.ulaval.ticketmaster.execptions;

public class InvalidPurchaseException extends DAException {

    private static final long serialVersionUID = -1857326237352134851L;

    public InvalidPurchaseException() {
	super("Un ou plusieurs billets sont invalides");
    }

    public InvalidPurchaseException(String errorMsg) {
	super(errorMsg);
    }

}
