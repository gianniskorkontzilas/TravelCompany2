package travelcompany.eshop.factory;

import travelcompany.eshop.domain.AirportCode;
import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.enumer.CustomerCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates airport code objects from a set of lines
 */
public class AirportCodeFactory implements BaseFactory<AirportCode> {
    @Override
    public List<AirportCode> parse(List<String> lines) {
        List<AirportCode> airportCodes = new ArrayList<>();
        for (String line : lines) {
            String airportCodeStr = line;
            AirportCode airportCode = new AirportCode(airportCodeStr);
            airportCodes.add(airportCode);
        }
        return airportCodes;
    }
}
