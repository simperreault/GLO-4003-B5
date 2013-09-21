/**
 * 
 * @author Ben
 *	La javadoc, la javadoc !
 */

package ca.ulaval.billet.model;


import java.util.Map;
import java.util.HashMap;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String accessLevel;
	private Map<Integer,Integer> userTickets;
	
	public User (String _username) {
		username = _username;
		userTickets = new HashMap<Integer,Integer>();
	}
	
	public User (String _username,  String _password, String _firstName, String _lastName, String _email, String _accessLevel) {
		username = _username;
		password = _password;
		firstName = _firstName;
		lastName = _lastName;
		email = _email;
		accessLevel = _accessLevel;
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
		lastName = lastName;
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

	public Map<Integer,Integer> getUserTickets() {
		return userTickets;
	}

	public void setUserTickets(HashMap<Integer,Integer> userTickets) {
		this.userTickets = userTickets;
	}

	
	
}
