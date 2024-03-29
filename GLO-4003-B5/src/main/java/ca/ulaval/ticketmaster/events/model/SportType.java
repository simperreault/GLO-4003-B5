package ca.ulaval.ticketmaster.events.model;

public enum SportType {
	FOOTBALL("Football"), BASKETBALL("Basketball"), RUGBY("Rugby"), SOCCER("Soccer"), VOLLEYBALL("Volleyball");

	private String displayName;

	SportType(String displayName) {
		this.displayName = displayName;
	}

	public String toString() {
		return displayName;
	}

	public static SportType matchString(String sport)
	{
		for (SportType s : SportType.values()) {
			if (sport.equals(s.toString().toUpperCase()))
				return s;
		}
		return null;
	}
}
