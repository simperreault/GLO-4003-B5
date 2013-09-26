package ca.ulaval.ticketmaster.web.viewmodels;

import java.util.Date;

public class EventViewModel {
	
	public enum Sport {
		Football, Basketball, Rugby, Soccer, Volleyball
	}

	//public Sport sport;
	public String sport;
	public String gender;
	public String homeTeam;
	public String visitorsTeam;
	public String location;
	public String stadium;
	public Date date;
	public Date time;
	//public List<Ticket> ticketList;
	//public List<String> sectionList;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getVisitorsTeam() {
		return visitorsTeam;
	}
	public void setVisitorsTeam(String visitorsTeam) {
		this.visitorsTeam = visitorsTeam;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStadium() {
		return stadium;
	}
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	
	
}
