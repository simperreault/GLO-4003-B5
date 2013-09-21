package ca.ulaval.billet.dataUtil;

import ca.ulaval.billet.model.Event;
import ca.ulaval.billet.model.Ticket;

public class DataManager {

	private XmlWriter xmlWriter;
	private XmlReader xmlReader;
	
	private int nextEventId;
	

	public DataManager(){
		LoadStartupInformation();
	}
	
	private void LoadStartupInformation(){
		// Loader les id d'event pour ne pas écraser un event existant dans le cas de la création d'un nouvel event
		//nextEventId = dernier event dans le fichier + 1;
	}
	
	public int getNextEventId() {
		return nextEventId;
	}
	
	public void incrementEventId(){
		nextEventId++;
	}
	
	public boolean saveEvent(Event _event){
		return false;
	}
	
	public boolean saveTicket(Ticket _ticket){
		
		return false;
	}
	
	public boolean loadAllEvents(){
		return false;
	}
	
	public boolean loadAllTickets(int _eventId){
		return false;
	}
	
	public boolean loadUser(){
		return false;
	}
	
	public boolean saveUser(){
		return false;
	}
}
