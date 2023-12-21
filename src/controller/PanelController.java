package controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.PanelDetail;
import model.PanelHeader;
import model.User;
import view.FanPanelPage;
import view.InfluencerHomePage;

public class PanelController {

	public static ObservableList<PanelHeader> getAllPanels() {
		ObservableList<PanelHeader> panels = PanelHeader.getAllPanels();
		return panels;
	}

	public static void addPanel(User currentUser, LocalDateTime startTime, LocalDateTime endTime) {

		String panelTitle = InfluencerHomePage.titleField.getText();
		String panelDesc = InfluencerHomePage.descField.getText();
		String location = InfluencerHomePage.locField.getText();

		String res = PanelController.isValidPanelData(panelTitle, panelDesc, location, startTime, endTime);

		if (!res.equals("true")) {
			InfluencerHomePage.createPanelRes.setText("Invalid data formats");
			return;
		}

		res = PanelHeader.addPanel(currentUser.getUserID(), panelTitle, panelDesc, location, startTime, endTime);

		refreshTable(currentUser.getUserID());

		InfluencerHomePage.createPanelRes.setText(res);
	}

	public static String deleteAllPanelByInfluencer(String userID) {
		ObservableList<PanelHeader> panels = PanelHeader.getAllPanelByInfluencer(userID);
		if (panels.isEmpty()) {
			return "Failed to delete, " + userID + " doesn't have any panel to delete";
		}
		for (PanelHeader panel : panels) {
			PanelDetail.delete(panel.getPanelID());
		}
		PanelHeader.deleteAllPanelByInfluencer(userID);
		return "Succesfully deleted  all panel by UserID " + userID;
	}

//	Function to validate data for panel
	private static String isValidPanelData(String panelTitle, String panelDesc, String location,
			LocalDateTime startTime, LocalDateTime endTime) {
		
//		Check if panel title is empty
		if (panelTitle.equals("") || panelDesc.equals("") || location.equals("")) {
			return "Fields cannot be empty";
		}

//		Check if panel description is empty
		if (panelDesc.length() > 250) {
			return "Max description length is 250 characters long";
		}

//		Check if location is valid
		if (!PanelController.hasTwoWords(location)) {
			return "Location must be at least 2 words long.";
		}

		LocalDateTime now = LocalDateTime.now();

//		Validate start time
		if (startTime.isBefore(now)) {
			return "Start time must be later than the current time.";
		}

		if (startTime.isAfter(endTime)) {
			return "Start time cannot be later than end time.";
		}

		return "true";
	}

	public static PanelHeader getPanel(String panelID) {
		PanelHeader panel = PanelHeader.getPanel(panelID);
		return panel;

	}

	public static ObservableList<PanelHeader> getAllFinishedPanels() {
		ObservableList<PanelHeader> panels = PanelHeader.getAllFinishedPanels();
		return panels;
	}

	public static ObservableList<PanelHeader> getAllUnfinishedPanels() {
		ObservableList<PanelHeader> panels = PanelHeader.getAllUnFinishedPanels();
		return panels;
	}

	public static ObservableList<PanelHeader> getAllFinishedPanelsByInfluencer(String userID) {
		ObservableList<PanelHeader> panels = PanelHeader.getAllFinishedPanelsByInfluencer(userID);
		return panels;
	}

	public static ObservableList<PanelHeader> getAllUnfinishedPanelsByInfluencer(String userID) {
		ObservableList<PanelHeader> panels = PanelHeader.getAllUnFinishedPanelsByInfluencer(userID);
		return panels;
	}

	public static void finishPanel(String panelID) {
		PanelHeader.finishPanel(panelID);
	}

	public static String attendPanel(String panelID, String userID) {
		PanelHeader panel = getPanel(panelID);
		if (panel.getIsFinished().equals("Finished")) {
			return "Can't no longer attend, this panel already finished";
		}
		ObservableList<PanelDetail> panelsD = getAllAttendees(panelID);
		for (PanelDetail panelDetail : panelsD) {
			if (panelDetail.getUserID().equals(userID)) {
				return "Already attended this panel";
			}
		}
		PanelDetail.addAttendee(panelID, userID);
		return "Succesfully attended this panel";
	}

