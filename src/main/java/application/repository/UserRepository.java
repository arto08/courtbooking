package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
