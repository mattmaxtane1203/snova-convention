package model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Item {
	
	private static Connect con = Connect.getInstance();
	
	private String itemID, userID, itemName, itemDescription;
	private BigDecimal price;
	
	public Item(String itemID, String userID, String itemName, String itemDescription, BigDecimal price) {
		super();
		this.itemID = itemID;
		this.userID = userID;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.price = price;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public void printItemDetails() {
		System.out.println(itemID + " " + userID + " " + itemName + " " + itemDescription + " " + price);
	}

	private static ObservableList<Item> convertMultipleResultSet(ResultSet rs) {
		
		String currItemID, currUserID, currItemName, currItemDescription;
		BigDecimal currPrice;
		
		ObservableList<Item> items = FXCollections.observableArrayList();
		
		try {
			while(rs.next()) {
				currItemID = rs.getString("ItemID");
				currUserID = rs.getString("UserID");
				currItemName = rs.getString("ItemName");
				currItemDescription = rs.getString("ItemDescription"); 
				currPrice = rs.getBigDecimal("Price");
				
				Item item = new Item(currItemID, currUserID, currItemName, currItemDescription, currPrice);
				
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}

	private static Item convertResultSet(ResultSet rs) {
		
		String currItemID, currUserID, currItemName, currItemDescription;
		BigDecimal currPrice;
		
		Item item = null;
		
		try {
			if(rs.next()) {
				currItemID = rs.getString("ItemID");
				currUserID = rs.getString("UserID");
				currItemName = rs.getString("ItemName");
				currItemDescription = rs.getString("ItemDescription"); 
				currPrice = rs.getBigDecimal("Price");
				
				item = new Item(currItemID, currUserID, currItemName, currItemDescription, currPrice);
				
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ObservableList<Item> getAllItems() {
		con = Connect.getInstance();
		
		if(!con.isConnected()) {
			return null;
		}
		
		ObservableList<Item> items = FXCollections.observableArrayList();
		String query = "select * from item";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ResultSet rs = ps.executeQuery();
			
			items = Item.convertMultipleResultSet(rs);
			
			return items;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ObservableList<Item> getAllItemsByVendor(String userID) {
con = Connect.getInstance();
		
		if(!con.isConnected()) {
			return null;
		}
		
		ObservableList<Item> items = FXCollections.observableArrayList();
		String query = "select * from item where UserID = ?";
		PreparedStatement ps = con.prepareStatement(query);
		try {
			ps.setInt(1, Integer.parseInt(userID));
			ResultSet rs = ps.executeQuery();
			
			items = Item.convertMultipleResultSet(rs);
			
			return items;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static Item getItem(String itemID) {
		
		con = Connect.getInstance();
		
		if(!con.isConnected()) {
			return null;
		}
		
		Item item = null;
		String query = "select * from item where ItemID = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, Integer.parseInt(itemID));
			ResultSet rs = ps.executeQuery();
			
			item = Item.convertResultSet(rs);
			
			System.out.println(item);
			
			if(item == null) {
				System.out.println("Item does not exist");
				return null;
			}
			
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static String addItem(String userID, String itemName, String itemDescription, BigDecimal price) {
		
		con = Connect.getInstance();

		if (!con.isConnected()) {
			return "Failed to connect to database";
		}
		
		String query = "insert into item (UserID, ItemName, ItemDescription, Price) values (?, ?, ?, ?);";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, Integer.parseInt(userID));
			ps.setString(2, itemName);
			ps.setString(3, itemDescription);
			ps.setBigDecimal(4, price);
			
			int rowsAffected = ps.executeUpdate();

			ps.close();
			
			if (rowsAffected > 0) {
				return "Item added succesfully";
			} else {
				return "Failed to add item";
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Something went wrong...";
	}

	public static String deleteItem(String itemID) {
		
		String query = "delete from item where ItemID = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, Integer.parseInt(itemID));
			
			int rowsAffected = ps.executeUpdate();

			ps.close();
			
			if (rowsAffected > 0) {
				return "Item deleted succesfully";
			} else {
				return "Failed to delete item";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Something went wrong...";
	}

	public static String setItemPrice(String itemID, BigDecimal price) {
		
		String query = "update item set Price = ? where ItemID = ?;";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setBigDecimal(1, price);
			ps.setInt(2, Integer.parseInt(itemID));
			
			int rowsAffected = ps.executeUpdate();

			ps.close();
			
			if (rowsAffected > 0) {
				return "Item price edited succesfully";
			} else {
				return "Failed to edit item price";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Something went wrong...";
	}
	
	public static String deleteAllItemsByVendor(String userID) {
		
		String query = "delete from item where UserID = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, Integer.parseInt(userID));
			
			int rowsAffected = ps.executeUpdate();

			ps.close();
			
			if (rowsAffected > 0) {
				return "Items from vendor with ID " + userID + " was deleted succesfully";
			} else {
				return "Failed to delete items from vendor with ID " + userID;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Something went wrong...";
	}
}
