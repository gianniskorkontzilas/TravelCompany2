package travelcompany.eshop.factory;

import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.Itinerary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates itinerary objects from a set of lines
 */
public class ItineraryFactory implements BaseFactory<Itinerary>{
    public List<Itinerary> parse(List<String> lines) {
        List<Itinerary> itineraries = new ArrayList<>();
        for (String line : lines) {
            final String[] values = line.split(",");
            AirportCode departureAirport = new AirportCode(values[0]);
            AirportCode destinationAirport =  new AirportCode(values[1]);
            String departureDate = values[2];
            String airline = values[3];
            BigDecimal basePrice = new BigDecimal(values[4]);
            Itinerary itinerary = new Itinerary(departureAirport, destinationAirport, departureDate, airline, basePrice);
            itineraries.add(itinerary);
        }
        return itineraries;
    }
}
