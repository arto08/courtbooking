package application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Location {
	
	private @Id @GeneratedValue Long id;
	private String name;
	
	public Location() {}
	
	public Location(String name){
		this.name = name;
	}

}
