package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.Init;
import view.LoginPage;
import view.RegisterPage;

public class NavigationController {

	public static void homePage(Stage stage) {
		new Init(stage);
	}
	
	public static void navigateLoginPage(Button loginBtn, Stage stage) {
		
		
	}
	
	public static void navigateRegisterPage(Button registerBtn, Stage stage) {
		registerBtn.setOnMouseClicked(e -> {
			new RegisterPage(stage);
		});
	}
	
}
