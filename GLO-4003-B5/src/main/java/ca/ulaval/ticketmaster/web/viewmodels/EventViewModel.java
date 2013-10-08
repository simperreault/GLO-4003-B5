package ca.ulaval.ticketmaster.web.viewmodels;

import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckDate;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckGender;

public class EventViewModel {

    public SportType sport;
    @CheckGender
    public String gender;
    public String homeTeam;
    public String visitorsTeam;
    public String location;
    public String stadium;
    @CheckDate
    public String date;

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

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public SportType getSport() {
	return sport;
    }

    public void setSport(SportType sport) {
	this.sport = sport;
    }

}
