package main;

import controller.TransactionController;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import view.FanHomePage;
import view.FanPanelPage;
import view.InfluencerHomePage;
import view.RegisterPage;
import view.VendorHomePage;

public class Main extends Application {

	public Main() {
//		String res = TransactionController.addTransaction("26", "22", 1);
//		System.out.println(res);
	}

	public static void main(String[] args) {
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
//		new Main();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		new RegisterPage(primaryStage);
		
//		new InfluencerHomePage(primaryStage, UserController.getUser("25"));
		new VendorHomePage(primaryStage, UserController.getUser("27"));
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
