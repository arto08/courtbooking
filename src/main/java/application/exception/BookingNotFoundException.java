package application.exception;

public class BookingNotFoundException extends RuntimeException {
	public BookingNotFoundException(Long id) {
	    super("Could not find Booking" + id);
	  }
}
