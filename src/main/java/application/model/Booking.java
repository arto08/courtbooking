package application.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Booking {
	
	private @Id @GeneratedValue Long id;
	private Date begin, end;
	@ManyToOne
	private User booker, opponent;
	@ManyToOne
	private Court court;

}
