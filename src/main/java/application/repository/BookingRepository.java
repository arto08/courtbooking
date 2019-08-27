package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {}