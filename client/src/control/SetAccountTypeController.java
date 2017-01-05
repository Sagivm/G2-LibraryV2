package control;

import java.io.IOException;

import enums.ActionType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

/**
 * SetAccountTypeController is the controller that responsible to send the Account Type request
 * of the user to the DB.
 * @author sagivm
 */
public class SetAccountTypeController {
	
	
	/**
	 * List of Account Types
	 */
	@FXML private ComboBox settingList;
	
	private entity.User user;
	
	/**
	 * Initialize account type list with available account types for the user
	 * 
	 * @param type
	 */
	@FXML
	//public void initializeSettingList(ActionType type) {
	public void initializeSettingList() {
		if (user.getAccountType() != entity.AccountType.Intrested) {
			// Do Nothing
		} else {
			settingList.getItems().addAll("Per book sub.", "Monthly sub.", "Yearly sub.");
			settingList.setPromptText("Select sub.");
		}
	}
	
	/**
	 * Update's User information to pending and send a notification to librarian
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void submitSettingButtonPressed(ActionEvent event) {
		boolean valid = true;
		switch ((String) settingList.getValue()) {
		case "Per book sub": {
			user.setAccountStatus("PendingPerBook");
			break;
		}
		case "Monthly sub": {
			user.setAccountStatus("PendingMonthly");
			break;
		}
		case "Yearly sub": {
			user.setAccountStatus("PendingYearly");
			break;
		}
		case "": {
			actionToDisplay(ActionType.CONTINUE, "Please select a valid subscription");
			valid = false;
			break;
		}
		}
		if(valid==true)
		{
			
		}
	}
	
	/**
	 * This function choose what to display the user.
	 * 
	 * @param type
	 *            - Gets the type of action after display.
	 * @param message
	 *            - Gets the message to display in popup.
	 */
	public void actionToDisplay(ActionType type, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		if (type == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}

}
