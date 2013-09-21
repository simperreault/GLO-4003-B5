/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.billet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
	private int id;
	private boolean open;
	private int ticketsTotal;
	private int ticketsAvailable;
	private String sport;
	private String gender;
	private String homeTeam;
	private String visitorsTeam;
	private String location;
	private String stadium;
	private Date date;
	private Date time;
	private List<Ticket> ticketList;
	private List<String> sectionList;
	
	public Event(int _id){
		ticketList = new ArrayList<Ticket>();
		sectionList= new ArrayList<String>();
	}
	
	public Event(int _id, boolean _open, int _ticketsTotal, int _ticketsAvailable, String _sport, String _gender, String _homeTeam, String _visitorsTeam, String _location, String _stadium, Date _date, Date _time){
		id = _id;
		open = _open;
		ticketsTotal = _ticketsTotal;
		ticketsAvailable = _ticketsAvailable;
		sport = _sport;
		gender = _gender;
		homeTeam = _homeTeam;
		visitorsTeam = _visitorsTeam;
		location = _location;
		stadium = _stadium;
		date = _date;
		time = _time;
		ticketList = new ArrayList<Ticket>();
		sectionList= new ArrayList<String>();
	}

	public List<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public int getTicketsTotal() {
		return ticketsTotal;
	}
	public void setTicketsTotal(int ticketsTotal) {
		this.ticketsTotal = ticketsTotal;
	}
	public int getTicketsAvailable() {
		return ticketsAvailable;
	}
	public void setTicketsAvailable(int ticketsAvailable) {
		this.ticketsAvailable = ticketsAvailable;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getVisitorsTeam() {
		return visitorsTeam;
	}
	public void setVisitorsTeam(String visitorsTeam) {
		this.visitorsTeam = visitorsTeam;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStadium() {
		return stadium;
	}
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	public List<String> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<String> sectionList) {
		this.sectionList = sectionList;
	}
	
}
