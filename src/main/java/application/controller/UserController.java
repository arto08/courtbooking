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

import application.assembler.UserResourceAssembler;
import application.exception.UserNotFoundException;
import application.model.User;
import application.repository.UserRepository;

@RestController
public class UserController {

	private final UserRepository repository;
	private final UserResourceAssembler assembler;

	public UserController(UserRepository repository, UserResourceAssembler assembler) {
	    this.repository = repository;
	    this.assembler = assembler;
	  }

	// Aggregate root

	@GetMapping("/users")
	public Resources<Resource<User>> allUsers() {
		List<Resource<User>> users = repository.findAll().stream()
		.map(assembler::toResource)
		.collect(Collectors.toList());
		
		return new Resources<>(users, 
				linkTo(methodOn(UserController.class).allUsers()).withSelfRel());
	}

	@PostMapping("/users")
	public ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException{
		
		Resource<User> resource = assembler.toResource(repository.save(newUser));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Single item

	@GetMapping("/users/{id}")
	public Resource<User> oneUser(@PathVariable Long id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));
		
		return assembler.toResource(user);
//		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@PutMapping("/users/{id}")
	ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id)
			throws URISyntaxException{

		User updatedUser = repository.findById(id).map(user -> {
			user.setName(newUser.getName());
			return repository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return repository.save(newUser);
		});
		
		Resource<User> resource = assembler.toResource(updatedUser);
		
		return ResponseEntity
			    .created(new URI(resource.getId().expand().getHref()))
			    .body(resource);
	}

	@DeleteMapping("/users/{id}") //TODO
	void deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
