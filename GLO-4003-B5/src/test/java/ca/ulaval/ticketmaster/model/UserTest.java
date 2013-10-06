package ca.ulaval.ticketmaster.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.hamcrest.CoreMatchers;

import ca.ulaval.ticketmaster.dao.util.Pair;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
	
	//wtf tester les enums... (compte dans le % de coverage)
	@Test
	public void testEnumAccessLevel() {
		
		org.junit.Assert.assertThat(AccessLevel.valueOf("User"), 
				org.hamcrest.CoreMatchers.is(org.hamcrest.CoreMatchers.notNullValue()));
		
		org.junit.Assert.assertThat(AccessLevel.valueOf("Admin"), 
				org.hamcrest.CoreMatchers.is(org.hamcrest.CoreMatchers.notNullValue()));
		
		//mph ci-bas pas capable; il em faut le nom des packages :/
		//assertThat(AccessLevel.valueOf("User"), is(notNullValue()));
	}
	
	@Test
	public void testConstructorSimple() {
		String strUsername = "bob";
		
		User bob = new User(strUsername);
		
		assertEquals(bob.getUsername(), strUsername);
		
		assertNotNull(bob.getUserTickets());
		assertTrue(bob.getUserTickets().isEmpty());

		assertEquals(bob.getFirstName(), "");
		assertEquals(bob.getLastName(), "");
		assertEquals(bob.getEmail(), "");
		assertEquals(bob.getAccessLevel(), AccessLevel.User);
		assertEquals(bob.getFavSport(), "");
		assertEquals(bob.getFavGender(), "");
		assertEquals(bob.getFavType(), TicketType.GENERAL);
		assertEquals(bob.getFavLocation(), "");
	}
	
	@Test
	public void testConstructorComplique() {
		String strUsername = "bob";
	    String password = "secret";
	    String firstName = "Robert";
	    String lastName = "Gagnon";
	    String email = "Robert.Gagnon@ulaval.ca";
	    AccessLevel accessLevel = AccessLevel.User;
	    String favSport = "Soccer";
	    String favGender = "Feminin";
	    TicketType favType = TicketType.GENERAL;
	    String favLocation = "Plaines";
		
		User bob = new User(strUsername, password, firstName, lastName, email,
				accessLevel, favSport, favGender, favType, favLocation );
		
		assertEquals(bob.getUsername(), strUsername);
		
		assertNotNull(bob.getUserTickets());
		assertTrue(bob.getUserTickets().isEmpty());

		assertEquals(bob.getFirstName(), firstName);
		assertEquals(bob.getLastName(), lastName);
		assertEquals(bob.getEmail(), email);
		assertEquals(bob.getAccessLevel(), accessLevel);
		assertEquals(bob.getFavSport(), favSport);
		assertEquals(bob.getFavGender(), favGender);
		assertEquals(bob.getFavType(), favType);
		assertEquals(bob.getFavLocation(), favLocation);
	}
	
	@Test
	public void testGetSetUsername() {
		String strUsername = "bob";
		
		User bob = new User(strUsername);
		
		assertEquals(bob.getUsername(), strUsername);
		
		String strUsername2 = "ben";
		
		bob.setUsername(strUsername2);
		
		assertEquals(bob.getUsername(), strUsername2);
	}
	
	@Test
	public void testGetSetPassword() {
		String strPassword = "secret";
		
		User bob = new User("bob");
		
		bob.setPassword(strPassword);

		assertEquals(bob.getPassword(), strPassword);
		
	}
	
	@Test
	public void testGetSetFirstName() {
		String strFirstName = "Robert";
		
		User bob = new User("bob");
		
		bob.setFirstName(strFirstName);

		assertEquals(bob.getFirstName(), strFirstName);
		
	}
	
	@Test
	public void testGetSetLastName() {
		String strLastName = "Gagnon";
		
		User bob = new User("bob");
		
		bob.setLastName(strLastName);

		assertEquals(bob.getLastName(), strLastName);
		
	}
	
	@Test
	public void testGetSetEmail() {
		String strEmail = "a@b.c";
		
		User bob = new User("bob");
		
		bob.setEmail(strEmail);

		assertEquals(bob.getEmail(), strEmail);
		
	}
	
	@Test
	public void testGetSetUserTickets() {
		List<Pair<Integer, Integer>> ticketList = new ArrayList<Pair<Integer, Integer>>();
		
		User bob = new User("bob");
		
		bob.setUserTickets(ticketList);

		assertEquals(bob.getUserTickets(), ticketList);
		
	}
	
	@Test
	public void testGetSetAccessLevel() {
		AccessLevel accessLevel = AccessLevel.Admin;
		
		User bob = new User("bob");
		
		bob.setAccessLevel(accessLevel);

		assertEquals(bob.getAccessLevel(), accessLevel);
		
	}
	
	@Test
	public void testGetSetFavSport() {
		String favSport = "Soccer";
		
		User bob = new User("bob");
		
		bob.setFavSport(favSport);

		assertEquals(bob.getFavSport(), favSport);
		
	}
	
	@Test
	public void testGetSetFavGender() {
		String favGender = "Feminin";
		
		User bob = new User("bob");
		
		bob.setFavGender(favGender);

		assertEquals(bob.getFavGender(), favGender);
		
	}
	
	@Test
	public void testGetSetFavType() {
		TicketType favType = TicketType.SEASON;
		
		User bob = new User("bob");
		
		bob.setFavType(favType);

		assertEquals(bob.getFavType(), favType);
		
	}
	
	@Test
	public void testGetSetFavLocation() {
		String favLocation = "Les Plaines";
		
		User bob = new User("bob");
		
		bob.setFavLocation(favLocation);

		assertEquals(bob.getFavLocation(), favLocation);
		
	}
	
	@Test
	public void testToString() {

		String strUsername = "bob";
	    String password = "secret";
	    String firstName = "Robert";
	    String lastName = "Gagnon";
	    String email = "Robert.Gagnon@ulaval.ca";
	    AccessLevel accessLevel = AccessLevel.User;
	    String favSport = "Soccer";
	    String favGender = "Feminin";
	    TicketType favType = TicketType.GENERAL;
	    String favLocation = "Plaines";
		
		User bob = new User(strUsername, password, firstName, lastName, email,
				accessLevel, favSport, favGender, favType, favLocation );
		
		String bobToString = bob.toString();

		assertTrue(bobToString.contains(strUsername));
		assertTrue(bobToString.contains(password));
		assertTrue(bobToString.contains(firstName));
		assertTrue(bobToString.contains(lastName));
		assertTrue(bobToString.contains(email));
		assertTrue(bobToString.contains(AccessLevel.User.toString()));
		assertTrue(bobToString.contains(favSport));
		assertTrue(bobToString.contains(favGender));
		assertTrue(bobToString.contains(favType.toString()));
		assertTrue(bobToString.contains(favLocation));

	}
	
}
