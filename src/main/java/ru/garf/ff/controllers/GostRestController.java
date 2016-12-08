package ru.garf.ff.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.garf.ff.entity.Users;
import ru.garf.ff.model.ErrorReportMessage;
import ru.garf.ff.model.ReportMessage;
import ru.garf.ff.repositories.UsersRepository;

@RestController
public class GostRestController {
	@Autowired
	UsersRepository usersRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	Collection<Users> listU() {
		return this.usersRepository.findAll();
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	Users getUser(@RequestParam("id") Long id) {
		Users user = this.usersRepository.findOne(id);
		if (user != null) {
			return user;
		} else {
			System.out.println("Пользователя с ID: '" + id + "' не существует.");
			return new Users();
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	ReportMessage deleteUser(@RequestParam("id") Long id) {
		Users user = this.usersRepository.findOne(id);
		if (user != null) {
			this.usersRepository.delete(id);
			return new ReportMessage();
		} else {
			return new ErrorReportMessage().addError("Пользователя с ID: '" + id + "' не существует.");
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	ReportMessage addUser(@Valid @RequestBody Users users, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorReportMessage messageErr = new ErrorReportMessage();
			for (FieldError err : bindingResult.getFieldErrors()) {
				messageErr.addError(err.getDefaultMessage());
			}
			return messageErr;
		}
		users = this.usersRepository.save(users);
		return new ReportMessage();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = "application/json")
	ReportMessage editUser(@Valid @RequestBody Users users, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorReportMessage messageErr = new ErrorReportMessage();
			for (FieldError err : bindingResult.getFieldErrors()) {
				messageErr.addError(err.getDefaultMessage());
			}
			return messageErr;
		}

		if (this.usersRepository.findOne(users.getId()) == null) {
			return new ErrorReportMessage().addError("Пользователя с Id: '" + users.getId() + "' не существует.");
		} else {
			this.usersRepository.save(users);
			return new ReportMessage();
		}

	}

	@ExceptionHandler(value = JpaObjectRetrievalFailureException.class)
	ReportMessage globalErrorReport(JpaObjectRetrievalFailureException e){
		String message = e.getMessage();
		System.err.println(message);
		String id = message.split(";")[0].split(" id ")[1];
		return new ErrorReportMessage().addError("Роли со значение id: '" + id + "' не сущестует");
		
	}
	
	@ExceptionHandler(value = Exception.class)
	ReportMessage globalErrorReport(Exception e){
		System.err.println(e.getMessage());
		return new ErrorReportMessage().addError("Произошла какая-то глобальная ошибка");
		
	}


	
	
}
