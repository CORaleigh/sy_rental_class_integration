package gov.raleighnc.switchyard.integration.service.classdb.rental.iterator;

import gov.raleighnc.switchyard.integration.domain.classdb.rental.Rental;

import java.util.Iterator;

/**
 * Iterator implementation for {@link Rental}.
 * 
 * @author mikev
 */
public class RentalIterator implements Iterator<Object> {
    private final Rental rental;
    private int pointer;

    public RentalIterator(Rental rental) {
        this.rental = rental;
    }

    @Override
    public boolean hasNext() {
        return pointer < 9;
    }

    @Override
    public Object next() {
        switch (pointer++) {
	        case 0: return rental.getMaintenanceBooking();
	        case 1: return rental.getMaintenanceStart();
	        case 2: return rental.getMaintenanceEnd();
	        case 3: return rental.getRelatedBooking();
	        case 4: return rental.getRelatedBookingType();
	        case 5: return rental.getRelatedBookingReference();
	        case 6: return rental.getRentalContractNumber();
	        case 7: return rental.getRentalTitle();
	        case 8: return rental.getRentalDescription();
        }
        
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}