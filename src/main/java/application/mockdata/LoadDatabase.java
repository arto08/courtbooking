package application.mockdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.model.Booking;
import application.model.Court;
import application.model.Location;
import application.model.User;
import application.repository.BookingRepository;
import application.repository.CourtRepository;
import application.repository.LocationRepository;
import application.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(LocationRepository locationRepo,
			UserRepository userRepo,
			CourtRepository courtRepo,
			BookingRepository bookingRepo) {
		return args -> {
			User arto = new User("Arto");
			User pat = new User("Pat");
			User steve = new User("Steve");
			Location cobbleHill = new Location("Cobble Hill");
			Location bkHeights = new Location("Brooklyn Heights");
			Court courtA = new Court("A", cobbleHill); 
			Court courtB = new Court("B", cobbleHill); 
			Booking b1 = new Booking(arto, "1000", "1045", courtA);
			Booking b2 = new Booking(arto, "1045", "1130", courtA);
			
			log.info("Preloading " + locationRepo.save(cobbleHill));
			log.info("Preloading " + locationRepo.save(bkHeights));
			
			log.info("Preloading " + userRepo.save(arto));
			log.info("Preloading " + userRepo.save(pat));
			log.info("Preloading " + userRepo.save(steve));
			log.info("Preloading " + courtRepo.save(courtA));
			log.info("Preloading " + courtRepo.save(courtB));
			log.info("Preloading " + bookingRepo.save(b1));
			log.info("Preloading " + bookingRepo.save(b2));
			
		};
	}
}