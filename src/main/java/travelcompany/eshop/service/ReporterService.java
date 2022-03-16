package travelcompany.eshop.service;

import lombok.AllArgsConstructor;
import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.Itinerary;
import travelcompany.eshop.domain.Ticket;
import travelcompany.eshop.transfer.CustomerForReportingDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generates various reports for the system based on the tickets purchased
 * For the following reports, any return type could be valid, for example, the developer could choose to return
 * a list of strings, a list of objects or a DTO. All these ideas have been implemented one way or another
 */
@AllArgsConstructor
public class ReporterService {
    private TicketService ticketService;
    private CustomerService customerService;
    private ItineraryService itineraryService;
    private AirportCodeService airportCodeService;

    /**
     *  List of the total number and list of the cost of tickets for all customers
     * @return
     */
    public List<CustomerForReportingDTO> totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer(){
        List<CustomerForReportingDTO> customerPerLine = new ArrayList<>();
        List<Ticket> allTickets = ticketService.getAll();
        Map<Integer, Long> totalNumberOfTicketsPerCustomer = allTickets.stream()
                .collect(Collectors.groupingBy(Ticket::getCustomerCode, Collectors.counting()));
        for (Customer customer : customerService.getAll()) {
            BigDecimal totalCostForCustomer = allTickets.stream()
                    .filter(ticket -> ticket.getCustomerCode() == customer.getId())
                    .map(Ticket::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            customerPerLine.add(new CustomerForReportingDTO(totalNumberOfTicketsPerCustomer.get(customer.getId()), totalCostForCustomer, customer));
        }
        return customerPerLine;
    }

    /**
     * List of the total offered itineraries per destination and offered itineraries per departure
     * @return
     */
    public StringBuilder totalOfferedItinerariesPerDestinationAndTotalOfferedItinerariesPerDeparture(){
        List<Itinerary> allItineraries = itineraryService.getAll();
        StringBuilder result = new StringBuilder();
        result.append("\n-------------------");
        result.append("\nPER DEPARTURE");
        result.append("\n-------------------");
        for (AirportCode airportCode : airportCodeService.getAll()) {
            List<Itinerary> perDepartureList = allItineraries.stream()
                    .filter(itinerary -> itinerary.getDepartureAirport().equals(airportCode))
                    .collect(Collectors.toList());
            result.append("\n").append("For: ").append(airportCode.getAirportCode());
            if(perDepartureList.isEmpty()){
                result.append("\n").append("NONE");
            }
            else {
                for (Itinerary itinerary : perDepartureList) {
                    result.append("\n\t").append(itinerary);
                }
            }
        }
        result.append("\n-------------------");
        result.append("\nPER DESTINATION");
        result.append("\n-------------------");
        for (AirportCode airportCode : airportCodeService.getAll()) {
            List<Itinerary> perDepartureList = allItineraries.stream()
                    .filter(itinerary -> itinerary.getDestinationAirport().equals(airportCode))
                    .collect(Collectors.toList());
            result.append("\n").append(airportCode.getAirportCode());
            if(perDepartureList.isEmpty()){
                result.append("\n\t").append("NONE");
            }
            else {
                for (Itinerary itinerary : perDepartureList) {
                    result.append("\n\t").append(itinerary);
                }
            }
        }
        return result;
    }

    /**
     * List of the customers with the most tickets and with the largest cost of purchases
     * @return
     */
    public StringBuilder customersWithMostTicketAndLargestCostPurchased(){
        List<CustomerForReportingDTO> perCustomer = totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer();
        StringBuilder result = new StringBuilder();
        result.append("-------------------");
        result.append("\nMOST TICKETS");
        result.append("\n-------------------");
        perCustomer.stream()
                // the filter is required as the sorting throws null pointer exception
                .filter(customerForReportingDTO -> customerForReportingDTO.getTotalTickets() != null)
                .sorted((dto1, dto2) -> (int) (dto2.getTotalTickets() - dto1.getTotalTickets()))
                .forEach(dto -> result.append("\n\t").append(dto));
        result.append("\n-------------------");
        result.append("\nLARGEST COST");
        result.append("\n-------------------");
        perCustomer.stream()
                // the filter is required as the sorting throws null pointer exception
                .filter(customerForReportingDTO -> customerForReportingDTO.getTotalTickets() != null)
                .sorted(Comparator.comparing(CustomerForReportingDTO::getTotalCost).reversed())
                .forEach(dto -> result.append("\n\t").append(dto));

        return result;
    }

    /**
     *  List of the customers who have not purchased any tickets
     * @return
     */
    public ArrayList<Customer> customersWithNoTicketsPurchased(){
        List<Ticket> allTickets = ticketService.getAll();
        ArrayList<Customer> customersWithNoTickets = new ArrayList<>();
        for (Customer customer : customerService.getAll()) {
            // the same implementation as the one used in totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer() could
            // be used here, as the Collectors.counting() method will also return nulls
            // However, let's see another implementation here...
            Optional<Ticket> customerOptional = allTickets.stream().filter(ticket -> ticket.getCustomerCode() == customer.getId()).findAny();
            if(customerOptional.isEmpty()){
                customersWithNoTickets.add(customer);
            }
        }
        return customersWithNoTickets;
    }
}
