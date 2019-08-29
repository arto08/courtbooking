package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
	
	public List<Session> findByCourtId(Long id);
}
