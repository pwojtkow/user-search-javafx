package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.javafx.dataprovider.data.UserVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Data displayed on the person search screen.
 *
 * @author Leszek
 */
public class SearchUserProfiles {
	private final StringProperty login = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty surname = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final ListProperty<UserVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getEmail() {
		return email.get();
	}
	
	public final void setEmail(String value) {
		email.set(value);
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	
	public final String getName() {
		return name.get();
	}

	public final void setName(String value) {
		name.set(value);
	}

	public StringProperty nameProperty() {
		return name;
	}

	
	public final String getLogin() {
		return login.get();
	}

	public final void setLogin(String value) {
		login.set(value);
	}

	public StringProperty loginProperty() {
		return login;
	}	
	
	
	public final String getSurname() {
		return surname.get();
	}

	public final void setSurname(String value) {
		surname.set(value);
	}

	public StringProperty surnameProperty() {
		return surname;
	}
	

	public final List<UserVO> getResult() {
		return result.get();
	}

	public final void setResult(List<UserVO> value) {
		result.setAll(value);
	}

	public ListProperty<UserVO> resultProperty() {
		return result;
	}

}
