package gov.raleighnc.switchyard.integration.service.classdb.rental;

import gov.raleighnc.switchyard.integration.domain.cityworks.workorder.WorkOrder;
import gov.raleighnc.switchyard.integration.domain.classdb.booking.BookingWorkOrder;
import gov.raleighnc.switchyard.integration.domain.classdb.rental.Rental;

import javax.inject.Inject;

import org.switchyard.component.bean.Property;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

/**
 * Switchyard "Component" that is a "Bean Implementation" and implements the RentalService contract.
 * 
 * @author mikev
 *
 */
@Service(RentalService.class)
public class RentalServiceBean implements RentalService {
	@Inject
	@Reference
	private RentalCwRestInterface cwRestInterface;
	
	@Inject
	@Reference
	private RentalCwJpaInterface cwJpaInterface;
	
	@Inject
	@Reference
	private RentalCwSqlInterface cwSqlInterface;
	
	@Property(name = "supervisor")
	private String supervisor;
	
	@Property(name = "requestedBy")
	private String requestedBy;
	
	@Property(name = "initiatedBy")
	private String initiatedBy;
	
	@Property(name = "priority")
	private String priority;
	
	@Property(name = "numDaysBefore")
	private String numDaysBefore;
	
	@Property(name = "woCategory")
	private String woCategory;
	
	@Property(name = "submitTo")
	private String submitTo;
	
	@Property(name = "status")
	private String status;
	
	@Property(name = "woTemplateId")
	private String woTemplateId;
	
	@Override
	public void createWorkOrder(Rental[] rentals) {
		for (Rental rental : rentals) {
			// first check to see if a WO has already been created for this rental
			BookingWorkOrder[] results = cwSqlInterface.getBookingWoMapping(rental.getMaintenanceBooking());
			
			if (results != null && results.length > 0) {
				// since we found at least one BookingWorkOrder record (should only be one record) from the 
				// mapping table, that means we already created a WO for this booking hence skip this particular
				// record from being created as a new WO
				continue;
			}
			
			// create the WO from rental information
			WorkOrder wo = new WorkOrder();
			wo.setWorkOrderId(rental.getMaintenanceBooking().toString());
			wo.setLocation(rental.getRentalDescription());
			wo.setDescription(rental.getRentalTitle());
			wo.setProjectStartDate(rental.getMaintenanceStart());
			wo.setProjectFinishDate(rental.getMaintenanceEnd());
			wo.setInitiateDate(rental.getMaintenanceStart());
			wo.setSupervisor(supervisor);
			wo.setRequestedBy(requestedBy);
			wo.setInitiatedBy(initiatedBy);
			wo.setPriority(priority);
			wo.setNumDaysBefore(Integer.parseInt(numDaysBefore));
			wo.setWoCategory(woCategory);
			wo.setSubmitTo(submitTo);
			wo.setStatus(status);
			wo.setWoTemplateId(woTemplateId);
			
			String cwWoId = cwRestInterface.createWorkOrder(wo);
			
			// store the mapping between WO id and booking id
			if (cwWoId != null && cwWoId.length() > 0) {
				BookingWorkOrder bwo = new BookingWorkOrder(rental.getMaintenanceBooking(), cwWoId);
				cwJpaInterface.createBookingWoMapping(bwo);
			}
		}
	}
}