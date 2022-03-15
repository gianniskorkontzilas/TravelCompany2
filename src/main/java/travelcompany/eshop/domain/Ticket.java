package travelcompany.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import travelcompany.eshop.domain.enumer.PurchaseMethod;

import java.math.BigDecimal;

/**
 * Represents a ticket.
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Ticket extends BaseEntity{
	private final int customerCode;
	private final int itineraryCode;
	private final PurchaseMethod purchaseMethod;
	private final BigDecimal price;
}