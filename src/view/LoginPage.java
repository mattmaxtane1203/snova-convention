package view;

import controller.NavigationController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginPage {

	Scene mainScene;
	BorderPane loginPane;
	VBox loginContainer, emailBox, passwordBox;
	
	Label titlePage, emailLabel, passwordLabel, or;
	public static Label response;
	public static TextField emailField;
	public static PasswordField passwordField;
	Button loginBtn, registerBtn;
	
	private void init() {
		loginPane = new BorderPane();
		
		loginContainer = new VBox();
		emailBox = new VBox();
		passwordBox = new VBox();
		
		titlePage = new Label("Login");
		
		emailLabel = new Label("Email");
		emailField = new TextField();
		
		passwordLabel = new Label("Password");
		passwordField = new PasswordField();
		
		response = new Label("");
		
		loginBtn = new Button("Login");
		
		or = new Label("or");
		
		registerBtn = new Button("Register");
	}
	
	private void styling() {
		Font titleFont = Font.font("Poppins", FontWeight.BOLD, 40);
        titlePage.setFont(titleFont);

        Font textFont = Font.font("Poppins", 16);
        Font alertFont = Font.font("Poppins", FontWeight.BOLD, 16);

        emailLabel.setFont(textFont);
        passwordLabel.setFont(textFont);

        response.setFont(textFont);
        response.setStyle("-fx-text-fill: red;");
	}
	
	private void layouting() {
		titlePage.setPadding(new Insets(0,0,10,0));
		
		emailBox.getChildren().addAll(emailLabel, emailField);
		passwordBox.getChildren().addAll(passwordLabel, passwordField);
		
		loginContainer.getChildren().addAll(
				titlePage, 
				emailBox, passwordBox,
				response,
				loginBtn,
				or,
				registerBtn
		);
		
		loginContainer.setPadding(new Insets(50, 50, 50, 50));
		
		loginPane.setCenter(loginContainer);
		
		mainScene = new Scene(loginPane, 600, 600);
	}
	
	private void actions(Stage stage) {
		NavigationController.navigateRegisterPage(registerBtn, stage);
		UserController.handleLogin(loginBtn, stage);
	}
	
	public LoginPage(Stage stage) {
		init();
		styling();
		layouting();
		actions(stage);
		stage.setScene(mainScene);
	}

}
