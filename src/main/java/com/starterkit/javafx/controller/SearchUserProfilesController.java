package com.starterkit.javafx.controller;

import static com.starterkit.javafx.ApplicationProperties.BASE_BUNDLE_PATH;
import static com.starterkit.javafx.ApplicationProperties.CONNECTION_WITH_DATABASE_FAILED_MESSAGE;
import static com.starterkit.javafx.ApplicationProperties.EMPTY_TABLE_TEXT;
import static com.starterkit.javafx.ApplicationProperties.ERROR_WINDOW_TITLE;
import static com.starterkit.javafx.ApplicationProperties.FAILED_TO_DELETE_USER_MESSAGE;
import static com.starterkit.javafx.ApplicationProperties.SUCCESS_WINDOW_TITLE;
import static com.starterkit.javafx.ApplicationProperties.USER_DELETED_MESSAGE_PART1;
import static com.starterkit.javafx.ApplicationProperties.USER_DELETED_MESSAGE_PART2;
import static com.starterkit.javafx.ApplicationProperties.USER_PROFILE_CONTROLLER_FXML_PATH;
import static com.starterkit.javafx.ApplicationProperties.USER_PROFILE_WINDOW_TITLE;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import com.starterkit.javafx.dataprovider.UsersDataProvider;
import com.starterkit.javafx.dataprovider.data.UserVO;
import com.starterkit.javafx.model.SearchUserProfiles;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller for the user search screen.
 * @author PWOJTKOW
 */
public class SearchUserProfilesController {


	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	@FXML
	private TextField userLogin;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private Button searchButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private BorderPane stage;

	@FXML
	private SearchUserProfiles model = new SearchUserProfiles();

	@FXML
	private TableView<UserVO> resultTable;

	@FXML
	private TableColumn<UserVO, String> userLoginColumn;

	@FXML
	private TableColumn<UserVO, String> firstNameColumn;

	@FXML
	private TableColumn<UserVO, String> emailColumn;

	@FXML
	private TableColumn<UserVO, String> lastNameColumn;
	
	private final UsersDataProvider usersDataProvider = UsersDataProvider.INSTANCE;

	@FXML
	private void initialize() {
		
		initializeResultTable();

		userLogin.textProperty().bindBidirectional(model.loginProperty());
		firstName.textProperty().bindBidirectional(model.nameProperty());
		lastName.textProperty().bindBidirectional(model.surnameProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		searchButton.disableProperty().bind(userLogin.textProperty().isEmpty().and(firstName.textProperty().isEmpty())
				.and(lastName.textProperty().isEmpty()));
		editButton.disableProperty().bind(Bindings.isEmpty(resultTable.getSelectionModel().getSelectedItems()));
		deleteButton.disableProperty().bind(Bindings.isEmpty(resultTable.getSelectionModel().getSelectedItems()));
	}

	private void initializeResultTable() {
		
		userLoginColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
		firstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSurname()));
		emailColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
		
		resultTable.setPlaceholder(new Label(resources.getString(EMPTY_TABLE_TEXT)));
		
	}

	@FXML
	private void searchButtonAction() {
		
		resultTable.getItems().clear();
		
		Task<Collection<UserVO>> searchingTask = new Task<Collection<UserVO>>() {

			@Override
			protected Collection<UserVO> call() throws Exception {

				Collection<UserVO> result = usersDataProvider.findUsers( //
						model.getLogin(), //
						model.getName(), //
						model.getSurname());
				
				return result;
			}

			@Override
			protected void succeeded() {
				Collection<UserVO> result = getValue();
				model.setResult(new ArrayList<UserVO>(result));
			}
			
			@Override
			protected void failed() {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(ERROR_WINDOW_TITLE);
				alert.setHeaderText(null);
				alert.setContentText(CONNECTION_WITH_DATABASE_FAILED_MESSAGE);

				alert.showAndWait();
			}
		};
		
		new Thread(searchingTask).start();
	}

	@FXML
	private void deleteButtonAction() {
		
		Task<String> deletingTask = new Task<String>() {
		String result = "";
			
			@Override
			protected String call() throws Exception {
				return result = usersDataProvider.deleteUser(resultTable.getSelectionModel().getSelectedItem());
			}

			@Override
			protected void succeeded() {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(SUCCESS_WINDOW_TITLE);
				alert.setHeaderText(null);
				alert.setContentText(USER_DELETED_MESSAGE_PART1 + result + USER_DELETED_MESSAGE_PART2);

				alert.showAndWait();
			}
			
			@Override
			protected void failed() {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(ERROR_WINDOW_TITLE);
				alert.setHeaderText(null);
				alert.setContentText(FAILED_TO_DELETE_USER_MESSAGE);

				alert.showAndWait();
			}
		};
		new Thread(deletingTask).start();
	}

	@FXML
	private void editButtonAction() throws Exception {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(USER_PROFILE_CONTROLLER_FXML_PATH),//
					ResourceBundle.getBundle(BASE_BUNDLE_PATH));
			
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(USER_PROFILE_WINDOW_TITLE);
			stage.setScene(new Scene((Pane) loader.load()));
			UserProfileController controller = loader.<UserProfileController>getController();
			controller.initData(resultTable.getSelectionModel().getSelectedItem());
			stage.setMinWidth(600);
			stage.setMinHeight(500);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOnHidden(e -> searchButtonAction());
			stage.setOnHidden(e -> userLogin.setText(""));
			stage.setOnHidden(e -> firstName.setText(""));
			stage.setOnHidden(e -> lastName.setText(""));
			
			stage.show();
	}

}
