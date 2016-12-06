package ru.garf.ff.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ru.garf.ff.entity.Roles;
import ru.garf.ff.entity.UserRoles;
import ru.garf.ff.entity.Users;
import ru.garf.ff.repositories.RolesRepository;
import ru.garf.ff.repositories.UserRolesRepository;
import ru.garf.ff.repositories.UsersRepository;


@RestController
public class GostRestController {
	@Autowired UsersRepository usersRepository;
	@Autowired RolesRepository roleRepository;
	@Autowired UserRolesRepository userRolesRepository;

	
	@RequestMapping(value = "/u", method=RequestMethod.GET)
	Collection<Users> listU(){
		return this.usersRepository.findAll();
	}
	
	@RequestMapping(value = "/r", method=RequestMethod.GET)
	Collection<Roles> listR(){
		return this.roleRepository.findAll();
	}
	
	
	@RequestMapping(value = "/ur", method=RequestMethod.GET)
	Collection<UserRoles> listUr(){
		return this.userRolesRepository.findAll();
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	Users getUser(@RequestParam("id") Long id) {
		return this.usersRepository.findOne(id);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	void deleteUser(@RequestParam("id") Long id) {
		this.userRolesRepository.deleteInBatch(this.userRolesRepository.findByUserid(id));
		this.usersRepository.delete(id);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
	void addUser(@RequestBody Users users) {
			this.usersRepository.save(users);
			for(Roles ur :users.getRoles())
				this.userRolesRepository.save(new UserRoles(users.getId(),ur.getId()));
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, consumes="application/json")
	void editUser(@RequestBody Users users) {
		deleteUser(users.getId());
		this.usersRepository.save(users);
		for(Roles ur :users.getRoles())
		this.userRolesRepository.save(new UserRoles(users.getId(),ur.getId()));
	}
	
}
