package travelcompany.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Represents a flight itinerary.
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Itinerary extends BaseEntity{
	private AirportCode departureAirport;
	private AirportCode destinationAirport;
	private String departureDate;
	private String airline;
	private BigDecimal basePrice;
}
