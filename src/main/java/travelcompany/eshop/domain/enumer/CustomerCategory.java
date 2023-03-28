package travelcompany.eshop.domain.enumer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents different customer types with their discounts.
 */
@Getter
@AllArgsConstructor
public enum CustomerCategory {
        INDIVIDUAL(-0.20),
        BUSINESS(0.10);

        private final double discount;
}
