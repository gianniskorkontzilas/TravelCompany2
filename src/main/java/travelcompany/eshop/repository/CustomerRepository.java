package travelcompany.eshop.repository;

import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD repository implementation for Customer objects.
 */
public class CustomerRepository implements BaseRepository<Customer> {
    private final List<Customer> customerList = new ArrayList<>();
    private static int nextId = 1; // counter that keeps track of current id given

    /**
     * Get the collection of Customer objects this repository holds.
     * @return a list of objects of class Customer
     */
    @Override
    public List<Customer> getAll() {
        return customerList;
    }

    /**
     * Get an object of class Customer from this repository by id.
     * @throws InvalidCodeException
     * @param id The id of the Customer to be fetched
     * @return an object of class Customer
     */
    @Override
    public Customer get(int id) throws InvalidCodeException {
        for(Customer customer: customerList){
            if(customer.getId() == id){
                return customer;
            }
        }
        throw new InvalidCodeException("Couldn't find a matching id for customer.");
    }

    /**
     * Assign a list of objects of class Customer, as this repository's list.
     * @param customers a list of objects of class Customer
     */
    @Override
    public void addAll(List<Customer> customers) {
        for (Customer customer : customers) {
            add(customer);
        }
    }

    /**
     * Assign an object of class Customer to this repository's list.
     * @param customer an object of class Customer
     */
    @Override
    public void add(Customer customer) {
        customer.setId(nextId++);
        customerList.add(customer);
    }

    /**
     * Delete a specific Customer from this repository.
     * @param customer an object of class Customer
     */
    @Override
    public void delete(Customer customer) {
        customerList.remove(customer);
    }

    /**
     * Delete a Customer identified by a specific id, from this repository.
     * @throws InvalidCodeException
     * @param id the id of the Customer to be removed
     */
    @Override
    public void delete(int id) throws InvalidCodeException {
        Customer foundCustomer = get(id);
        if(foundCustomer != null) {
            customerList.remove(foundCustomer);
        }
    }
}
