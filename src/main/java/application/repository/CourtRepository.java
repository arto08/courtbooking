package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Court;
import application.model.Location;

public interface CourtRepository extends JpaRepository<Court, Long> {
	
	public Location findByLocationId(long locationId);

}
