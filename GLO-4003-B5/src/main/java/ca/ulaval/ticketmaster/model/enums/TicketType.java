package ca.ulaval.ticketmaster.model.enums;

public enum TicketType {
    SIMPLE("Simple"), GENERAL("Admission Générale"), SEASON("Saison"), RESERVED("Réservé");

    private String displayName;

    TicketType(String displayName) {
	this.displayName = displayName;
    }

    public String toString() {
	return displayName;
    }
}