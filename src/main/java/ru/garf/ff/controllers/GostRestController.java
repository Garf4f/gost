package ru.garf.ff.controllers;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.garf.ff.entity.Roles;
import ru.garf.ff.entity.UserRoles;
import ru.garf.ff.entity.Users;
import ru.garf.ff.model.UsersRolesView;
import ru.garf.ff.repositories.RolesRepository;
import ru.garf.ff.repositories.UserRolesRepository;
import ru.garf.ff.repositories.UsersRepository;

@RestController
public class GostRestController {
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RolesRepository roleRepository;
	@Autowired
	UserRolesRepository userRolesRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	Collection<Users> listU() {
		return this.usersRepository.findAll();
	}
	
	@RequestMapping(value = "/u", method = RequestMethod.GET)
	Collection<Roles> listUR() {
		return this.roleRepository.findAll();
	}
	
	@RequestMapping(value = "/r", method = RequestMethod.GET)
	Collection<UserRoles> listR() {
		return this.userRolesRepository.findAll();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	UsersRolesView getUser(@RequestParam("id") Long id) {
		Users user = this.usersRepository.findOne(id);
		if (user != null) {
			Set<Long> list = new TreeSet<Long>();
			for (UserRoles userRoles : this.userRolesRepository.findByUserid(id)) {
				list.add(userRoles.getRoleid());
			}
			UsersRolesView usersRolesView = new UsersRolesView(user, list);
			return usersRolesView;
		} else {
			return new UsersRolesView();
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	void deleteUser(@RequestParam("id") Long id) {
		Users user = this.usersRepository.findOne(id);
		if (user != null) {
			this.userRolesRepository.deleteInBatch(this.userRolesRepository.findByUserid(id));
			this.usersRepository.delete(id);
		} else {
			System.out.println("Нет пользователя с таким ID");
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	void addUser(@RequestBody UsersRolesView usersRolesView) {
		Users users = new Users(usersRolesView.getName(), usersRolesView.getLogin(), usersRolesView.getPassword());
		this.usersRepository.save(users);
		users = this.usersRepository.findOneByLogin(users.getLogin());
		for (Long roleId : usersRolesView.getRoles())
			this.userRolesRepository.save(new UserRoles(users.getId(), roleId));
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = "application/json")
	void editUser(@RequestBody UsersRolesView usersRolesView) {
		Users users = new Users(usersRolesView.getName(), usersRolesView.getLogin(), usersRolesView.getPassword());
		Users uC = this.usersRepository.findOneByLogin(users.getLogin());
		if(uC == null){
			this.usersRepository.save(users);
			users = this.usersRepository.findOneByLogin(users.getLogin());
			for (Long roleId : usersRolesView.getRoles())
				this.userRolesRepository.save(new UserRoles(users.getId(), roleId));
		} else {
			users = new Users(uC.getId(),users.getName(),users.getLogin(),users.getPassword());
			this.usersRepository.save(users);
			
			this.userRolesRepository.deleteInBatch(this.userRolesRepository.findByUserid(uC.getId()));
			for (Long roleId : usersRolesView.getRoles())
				this.userRolesRepository.save(new UserRoles(uC.getId(), roleId));
			
		}
	}

}
