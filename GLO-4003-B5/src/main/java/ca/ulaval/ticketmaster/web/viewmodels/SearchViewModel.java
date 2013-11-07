package ca.ulaval.ticketmaster.web.viewmodels;

import ca.ulaval.ticketmaster.model.enums.SportType;

public class SearchViewModel {

	public SportType sport;
	public String team;
	public int days;
	public String message;

	public SearchViewModel() {
		sport = null;
		team = null;
		days = 0;
		message = "";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SportType getSport() {
		return sport;
	}

	public void setSport(SportType sport) {
		this.sport = sport;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
