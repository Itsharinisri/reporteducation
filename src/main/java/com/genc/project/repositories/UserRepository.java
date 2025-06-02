package com.genc.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genc.project.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User getUserByEmail(String email);

	User getUserByName(String username);

	@Modifying
	@Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
	void updatePassword(@Param("id") int id, @Param("password") String password);

}
