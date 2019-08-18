package application.exception;

public class LocationNotFoundException extends RuntimeException {

	public LocationNotFoundException(Long id) {
	    super("Could not find Location " + id);
	  }
}