	public static ObservableList<PanelDetail> getAllAttendees(String panelID) {
		ObservableList<PanelDetail> panelsD = PanelDetail.getAllAttendees(panelID);
		return panelsD;
	}

	public static String deleteFanAttendance(String userID) {
		PanelDetail.deleteFanAttendance(userID);
		return "All attendance panel for this UserID " + userID + " has been deleted";
	}

//	Function to attend a panel
	public static void attendAction(Button attendBtn, PanelHeader selectedPanel, User currentUser) {
		
//		If attend button is pressed, call attend panel function
		attendBtn.setOnAction(e -> {
			String res = PanelController.attendPanel(selectedPanel.getPanelID(), currentUser.getUserID());
			FanPanelPage.response.setText(res);
		});
	}

//	Function to refresh table in Influencer Home Page
	public static void refreshTable(String userID) {
		InfluencerHomePage.upcomingPanelTable.setItems(getAllUnfinishedPanelsByInfluencer(userID));
		InfluencerHomePage.finishedPanelTable.setItems(getAllFinishedPanelsByInfluencer(userID));
	}

//	Function to handle the creation of panel
	public static void handleCreatePanel(Button btn, User currentUser) {
		
//		If create panel button is pressed
		btn.setOnMouseClicked(e -> {

//			Get start and end date from input fields
			LocalDateTime startDateTime = LocalDateTime.of(InfluencerHomePage.startDatePicker.getValue(),
					parseTime(InfluencerHomePage.startTimeField.getText()));
			LocalDateTime endDateTime = LocalDateTime.of(InfluencerHomePage.endDatePicker.getValue(),
					parseTime(InfluencerHomePage.endTimeField.getText()));

			addPanel(currentUser, startDateTime, endDateTime);
			refreshTable(currentUser.getUserID());
		});
	}

//	Function to parse time from text field
	public static LocalTime parseTime(String timeString) {
		try {
			
//			Parse time of pattern HH:mm
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			return LocalTime.parse(timeString, formatter);
		} catch (DateTimeParseException e) {
			InfluencerHomePage.createPanelRes.setText("Please fix time format to HH:mm");
			return null;
		}
	}

//	Function to view panel detail in finished panel table
	public static void viewPanelAction(Button btn) {
		btn.setOnMouseClicked(e -> {
			
//			Get selected panel header from finished panel table
			PanelHeader selectedPanel = (PanelHeader) InfluencerHomePage.finishedPanelTable.getSelectionModel()
					.getSelectedItem();

			if (selectedPanel != null) {
				
//				View panel
				InfluencerHomePage.panelDetail(selectedPanel.getPanelID());
				return;
			}

			InfluencerHomePage.panelRes.setText("Please select a panel.");
		});
	}

//	Function to finish panel
	public static void finishPanelAction(Button btn, User currentUser) {
		btn.setOnMouseClicked(e -> {
			
//			Get selected panel header from upcoming panel table
			PanelHeader selectedPanel = (PanelHeader) InfluencerHomePage.upcomingPanelTable.getSelectionModel()
					.getSelectedItem();

			if (selectedPanel != null) {
				
//				Finish panel and refresh table
				PanelController.finishPanel(selectedPanel.getPanelID());
				PanelController.refreshTable(currentUser.getUserID());
				return;
			}

			InfluencerHomePage.panelRes.setText("Please select a panel");
		});
	}

//	Function to view panel detail in upcoming panel table
	public static void viewPanelDetailAction(Button btn) {
		btn.setOnMouseClicked(e -> {

			PanelHeader selectedPanel = (PanelHeader) FanPanelPage.upcomingPanelTable.getSelectionModel()
					.getSelectedItem();

			if (selectedPanel != null) {
				FanPanelPage.panelDetail(selectedPanel);
			}
		});
	}

	// VALIDASI TAMBAHAN
	private static Boolean hasTwoWords(String input) {
		String[] words = input.split("\\s+");
		if (words.length >= 2) {
			return true;
		}
		return false;
	}

}
