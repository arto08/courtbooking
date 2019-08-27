package application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Court {
	private @Id @GeneratedValue Long id;
	private String name;
	@ManyToOne
	private Location location;

	public Court() {}
	
	public Court(String name, Location location){
		this.name = name;
		this.location = location;
	}
}
