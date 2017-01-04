package control;

import java.io.IOException;

import entity.ScreensInfo;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.animation.ScaleTransitionBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HomepageController is the controller after user logged in.
 * this the main menu of the user, here he manages the actions in system.
 * @author nire
 */
public class HomepageUserController implements ScreensIF {
	
	/**
	 * the main content frame
	 */
	@FXML
	private AnchorPane content;
	private entity.User user;
	@FXML private ComboBox settingList;

	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#pressedCloseMenu(javafx.event.ActionEvent)
	 */
	@Override
	public void pressedCloseMenu(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType, java.lang.String)
	 */
	@Override
public void actionOnError(ActionType type, String errorCode) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE)
		{
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}
	
	
	/**Handler when pressed "search book". this function open the search book form.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void searchBookButtonPressed(ActionEvent event) throws IOException{    
		try{
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.SEARCH_BOOK_SCREEN));
			content.getChildren().add(root);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Handler when pressed "set Account Type". this function open the account type request form.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void settingsButtonPressed(ActionEvent event) throws IOException{    
		try{
			//help: https://www.youtube.com/watch?v=Y-NjIPV1kLQ
			//content.getChildren().remove(0);
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.HOMEPAGE_SET_ACCOUNT_TYPE_SCREEN));
			content.getChildren().add(root);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialize account type list with available account types
	 * for the user
	 * @param type
	 */
	@FXML
	public void initializeSettingList(ActionType type)
	{
		if(user.getAccountType()!=entity.AccountType.Intrested)
		{
			//Do Nothing
		}
		else
		{
			settingList.getItems().addAll("Per book sub.","Monthly sub.","Yearly sub.");
		}
		settingList.setPromptText("Select sub.");
	}
	/**
	 * Update's User information to pending and send a notification to librarian
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void submitSettingButtonPressed(ActionEvent event)throws IOException
	{
		String choice=(String)settingList.getValue();
		try
		{
			switch ((String)settingList.getValue())
			{
			case "Per book sub":
				user.setAccountStatus("PendingPerBook");
			case "Monthly sub":
				user.setAccountStatus("PendingMonthly");
			case "Yearly sub":
				user.setAccountStatus("PendingYearly");
			}
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		
	}


}
