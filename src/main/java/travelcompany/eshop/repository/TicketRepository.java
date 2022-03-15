package travelcompany.eshop.repository;

import travelcompany.eshop.domain.Ticket;
import travelcompany.eshop.domain.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD repository implementation for Ticket objects.
 */
public class TicketRepository implements BaseRepository<Ticket> {
    private List<Ticket> ticketList = new ArrayList<>();
    private static int nextId = 1; // counter that keeps track of current id given

    /**
     * Get the collection of Ticket objects this repository holds.
     * @return a list of objects of class Ticket
     */
    @Override
    public List<Ticket> getAll() {
        return ticketList;
    }

    /**
     * Get an object of class Ticket from this repository by id.
     * @throws InvalidCodeException
     * @param id The id of the Ticket to be fetched
     * @return an object of class Ticket
     */
    @Override
    public Ticket get(int id) throws InvalidCodeException {
        for (Ticket ticket : ticketList) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        throw new InvalidCodeException("Couldn't find a matching id for ticket.");
    }

    /**
     * Assign a list of objects of class Ticket, as this repository's list.
     * @param tickets a list of objects of class Ticket
     */
    @Override
    public void addAll(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            add(ticket);
        }
    }

    /**
     * Assign an object of class Ticket to this repository's list.
     * @param ticket an object of class Ticket
     */
    @Override
    public void add(Ticket ticket) {
        ticket.setId(nextId++);
        ticketList.add(ticket);
    }

    /**
     * Delete a specific Ticket from this repository.
     * @param ticket an object of class Ticket
     */
    @Override
    public void delete(Ticket ticket) {
        ticketList.remove(ticket);
    }

    /**
     * Delete an Ticket identified by a specific id, from this repository.
     * @throws InvalidCodeException
     * @param id the id of the Ticket to be removed
     */
    @Override
    public void delete(int id) throws InvalidCodeException {
        Ticket foundTicket = get(id);
        if (foundTicket != null) {
            ticketList.remove(foundTicket);
        }
    }
}
