package application.exception;

public class CourtNotFoundException extends RuntimeException {
	public CourtNotFoundException(Long id) {
	    super("Could not find Court " + id);
	  }
}
