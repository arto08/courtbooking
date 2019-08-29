package application.mockdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.model.Session;
import application.model.Court;
import application.model.Location;
import application.model.User;
import application.repository.SessionRepository;
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
			SessionRepository sessionRepo) {
		return args -> {
			User arto = new User("Arto");
			User pat = new User("Pat");
			User steve = new User("Steve");
			Location cobbleHill = new Location("Cobble Hill");
			Location bkHeights = new Location("Brooklyn Heights");
			Court courtA = new Court("A", cobbleHill); 
			Court courtB = new Court("B", cobbleHill); 
			Session s1 = new Session("1000", "1045", courtA);
			Session s2 = new Session("1045", "1130", courtA);
			Session s3 = new Session("1045", "1130", courtB);
			Session s4 = new Session("1045", "1130", courtB);
			s2.setBooker(arto);
			s2.setBooked(true);
			s1.setBooker(pat);
			s1.setBooked(true);
			
			log.info("Preloading " + locationRepo.save(cobbleHill));
			log.info("Preloading " + locationRepo.save(bkHeights));
			
			log.info("Preloading " + userRepo.save(arto));
			log.info("Preloading " + userRepo.save(pat));
			log.info("Preloading " + userRepo.save(steve));
			log.info("Preloading " + courtRepo.save(courtA));
			log.info("Preloading " + courtRepo.save(courtB));
			log.info("Preloading " + sessionRepo.save(s1));
			log.info("Preloading " + sessionRepo.save(s2));
			log.info("Preloading " + sessionRepo.save(s3));
			log.info("Preloading " + sessionRepo.save(s4));
			
		};
	}
}