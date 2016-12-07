package ru.garf.ff.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Roles {
	
	@Id
	private Long id;
	private String name;
	
	public Roles() {
		super();
	}

	public Roles(Long id, String role) {
		this.id = id;
		this.name = role;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
