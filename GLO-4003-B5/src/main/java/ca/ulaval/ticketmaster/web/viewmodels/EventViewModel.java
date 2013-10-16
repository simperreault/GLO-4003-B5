package ca.ulaval.ticketmaster.web.viewmodels;

import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckDate;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckGender;

public class EventViewModel {

    public UUID id;
    public SportType sport;
    @CheckGender
    public String gender;
    @NotEmpty(message = "'Équipe maison' ne doit pas etre vide.")
    public String homeTeam;
    @NotEmpty(message = "'Équipe visiteur' ne doit pas etre vide.")
    public String visitorsTeam;
    @NotEmpty(message = "'Endroit' ne doit pas etre vide.")
    public String location;
    @NotEmpty(message = "'Stade' ne doit pas etre vide.")
    public String stadium;
    @CheckDate(message = "'Date' doit avoir le format dd/mm/yyyy HH:mm.")
    @NotEmpty(message = "'Date' ne doit pas etre vide.")
    public String date;

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

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
