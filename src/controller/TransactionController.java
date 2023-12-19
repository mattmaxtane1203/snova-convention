package controller;

import java.util.Vector;

import javafx.collections.ObservableList;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionController {
	public static String addTransaction(String userID, String itemID, int quantity) {
		String transactionID = Integer.toString(TransactionHeader.addTransactionHeader(userID));
		if(transactionID == "-1") {
			return "Failed to add the transaction.";
		}
		TransactionDetail.addTransactionDetail(transactionID, itemID, quantity);
		return "Succesfully added the transaction.";
	}
	
	public static String deleteAllTransactionOfItem(String itemID) {
		ObservableList<TransactionDetail> td = TransactionDetail.getAllIDByItem(itemID);
		if(td.isEmpty())return "Item not available in any transaction.";
		for (TransactionDetail detail : td) {
			TransactionDetail.delete(detail.getTransactionID());
			TransactionHeader.delete(detail.getTransactionID());
		}
		return "Succesfully delete all Transaction with ItemID" + itemID;
	}
	
	public static String deleteAllTransactionByFan(String userID) {
		Vector<TransactionHeader> transactions = TransactionHeader.getAllIDByUser(userID);
		if(transactions.isEmpty())return "Failed to delete. Fan hasn't made any recent transaction.";
		TransactionHeader.deleteAllTransactionByFan(userID);
		return "Succesfully delete all Transaction with UserID" + userID;
	}
	
	public static ObservableList<TransactionDetail> getTransactionByFan(String userID) {
		ObservableList<TransactionDetail> td = TransactionDetail.getTransacationByFan(userID);
		return td;
	}
}
