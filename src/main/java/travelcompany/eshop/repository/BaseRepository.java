package travelcompany.eshop.repository;

import travelcompany.eshop.domain.exception.InvalidCodeException;

import java.util.List;

/**
 * CRUD repository generic interface.
 */
public interface BaseRepository<T>{
    /**
     * Get the collection of objects this repository holds.
     * @return a list of objects of class T
     */
    List<T> getAll();

    /**
     * Get an object of class T from this repository by id.
     * @throws InvalidCodeException
     * @param id The id of the object to be fetched
     * @return an object of class T
     */
    T get(int id) throws InvalidCodeException;

    /**
     * Assign a list of objects of class T, as this repository's list.
     * @param t a list of objects of class T
     */
    void addAll(List<T> t);

    /**
     * Assign an object of class T to this repository's list.
     * @param t an object of class T
     */
    void add(T t);
    /**
     * Delete a specific object from this repository.
     * @param t an object of class T
     */
    void delete(T t);
    /**
     * Delete an object identified by a specific id, from this repository.
     * @throws InvalidCodeException
     * @param id the id of the object to be removed
     */
    void delete(int id) throws InvalidCodeException;
}
