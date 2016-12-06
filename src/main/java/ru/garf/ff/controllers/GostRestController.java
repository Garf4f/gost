package ru.garf.ff.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.garf.ff.entity.Roles;
import ru.garf.ff.entity.UserRoles;
import ru.garf.ff.entity.Users;
import ru.garf.ff.model.ErrorReportMessage;
import ru.garf.ff.model.ReportMessage;
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

	@RequestMapping(value = "/r", method = RequestMethod.GET)
	Collection<Roles> listUR() {
		return this.roleRepository.findAll();
	}

	@RequestMapping(value = "/u", method = RequestMethod.GET)
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
			System.out.println("Пользователя с ID: " + id + " не существует.");
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
			System.out.println("Пользователя с ID: " + id + " не существует.");
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	ReportMessage addUser(@Valid @RequestBody UsersRolesView usersRolesView, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorReportMessage messageErr = new ErrorReportMessage();
			for (FieldError err : bindingResult.getFieldErrors()) {
				messageErr.addError(err.getDefaultMessage());
			}
			return messageErr;
		}

		Users users = new Users(
				null, 
				usersRolesView.getName(), 
				usersRolesView.getLogin(),
				usersRolesView.getPassword());
		
		users = this.usersRepository.save(users);
		for (Long roleId : usersRolesView.getRoles()){
			this.userRolesRepository.save(new UserRoles(null, users.getId(), roleId));
		}
		return new ReportMessage();	
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = "application/json")
	ReportMessage editUser(@Valid @RequestBody UsersRolesView usersRolesView, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorReportMessage messageErr = new ErrorReportMessage();
			for (FieldError err : bindingResult.getFieldErrors()) {
				messageErr.addError(err.getDefaultMessage());
			}
			return messageErr;
		}
		
		Users users = new Users(null, usersRolesView.getName(), usersRolesView.getLogin(),
				usersRolesView.getPassword());

		// Вариант при login UNIQUE
		// Users editingUser =
		// this.usersRepository.findOneByLogin(users.getLogin());

		List<Users> usersList = this.usersRepository.findAllByLogin(users.getLogin());
		Users editingUser = usersList.size() != 0 ? usersList.get(0) : null;

		if (editingUser == null) {
			return new ErrorReportMessage().addError("Пользователя с логином: " + usersRolesView.getLogin() + " не существует.");
			
			// Реализация добавления нового пользователя в БД
			//
			// this.usersRepository.save(users);
			// users = this.usersRepository.findOneByLogin(users.getLogin());
			// for (Long roleId : usersRolesView.getRoles())
			// this.userRolesRepository.save(new UserRoles(users.getId(),
			// roleId));

		} else {

			users = new Users(editingUser.getId(), users.getName(), users.getLogin(), users.getPassword());
			this.usersRepository.save(users);

			this.userRolesRepository.deleteInBatch(this.userRolesRepository.findByUserid(editingUser.getId()));
			for (Long roleId : usersRolesView.getRoles()) {

				this.userRolesRepository.save(new UserRoles(null, editingUser.getId(), roleId));

			}
			return new ReportMessage();
		}
		
	}

}
