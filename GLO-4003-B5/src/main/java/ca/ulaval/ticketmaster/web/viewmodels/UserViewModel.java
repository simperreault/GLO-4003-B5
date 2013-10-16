package ca.ulaval.ticketmaster.web.viewmodels;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.User.AccessLevel;
import ca.ulaval.ticketmaster.model.enums.TicketType;

public class UserViewModel {

    // TODO : msemble de quoi a faire avec le public + setters/getters = ...
    @NotEmpty(message = "Le nom d'usager doit etre present")
    // @CheckUserNotPresent
    public String username;
    @NotEmpty(message = "Un mot de passe doit etre present")
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public AccessLevel accessLevel;
    public String favSport;
    public String favGender;
    public TicketType favType;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public AccessLevel getAccessLevel() {
	return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
	this.accessLevel = accessLevel;
    }

    public String getFavSport() {
	return favSport;
    }

    public void setFavSport(String favSport) {
	this.favSport = favSport;
    }

    public String getFavGender() {
	return favGender;
    }

    public void setFavGender(String favGender) {
	this.favGender = favGender;
    }

    public TicketType getFavType() {
	return favType;
    }

    public void setFavType(TicketType favType) {
	this.favType = favType;
    }

    public String getFavLocation() {
	return favLocation;
    }

    public void setFavLocation(String favLocation) {
	this.favLocation = favLocation;
    }

    public String favLocation;

}