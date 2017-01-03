package control;

import java.io.IOException;

import entity.ScreensInfo;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/**
 * HomepageController is the controller after user logged in.
 * this the main menu of the user, here he manages the actions in system.
 * @author nire
 */
public class HomepageUserController implements ScreensIF {
	
	@FXML
	private AnchorPane content;

/*	@FXML
	private void handleButtonAction(ActionEvent event) {        
		content.getChildren().setAll(FXMLLoader.load("RegisterUI.fxml"));
		//content = (AnchorPane) FXMLLoader.load("RegisterUI.fxml");
	}*/

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
		// TODO Auto-generated method stub
		
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
	

	/** Handler when pressed "settings". this function forward to SetAccountType
	 * @param event- gets the ActionEvent when the function called.
	 * @throws IOException
	 */

public void settingsButtonPressed(){
	
}
	public void settingsButtonPressed(ActionEvent event) throws IOException
	{
		
		ScreenController screenController = new ScreenController();
        try {
			screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_SET_ACCOUNT_TYPE_SCREEN,ScreensInfo.HOMEPAGE_SET_ACCOUNT_TYPE_TITLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
