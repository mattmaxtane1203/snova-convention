package view;

import java.math.BigDecimal;

import controller.ItemController;
import controller.NavigationController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

public class FanVendorPage {
	
	User currentUser;

	// Scene, BorderPane, VBox
	Scene allVendorScene;
	BorderPane allVendorPane = new BorderPane();
	VBox allVendorContainer = new VBox(10);
	VBox paneBox = new VBox();

	// Table View & Columns
	TableView allVendorTable = new TableView();
	TableColumn<User, String> column1 = new TableColumn<>("User ID");
	TableColumn<User, String> column2 = new TableColumn<>("Username");
	TableColumn<User, String> column3 = new TableColumn<>("Email");

	TableView allItemTable = new TableView();
	TableColumn<model.Item, String> column4 = new TableColumn<>("Name");
	TableColumn<model.Item, String> column5 = new TableColumn<>("Price");

	public static Label mainResponse = new Label("");
	Button detail = new Button("Detail");
	Button back = new Button("Back");
	
	public static Label popupResponse = new Label("");
	Button buyButton = new Button("Buy Item");

	private void styling() {

		allVendorTable.setMaxWidth(600);
		allVendorTable.setMaxHeight(300);
		allItemTable.setMaxWidth(600);

		column1.setPrefWidth(100);
		column2.setPrefWidth(200);
		column3.setPrefWidth(300);
		column4.setPrefWidth(200);
		column5.setPrefWidth(200);

		column1.setResizable(false);
		column2.setResizable(false);
		column3.setResizable(false);
		column4.setResizable(false);
		column5.setResizable(false);

	}

	private void init() {

		column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserID()));
		column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		column3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		
		column4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
		column5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));

		allVendorTable.getColumns().add(column1);
		allVendorTable.getColumns().add(column2);
		allVendorTable.getColumns().add(column3);
		allItemTable.getColumns().add(column4);
		allItemTable.getColumns().add(column5);

		allVendorTable.setItems(UserController.getAllUserInRole("Vendor"));
		allItemTable.setItems(ItemController.getAllItems());

//		If a new vendor is selected, automatically update the item table
		allVendorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateAllItemTable((User) newSelection);
            }
        });
		
		allVendorContainer.getChildren().addAll(allVendorTable, allItemTable, mainResponse, detail, back);
		paneBox.getChildren().add(allVendorContainer);
		allVendorPane.setCenter(paneBox);

		allVendorScene = new Scene(allVendorPane, 600, 600);

	}
	
//	Get all items from vendor and update table
	private void updateAllItemTable(User selectedUser) {
        ObservableList<model.Item> items = ItemController.getAllItemsByVendor(selectedUser.getUserID());
        allItemTable.setItems(items);
    }
	
	public void itemDetail() {
        model.Item selectedItem = (model.Item) allItemTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            model.Item detailedItem = ItemController.getItem(selectedItem.getItemID());

            showItemDetailPopup(detailedItem);
        } else {
            System.out.println("No item selected.");
        }
    }

    private void showItemDetailPopup(model.Item detailedItem) {
        Stage popupStage = new Stage();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Item Detail");

        Scene popupScene;
        BorderPane popupPane = new BorderPane();
        VBox popupContainer = new VBox(5);
        VBox paneBox = new VBox();


        Label nameLabel = new Label("Name");
        Label name = new Label(detailedItem.getItemName());

        Label descLabel = new Label("Description");
        Label desc = new Label(detailedItem.getItemDescription());

        Label priceLabel = new Label("Price");
        Label price = new Label(detailedItem.getPrice().toString());
        
        		
        Label qtyLabel = new Label("Quantity");
        TextField qty = new TextField();
        
        ItemController.buyItemAction(buyButton, detailedItem, qty, currentUser);

        popupContainer.getChildren().addAll(nameLabel, name, descLabel, desc, priceLabel, price,
                qtyLabel, qty, popupResponse, buyButton);
        paneBox.getChildren().add(popupContainer);
        popupPane.setCenter(paneBox);

        popupContainer.setAlignment(Pos.CENTER);
        paneBox.setAlignment(Pos.CENTER);
        popupContainer.setMaxWidth(400);

        popupScene = new Scene(popupPane, 400, 400);

        popupStage.setScene(popupScene);

        popupStage.show();
    }

	private void action(Stage stage, User currentUser) {
		NavigationController.navigateFanHomePage(back, stage, currentUser);
		detail.setOnMouseClicked(e -> itemDetail());
	}

	public FanVendorPage(Stage stage, User currentUser) {
		this.currentUser = currentUser;
		
		init();
		styling();
		action(stage, currentUser);
		
		stage.setTitle("All Vendor");
		stage.setResizable(false);
		stage.setScene(allVendorScene);
		stage.show();
	}
}
