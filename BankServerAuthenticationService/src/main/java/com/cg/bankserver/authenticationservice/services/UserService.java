package com.cg.bankserver.authenticationservice.services;

import java.util.List;
import java.util.Optional;

import com.cg.bankserver.authenticationservice.exceptions.UsernameAlreadyExistsException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import com.cg.bankserver.authenticationservice.entities.User;

public interface UserService {

	List<User> getUsersList();

	User encryptAndSave(User user) throws UsernameAlreadyExistsException;

	User encryptAndUpdate(User user);

	User saveOrUpdate(User user);

	void deleteEntry(User user);

	User getUserByUsrNameAndPwd(User user);

	Optional<User> getByUsrName(String userName);

	Optional<User> findById(String id);

	List<User> findUsersByStr(String searchStr);

}