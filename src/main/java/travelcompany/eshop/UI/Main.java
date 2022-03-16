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
import travelcompany.eshop.repository.AirportCodeRepository;
import travelcompany.eshop.repository.BaseRepository;
import travelcompany.eshop.repository.CustomerRepository;
import travelcompany.eshop.repository.ItineraryRepository;
import travelcompany.eshop.repository.TicketRepository;
import travelcompany.eshop.service.AirportCodeService;
import travelcompany.eshop.service.CustomerService;
import travelcompany.eshop.service.ItineraryService;
import travelcompany.eshop.service.ReporterService;
import travelcompany.eshop.service.TicketService;
import travelcompany.eshop.service.TicketServiceImpl;
import travelcompany.eshop.transfer.CustomerForReportingDTO;
import travelcompany.eshop.util.FileParser;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String LINE_SEPARATOR = "=========================";

    private static void log(String text) {
        System.out.println(text);
    }

    private static void newSection(String message) {
        log("\n" + LINE_SEPARATOR + " " + message + " " + LINE_SEPARATOR);
    }

    private static void showCustomers(List<Customer> customerList) {
        newSection("showCustomers");
        log("Customers:");
        for (Customer customer : customerList) {
            log(customer.toString());
        }
    }

    private static void showItinerary(List<Itinerary> itineraryList) {
        newSection("showItinerary");
        log("Itineraries:");
        for (Itinerary itinerary : itineraryList) {
            log(itinerary.toString());
        }
    }

    private static void addTicketsToService(List<Customer> customerList, List<Itinerary> itineraryList, TicketService ticketService) {
        newSection("addTicketsToService");
        try {
            log("Adding Tickets...");
            for (int i = 0; i < customerList.size() - 2; i++) {
                ticketService.publishTicket(customerList.get(i).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
                ticketService.publishTicket(customerList.get(i).getId(), itineraryList.get(0).getId(), PurchaseMethod.CASH);
            }
            // Some extra additions...
            ticketService.publishTicket(customerList.get(4).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
            ticketService.publishTicket(customerList.get(2).getId(), itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            log("Business exception occurred: " + e.getMessage());
        }
    }

    private static void checkInvalidItinerary(List<Customer> customerList, TicketService ticketService) {
        newSection("checkInvalidItinerary");
        try {
            log("Adding a ticket for an itinerary that doesn't exist.");
            ticketService.publishTicket(customerList.get(0).getId(), 22000, PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            log("Business exception occurred: " + e.getMessage());
        }
    }

    private static void checkInvalidCustomer(List<Itinerary> itineraryList, TicketService ticketService) {
        newSection("checkInvalidCustomer");
        try {
            log("Adding a ticket for a customer that doesn't exist.");
            ticketService.publishTicket(22000, itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            log("Business exception occurred: " + e.getMessage());
        }
    }

    private static void confirmTicketRegistrations(TicketService ticketService) {
        newSection("confirmTicketRegistrations");
        log("Checking if tickets are properly registered");
        List<Ticket> allTickets = ticketService.getAll();
        for (Ticket ticket : allTickets) {
            log(ticket.toString());
        }
    }

    private static ReporterService getTicketsAndCosts(CustomerService customerService, AirportCodeService airportCodeService, ItineraryService itineraryService, TicketService ticketService) {
        newSection("getTicketsAndCosts");
        ReporterService reporterService = new ReporterService(ticketService, customerService, itineraryService, airportCodeService);
        log("List of the total number and list of the cost of tickets for all customers");
        List<CustomerForReportingDTO> report = reporterService.totalNumberOfTicketsAndTotalCostOfTicketsPerCustomer();
        for (CustomerForReportingDTO perCustomer : report) {
            log("\t Total Tickets: " + perCustomer.getTotalTickets()
                    + ", Total Cost: " + perCustomer.getTotalCost()
                    + ", Customer Info: " + perCustomer.getCustomer());
        }
        return reporterService;
    }

    private static void getItinerariesPerDestination(ReporterService reporterService) {
        newSection("getItinerariesPerDestination");
        log("List of the total offered itineraries per destination and offered itineraries per departure");
        StringBuilder totalItineraries = reporterService.totalOfferedItinerariesPerDestinationAndTotalOfferedItinerariesPerDeparture();
        log(totalItineraries.toString());
    }

    private static void getCustomersWithMostTickets(ReporterService reporterService) {
        newSection("getCustomersWithMostTickets");
        log("List of the customers with the most tickets and with the largest cost of purchases");
        StringBuilder customers = reporterService.customersWithMostTicketAndLargestCostPurchased();
        log(customers.toString());
    }

    private static void getCustomersWithNoTickets(ReporterService reporterService) {
        newSection("getCustomersWithNoTickets");
        log("List of the customers who have not purchased any tickets");
        ArrayList<Customer> customersWithNoTicketsPurchased = reporterService.customersWithNoTicketsPurchased();
        for (Customer customer : customersWithNoTicketsPurchased) {
            log("\t" + customer);
        }
    }

    private static ItineraryService createBaseItineraryService(List<Itinerary> itinerariesFromFactory, BaseRepository<Itinerary> itineraryRepository, AirportCodeService airportCodeService) {
        newSection("createBaseItineraryService");
        ItineraryService itineraryService = new ItineraryService(itineraryRepository, airportCodeService);
        try {
            itineraryService.addAll(itinerariesFromFactory);
        } catch (BusinessException e) {
            log("Could not add itineraries from factory...");
            log("Message: " + e.getMessage());
            System.exit(1);
        }
        log("Service created: createBaseItineraryService");
        return itineraryService;
    }

    private static ItineraryService createFullItineraryService(AirportCodeService airportCodeService) {
        newSection("createFullItineraryService");
        List<String> itinerariesAsLines = FileParser.load("src/main/resources/data/itineraries.csv");
        ItineraryFactory itineraryFactory = new ItineraryFactory();
        List<Itinerary> itinerariesFromFactory = itineraryFactory.parse(itinerariesAsLines);
        BaseRepository<Itinerary> itineraryRepository = new ItineraryRepository();
        ItineraryService itineraryService = createBaseItineraryService(itinerariesFromFactory, itineraryRepository, airportCodeService);
        log("Service created: createFullItineraryService");
        return itineraryService;
    }

    private static AirportCodeService createAirportCodeService() {
        newSection("createAirportCodeService");
        List<String> airportCodesAsLines = FileParser.load("src/main/resources/data/airport_codes.csv");
        AirportCodeFactory airportCodeFactory = new AirportCodeFactory();
        List<AirportCode> airportCodesFromFactory = airportCodeFactory.parse(airportCodesAsLines);
        BaseRepository<AirportCode> airportCodeRepository = new AirportCodeRepository();
        AirportCodeService airportCodeService = new AirportCodeService(airportCodeRepository);
        airportCodeService.addAll(airportCodesFromFactory);
        log("Service created: createAirportCodeService");
        return airportCodeService;
    }

    private static CustomerService createCustomerService() {
        newSection("createCustomerService");
        List<String> customersAsLines = FileParser.load("src/main/resources/data/customers.csv");
        CustomerFactory customerFactory = new CustomerFactory();
        List<Customer> customersFromFactory = customerFactory.parse(customersAsLines);
        BaseRepository<Customer> customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        try {
            customerService.addAll(customersFromFactory);
        } catch (BusinessException e) {
            log("Could not add customers from factory...");
            log("Message: " + e.getMessage());
            System.exit(1);
        }
        log("Service created: createCustomerService");
        return customerService;
    }

    /**
     * Tests use-cases for travelcompany's eshop.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Loading collections from file to repositories
        // for customers
        CustomerService customerService = createCustomerService();
        List<Customer> customerList = customerService.getAll();
        // for itineraries
        AirportCodeService airportCodeService = createAirportCodeService();
        ItineraryService itineraryService = createFullItineraryService(airportCodeService);
        List<Itinerary> itineraryList = itineraryService.getAll();
        //Check if the repositories loaded correctly
        showCustomers(customerList);
        showItinerary(itineraryList);
        // Instantiation of ticket repository & service, contains no tickets and simply holds them in memory.
        // Will be deleted when application stops or is restarted
        BaseRepository<Ticket> ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketServiceImpl(customerService, itineraryService, ticketRepository);
        addTicketsToService(customerList, itineraryList, ticketService);
        checkInvalidItinerary(customerList, ticketService);
        checkInvalidCustomer(itineraryList, ticketService);
        confirmTicketRegistrations(ticketService);
        ReporterService reporterService = getTicketsAndCosts(customerService, airportCodeService, itineraryService, ticketService);
        getItinerariesPerDestination(reporterService);
        getCustomersWithMostTickets(reporterService);
        getCustomersWithNoTickets(reporterService);
    }

}


