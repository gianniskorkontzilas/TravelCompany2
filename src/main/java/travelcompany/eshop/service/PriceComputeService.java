package travelcompany.eshop.service;

import travelcompany.eshop.domain.Customer;
import travelcompany.eshop.domain.enumer.PurchaseMethod;

import java.math.BigDecimal;

/**
 * Calculates total ticket discount
 */
public class PriceComputeService {

    /**
     * Computes the final charges for a ticket,
     *
     * @param customer: the customer object to obtain the customer category from
     * @param purchaseMethod: the purchase method used
     * @return the accumulated extra charges
     */
    public BigDecimal getAccumulatedExtraCharges(Customer customer, PurchaseMethod purchaseMethod) {
        return BigDecimal.valueOf(customer.getCategory().getDiscount()).add(BigDecimal.valueOf(purchaseMethod.getDiscount()));
    }

}
