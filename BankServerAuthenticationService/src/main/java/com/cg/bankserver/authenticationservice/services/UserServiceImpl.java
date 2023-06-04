package com.cg.bankserver.authenticationservice.services;

import com.cg.bankserver.authenticationservice.config.AppConstants;
import com.cg.bankserver.authenticationservice.config.Utilities;
import com.cg.bankserver.authenticationservice.dao.UserDAO;
import com.cg.bankserver.authenticationservice.entities.User;
import com.cg.bankserver.authenticationservice.exceptions.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO usersRepository;

	@Override
	public List<User> getUsersList() {
		List<User> usersLst = new ArrayList<User>();
		usersRepository.findAll().forEach(user -> usersLst.add(user));
		return usersLst;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public User encryptAndSave(User user) throws UsernameAlreadyExistsException {
		if (usersRepository.existsByUsername(user.getUsername()))
			throw new UsernameAlreadyExistsException("Username : " + user.getUsername() + " is already taken");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(AppConstants.bCryptEncoderStrength, new SecureRandom());
		String secPwd = encoder.encode(user.getPassword());
		user.setPassword(secPwd);
		return saveOrUpdate(user);
	}  
	
	@Override
	public User encryptAndUpdate(User user)   
	{  
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(AppConstants.bCryptEncoderStrength, new SecureRandom());
		String secPwd = encoder.encode(user.getPassword());
		user.setPassword(secPwd);
		return saveOrUpdate(user);
	} 
	
	@Override
	@Caching(evict = {@CacheEvict(value="user", key = "#user.getId()", allEntries = true)}, put = {@CachePut(value = "user", key = "#user.getId()", unless="#result == null")})
	public User saveOrUpdate(User user)   
	{  
		return usersRepository.save(user);  
	}  

	@Override
	@CacheEvict(value="user", key = "#user.getId()", allEntries = true)
	public void deleteEntry(User user) {
		usersRepository.delete(user);
	}
	
	@Override
	public User getUserByUsrNameAndPwd(User user) {
		return usersRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
	@Override
	public Optional<User> getByUsrName(String userName) {
		return usersRepository.findByUsername(userName);
	}
	
	@Override
	@Cacheable(value="user", key = "#id", unless="#result == null")
	public Optional<User> findById(String id) {
		return usersRepository.findById(id);
	}
	
	@Override
	public List<User> findUsersByStr(String searchStr) {
		List<User> result = new ArrayList<User>();

		HashSet<User> tmpresult = new HashSet<User>();
		
		HashSet<User> artName = usersRepository.findByUsernameContaining(searchStr);
//		HashSet<User> artPreview = usersRepository.findByFullnameContaining(searchStr);
		
		if(artName!=null && artName.size()>0) {
			tmpresult.addAll(artName);
		}
//		if(artPreview!=null && artPreview.size()>0) {
//			tmpresult.addAll(artPreview);
//		}
		
		result = new ArrayList<User>(tmpresult);
		System.out.println(result.size());
		
		return result;
	}
	
}