package ru.garf.ff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.garf.ff.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findOneByLogin(String login);
	

}
