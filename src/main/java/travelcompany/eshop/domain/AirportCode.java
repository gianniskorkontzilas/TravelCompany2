package travelcompany.eshop.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Represents an airport code
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class AirportCode extends BaseEntity{
    private String airportCode;
}
