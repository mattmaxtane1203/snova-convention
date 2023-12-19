package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import model.TransactionDetail;
import model.User;

import java.math.BigDecimal;

import controller.ItemController;
import controller.NavigationController;
import controller.TransactionController;

public class FanTransactionPage {

    User currentUser;

    // Scene, BorderPane, VBox
    Scene transactionScene;
    BorderPane transactionPane = new BorderPane();
    VBox transactionContainer = new VBox(10);
    VBox paneBox = new VBox();

    // Table View & Columns
    TableView<TransactionDetail> transactionTable = new TableView<>();
    TableColumn<TransactionDetail, String> itemNameColumn = new TableColumn<>("Item Name");
    TableColumn<TransactionDetail, String> itemPriceColumn = new TableColumn<>("Item Price");
    TableColumn<TransactionDetail, String> quantityColumn = new TableColumn<>("Quantity");

    Button backButton = new Button("Back");


    private void styling() {
        transactionTable.setMaxWidth(600);
        transactionTable.setMaxHeight(300);

        itemNameColumn.setPrefWidth(200);
        itemPriceColumn.setPrefWidth(200);
        quantityColumn.setPrefWidth(200);

        itemNameColumn.setResizable(false);
        itemPriceColumn.setResizable(false);
        quantityColumn.setResizable(false);
    }

    private void init() {
        itemNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ItemController.getItem(cellData.getValue().getItemID()).getItemName()));
        itemPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ItemController.getItem(cellData.getValue().getItemID()).getPrice().toString()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString()));

        transactionTable.getColumns().addAll(itemNameColumn, itemPriceColumn, quantityColumn);

        ObservableList<TransactionDetail> transactions = TransactionController.getTransactionByFan(currentUser.getUserID());
        transactionTable.setItems(transactions);

        transactionContainer.getChildren().addAll(transactionTable, backButton);
        paneBox.getChildren().add(transactionContainer);
        transactionPane.setCenter(paneBox);

        transactionScene = new Scene(transactionPane, 600, 600);
    }

    private void action(Stage stage) {
        NavigationController.navigateFanHomePage(backButton, stage, currentUser);
    }
    
    public FanTransactionPage(Stage stage, User currentUser) {
    	this.currentUser = currentUser;
    	
    	init();
    	styling();
    	action(stage);
    	
    	stage.setTitle("Fan Transactions");
    	stage.setResizable(false);
    	stage.setScene(transactionScene);
    	stage.show();
    }
}
