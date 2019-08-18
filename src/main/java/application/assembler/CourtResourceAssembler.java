package application.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import application.controller.CourtController;
import application.controller.LocationController;
import application.model.Court;

@Component
public class CourtResourceAssembler implements ResourceAssembler<Court, Resource<Court>> {

	
	@Override
	public Resource<Court> toResource(Court court) {
		return new Resource<>(court, 
				linkTo(methodOn(CourtController.class).oneCourt(court.getId())).withSelfRel(),
				linkTo(methodOn(CourtController.class).allCourts()).withRel("courts"),
				linkTo(methodOn(LocationController.class).oneLocation(court.getLocation().getId())).withRel("location")
				);
	}
}
