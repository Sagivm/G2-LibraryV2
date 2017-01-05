package control;

import java.io.IOException;

import enums.ActionType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
//
/**
 * SetAccountTypeController is the controller that responsible to send the
 * Account Type request of the user to the DB.
 * 
 * @author sagivm
 */
public class SetAccountTypeController {

	/**
	 * List of Account Types
	 */
	@FXML
	private ComboBox settingList;

	private entity.User user;

	/**
	 * Initialize account type list with available account types for the user
	 * 
	 * @param type
	 */
	@FXML
	// public void initializeSettingList(ActionType type) {
	public void initializeSettingList() {
		settingList.setPromptText("Select sub.");
		if (HomepageUserController.getConnectedUser().getAccountType() != entity.AccountType.Intrested) {
			// Do Nothing
		} else
			settingList.getItems().addAll("Per book sub.", "Monthly sub.", "Yearly sub.");
		settingList.setPromptText("Select sub.");
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
		String choice = (String) settingList.getSelectionModel().getSelectedItem();
		System.out.println(choice);
		if ((String) settingList.getSelectionModel().getSelectedItem() != null) {
			switch ((String) settingList.getValue()) {
			case "Per book sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingPerBook");
				break;
			}
			case "Monthly sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingMonthly");
				break;
			}
			case "Yearly sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingYearly");
				break;
			}
			}
			//
			//
			actionToDisplay(ActionType.CONTINUE,"Subscription details were sent to librarian for his approval");
		}
		else
		{
			actionToDisplay(ActionType.CONTINUE,"Subscription must be selected");
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
