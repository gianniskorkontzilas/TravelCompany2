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

    public static final String FILE_DATA_ROOT = "src/main/resources/data/";
    public static final String FILE_CUSTOMERS = FILE_DATA_ROOT + "customers.csv";
    public static final String FILE_AIRPORT_CODES = FILE_DATA_ROOT + "airport_codes.csv";
    public static final String FILE_ITINERARIES = FILE_DATA_ROOT + "itineraries.csv";

    private static void log(String text) {
        System.out.println(text);
    }

    private static void newSection(String message) {
        log("\n" + LINE_SEPARATOR + " " + message + " " + LINE_SEPARATOR);
    }

    /**
     * Use case: show details of all customers
     *
     * @param customerList the customers to show
     */
    private static void showCustomers(List<Customer> customerList) {
        newSection("showCustomers");
        log("Customers:");
        for (Customer customer : customerList) {
            log(customer.toString());
        }
    }

    /**
     * Use case: show details of all itineraries
     *
     * @param itineraryList the itineraries to show
     */
    private static void showItinerary(List<Itinerary> itineraryList) {
        newSection("showItinerary");
        log("Itineraries:");
        for (Itinerary itinerary : itineraryList) {
            log(itinerary.toString());
        }
    }

    /**
     * Use case: for a list of itineraries and a list of customers, push the related tickets to the ticketService. The
     * payment method is hard-coded as both credit and cash. A few additional test cases are attempted.
     *
     * @param customerList
     * @param itineraryList
     * @param ticketService
     */
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

    /**
     * Use case: attempt to build an invalid itinerary
     *
     * @param customerList
     * @param ticketService
     */
    private static void checkInvalidItinerary(List<Customer> customerList, TicketService ticketService) {
        newSection("checkInvalidItinerary");
        try {
            log("Adding a ticket for an itinerary that doesn't exist.");
            ticketService.publishTicket(customerList.get(0).getId(), 22000, PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            log("Business exception occurred: " + e.getMessage());
        }
    }

    /**
     * Use case: attempt to add a ticket for an invalid customer
     *
     * @param itineraryList
     * @param ticketService
     */
    private static void checkInvalidCustomer(List<Itinerary> itineraryList, TicketService ticketService) {
        newSection("checkInvalidCustomer");
        try {
            log("Adding a ticket for a customer that doesn't exist.");
            ticketService.publishTicket(22000, itineraryList.get(0).getId(), PurchaseMethod.CREDIT);
        } catch (BusinessException e) {
            log("Business exception occurred: " + e.getMessage());
        }
    }

    /**
     * Use case: show all ticketService ticket details
     *
     * @param ticketService
     */
    private static void confirmTicketRegistrations(TicketService ticketService) {
        newSection("confirmTicketRegistrations");
        log("Checking if tickets are properly registered");
        List<Ticket> allTickets = ticketService.getAll();
        for (Ticket ticket : allTickets) {
            log(ticket.toString());
        }
    }

    /**
     * Use case: show a report on tickets and costs
     *
     * @param customerService
     * @param airportCodeService
     * @param itineraryService
     * @param ticketService
     * @return
     */
    private static ReporterService getTicketsAndCosts(CustomerService customerService, AirportCodeService airportCodeService,
                                                      ItineraryService itineraryService, TicketService ticketService) {
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

    /**
     * Use case: show a report on itineraries per destination
     *
     * @param reporterService
     */
    private static void getItinerariesPerDestination(ReporterService reporterService) {
        newSection("getItinerariesPerDestination");
        log("List of the total offered itineraries per destination");
        String totalItineraries = reporterService.totalOfferedItinerariesPerDestination();
        log(totalItineraries);
    }

    /**
     * Use case: show a report on itineraries per departure
     *
     * @param reporterService
     */
    private static void getItinerariesPerDeparture(ReporterService reporterService) {
        newSection("getItinerariesPerDeparture");
        log("List of the total offered itineraries per destination and offered itineraries per departure");
        String totalItineraries = reporterService.totalOfferedItinerariesPerDeparture();
        log(totalItineraries);
    }

    /**
     * Use case: show a report on customers with most tickets
     *
     * @param reporterService
     */
    private static void getCustomersWithMostTickets(ReporterService reporterService) {
        newSection("getCustomersWithMostTickets");
        log("List of the customers with the most tickets and with the largest cost of purchases");
        String customers = reporterService.customersWithMostTickets();
        log(customers);
    }

    /**
     * Use case: show a report on customers with the largest cost purchased
     *
     * @param reporterService
     */
    private static void getCustomersWithLargestCostPurchased(ReporterService reporterService) {
        newSection("getCustomersWithLargestCostPurchased");
        log("List of the customers with the most tickets and with the largest cost of purchases");
        String customers = reporterService.customersWithLargestCostPurchased();
        log(customers);
    }

    /**
     * Use case: show a report on customers with no tickets purchased
     *
     * @param reporterService
     */
    private static void getCustomersWithNoTickets(ReporterService reporterService) {
        newSection("getCustomersWithNoTickets");
        log("List of the customers who have not purchased any tickets");
        ArrayList<Customer> customersWithNoTicketsPurchased = reporterService.customersWithNoTicketsPurchased();
        for (Customer customer : customersWithNoTicketsPurchased) {
            log("\t" + customer);
        }
    }

    /**
     * Use case: create the ItineraryService that will service the provided list of itineraries, with a given repository.
     * A list of airport codes is also provided, to check the validity of the itineraries
     *
     * @param itinerariesFromFactory
     * @param itineraryRepository
     * @param airportCodeService     a service which returns information on airports - needed to check the validity of the itineraries
     * @return
     */
    private static ItineraryService createBaseItineraryService(List<Itinerary> itinerariesFromFactory,
                                                               BaseRepository<Itinerary> itineraryRepository,
                                                               AirportCodeService airportCodeService) {
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

    /**
     * Use case: read the itinerary data from a file and give the ItineraryService for them
     *
     * @param airportCodeService a service which returns information on airports - needed to check the validity of the itineraries
     * @return
     */
    private static ItineraryService createFullItineraryService(AirportCodeService airportCodeService) {
        newSection("createFullItineraryService");
        List<String> itinerariesAsLines = FileParser.load(FILE_ITINERARIES);
        ItineraryFactory itineraryFactory = new ItineraryFactory();
        List<Itinerary> itinerariesFromFactory = itineraryFactory.parse(itinerariesAsLines);
        BaseRepository<Itinerary> itineraryRepository = new ItineraryRepository();
        ItineraryService itineraryService = createBaseItineraryService(itinerariesFromFactory, itineraryRepository, airportCodeService);
        log("Service created: createFullItineraryService");
        return itineraryService;
    }

    /**
     * Use case: read the airport data from a file and give the AirportCodeService for them
     *
     * @return
     */
    private static AirportCodeService createAirportCodeService() {
        newSection("createAirportCodeService");
        List<String> airportCodesAsLines = FileParser.load(FILE_AIRPORT_CODES);
        AirportCodeFactory airportCodeFactory = new AirportCodeFactory();
        List<AirportCode> airportCodesFromFactory = airportCodeFactory.parse(airportCodesAsLines);
        BaseRepository<AirportCode> airportCodeRepository = new AirportCodeRepository();
        AirportCodeService airportCodeService = new AirportCodeService(airportCodeRepository);
        airportCodeService.addAll(airportCodesFromFactory);
        log("Service created: createAirportCodeService");
        return airportCodeService;
    }

    /**
     * Use case: read the customer  data from a file and give the CustomerService for them
     *
     * @return
     */
    private static CustomerService createCustomerService() {
        newSection("createCustomerService");
        List<String> customersAsLines = FileParser.load(FILE_CUSTOMERS);
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
        // Load collections from file to repositories and create services
        CustomerService customerService = createCustomerService();
        List<Customer> customerList = customerService.getAll();
        AirportCodeService airportCodeService = createAirportCodeService();
        ItineraryService itineraryService = createFullItineraryService(airportCodeService);
        List<Itinerary> itineraryList = itineraryService.getAll();

        //Check if the repositories loaded correctly
        showCustomers(customerList);
        showItinerary(itineraryList);

        // Create ticket service from all data collected
        BaseRepository<Ticket> ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketServiceImpl(customerService, itineraryService, ticketRepository);

        // Start use cases
        addTicketsToService(customerList, itineraryList, ticketService);
        checkInvalidItinerary(customerList, ticketService);
        checkInvalidCustomer(itineraryList, ticketService);
        confirmTicketRegistrations(ticketService);
        ReporterService reporterService = getTicketsAndCosts(customerService, airportCodeService, itineraryService, ticketService);
        getItinerariesPerDestination(reporterService);
        getItinerariesPerDeparture(reporterService);
        getCustomersWithMostTickets(reporterService);
        getCustomersWithLargestCostPurchased(reporterService);
        getCustomersWithNoTickets(reporterService);
    }

}


