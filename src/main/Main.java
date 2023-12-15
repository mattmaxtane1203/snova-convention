package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.InfluencerPage;
import view.RegisterPage;

public class Main extends Application {

	public Main() {
		
	}

	public static void main(String[] args) {
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
		//new Main();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new RegisterPage(primaryStage);
//		new InfluencerPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
