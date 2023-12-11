package main;

import java.math.BigDecimal;
import java.util.Vector;

import controller.ItemController;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Item;
import model.User;
import view.Init;
import view.LoginPage;
import view.RegisterPage;

public class Main extends Application {

	public Main() {
//		Vector<Item> items = ItemController.getAllItems("5");
//		
//		if(!items.isEmpty()) {
//			for (Item item : items) {
//				System.out.println(item.getItemID() + " " + item.getUserID() + " " + item.getItemName() + " " + item.getItemDescription() + " " + item.getPrice());
//			}
//		}
		
//		Item item = Item.getItem("2");
//		item.printItemDetails();
		
//		String addItemRes = ItemController.addItem("2", "TestItem", "Testing", new BigDecimal(1));
//		System.out.println(addItemRes);
		
//		String deleteItemRes = ItemController.deleteItem("1");
//		System.out.println(deleteItemRes);
		
//		String editItemPriceRes = ItemController.setItemPrice("2", new BigDecimal(12));
//		System.out.println(editItemPriceRes);
		
//		String deleteItemFromVendorRes = ItemController.deleteAllItemsByVendor("3");
//		System.out.println(deleteItemFromVendorRes);
		
//		Vector<Item> items = ItemController.getAllItemsByVendor("3");
//		if(!items.isEmpty()) {
//			for (Item item : items) {
//				System.out.println(item.getItemID() + " " + item.getUserID() + " " + item.getItemName() + " " + item.getItemDescription() + " " + item.getPrice());
//			}
//		}
	}

	public static void main(String[] args) {
//		SWITCH THESE WHEN TESTING VIEWS AND CONTROLLERS
		
//		new Main();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		new LoginPage(primaryStage);
		new RegisterPage(primaryStage);
		
		primaryStage.setTitle("SNova Convention");
		primaryStage.show();
	}

}
