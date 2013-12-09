package gov.raleighnc.switchyard.integration.service.classdb.rental;

import gov.raleighnc.switchyard.integration.domain.classdb.rental.Rental;

/**
 * A Switchyard "Composite Service" consisting of rental services (from the Class database) usable by the outside world.
 * 
 * @author mikev
 *
 */
public interface RentalService {
	/**
	 * Create a Cityworks work order for each rental passed.
	 * 
	 * @param rentals An array of rental information to create work orders from
	 */
	void createWorkOrder(Rental[] rentals);
}