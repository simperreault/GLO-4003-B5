package ca.ulaval.ticketmaster.model.enums;

public enum PaymentType {
    VISA("VISA"), MASTERCARD("MASTERCARD"), AMERICAN_EXPRESS("AMERICAN EXPRESS");

    private String displayName;

    PaymentType(String displayName) {
	this.displayName = displayName;
    }

    public String toString() {
	return displayName;
    }
}
