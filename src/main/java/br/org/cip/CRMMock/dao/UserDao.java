package br.org.cip.CRMMock.dao;

import java.util.List;

import br.org.cip.CRMMock.model.UserVO;


public interface UserDao {

	UserVO findByName(String username);
	
	UserVO findByNamePassword(String username, String password);
	
	//List<User> findAll();

	UserVO save(UserVO user);
}