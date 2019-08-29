package application.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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

import application.assembler.SessionResourceAssembler;
import application.exception.SessionNotFoundException;
import application.exception.InvalidBookingException;
import application.model.Session;
import application.repository.SessionRepository;

@RestController
public class SessionController {

	private final SessionRepository repository;
	private final SessionResourceAssembler assembler;

	public SessionController(SessionRepository repository, SessionResourceAssembler assembler) {
	    this.repository = repository;
	    this.assembler = assembler;
	  }

	// Aggregate root

	@GetMapping("/sessions")
	public Resources<Resource<Session>> allBookings() {
		List<Resource<Session>> bookings = repository.findAll().stream()
		.map(assembler::toResource)
		.collect(Collectors.toList());
		
		return new Resources<>(bookings, 
				linkTo(methodOn(SessionController.class).allBookings()).withSelfRel());
	}

	@PostMapping("/sessions")
	public ResponseEntity<?> newBooking(@RequestBody Session newSession) throws URISyntaxException{
		
		List<Session> sessions = repository.findByCourtId(newSession.getCourt().getId());
		for(Session session : sessions)
			if(session.getStart().equals(newSession.getStart()))
				throw new InvalidBookingException(newSession.getId());
		
		Resource<Session> resource = assembler.toResource(repository.save(newSession));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Single item

	@GetMapping("/sessions/{id}")
	public Resource<Session> oneBooking(@PathVariable Long id) {
		Session session = repository.findById(id)
				.orElseThrow(() -> new SessionNotFoundException(id));
		
		return assembler.toResource(session);
//		return repository.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
	}

	@PutMapping("/sessions/{id}") 
	ResponseEntity<?> replaceBooking(@RequestBody Session newSession, @PathVariable Long id)
			throws URISyntaxException{

		Session updatedBooking = repository.findById(id).map(booking -> {
			if(!booking.isBooked() && newSession.getBooker() != null){
				booking.setBooker(newSession.getBooker());
				booking.setBooked(true);
			}else
				throw new InvalidBookingException(id);
			return repository.save(booking);
		}).orElseGet(() -> {
			newSession.setId(id);
			return repository.save(newSession);
		});
		
		Resource<Session> resource = assembler.toResource(updatedBooking);
		
		return ResponseEntity
			    .created(new URI(resource.getId().expand().getHref()))
			    .body(resource);
	}

	@DeleteMapping("/sessions/{id}") //TODO
	void deleteBooking(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
