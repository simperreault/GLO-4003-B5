package ca.ulaval.ticketmaster.web.DomaineAffaire;

public enum Page {
    // TODO coder une page erreur 403
    Home("Home"), Error403("Home"), CreateUser("CreateUser"), EventList("EventList"), TicketList("TicketList"), Detail(
	    "detail"), EventAdd("EventAdd"), TicketAdd("TicketAdd"), Basket("Basket");
    private final String name;

    Page(String str) {
	name = str;
    }

    @Override
    public String toString() {
	return name;
    }

}
