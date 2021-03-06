package ru.garf.ff.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Users {
	
	private static final String VALIDATION_LOGIN = "([a-zA-Z_0-9]*)";
	private static final String VALIDATION_PASSWORD = "(.*[0-9].*[A-Z].*)|(.*[A-Z].*[0-9].*)";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@SequenceGenerator(name="seq", sequenceName = "users_id_seq")
	private Long id;
	private String name;
	@Size(min=1, message = "Логин не может быть пустым")
	@Pattern (regexp = VALIDATION_LOGIN, message="Логин должен содержать [a-zA-Z_0-9]")
	private String login;
	@Size(min=1, message = "Пароль не может быть пустым")
	@Pattern (regexp = VALIDATION_PASSWORD, message="Пароль должен содержать хотя бы одну [A-Z] и одну [0-9]")
	private String password;

	
	@ManyToMany
	@JoinTable(
		      name="user_roles",
		      joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
		      inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id")		    		 
		      )
	 
	private Set<Roles> userRolesSet;
	
	
	
	public Set<Roles> getUserRolesList() {
		return userRolesSet;
	}


	public void setUserRolesList(Set<Roles> userRolesList) {
		this.userRolesSet = userRolesList;
	}


	public Users() {
		super();
	}


	public Users(String name, String login, String password) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public Users(Long id, String name, String login, String password) {
		super();
		this.id=id;
		this.name = name;
		this.login = login;
		this.password = password;
	}


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
