package br.org.cip.CRMMock.dao;

import java.util.List;

import br.org.cip.CRMMock.model.User;


public interface UserDao {

	User findByName(String username);
	
	User findByNamePassword(String username, String password);
	
	//List<User> findAll();

	User save(User user);
}