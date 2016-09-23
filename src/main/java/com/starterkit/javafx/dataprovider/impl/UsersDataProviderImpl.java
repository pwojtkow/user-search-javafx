package com.starterkit.javafx.dataprovider.impl;

import static com.starterkit.javafx.ApplicationProperties.DELETE_USER_REST_SERVICE_URL;
import static com.starterkit.javafx.ApplicationProperties.FAILED_HTTP_ERROR_CODE_MESSAGE;
import static com.starterkit.javafx.ApplicationProperties.FIND_USERS_REST_SERVICE_URL;
import static com.starterkit.javafx.ApplicationProperties.UPDATE_USER_REST_SERVICE_URL;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.starterkit.javafx.dataprovider.UsersDataProvider;
import com.starterkit.javafx.dataprovider.data.UserVO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UsersDataProviderImpl implements UsersDataProvider {

	@Override
	public Collection<UserVO> findUsers(String login, String name, String surname) throws Exception{

		String url = FIND_USERS_REST_SERVICE_URL;

		if (login == null) {
			login = "";
		}
		if (name == null) {
			name = "";
		}
		if (surname == null) {
			surname = "";
		};

		Client client = Client.create();
		WebResource webResource = client.resource(url)//
				.queryParam("login", login).queryParam("name", name).queryParam("surname", surname);
		String jsonResult = webResource.accept("application/json").get(ClientResponse.class).getEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();

		
		List<UserVO> usersVO = new ArrayList<UserVO>();
			try {
				usersVO = mapper.readValue(jsonResult, new TypeReference<List<UserVO>>() {
				});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		return usersVO;

	}

	@Override
	public String deleteUser(UserVO user) throws Exception {

		String idParam = Long.toString(user.getId());

			URL url = new URL(DELETE_USER_REST_SERVICE_URL);

			Client client = Client.create();
			WebResource webResource = client.resource(url + idParam);

			ClientResponse response = webResource.type("application/json").delete(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException(FAILED_HTTP_ERROR_CODE_MESSAGE + response.getStatus());
			}

		return idParam;

	}

	@Override
	public UserVO updateUser(UserVO user) throws Exception {

		UserVO userVO = new UserVO();

			String url = UPDATE_USER_REST_SERVICE_URL;

			ObjectMapper mapper = new ObjectMapper();
			Client client = Client.create();
			WebResource webResource = client.resource(url);

			String jsonInput = mapper.writeValueAsString(user);
			ClientResponse response = webResource.type("application/json").accept("application/json").put(ClientResponse.class, jsonInput);

			userVO = response.getEntity(UserVO.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException(FAILED_HTTP_ERROR_CODE_MESSAGE + response.getStatus());
			}

		return userVO;
	}

	@Override
	public UserVO findUserByLogin(String login) throws Exception {

		UserVO userVO = new UserVO();

			String url = DELETE_USER_REST_SERVICE_URL + login;

			Client client = Client.create();
			WebResource webResource = client.resource(url);

			String jsonResult = webResource.accept("application/json").get(ClientResponse.class)
					.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			userVO = mapper.readValue(jsonResult, new TypeReference<UserVO>() {
			});

			userVO = mapper.readValue(jsonResult, new TypeReference<UserVO>() {
			});

		return userVO;
	}

}
