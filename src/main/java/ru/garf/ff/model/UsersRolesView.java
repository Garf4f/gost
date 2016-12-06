package ru.garf.ff.model;

import java.util.Set;
import java.util.TreeSet;

import ru.garf.ff.entity.Users;

public class UsersRolesView extends Users {

	private Set<Long> roles;
	
	public UsersRolesView(String name, String login, String password, Set<Long> roles) {
		super(null, name, login, password);
		this.roles = roles;
	}

	public UsersRolesView(Users user, Set<Long> roles) {
		super(user.getId(),user.getName(),user.getLogin(),user.getPassword());
		this.roles = roles;
	}

	public UsersRolesView() {
		super();
		this.roles = new TreeSet<Long>();
	}

	public Set<Long> getRoles() {
		return roles;
	}

	public void setRoles(TreeSet<Long> roles) {
		this.roles = roles;
	}

}
