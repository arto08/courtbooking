package application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	
	private @Id @GeneratedValue Long id;
	private String name;
	
	public User(){}
	
	public User(String name){
		this.name = name;
	}
}
