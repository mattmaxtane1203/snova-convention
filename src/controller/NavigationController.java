package controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import view.AdminFansPage;
import view.AdminHomePage;
import view.AdminInfluencersPage;
import view.AdminVendorsPage;
import view.FanHomePage;
import view.FanPanelPage;
import view.FanTransactionPage;
import view.FanVendorPage;
import view.InfluencerHomePage;
import view.LoginPage;
import view.RegisterPage;
import view.VendorHomePage;

public class NavigationController {

//	Functions to navigate to different pages
//	All functions have button and stage as parameters

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

//	Fan-related pages
	public static void navigateFanHomePage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new FanHomePage(stage, currentUser);
		});
	}

	public static void navigateFanPanelPage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new FanPanelPage(stage, currentUser);
		});
	}

	public static void navigateFanVendorPage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new FanVendorPage(stage, currentUser);
		});
	}

	public static void navigateFanTransactionPage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new FanTransactionPage(stage, currentUser);
		});
	}

//	Influencer-related pages
	public static void navigateInfluencerHomePage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new InfluencerHomePage(stage, currentUser);
		});
	}

//	Vendor-related pages
	public static void navigateVendorHomePage(Button btn, Stage stage, User currentUser) {
		btn.setOnMouseClicked(e -> {
			new VendorHomePage(stage, currentUser);
		});
	}
}
