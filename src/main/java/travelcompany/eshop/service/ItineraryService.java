package travelcompany.eshop.service;

import lombok.AllArgsConstructor;
import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Itinerary;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.InvalidCodeException;
import travelcompany.eshop.repository.BaseRepository;

import java.util.List;

/**
 * Responsible for managing Itinerary objects
 */
@AllArgsConstructor
public class ItineraryService implements BaseService<Itinerary> {
    private final BaseRepository<Itinerary> itineraryRepository;
    private final BaseService<AirportCode> airportCodeService;

    /**
     * Assign an object of class Itinerary to this service's repository.
     *
     * @param itinerary an object of class Itinerary
     */
    @Override
    public void add(Itinerary itinerary) throws BusinessException {
        AirportCode departureAirport = itinerary.getDepartureAirport();
        AirportCode destinationAirport = itinerary.getDestinationAirport();
        List<AirportCode> allAirportCodes = airportCodeService.getAll();
        if (allAirportCodes.contains(departureAirport) && allAirportCodes.contains(destinationAirport)) {
            itineraryRepository.add(itinerary);
        } else {
            throw new BusinessException("Airline code does not exist, not creating itinerary.");
        }
    }

    /**
     * Assign a list of objects of class Itinerary, as this service's repository.
     *
     * @param itineraries a list of objects of class Itinerary
     */
    @Override
    public void addAll(List<Itinerary> itineraries) throws BusinessException {
        for (Itinerary itinerary : itineraries) {
            add(itinerary);
        }
    }

    /**
     * Delete a specific itinerary from this service's repository.
     *
     * @param itinerary an object of class Itinerary
     */
    @Override
    public void delete(Itinerary itinerary) {
        itineraryRepository.delete(itinerary);
    }

    /**
     * Delete an itinerary identified by a specific id, from this service's repository.
     *
     * @param id the id of the itinerary to be removed
     * @throws InvalidCodeException
     */
    @Override
    public void delete(int id) throws BusinessException {
        itineraryRepository.delete(id);
    }

    /**
     * Get an object of class Itinerary from this service's repository by id.
     *
     * @param id The id of the itinerary to be fetched
     * @return an object of class Itinerary
     * @throws InvalidCodeException
     */
    @Override
    public Itinerary get(int id) throws BusinessException {
        return itineraryRepository.get(id);
    }

    /**
     * Get the collection of itineraries this service's repository holds.
     *
     * @return a list of objects of class TItinerary
     */
    @Override
    public List<Itinerary> getAll() {
        return itineraryRepository.getAll();
    }
}
