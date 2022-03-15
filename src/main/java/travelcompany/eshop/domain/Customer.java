package travelcompany.eshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import travelcompany.eshop.domain.enumer.CustomerCategory;

/**
 * Represents a customer
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Customer extends BaseEntity{
	private String name;
	private String email;
	private String address;
	private String nationality;
	private CustomerCategory category;
}
