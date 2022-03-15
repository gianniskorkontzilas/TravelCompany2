package travelcompany.eshop.factory;

import java.util.List;

/**
 * This interface declares all basic functionality for parsing domain objects
 *
 */
public interface BaseFactory<T> {
    List<T> parse(List<String> lines);
}
