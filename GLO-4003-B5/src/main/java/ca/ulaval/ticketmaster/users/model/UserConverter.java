package ca.ulaval.ticketmaster.users.model;

import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

public class UserConverter {

    static public User convert(UserViewModel viewmodel) {
	User entry = new User(viewmodel.username, viewmodel.password, viewmodel.firstName,
		viewmodel.lastName, viewmodel.email, AccessLevel.User, viewmodel.favSport,
		viewmodel.favGender, viewmodel.favType, viewmodel.favLocation);

	return entry;
    }

    static public UserViewModel convert(User entry) {
	UserViewModel viewmodel = new UserViewModel();

	viewmodel.setUsername(entry.getUsername());
	viewmodel.setPassword(entry.getPassword());
	viewmodel.setFirstName(entry.getFirstName());
	viewmodel.setLastName(entry.getLastName());
	viewmodel.setEmail(entry.getEmail());
	viewmodel.setAccessLevel(entry.getAccessLevel());
	viewmodel.setFavSport(entry.getFavSport());
	viewmodel.setFavGender(entry.getFavGender());
	viewmodel.setFavType(entry.getFavType());
	viewmodel.setFavLocation(entry.getFavLocation());

	return viewmodel;
    }

}
