package travelcompany.eshop.transfer;

import lombok.Value;
import travelcompany.eshop.domain.Customer;

import java.math.BigDecimal;

/**
 * DTO class that is only used to transfer the information needed for report case 1 and report case 2
 */
@Value
public class CustomerForReportingDTO {
    Long totalTickets;
    BigDecimal totalCost;
    Customer customer;
}
