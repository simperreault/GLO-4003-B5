package ca.ulaval.ticketmaster.execptions;

public abstract class DAException extends Exception {
    private static final long serialVersionUID = 7580189856047217417L;

    private String errorMsg;

    public DAException() {
    }

    public DAException(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
	return errorMsg;
    }

}
