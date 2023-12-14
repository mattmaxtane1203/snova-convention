package view;

import controller.PanelController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PanelHeader;

public class InfluencerPage {

	public class InfluencerVar{
		Scene scene;
		BorderPane bp = new BorderPane();
		VBox formContainer = new VBox(10);
		VBox menuContainer = new VBox();
		
		//Label
		Label titleTable = new Label("All Panels");
		Label titleForm = new Label("Create new panel");
		Label userIDLbl = new Label("UserID");
		Label titleLbl = new Label("Panel Title");
		Label descLbl = new Label("Panel Description");
		Label locationLbl = new Label("Location");
		Label startTimeLbl = new Label("Start Time");
		Label endTimeLbl = new Label("End Time");
		Label responseTxt = new Label("");
		
		
		//Field
		TextField userIDField = new TextField();
		TextField titleField = new TextField();
		TextField descField = new TextField();
		TextField locationField = new TextField();
		TextField startTimeField = new TextField();
		TextField endTimeField = new TextField();
		
		
		//Table
		TableView<PanelHeader> panelTable = new TableView<>();
		TableColumn<PanelHeader, String> panelIDCol, userIDCol, titleCol, descCol, locationCol, startCol, endCol;
		TableColumn<PanelHeader, Boolean>isFinishedCol;
		
		//Button
		Button createPanel = new Button("Create Panel");
	}
	
	private void styling(InfluencerVar var) {
		var.menuContainer.setAlignment(Pos.CENTER);
		var.formContainer.setMaxWidth(400);
	}

	//MASIH ERROR   :(  ====== List View ========
//	private TableView<PanelHeader> tableView(InfluencerVar var) {
//		var.panelIDCol = new TableColumn<>("Panel ID");
//		var.panelIDCol.setCellValueFactory(new PropertyValueFactory<>("panelID"));
//		
//		var.userIDCol = new TableColumn<>("User ID");
//		var.userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
//		
//		var.titleCol = new TableColumn<>("Panel Title");
//		var.titleCol.setCellValueFactory(new PropertyValueFactory<>("panelTitle"));
//		
//		var.descCol = new TableColumn<>("Panel Description");
//		var.descCol.setCellValueFactory(new PropertyValueFactory<>("panelDescription"));
//		
//		var.locationCol = new TableColumn<>("Location");
//		var.locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
//		
//		var.startCol = new TableColumn<>("Start Time");
//		var.startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
//		
//		var.endCol = new TableColumn<>("End Time");
//		var.endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
//		
//		var.isFinishedCol = new TableColumn<>("Is Finished");
//		var.isFinishedCol.setCellValueFactory(new PropertyValueFactory<>("isFinished"));
//		
//		
//		ObservableList<PanelHeader> panelList = FXCollections.observableArrayList();
//		panelList.addAll((PanelHeader) PanelController.getAllPanels());
//		var.panelTable.setItems(panelList);
//		return var.panelTable;
//	}
	
	private void init(InfluencerVar var) {
		var.formContainer.getChildren().addAll(
				var.titleForm,
				var.userIDLbl, var.userIDField, var.titleLbl,
				var.titleField, var.descLbl, var.descField, 
				var.locationLbl, var.locationField, var.startTimeLbl,
				var.startTimeField, var.endTimeLbl, var.endTimeField, 
				var.createPanel, var.responseTxt
				);
		
		 var.menuContainer.getChildren().add(var.formContainer);
//		 var.bp.setCenter(tableView(var));
		 var.bp.setCenter(var.menuContainer);
		 var.scene = new Scene(var.bp,800,800);
	}
	
	private void actions(InfluencerVar var) {
		
		var.createPanel.setOnMouseClicked(e -> {
			String userID = var.userIDField.getText();
			String panelTitle = var.titleField.getText();
			String panelDesc = var.descField.getText(); 
			String location  = var.locationField.getText();
			String startTime = var.startTimeField.getText();
			String endTime = var.endTimeField.getText();
			String responseVal = PanelController.addPanel(userID, panelTitle, panelDesc, location, startTime, endTime);
			var.responseTxt.setText(responseVal);			
		});
	}
	
	public InfluencerPage(Stage stage) {
		InfluencerVar var = new InfluencerVar();
		init(var);
		styling(var);
		actions(var);
		stage.setScene(var.scene);
		stage.setTitle("Influencer Menu");
		stage.setResizable(false);
		stage.show();
	}
	
}
