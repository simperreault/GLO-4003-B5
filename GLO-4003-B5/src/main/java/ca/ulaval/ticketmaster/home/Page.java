package ca.ulaval.ticketmaster.home;

public enum Page {
    // TODO coder une page erreur 403
    Home("Home"), Error403("ErrorUnauthorized"), Error404("ErrorNotFound"), CreateUser("UserAdd"), UserEdit(
	    "UserEdit"), UserIndex("UserIndex"), EventList("EventList"), TicketList("TicketList"), Detail(
	    "TicketDetail"), EventAdd("EventAdd"), TicketAdd("TicketAdd"), Basket("Basket"), Purchase(
	    "Purchase"), Confirmation("Confirmation"), Contact("Contact"), About("About");
    private final String name;

    Page(String str) {
	name = str;
    }

    @Override
    public String toString() {
	return name;
    }

}
