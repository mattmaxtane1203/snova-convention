package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import view.AdminFansPage;
import view.AdminHomePage;
import view.AdminInfluencersPage;
import view.AdminVendorsPage;
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
	
//	Admin-related Pages
	public static void navigateAdminHomePage(Stage stage, User currentAdmin) {		
		new AdminHomePage(stage, currentAdmin);
	}
	
	public static void navigateAdminHomePage(Button btn, Stage stage, User currentAdmin) {
		btn.setOnMouseClicked(e -> {		
			new AdminHomePage(stage, currentAdmin);
		});
	}
	
	public static void navigateAdminFansPage(Button btn, Stage stage, User currentAdmin) {
		btn.setOnMouseClicked(e -> {
			new AdminFansPage(stage, currentAdmin);
		});
	}
	
	public static void navigateAdminVendorsPage(Button btn, Stage stage, User currentAdmin) {
		btn.setOnMouseClicked(e -> {
			new AdminVendorsPage(stage, currentAdmin);
		});
	}
	
	public static void navigateAdminInfluencersPage(Button btn, Stage stage, User currentAdmin) {
		btn.setOnMouseClicked(e -> {
			new AdminInfluencersPage(stage, currentAdmin);
		});
	}
	
}
