package com.starterkit.javafx.dataprovider;

import java.util.Collection;

import com.starterkit.javafx.dataprovider.data.UserVO;
import com.starterkit.javafx.dataprovider.impl.UsersDataProviderImpl;

public interface UsersDataProvider {

	/**
	 * User data provider object instance
	 */
	UsersDataProvider INSTANCE = new UsersDataProviderImpl();
	
	/**
	 * @param userLogin
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws Exception
	 */
	Collection<UserVO> findUsers(String userLogin, String firstName, String lastName) throws Exception;
	
	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	String deleteUser(UserVO user) throws Exception;
	
	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	UserVO updateUser(UserVO user) throws Exception;
	
	/**
	 * @param login
	 * @return
	 * @throws Exception
	 */
	UserVO findUserByLogin(String login) throws Exception;
}
