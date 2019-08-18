package application.mockdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.model.Court;
import application.model.Location;
import application.repository.CourtRepository;
import application.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(LocationRepository locationRepo) {
		return args -> {
			Location cobbleHill = new Location("Cobble Hill");
			log.info("Preloading " + locationRepo.save(cobbleHill));
			log.info("Preloading " + locationRepo.save(new Location("Brooklyn Heights")));
		};
	}
}