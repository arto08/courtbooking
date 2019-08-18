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

import application.assembler.LocationResourceAssembler;
import application.exception.LocationNotFoundException;
import application.model.Location;
import application.repository.LocationRepository;

@RestController
public class LocationController {

	private final LocationRepository repository;
	private final LocationResourceAssembler assembler;

	public LocationController(LocationRepository repository, LocationResourceAssembler assembler) {
	    this.repository = repository;
	    this.assembler = assembler;
	  }

	// Aggregate root

	@GetMapping("/locations")
	public Resources<Resource<Location>> allLocations() {
		List<Resource<Location>> locations = repository.findAll().stream()
		.map(assembler::toResource)
		.collect(Collectors.toList());
		
		return new Resources<>(locations, 
				linkTo(methodOn(LocationController.class).allLocations()).withSelfRel());
	}

	@PostMapping("/locations")
	public ResponseEntity<?> newLocation(@RequestBody Location newLocation) throws URISyntaxException{
		
		Resource<Location> resource = assembler.toResource(repository.save(newLocation));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Single item

	@GetMapping("/locations/{id}")
	public Resource<Location> oneLocation(@PathVariable Long id) {
		Location location = repository.findById(id)
				.orElseThrow(() -> new LocationNotFoundException(id));
		
		return assembler.toResource(location);
//		return repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
	}

	@PutMapping("/locations/{id}")
	ResponseEntity<?> replaceLocation(@RequestBody Location newLocation, @PathVariable Long id)
			throws URISyntaxException{

		Location updatedLocation = repository.findById(id).map(location -> {
			location.setName(newLocation.getName());
			return repository.save(location);
		}).orElseGet(() -> {
			newLocation.setId(id);
			return repository.save(newLocation);
		});
		
		Resource<Location> resource = assembler.toResource(updatedLocation);
		
		return ResponseEntity
			    .created(new URI(resource.getId().expand().getHref()))
			    .body(resource);
	}

	@DeleteMapping("/locations/{id}") //TODO
	void deleteLocation(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
