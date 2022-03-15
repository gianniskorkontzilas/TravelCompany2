package travelcompany.eshop.service;

import lombok.AllArgsConstructor;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.EmailNotValidException;
import travelcompany.eshop.domain.exception.InvalidCodeException;
import travelcompany.eshop.repository.BaseRepository;

import java.util.List;

/**
 * Responsible for managing Customer objects
 */
@AllArgsConstructor
public class CustomerService implements BaseService<Customer> {
    private final BaseRepository<Customer> customerRepository;

    /**
     * Assign an object of class Customer to this service's repository.
     *
     * @param customer an object of class Customer
     */
    @Override
    public void add(Customer customer) throws BusinessException {
        String email = customer.getEmail();
        if (!email.endsWith("@travelcompany.com") || email.length() <= "@travelcompany.com".length()) {
            throw new EmailNotValidException("Customer's email does not end with '@travelcompany.com'");
        }
        customerRepository.add(customer);
    }

    /**
     * Assign a list of objects of class Customer, as this service's repository.
     *
     * @param customers a list of objects of class Customer
     */
    @Override
    public void addAll(List<Customer> customers) throws BusinessException {
        for (Customer customer : customers) {
            add(customer);
        }
    }

    /**
     * Delete a specific customer from this service's repository.
     *
     * @param customer an object of class Customer
     */
    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    /**
     * Delete a customer identified by a specific id, from this service's repository.
     *
     * @param id the id of the customer to be removed
     * @throws InvalidCodeException
     */
    @Override
    public void delete(int id) throws BusinessException {
        customerRepository.delete(id);
    }

    /**
     * Get an object of class Customer from this service's repository by id.
     *
     * @param id The id of the customer to be fetched
     * @return an object of class Customer
     * @throws InvalidCodeException
     */
    @Override
    public Customer get(int id) throws BusinessException {
        return customerRepository.get(id);
    }

    /**
     * Get the collection of objects this service's repository holds.
     *
     * @return a list of objects of class Customer
     */
    @Override
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }
}
