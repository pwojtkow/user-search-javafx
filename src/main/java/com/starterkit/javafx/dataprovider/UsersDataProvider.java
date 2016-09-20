package com.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.UserVO;
import com.starterkit.javafx.dataprovider.impl.UsersDataProviderImpl;

public interface UsersDataProvider {

	UsersDataProvider INSTANCE = new UsersDataProviderImpl();
	
	Collection<UserVO> findUsers(String userLogin, String firstName, String lastName) throws Exception;
	
	String deleteUser(UserVO user) throws Exception;
	
	UserVO updateUser(UserVO user) throws Exception;
	
	UserVO findUserByLogin(String login) throws Exception;
}
