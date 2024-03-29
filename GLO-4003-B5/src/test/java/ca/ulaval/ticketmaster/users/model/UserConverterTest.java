package ca.ulaval.ticketmaster.users.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.UserConverter;
import ca.ulaval.ticketmaster.users.model.UserViewModel;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

//Note : extends UserConverter afin d'avoir la classe comme "verifiee" par EclEmma
public class UserConverterTest extends UserConverter {

    @Test
    public void convertEntryToviewmodel() {
	User user = new User("Roger");
	user.setPassword("123");
	user.setFirstName("Roger");
	user.setLastName("That");
	user.setAccessLevel(AccessLevel.User);
	user.setEmail("abc@def.com");
	user.setFavGender("favGenre");
	user.setFavLocation("MaLocation");
	user.setFavSport("LeSoccer");
	user.setFavType(TicketType.GENERAL);

	UserViewModel viewmodel = UserConverter.convert(user);

	assertEquals(user.getUsername(), viewmodel.getUsername());
	assertEquals(user.getPassword(), viewmodel.getPassword());
	assertEquals(user.getEmail(), viewmodel.getEmail());
	assertEquals(user.getFirstName(), viewmodel.getFirstName());
	assertEquals(user.getLastName(), viewmodel.getLastName());
	assertEquals(user.getAccessLevel(), viewmodel.getAccessLevel());
	assertEquals(user.getFavGender(), viewmodel.getFavGender());
	assertEquals(user.getFavLocation(), viewmodel.getFavLocation());
	assertEquals(user.getFavSport(), viewmodel.getFavSport());
	assertEquals(user.getFavType(), viewmodel.getFavType());
    }

    @Test
    public void convertViewmodelToEntry() {
	UserViewModel viewmodel = new UserViewModel();

	viewmodel.setUsername("Roger");
	viewmodel.setPassword("123");
	viewmodel.setFirstName("Roger");
	viewmodel.setLastName("That");
	viewmodel.setAccessLevel(AccessLevel.User);
	viewmodel.setEmail("abc@def.com");
	viewmodel.setFavGender("favGenre");
	viewmodel.setFavLocation("MaLocation");
	viewmodel.setFavSport("LeSoccer");
	viewmodel.setFavType(TicketType.GENERAL);

	User user = UserConverter.convert(viewmodel);

	assertEquals(user.getUsername(), viewmodel.getUsername());
	assertEquals(user.getPassword(), viewmodel.getPassword());
	assertEquals(user.getEmail(), viewmodel.getEmail());
	assertEquals(user.getFirstName(), viewmodel.getFirstName());
	assertEquals(user.getLastName(), viewmodel.getLastName());
	assertEquals(user.getAccessLevel(), viewmodel.getAccessLevel());
	assertEquals(user.getFavGender(), viewmodel.getFavGender());
	assertEquals(user.getFavLocation(), viewmodel.getFavLocation());
	assertEquals(user.getFavSport(), viewmodel.getFavSport());
	assertEquals(user.getFavType(), viewmodel.getFavType());
    }
}
