/**
 * 
 * @author Ben
 *	La javadoc, la javadoc !
 */

package ca.ulaval.billet.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import ca.ulaval.billet.dataUtil.Pair;
import ca.ulaval.billet.model.Ticket.ticketType;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String accessLevel;
	private String favSport;
	private String favGender;
	private ticketType favType;
	private String favLocation;
	private List<Pair<Integer,Integer>> userTickets;
	
	public User (String _username) {
		username = _username;
		userTickets = new ArrayList<Pair<Integer,Integer>>();
	}
	
	public User (String _username,  String _password, String _firstName, String _lastName, String _email, String _accessLevel, String _favSport, String _favGender, ticketType _favType, String _favLocation ) {
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
		userTickets = new ArrayList<Pair<Integer,Integer>>();
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
	
	public String getAccessLevel() {
		return accessLevel;
	}
	
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public List<Pair<Integer,Integer>> getUserTickets() {
		return userTickets;
	}

	public void setUserTickets(List<Pair<Integer,Integer>> userTickets) {
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

	
	
}
