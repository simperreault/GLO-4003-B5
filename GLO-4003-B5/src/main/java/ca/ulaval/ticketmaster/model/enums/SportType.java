package ca.ulaval.ticketmaster.model.enums;

public enum SportType {
    Football("Football"), Basketball("Basketball"), Rugby("Rugby"), Soccer("Soccer"), Volleyball("Volleyball");

    private String displayName;

    SportType(String displayName) {
	this.displayName = displayName;
    }

    public String toString() {
	return displayName;
    }
}
