package application.exception;

public class SessionNotFoundException extends RuntimeException {
	public SessionNotFoundException(Long id) {
	    super("Could not find Booking" + id);
	  }
}
