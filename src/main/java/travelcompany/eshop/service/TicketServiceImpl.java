package travelcompany.eshop.service;

import lombok.AllArgsConstructor;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.Itinerary;
import travelcompany.eshop.domain.Ticket;
import travelcompany.eshop.domain.enumer.PurchaseMethod;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.domain.exception.InvalidCodeException;
import travelcompany.eshop.repository.BaseRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class responsible for managing Ticket objects
 */
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{
    private final BaseService<Customer> customerService;
    private final BaseService<Itinerary> itineraryService;
    private final BaseRepository<Ticket> ticketRepository;
    private final PriceComputeService priceComputeService = new PriceComputeService();

    /**
     * Create a Ticket object and assign it to the corresponding Customer.
     *
     * @param customerCode the Customer object's unique id.
     * @param itineraryCode the itinerary object's unique id.
     * @param purchaseMethod the Ticket's payment method.
     */
    public void publishTicket(int customerCode, int itineraryCode, PurchaseMethod purchaseMethod) throws BusinessException {
        Customer customer;
        Itinerary itinerary;
        BigDecimal basePrice;
        try {
            customer = customerService.get(customerCode);
            itinerary = itineraryService.get(itineraryCode);
            basePrice = itinerary.getBasePrice();
        } catch (InvalidCodeException e) {
            System.out.println("Something went wrong during the publishing of a ticket: " + e.getMessage()); // logging
            throw new BusinessException(e.getMessage());
        }

        BigDecimal finalPrice = computeFinalPrice(customer, basePrice, purchaseMethod);
        add(new Ticket(customerCode, itineraryCode, purchaseMethod, finalPrice));
    }

    /**
     * Calculates the final price based on the base price and the various discounts that are applied
     *
     * @return the final discounted price of ticket
     */
    private BigDecimal computeFinalPrice(Customer customer, BigDecimal basePrice, PurchaseMethod purchaseMethod) {
        BigDecimal accumulatedExtraCharges = priceComputeService.getAccumulatedExtraCharges(customer, purchaseMethod);
        BigDecimal extraChargesToBeSubtracted = basePrice.multiply(accumulatedExtraCharges);
        return basePrice.subtract(extraChargesToBeSubtracted);
    }

    /**
     * Assign an object of class Ticket to this service's repository.
     * @param ticket an object of class Ticket
     */
    @Override
    public void add(Ticket ticket) {
        ticketRepository.add(ticket);
    }

    /**
     * Assign a list of objects of class Ticket, as this service's repository.
     * @param tickets a list of objects of class Ticket
     */
    @Override
    public void addAll(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            add(ticket);
        }
    }

    /**
     * Delete a specific ticket from this service's repository.
     * @param ticket an object of class Ticket
     */
    @Override
    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    /**
     * Delete a ticket identified by a specific id, from this service's repository.
     * @throws InvalidCodeException
     * @param id the id of the ticket to be removed
     */
    @Override
    public void delete(int id) throws BusinessException {
        ticketRepository.delete(id);
    }

    /**
     * Get an object of class Ticket from this service's repository by id.
     * @throws InvalidCodeException
     * @param id The id of the ticket to be fetched
     * @return an object of class Ticket
     */
    @Override
    public Ticket get(int id) throws BusinessException {
        return ticketRepository.get(id);
    }

    /**
     * Get the collection of tickets this service's repository holds.
     * @return a list of objects of class Ticket
     */
    @Override
    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }
}
