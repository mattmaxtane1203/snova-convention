package main;

import controller.TransactionController;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.AdminHomePage;
import view.FanHomePage;
import view.FanPanelPage;
import view.InfluencerHomePage;
import view.LoginPage;
import view.RegisterPage;
import view.VendorHomePage;

public class Main extends Application {

	public Main() {
		
	}

	public static void main(String[] args) {
//		new Main();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
