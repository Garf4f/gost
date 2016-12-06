package ru.garf.ff.model;

import java.util.Set;
import java.util.TreeSet;

import ru.garf.ff.entity.Users;

public class UsersRolesView extends Users {
	
	protected Long id;
	protected String name;
	protected String login;
	protected String password;
	Set<Long> roles = new TreeSet<Long>();
	
	public UsersRolesView(String name, String login, String password, Set<Long> roles) {
		super(name, login, password);
		this.roles = roles;
	}

	public UsersRolesView(Users user, Set<Long> roles) {
		super(user);
		this.roles = roles;
	}

	public UsersRolesView() {
		super();
	}

	public Set<Long> getRoles() {
		return roles;
	}

	public void setRoles(TreeSet<Long> roles) {
		this.roles = roles;
	}
	
	public Long getId() {
		return super.getId();
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
		return "UsersRolesView [id=" + id + ", name=" + name + ", login=" + login + ", password=" + password
				+ ", roles=" + roles + "]";
	}


	
	

}
