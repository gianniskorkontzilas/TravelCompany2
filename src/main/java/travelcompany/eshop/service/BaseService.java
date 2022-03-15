package travelcompany.eshop.service;

import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.InvalidCodeException;

import java.util.List;

/**
 * This interface declares all basic functionality for a given domain object
 *
 * @param <T>  The domain object whose functionality is declared below
 */
public interface BaseService<T> {
    /**
     * Assign an object of class T to this service's repository.
     * @param t an object of class T
     */
    void add(T t) throws BusinessException;

    /**
     * Assign a list of objects of class T, as this service's repository.
     * @param t a list of objects of class T
     */
    void addAll(List<T> t) throws BusinessException;

    /**
     * Delete a specific object from this service's repository.
     * @param t an object of class T
     */
    void delete(T t);

    /**
     * Delete an object identified by a specific id, from this service's repository.
     * @throws InvalidCodeException
     * @param id the id of the object to be removed
     */
    void delete(int id) throws BusinessException;

    /**
     * Get an object of class T from this service's repository by id.
     * @throws InvalidCodeException
     * @param id The id of the object to be fetched
     * @return an object of class T
     */
    T get(int id) throws BusinessException;

    /**
     * Get the collection of objects this service's repository holds.
     * @return a list of objects of class T
     */
    List<T> getAll();
}
