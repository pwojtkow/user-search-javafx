package com.starterkit.javafx.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.starterkit.javafx.dataprovider.UsersDataProvider;
import com.starterkit.javafx.dataprovider.data.UserVO;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class UserProfileController {

	
	@FXML 
	private TextField login;
	
	@FXML 
	private TextField name;
	
	@FXML 
	private TextField surname;
	
	@FXML 
	private TextField email;
	
	@FXML 
	private TextField password;
	
	@FXML 
	private TextArea aboutMe;
	
	@FXML 
	private TextArea lifeMotto;

	@FXML 
	private Button cancelButton;

	@FXML 
	private Button saveButton;

	private UserVO userToEdit;
	
	private final UsersDataProvider usersDataProvider = UsersDataProvider.INSTANCE;
	
	@FXML 
	private void cancelButtonAction() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML 
	private void saveButtonAction() {
		Task<UserVO> savingTask = new Task<UserVO>() {
			
			
			@Override
			protected UserVO call() throws Exception {

				UserVO user = new UserVO();
				user.setAboutMe(aboutMe.getText());
				user.setEmail(email.getText());
				user.setId(userToEdit.getId());
				user.setLifeMotto(lifeMotto.getText());
				user.setLogin(login.getText());
				user.setName(name.getText());
				user.setPassword(password.getText());
				user.setSurname(surname.getText());
				
				/*
				 * Get the data.
				 */
				UserVO result = usersDataProvider.updateUser(user);

				/*
				 * Value returned from this method is stored as a result of task
				 * execution.
				 */
				return result;

			}

			@Override
			protected void succeeded() {

				/*
				 * Get result of the task execution.
				 */
				UserVO result = getValue();

				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Succes!");
				alert.setHeaderText(null);
				alert.setContentText("User with id: " + result.getId() + " has been edited");

				alert.showAndWait();
				
				Stage stage = (Stage) saveButton.getScene().getWindow();
				stage.close();
				
			}

		};
		
		new Thread(savingTask).start();
		
	}

	public void initData(UserVO selectedUser) {
		
		Task<UserVO> readUserTask = new Task<UserVO>() {

			@Override
			protected UserVO call() throws Exception {
				userToEdit = usersDataProvider.findUserByLogin(selectedUser.getLogin());				
				return userToEdit;
			} 
			
			@Override
			protected void succeeded() {
				login.setText(userToEdit.getLogin());
				name.setText(userToEdit.getName());
				surname.setText(userToEdit.getSurname());
				email.setText(userToEdit.getEmail());
				password.setText(userToEdit.getPassword());
				aboutMe.setText(userToEdit.getAboutMe());
				lifeMotto.setText(userToEdit.getLifeMotto());
			}
			
			@Override
			protected void failed() {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setContentText("Failed to read user data");

				alert.showAndWait();
				
				Stage stage = (Stage) saveButton.getScene().getWindow();
				stage.close();
			}
			
		};
		
		new Thread(readUserTask).start();
		
	}
	
	
}
