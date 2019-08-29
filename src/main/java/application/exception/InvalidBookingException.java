package application.exception;

public class InvalidBookingException extends RuntimeException {
	public InvalidBookingException(Long sessionId) {
	    super("Can not book this session " + sessionId);
	  }
}
