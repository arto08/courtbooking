package application.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Session {
	
	private @Id @GeneratedValue Long id;
//	private Date begin, end;
	private String start, end;
	@ManyToOne
	private User booker, opponent;
	@ManyToOne
	private Court court;
	private boolean booked = false;
	
	public Session(){}
	
	public Session(String start, String end, Court court){
//		this.booker = booker;
		this.start = start;
		this.end = end;
		this.court = court;
	}
}
