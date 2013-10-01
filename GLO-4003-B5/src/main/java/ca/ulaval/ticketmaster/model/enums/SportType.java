package ca.ulaval.ticketmaster.model.enums;

public enum SportType {
    FOOTBALL("Football"), BASKETBALL("Basketball"), RUGBY("Rugby"), SOCCER("Soccer"), VOLLEYBALL("Volleyball");

    private String displayName;

    SportType(String displayName) {
	this.displayName = displayName;
    }

    public String toString() {
	return displayName;
    }
}
