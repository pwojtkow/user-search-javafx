package com.starterkit.javafx;

import static com.starterkit.javafx.ApplicationProperties.SEARCH_USER_PROFILES;
import static com.starterkit.javafx.ApplicationProperties.BASE_BUNDLE_PATH;
import static com.starterkit.javafx.ApplicationProperties.SEARCH_USER_PROFILES_WINDOW_FXML_PATH;
import static com.starterkit.javafx.ApplicationProperties.STANDARD_CSS_STYLESHEET_PATH;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application entry point
 * 
 * @author PWOJTKOW
 */
public class Startup extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		String langCode = getParameters().getNamed().get("lang");
		if (langCode != null && !langCode.isEmpty()) {
			Locale.setDefault(Locale.forLanguageTag(langCode));
		}

		primaryStage.setTitle(SEARCH_USER_PROFILES);

		/*
		 * Load screen from FXML file with specific language bundle (derived
		 * from default locale).
		 */
		Parent root = FXMLLoader.load(getClass().getResource(SEARCH_USER_PROFILES_WINDOW_FXML_PATH), //
				ResourceBundle.getBundle(BASE_BUNDLE_PATH));

		Scene scene = new Scene(root);

		/*
		 * Set the style sheet(s) for application.
		 */
		scene.getStylesheets().add(getClass().getResource(STANDARD_CSS_STYLESHEET_PATH).toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setWidth(550.0);
		primaryStage.setHeight(500.0);
		primaryStage.setResizable(false);

		primaryStage.show();
	}
}
