package travelcompany.eshop.UI;

import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.Itinerary;
import travelcompany.eshop.domain.Ticket;
import travelcompany.eshop.domain.enumer.PurchaseMethod;
import travelcompany.eshop.domain.exception.BusinessException;
import travelcompany.eshop.factory.AirportCodeFactory;
import travelcompany.eshop.factory.CustomerFactory;
import travelcompany.eshop.factory.ItineraryFactory;
import travelcompany.eshop.repository.*;
import travelcompany.eshop.service.*;
import travelcompany.eshop.transfer.CustomerForReportingDTO;
import travelcompany.eshop.util.FileParser;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String LINE_SEPARATOR = "-------------------------";

    /**
     * Tests use-cases for travelcompany's eshop.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Loading collections from file to repositories
        // for customers
        List<String> customersAsLines = FileParser.load("customers.csv");
        CustomerFactory customerFactory = new CustomerFactory();
        List<Customer> customersFromFactory = customerFactory.parse(customersAsLines);
        BaseRepository<Customer> customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        try {
            customerService.addAll(customersFromFactory);
        } catch (BusinessException e) {
            System.out.println("Could not add customers from factory...");
            System.out.println("Message: " + e.getMessage());
            System.exit(1);
        }
        List<Customer> customerList = customerService.getAll();

        // for itineraries
        List<String> itinerariesAsLines = FileParser.load("itineraries.csv");
        ItineraryFactory itineraryFactory = new ItineraryFactory();
        List<Itinerary> itinerariesFromFactory = itineraryFactory.parse(itinerariesAsLines);
        BaseRepository<Itinerary> itineraryRepository = new ItineraryRepository();
        List<String> airportCodesAsLines = FileParser.load("airport_codes.csv");
        AirportCodeFactory airportCodeFactory = new AirportCodeFactory();
        List<AirportCode> airportCodesFromFactory = airportCodeFactory.parse(airportCodesAsLines);
        BaseRepository<AirportCode> airportCodeRepository = new AirportCodeRepository();
        AirportCodeService airportCodeService = new AirportCodeService(airportCodeRepository);
        airportCodeService.addAll(airportCodesFromFactory);
        ItineraryService itineraryService = new ItineraryService(itineraryRepository, airportCodeService);
        try {
            itineraryService.addAll(itinerariesFromFactory);
        } catch (BusinessException e) {
            System.out.println("Could not add itineraries from factory...");
            System.out.println("Message: " + e.getMessage());
            System.exit(1);
        }
        List<Itinerary> itineraryList = itineraryService.getAll();

        //Check if the repositories loaded correctly
        System.out.println("Customers:");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }

        System.out.println(LINE_SEPARATOR);

        System.out.println("Itineraries:");
        for (Itinerary itinerary : itineraryList) {
            System.out.println(itinerary);
        }

        System.out.println(LINE_SEPARATOR);

        // Instantiation of ticket repository & service, contains no tickets and simply holds them in memory.
        // Will be deleted when application stops or is restarted
        BaseRepository<Ticket> ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketServiceImpl(customerService, itineraryService, ticketRepository);

        try {
            System.out.println("Adding Tickets...");
            for (int i = 0; i < customerList.size() - 2; i++) {
                ticketService.publishTicket(customerList.get(i).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
                ticketService.publishTicket(customerList.get(i).getId(), itineraryList.get(0).getId(), PurchaseMethod.CASH);
            }
            // Some extra additions...
            ticketService.publishTicket(customerList.get(4).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
            ticketService.publishTicket(customerList.get(2).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            System.out.println("Business exception occurred: " + e.getMessage());
        }

        System.out.println(LINE_SEPARATOR);

        try {
            System.out.println("Adding a ticket for an itinerary that doesn't exist.");
            ticketService.publishTicket(customerList.get(0).getId(), 22000, PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            System.out.println("Business exception occurred: " + e.getMessage());
        }

        System.out.println(LINE_SEPARATOR);

        try {
            System.out.println("Adding a ticket for a customer that doesn't exist.");
            ticketService.publishTicket(22000, itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            System.out.println("Business exception occurred: " + e.getMessage());
        }

        System.out.println(LINE_SEPARATOR);

        System.out.println("Checking if tickets are properly registered");
        List<Ticket> allTickets = ticketService.getAll();
        for (Ticket ticket : allTickets) {
            System.out.println(ticket);
        }

        System.out.println(LINE_SEPARATOR);

        ReporterService reporterService = new ReporterService(ticketService, customerService, itineraryService, airportCodeService);
        System.out.println("List of the total number and list of the cost of tickets for all customers");
        List<CustomerForReportingDTO> totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer = reporterService.totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer();
        for (CustomerForReportingDTO perCustomer : totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer) {
            System.out.println("\t Total Tickets: " + perCustomer.getTotalTickets()
                    + ", Total Cost: " + perCustomer.getTotalCost()
                    + ", Customer Info: " + perCustomer.getCustomer());
        }

        System.out.println(LINE_SEPARATOR);

        System.out.println("List of the total offered itineraries per destination and offered itineraries per departure");
        StringBuilder totalOfferedItinerariesPerDestinationAndTotalOfferedItinerariesPerDeparture = reporterService.totalOfferedItinerariesPerDestinationAndTotalOfferedItinerariesPerDeparture();
        System.out.println(totalOfferedItinerariesPerDestinationAndTotalOfferedItinerariesPerDeparture);

        System.out.println(LINE_SEPARATOR);

        System.out.println("List of the customers with the most tickets and with the largest cost of purchases");
        StringBuilder customersWithMostTicketAndLargestCostPurchased = reporterService.customersWWithMostTicketAndLargestCostPurchased();
        System.out.println(customersWithMostTicketAndLargestCostPurchased);

        System.out.println(LINE_SEPARATOR);

        System.out.println("List of the customers who have not purchased any tickets");
        ArrayList<Customer> customersWithNoTicketsPurchased = reporterService.customersWithNoTicketsPurchased();
        for (Customer customer : customersWithNoTicketsPurchased) {
            System.out.println("\t" + customer);
        }
    }
}


