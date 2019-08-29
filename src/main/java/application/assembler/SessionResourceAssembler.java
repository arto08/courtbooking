package application.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import application.controller.SessionController;
//import application.controller.BookingController;
import application.model.*;

@Component
public class SessionResourceAssembler implements ResourceAssembler<Session, Resource<Session>> {

	@Override
	public Resource<Session> toResource(Session booking) {
		return new Resource<>(booking, 
				linkTo(methodOn(SessionController.class).oneBooking(booking.getId())).withSelfRel(),
				linkTo(methodOn(SessionController.class).allBookings()).withRel("sessions")
				);
	}

	
}
 