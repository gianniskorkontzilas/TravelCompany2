package travelcompany.eshop.factory;

import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.enumer.CustomerCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates customer objects from a set of lines
 */
public class CustomerFactory implements BaseFactory<Customer>{

    public List<Customer> parse(List<String> lines) {
        List<Customer> customers = new ArrayList<>();
        for (String line : lines) {
            final String[] values = line.split(",");
            String name = values[0];
            String email = values[1];
            String address = values[2];
            String nationality = values[3];
            CustomerCategory type = CustomerCategory.valueOf(values[4].toUpperCase());
            Customer customer = new Customer(name, email, address, nationality, type);
            customers.add(customer);
        }
        return customers;
    }
}
