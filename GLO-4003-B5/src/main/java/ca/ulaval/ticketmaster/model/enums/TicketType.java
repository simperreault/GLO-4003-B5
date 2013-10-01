package ca.ulaval.ticketmaster.model.enums;

public enum TicketType {
    SIMPLE("Simple"), GENERAL("Admission G�n�rale"), SEASON("Saison"), RESERVED("R�serv�");

    private String displayName;

    TicketType(String displayName) {
	this.displayName = displayName;
    }

    public String toString() {
	return displayName;
    }
}