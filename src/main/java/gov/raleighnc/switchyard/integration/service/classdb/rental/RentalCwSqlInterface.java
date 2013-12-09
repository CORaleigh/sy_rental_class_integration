package gov.raleighnc.switchyard.integration.service.classdb.rental;

import gov.raleighnc.switchyard.integration.domain.classdb.booking.BookingWorkOrder;

/**
 * Switchyard SQL-based "Composite Reference" for all rental-based access to the Cityworks system.
 * 
 * @author mikev
 */
public interface RentalCwSqlInterface {
	BookingWorkOrder[] getBookingWoMapping(int bookingId); 
}