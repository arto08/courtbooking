package application.mockdata;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.model.Location;
import application.repository.LocationRepository;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(LocationRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Location("Cobble Hill")));
			log.info("Preloading " + repository.save(new Location("Brooklyn Heights")));
		};
	}
}