package ca.ulaval.ticketmaster.model;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    /**
     * Finds a cargo using given id.
     * 
     * @param trackingId
     *            Id
     * @return Cargo if found, else {@code null}
     */
    Event find(UUID _eventID) {
	return null;

    }

    /**
     * Finds all cargo.
     * 
     * @return All cargo.
     */
    List<Event> findAll() {
	return null;

    }

    /**
     * Saves given cargo.
     * 
     * @param cargo
     *            cargo to save
     */
    void store(Event _event) {

    }

    /**
     * @return A unique, generated tracking Id.
     */
    UUID nextId() {
	return null;

    }

}
