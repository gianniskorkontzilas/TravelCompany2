package travelcompany.eshop.repository;

import travelcompany.eshop.domain.Itinerary;
import travelcompany.eshop.domain.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD repository implementation for Itinerary objects.
 */
public class ItineraryRepository implements BaseRepository<Itinerary> {
    private List<Itinerary> itineraryList = new ArrayList<>();
    private static int nextId = 1; // counter that keeps track of current id given

    /**
     * Get the collection of Itinerary objects this repository holds.
     * @return a list of objects of class Itinerary
     */
    @Override
    public List<Itinerary> getAll() {
        return itineraryList;
    }

    /**
     * Get an object of class Itinerary from this repository by id.
     * @throws InvalidCodeException
     * @param id The id of the Itinerary to be fetched
     * @return an object of class Itinerary
     */
    @Override
    public Itinerary get(int id) throws InvalidCodeException {
        for(Itinerary itinerary: itineraryList){
            if(itinerary.getId() == id){
                return itinerary;
            }
        }
        throw new InvalidCodeException("Couldn't find a matching id for itinerary.");
    }

    /**
     * Assign a list of objects of class Itinerary, as this repository's list.
     * @param itineraries a list of objects of class Itinerary
     */
    @Override
    public void addAll(List<Itinerary> itineraries) {
        for (Itinerary itinerary : itineraries) {
            add(itinerary);
        }
    }

    /**
     * Assign an object of class Itinerary to this repository's list.
     * @param itinerary an object of class Itinerary
     */
    @Override
    public void add(Itinerary itinerary) {
        itinerary.setId(nextId++);
        itineraryList.add(itinerary);
    }

    /**
     * Delete a specific Itinerary from this repository.
     * @param itinerary an object of class Itinerary
     */
    @Override
    public void delete(Itinerary itinerary) {
        itineraryList.remove(itinerary);
    }

    /**
     * Delete an Itinerary identified by a specific id, from this repository.
     * @throws InvalidCodeException
     * @param id the id of the Itinerary to be removed
     */
    @Override
    public void delete(int id) throws InvalidCodeException {
        Itinerary foundItinerary = get(id);
        if(foundItinerary != null) {
            itineraryList.remove(foundItinerary);
        }
    }
}
