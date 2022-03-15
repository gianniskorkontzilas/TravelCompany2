package travelcompany.eshop.repository;

import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.InvalidCodeException;
import travelcompany.eshop.service.BaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD repository implementation for AirportCode objects.
 */
public class AirportCodeRepository implements BaseRepository<AirportCode> {
    private final List<AirportCode> airportCodesList = new ArrayList<>();

    /**
     * Assign an object of class AirportCode to this repository's list.
     * @param airportCode an object of class AirportCode
     */
    @Override
    public void add(AirportCode airportCode) {
        airportCodesList.add(airportCode);
    }

    /**
     * Assign a list of objects of class AirportCode, as this repository's list.
     * @param airportCodes a list of objects of class AirportCode
     */
    @Override
    public void addAll(List<AirportCode> airportCodes) {
        airportCodesList.addAll(airportCodes);
    }

    /**
     * Delete a specific AirportCode from this repository.
     * @param airportCode an object of class AirportCode
     */
    @Override
    public void delete(AirportCode airportCode) {
        throw new UnsupportedOperationException("Not yet supported!");
    }

    /**
     * Delete a AirportCode identified by a specific id, from this repository.
     * @throws InvalidCodeException
     * @param id the id of the AirportCode to be removed
     */
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not yet supported!");
    }

    /**
     * Get an object of class AirportCode from this repository by id.
     * @throws InvalidCodeException
     * @param id The id of the AirportCode to be fetched
     * @return an object of class AirportCode
     */
    @Override
    public AirportCode get(int id) throws InvalidCodeException {
        for(AirportCode airportCode: airportCodesList){
            if(airportCode.getId() == id){
                return airportCode;
            }
        }
        throw new InvalidCodeException("Couldn't find a matching id for airport code.");
    }

    /**
     * Get the collection of AirportCode objects this repository holds.
     * @return a list of objects of class AirportCode
     */
    @Override
    public List<AirportCode> getAll() {
        return airportCodesList;
    }
}
