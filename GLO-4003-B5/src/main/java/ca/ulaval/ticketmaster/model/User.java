/**
 * 
 * @author Ben
 *	La javadoc, la javadoc !
 */

package ca.ulaval.ticketmaster.model;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.ticketmaster.dao.util.Pair;
import ca.ulaval.ticketmaster.model.enums.TicketType;

public class User {

    public enum AccessLevel {
	Admin, User
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private AccessLevel accessLevel;
    private String favSport;
    private String favGender;
    private TicketType favType;
    private String favLocation;
    private List<Pair<Integer, Integer>> userTickets;

    public User(String _username) {
	username = _username;
	userTickets = new ArrayList<Pair<Integer, Integer>>();
	firstName = "";
	lastName = "";
	email = "";
	accessLevel = AccessLevel.User;
	favSport = "";
	favGender = "";
	favType = TicketType.GENERAL;
	favLocation = "";
    }

    public User(String _username, String _password, String _firstName, String _lastName, String _email,
	    AccessLevel _accessLevel, String _favSport, String _favGender, TicketType _favType,
	    String _favLocation) {
	username = _username;
	password = _password;
	firstName = _firstName;
	lastName = _lastName;
	email = _email;
	accessLevel = _accessLevel;
	favSport = _favSport;
	favGender = _favGender;
	favType = _favType;
	favLocation = _favLocation;
	userTickets = new ArrayList<Pair<Integer, Integer>>();
    }

    public String toString() {
	return "\n Username: " + username + " password: " + password + " " + "\nfirstName: " + firstName
		+ " lastName: " + lastName + " \nemail: " + email + " access level: " + accessLevel
		+ "\nFAVSCONCAT: " + favSport + favGender + favType.toString() + favLocation;

    }

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

    public List<Pair<Integer, Integer>> getUserTickets() {
	return userTickets;
    }

    public void setUserTickets(List<Pair<Integer, Integer>> userTickets) {
	this.userTickets = userTickets;
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

    //Semble pas utilisee...
    //@TODO enlever ? ou sinon au moins renommer
//    public void changeValuesFromUserObject(User _user) {
//	password = _user.getPassword();
//	firstName = _user.getFirstName();
//	lastName = _user.getLastName();
//	email = _user.getEmail();
//	accessLevel = _user.getAccessLevel();
//	favSport = _user.getFavSport();
//	favGender = _user.getFavGender();
//	favType = _user.getFavType();
//	favLocation = _user.getFavLocation();
//    }

}
