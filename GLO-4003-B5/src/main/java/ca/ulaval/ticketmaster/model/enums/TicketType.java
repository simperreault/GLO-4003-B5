package ca.ulaval.ticketmaster.model.enums;

public enum TicketType {
	Simple("Simple"),
	AdmissionGenerale("Admission Generale"),
	Saison("Saison"),
	Reserve("Reserve");
	
	private String displayName;
	
	TicketType(String displayName) {
		this.displayName = displayName;
	}
	
	public String toString() {
		return displayName; 
	}
}