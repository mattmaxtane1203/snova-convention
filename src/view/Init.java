package scene;

import db.Connect;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Init {
	
	Connect con;
	
	Scene scene;
	BorderPane bp;
	
	private void initialize() {
		bp = new BorderPane();
		scene = new Scene(bp, 600, 600);
	}
	
	private void layouting() {
		
	}
	
	private void actions() {
		
	}

	public Init(Stage stage) {
		this.con = Connect.getInstance();
		
		if(con.isConnected() == false) {
			System.out.println("DB failed to connect.");
		} else {
			System.out.println("DB successfully connected.");
		}
		
		initialize();
		layouting();
		actions();
		
		stage.setScene(scene);
	}

}
