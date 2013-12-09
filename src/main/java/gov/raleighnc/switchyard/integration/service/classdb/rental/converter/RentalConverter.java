package gov.raleighnc.switchyard.integration.service.classdb.rental.converter;

import gov.raleighnc.switchyard.integration.domain.classdb.rental.Rental;
import gov.raleighnc.switchyard.integration.service.classdb.rental.iterator.RentalIterator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.camel.Converter;

/**
 * Custom type converter used to convert from {@link Rental} into {@link Iterator}
 * as well as the creation of Rentals from the Class database record query.
 * 
 * @author mikev
 */
@Converter
public class RentalConverter {
	 /**
     * Wraps Rental into iterator.
     * 
     * @param rental Rental.
     * @return
     */
    @Converter
    public static Iterator<Object> from(Rental rental) {
        return new RentalIterator(rental);
    }
    
    @Converter
    public static Rental[] from(List<Map<String, Object>> objects) {
    	Rental[] rentals = new Rental[objects.size()];
        int position = 0;
        for (Map<String, Object> rental : objects) {
        	rentals[position++] = new Rental(
                (Integer)rental.get("Maintenance Booking"),
                (Date)rental.get("Maintenance Start"),
                (Date)rental.get("Maintenance End"),
                (Integer)rental.get("Related Booking"),
                (String)rental.get("Related Booking Type"),
                (Integer)rental.get("Related Booking Reference"),
                (Integer)rental.get("Rental Contract Number"),
                (String)rental.get("Rental Title"),
                (String)rental.get("Rental Description")
            );
        }
        
        return rentals;
    }
}