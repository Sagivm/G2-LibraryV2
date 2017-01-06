package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Message;
import entity.Register;
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

	/**
	 * Initialize account type list with available account types for the user
	 * 
	 * @param type
	 */
	@FXML
	 public void initializeSettingList() {
		settingList.setPromptText("Select sub.");
		HomepageUserController userPage = new HomepageUserController();
		if (userPage.getConnectedUser().getAccountType() != entity.AccountType.Intrested) {
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
	public void submitSettingButtonPressed(ActionEvent event) throws IOException {
		boolean valid = true;
		String choice=new String();
		if ((String) settingList.getSelectionModel().getSelectedItem() != null) {
			HomepageUserController userPage = new HomepageUserController();
			switch ((String) settingList.getValue()) {
			case "Per book sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingPerBook");
				choice="PendingPerBook";
				userPage.getConnectedUser().setAccountStatus("PendingPerBook");
				break;
			}
			case "Monthly sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingMonthly");
				choice="PendingMonthly";
				userPage.getConnectedUser().setAccountStatus("PendingMonthly");
				break;
			}
			case "Yearly sub.": {
				HomepageUserController.getConnectedUser().setAccountStatus("PendingYearly");
				choice="PendingYearly";
				userPage.getConnectedUser().setAccountStatus("PendingYearly");
				break;
			}
			}
			ArrayList <String> elementsList = new ArrayList<String>();
			elementsList.add(0,HomepageUserController.getConnectedUser().getId());
			elementsList.add(1,choice);
			Message message=new Message(ActionType.ACCOUNTTYPEREQ,elementsList);
			try {
				ClientController.clientConnectionController.sendToServer(message);
				
			} catch (IOException e) {
						
				actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
			}
		}
		else 
		{
			actionToDisplay("Info", ActionType.CONTINUE,"Subscription must be selected");
		}
		
	}

	/**
	 * This function choose what to display the user.
	 * @param type
	 *            - Defines if alert or info.
	 * @param actiontype
	 *            - Gets the type of action after display.
	 * @param message
	 *            - Gets the message to display in popup.
	 */
	public void actionToDisplay(String type,ActionType actiontype, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(type);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		if (actiontype == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (actiontype == ActionType.CONTINUE)
			return;
	}

}
