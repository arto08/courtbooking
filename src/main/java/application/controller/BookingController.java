package application.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.assembler.BookingResourceAssembler;
import application.exception.BookingNotFoundException;
import application.model.Booking;
import application.repository.BookingRepository;

@RestController
public class BookingController {

	private final BookingRepository repository;
	private final BookingResourceAssembler assembler;

	public BookingController(BookingRepository repository, BookingResourceAssembler assembler) {
	    this.repository = repository;
	    this.assembler = assembler;
	  }

	// Aggregate root

	@GetMapping("/bookings")
	public Resources<Resource<Booking>> allBookings() {
		List<Resource<Booking>> bookings = repository.findAll().stream()
		.map(assembler::toResource)
		.collect(Collectors.toList());
		
		return new Resources<>(bookings, 
				linkTo(methodOn(BookingController.class).allBookings()).withSelfRel());
	}

	@PostMapping("/bookings")
	public ResponseEntity<?> newBooking(@RequestBody Booking newBooking) throws URISyntaxException{
		
		Resource<Booking> resource = assembler.toResource(repository.save(newBooking));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Single item

	@GetMapping("/bookings/{id}")
	public Resource<Booking> oneBooking(@PathVariable Long id) {
		Booking booking = repository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException(id));
		
		return assembler.toResource(booking);
//		return repository.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
	}

	@PutMapping("/bookings/{id}")
	ResponseEntity<?> replaceBooking(@RequestBody Booking newBooking, @PathVariable Long id)
			throws URISyntaxException{

		Booking updatedBooking = repository.findById(id).map(booking -> {
			booking.setBooker(newBooking.getBooker());
			return repository.save(booking);
		}).orElseGet(() -> {
			newBooking.setId(id);
			return repository.save(newBooking);
		});
		
		Resource<Booking> resource = assembler.toResource(updatedBooking);
		
		return ResponseEntity
			    .created(new URI(resource.getId().expand().getHref()))
			    .body(resource);
	}

	@DeleteMapping("/bookings/{id}") //TODO
	void deleteBooking(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
