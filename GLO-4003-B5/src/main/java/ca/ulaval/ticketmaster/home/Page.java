package ca.ulaval.ticketmaster.home;

public enum Page {
    // TODO coder une page erreur 403
    Home("Home"), Error403("ErrorUnauthorized"), Error404("ErrorNotFound"), CreateUser("CreateUser"), EventList(
	    "EventList"), TicketList("TicketList"), Detail("TicketDetail"), EventAdd("EventAdd"), TicketAdd(
	    "TicketAdd"), Basket("Basket"), Purchase("Purchase"), Confirmation("Confirmation");
    private final String name;

    Page(String str) {
	name = str;
    }

    @Override
    public String toString() {
	return name;
    }

}
