package ru.garf.ff.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(name="users_id_seq", sequenceName = "users_id_seq")
	protected Long id;
	protected String name;
	protected String login;
	protected String password;

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


	protected Users(Users user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.login = user.getLogin();
		this.password = user.getPassword();
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


	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", login=" + login + ", password=" + password + "]";
	}

	



}
