package ru.garf.ff.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Roles {
	
	@Id
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy="userRolesList")
	private List<Users> Users;
	
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
