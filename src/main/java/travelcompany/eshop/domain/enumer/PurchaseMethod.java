package travelcompany.eshop.domain.enumer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents different purchase methods with their discounts.
 */
@Getter
@AllArgsConstructor
public enum PurchaseMethod {
    CASH(0),
    CREDIT(0.10);

    private final double discount;
}
