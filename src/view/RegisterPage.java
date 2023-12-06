package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterPage {
	Scene mainScene;
	BorderPane registerPane;
	VBox registerContainer;
	VBox usernameBox, emailBox, passwordBox, confirmPasswordBox, roleBox;
	
	Label titlePage, username, email, password, confirmPassword, role, response;
	TextField usernameField, emailField;
	PasswordField passwordField, confirmPasswordField;
	ComboBox<String> roleCB;
	Button register;
	
	private void init() {
		registerPane = new BorderPane();
		registerContainer = new VBox();
		
		usernameBox = new VBox();
		emailBox = new VBox();
		passwordBox = new VBox();
		confirmPasswordBox = new VBox();
		roleBox = new VBox();
		
		titlePage = new Label("Register New Account");
		
		username = new Label("Username");
		usernameField = new TextField();
		
		email = new Label("Email");
		emailField = new TextField();
		
		password = new Label("Password");
		passwordField = new PasswordField();
		
		confirmPassword = new Label("Confirm Password");
		confirmPasswordField = new PasswordField();
		
		role = new Label("Role");
		ArrayList<String> roles = new ArrayList<>();
		roles.add("Admin");
		roles.add("Fan");
		roles.add("Influencer");
		roles.add("Vendor");
		roleCB = new ComboBox<String>();
		roleCB.getItems().addAll(roles);
		roleCB.setValue(roles.get(0));
		
		response = new Label("");
		
		register = new Button("Register");	
	}
	
	private void styling() {
		Font title = Font.font("Poppins", FontWeight.BOLD, 40);
		titlePage.setFont(title);
		
		Font text = Font.font("Poppins", 16);
		Font alert = Font.font("Poppins", FontWeight.BOLD, 16);
		username.setFont(text);
		email.setFont(text);
		password.setFont(text);
		confirmPassword.setFont(text);
		role.setFont(text);
		
		response.setFont(text);
		response.setStyle("-fx-text-fill: red;");
	}
	
	private void layouting() {
		titlePage.setPadding(new Insets(0,0,10,0));
		
		usernameBox.getChildren().addAll(username, usernameField);
		usernameBox.setPadding(new Insets(10,0,10,0));
		
		emailBox.getChildren().addAll(email, emailField);
		emailBox.setPadding(new Insets(0,0,10,0));
		
		passwordBox.getChildren().addAll(password, passwordField);
		passwordBox.setPadding(new Insets(0,0,10,0));
		
		confirmPasswordBox.getChildren().addAll(confirmPassword, confirmPasswordField);
		confirmPasswordBox.setPadding(new Insets(0,0,10,0));
		
		role.setPadding(new Insets(0,0,5,0));
		roleBox.getChildren().addAll(role, roleCB);
		roleBox.setPadding(new Insets(0,0,10,0));
		
		registerContainer.getChildren().addAll(
				titlePage,
				usernameBox, emailBox, passwordBox, confirmPasswordBox, roleBox,
				response,
				register
				);
		
		registerContainer.setPadding(new Insets(50,50,50,50));
		
		registerPane.setCenter(registerContainer);
		
		mainScene = new Scene(registerPane, 600, 600);
	}

	private void actions(Stage stage) {
		
		register.setOnMouseClicked(e -> {
			String username = usernameField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			String confirmPassword = confirmPasswordField.getText();
			String role = roleCB.getValue();
			
			String responseVal = UserController.addUser(username, email, password, confirmPassword, role);
			
			if(responseVal.equals("User successfully added")) {
				new Init(stage);
			}
			
			response.setText(responseVal);			
		});
	}
	
	public RegisterPage(Stage stage) {
		init();
		styling();
		layouting();
		actions(stage);
		stage.setScene(mainScene);
	}
}
