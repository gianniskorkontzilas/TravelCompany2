package travelcompany.eshop.domain;

import lombok.Data;

/**
 * Entity that is shared amongst all objects. Done so for readability issues.
 */
@Data
public abstract class BaseEntity {
    private int id;
}
