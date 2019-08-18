package application.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import application.controller.LocationController;
//import application.controller.LocationController;
import application.model.*;

@Component
public class LocationResourceAssembler implements ResourceAssembler<Location, Resource<Location>> {

	@Override
	public Resource<Location> toResource(Location location) {
		return new Resource<>(location, 
				linkTo(methodOn(LocationController.class).oneLocation(location.getId())).withSelfRel(),
				linkTo(methodOn(LocationController.class).allLocations()).withRel("locations")
				);
	}

	
}
 