package view;

import controller.NavigationController;
import controller.PanelController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PanelHeader;
import model.User;

public class FanPanelPage {
	
	User currentUser;

	ObservableList<String> upcomingPanels = FXCollections.observableArrayList("Panel 1", "Panel 2", "Panel 3");
	ObservableList<String> finishedPanels = FXCollections.observableArrayList("Panel 4", "Panel 5", "Panel 6");

	// Scene, BorderPane, VBox
	Scene allpanelScene;
	BorderPane allpanelPane = new BorderPane();
	HBox allpanelContainer = new HBox();
	VBox paneBox = new VBox(10);

	// Table View & Columns
	TableView upcomingPanelTable = new TableView();
	TableColumn<PanelHeader, String> column1 = new TableColumn<>("Upcoming Panels");

	TableView finishedPanelTable = new TableView();
	TableColumn<PanelHeader, String> column2 = new TableColumn<>("Finished Panels");

	// Detail Button
	public Button detailButton = new Button("Detail");
	
//	Back Button
	Button backButton = new Button("Back to Home");

//	Popup-related components
	Stage popupStage = new Stage();

	Scene popupScene;
	BorderPane popupPane = new BorderPane();
	VBox popupContainer = new VBox(5);
	VBox popupPaneBox = new VBox();

	// Panel Title
	Label titleLabel = new Label("Title");
	Label title = new Label();

	// Panel Description
	Label descLabel = new Label("Description");
	Label desc = new Label();

	// Panel Location
	Label locLabel = new Label("Location");
	Label loc = new Label();

	// Panel Start Time
	Label startLabel = new Label("Start Time");
	Label start = new Label();

	// Panel End
	Label endLabel = new Label("End Time");
	Label end = new Label();

	public static Label response = new Label("");
	
	// Button
	Button attendButton = new Button("Attend Panel");

	private void styling() {

		upcomingPanelTable.setMaxWidth(300);
		finishedPanelTable.setMaxWidth(300);

		column1.setPrefWidth(300);
		column2.setPrefWidth(300);

		column1.setResizable(false);
		column2.setResizable(false);

	}

	private void init() {

		column1 = new TableColumn<>("Upcoming Panels");
		column2 = new TableColumn<>("Finished Panels");

		column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPanelTitle()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPanelTitle()));

		upcomingPanelTable.getColumns().add(column1);
		finishedPanelTable.getColumns().add(column2);

		upcomingPanelTable.setItems(PanelController.getAllUnfinishedPanels());
		finishedPanelTable.setItems(PanelController.getAllFinishedPanels());

		allpanelContainer.getChildren().addAll(upcomingPanelTable, finishedPanelTable);
		popupPaneBox.getChildren().addAll(allpanelContainer, detailButton, backButton);
		allpanelPane.setCenter(popupPaneBox);

		allpanelScene = new Scene(allpanelPane, 600, 600);

	}

	public void panelDetail(PanelHeader selectedPanel) {
		Stage popupStage = new Stage();

		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Panel Detail");

		VBox popupContainer = new VBox(5);
		VBox paneBox = new VBox();

		Label titleLabel = new Label("Title");
		Label title = new Label();

		Label descLabel = new Label("Description");
		Label desc = new Label();

		Label locLabel = new Label("Location");
		Label loc = new Label();

		Label startLabel = new Label("Start Time");
		Label start = new Label();

		Label endLabel = new Label("End Time");
		Label end = new Label();

		Button attendButton = new Button("Attend Panel");

		popupContainer.getChildren().addAll(titleLabel, title, descLabel, desc, locLabel, loc, startLabel, start,
				endLabel, end, 
				response,
				attendButton);
		paneBox.getChildren().add(popupContainer);

		popupContainer.setAlignment(Pos.CENTER);
		paneBox.setAlignment(Pos.CENTER);
		popupContainer.setMaxWidth(400);

		title.setText(selectedPanel.getPanelTitle());
		desc.setText(selectedPanel.getPanelDesc());
		loc.setText(selectedPanel.getLocation());
		start.setText(selectedPanel.getStartTime().toString());
		end.setText(selectedPanel.getEndTime().toString());
		
		PanelController.attendAction(attendButton, selectedPanel, currentUser);
		
		popupStage.setOnHidden(event -> response.setText(""));

		Scene popupScene = new Scene(paneBox, 400, 400);

		popupStage.setScene(popupScene);

		popupStage.show();
	}

	private void action(Stage stage) {
		NavigationController.navigateFanHomePage(backButton, stage, currentUser);
		
		upcomingPanelTable.setOnMouseClicked(event -> {

			if (event.getClickCount() == 1) {
				
				PanelHeader selectedPanel = (PanelHeader) upcomingPanelTable.getSelectionModel().getSelectedItem();
				
				if (selectedPanel != null) {
					updatePanelDetail(selectedPanel);
				}
			}
		});

		finishedPanelTable.setOnMouseClicked(event -> {
			
			if (event.getClickCount() == 1) {
				PanelHeader selectedPanel = (PanelHeader) finishedPanelTable.getSelectionModel().getSelectedItem();
				
				if (selectedPanel != null) {
					updatePanelDetail(selectedPanel);
				}
			}
		});

		detailButton.setOnMouseClicked(e -> {
			
			PanelHeader selectedPanel = (PanelHeader) upcomingPanelTable.getSelectionModel().getSelectedItem();
			
			if (selectedPanel != null) {
				panelDetail(selectedPanel);
			}
		});
	}

	private void updatePanelDetail(PanelHeader selectedPanel) {
		title.setText(selectedPanel.getPanelTitle());
		desc.setText(selectedPanel.getPanelDesc());
		loc.setText(selectedPanel.getLocation());
		start.setText(selectedPanel.getStartTime().toString());
		end.setText(selectedPanel.getEndTime().toString());
	}

	public FanPanelPage(Stage stage, User currentUser) {
		
		this.currentUser = currentUser;
		
		init();
		styling();
		action(stage);

		stage.setTitle("All Panels");
		stage.setResizable(false);
		stage.setScene(allpanelScene);
		stage.show();

	}

}
