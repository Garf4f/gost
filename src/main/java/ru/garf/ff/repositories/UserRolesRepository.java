package ru.garf.ff.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.garf.ff.entity.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
	List<UserRoles> findByUserid(Long userid);

}
