package application.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import application.controller.UserController;
//import application.controller.UserController;
import application.model.*;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

	@Override
	public Resource<User> toResource(User user) {
		return new Resource<>(user, 
				linkTo(methodOn(UserController.class).oneUser(user.getId())).withSelfRel(),
				linkTo(methodOn(UserController.class).allUsers()).withRel("users")
				);
	}

	
}
 