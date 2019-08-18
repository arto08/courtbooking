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

import application.assembler.CourtResourceAssembler;
import application.exception.CourtNotFoundException;
import application.exception.LocationNotFoundException;
import application.model.Court;
import application.repository.CourtRepository;
import application.repository.LocationRepository;

@RestController
public class CourtController {

	private final CourtRepository repository;
	private final CourtResourceAssembler assembler;

	public CourtController(CourtRepository repository, CourtResourceAssembler assembler) {
	    this.repository = repository;
	    this.assembler = assembler;
	  }

	// Aggregate root
	@GetMapping("/courts")
	public Resources<Resource<Court>> allCourts() {
		List<Resource<Court>> Courts = repository.findAll().stream()
		.map(assembler::toResource)
		.collect(Collectors.toList());
		
		return new Resources<>(Courts, 
				linkTo(methodOn(CourtController.class).allCourts()).withSelfRel());
	}

	@PostMapping("/courts")
	public ResponseEntity<?> newCourt(@RequestBody Court newCourt) throws URISyntaxException{
		
		//TODO check to see if location specified on newCourt exists
//		methodOn(LocationRepository.class).findById(newCourt.getId())
//		.orElseThrow(() -> new LocationNotFoundException(newCourt.getId()));
//		if(repository.findByLocationId(newCourt.getLocation().getId()) == null)
//			throw new LocationNotFoundException(newCourt.getLocation().getId());
		
		Resource<Court> resource = assembler.toResource(repository.save(newCourt));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Single item

	@GetMapping("/courts/{id}")
	public Resource<Court> oneCourt(@PathVariable Long id) {
		Court Court = repository.findById(id)
				.orElseThrow(() -> new CourtNotFoundException(id));
		
		return assembler.toResource(Court);
//		return repository.findById(id).orElseThrow(() -> new CourtNotFoundException(id));
	}

	@PutMapping("/courts/{id}")
	ResponseEntity<?> replaceCourt(@RequestBody Court newCourt, @PathVariable Long id)
			throws URISyntaxException{

		Court updatedCourt = repository.findById(id).map(Court -> {
			Court.setName(newCourt.getName());
			return repository.save(Court);
		}).orElseGet(() -> {
			newCourt.setId(id);
			return repository.save(newCourt);
		});
		
		Resource<Court> resource = assembler.toResource(updatedCourt);
		
		return ResponseEntity
			    .created(new URI(resource.getId().expand().getHref()))
			    .body(resource);
	}

	@DeleteMapping("/Courts/{id}") //TODO
	void deleteCourt(@PathVariable Long id) {
		repository.deleteById(id);
	}
}