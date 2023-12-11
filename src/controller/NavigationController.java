package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.Init;
import view.LoginPage;
import view.RegisterPage;

public class NavigationController {

	public static void navigateHomePage(Button btn, Stage stage) {
		btn.setOnMouseClicked(e -> {			
			new Init(stage);
		});
	}
	
	public static void navigateLoginPage(Button btn, Stage stage) {
		btn.setOnMouseClicked(e -> {
			new LoginPage(stage);
		});
	}
	
	public static void navigateRegisterPage(Button btn, Stage stage) {
		btn.setOnMouseClicked(e -> {
			new RegisterPage(stage);
		});
	}
	
}
