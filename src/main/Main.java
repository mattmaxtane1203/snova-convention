package main;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.Init;

public class Main extends Application {

	public Main() {
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Init(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
