package application.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import application.controller.BookingController;
//import application.controller.BookingController;
import application.model.*;

@Component
public class BookingResourceAssembler implements ResourceAssembler<Booking, Resource<Booking>> {

	@Override
	public Resource<Booking> toResource(Booking booking) {
		return new Resource<>(booking, 
				linkTo(methodOn(BookingController.class).oneBooking(booking.getId())).withSelfRel(),
				linkTo(methodOn(BookingController.class).allBookings()).withRel("bookings")
				);
	}

	
}
 