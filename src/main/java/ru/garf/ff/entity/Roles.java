package ru.garf.ff.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Roles {
	
	@Id
	private Long id;
	private String name;
	
	
	@ManyToOne(fetch = FetchType.EAGER,optional=true)
	@JoinTable(name = "user_roles", 
		joinColumns = @JoinColumn(name = "role_id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Users users;
	
	
	public Roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Roles(Long id, String role) {
		super();
		this.id = id;
		this.name = role;
	}

	public Long getId() {
		return id;
	}

//	public String getName() {
//		return name;
//	}

//	public Users getUsers() {
//		return users;
//	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


}
