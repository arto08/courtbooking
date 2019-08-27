package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Court;

public interface CourtRepository extends JpaRepository<Court, Long> {
	
	public List<Court> findByLocationId(long locationId);

}
