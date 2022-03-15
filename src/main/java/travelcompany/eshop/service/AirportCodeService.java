package travelcompany.eshop.service;

import lombok.AllArgsConstructor;
import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.InvalidCodeException;
import travelcompany.eshop.repository.BaseRepository;

import java.util.List;

/**
 * Responsible for managing AirportCode objects
 */
@AllArgsConstructor
public class AirportCodeService implements BaseService<AirportCode> {
    private final BaseRepository<AirportCode> airportCodeRepository;

    /**
     * Assign an object of class AirportCode to this service's repository.
     * @param airportCode an object of class AirportCode
     */
    @Override
    public void add(AirportCode airportCode) {
        airportCodeRepository.add(airportCode);
    }

    /**
     * Assign a list of objects of class AirportCode, as this service's repository.
     * @param airportCodes a list of objects of class AirportCode
     */
    @Override
    public void addAll(List<AirportCode> airportCodes) {
        for (AirportCode airportCode : airportCodes) {
            add(airportCode);
        }
    }

    /**
     * Delete a specific AirportCode from this service's repository.
     * @param airportCode an object of class AirportCode
     */
    @Override
    public void delete(AirportCode airportCode) {
        airportCodeRepository.delete(airportCode);
    }

    /**
     * Delete a AirportCode identified by a specific id, from this service's repository.
     * @throws InvalidCodeException
     * @param id the id of the AirportCode to be removed
     */
    @Override
    public void delete(int id) throws BusinessException {
        airportCodeRepository.delete(id);
    }

    /**
     * Get an object of class AirportCode from this service's repository by id.
     * @throws InvalidCodeException
     * @param id The id of the AirportCode to be fetched
     * @return an object of class AirportCode
     */
    @Override
    public AirportCode get(int id) throws BusinessException {
        return airportCodeRepository.get(id);
    }

    /**
     * Get the collection of AirportCode objects this service's repository holds.
     * @return a list of objects of class AirportCode
     */
    @Override
    public List<AirportCode> getAll() {
        return airportCodeRepository.getAll();
    }
}
