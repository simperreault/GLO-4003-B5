package ca.ulaval.ticketmaster.web.viewmodels;

import ca.ulaval.ticketmaster.model.Ticket.ticketType;
import ca.ulaval.ticketmaster.model.User.AccessLevel;

public class UserViewModel {

	//TODO : msemble de quoi à faire avec le public + setters/getters = ...
	public String username;
	public String password;
	public String firstName;
	public String lastName;
	public String email;
	public AccessLevel accessLevel;
	public String favSport;
	public String favGender;
	public ticketType favType;
	
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
	public ticketType getFavType() {
		return favType;
	}
	public void setFavType(ticketType favType) {
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