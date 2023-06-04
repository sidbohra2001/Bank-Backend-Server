package com.cg.bankserver.authenticationservice.dao;

import com.cg.bankserver.authenticationservice.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	HashSet<User> findByUsernameContaining(String username); 
	
//	HashSet<User> findByFullnameContaining(String fullname);
	
}