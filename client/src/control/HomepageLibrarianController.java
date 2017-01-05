package control;

import java.io.IOException;

import entity.ScreensInfo;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * HomepageLibrarianController is the controller after user logged in.
 * this the main menu of the librarian, here he manages the actions in system.
 * librarian got advanced premissions.
 * @author nire
 */
public class HomepageLibrarianController implements ScreensIF {

	
	/**
	 * the main content frame
	 */
	@FXML private AnchorPane content;
	
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
		// TODO Auto-generated method stub
		
	}
	
	
	/** Handler when pressed "Pending Registration requests". this function open the Pending Registration requests form.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void pendingRegistrationButtonPressed(ActionEvent event) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.HOMEPAGE_PENDING_REGISTRATION_SCREEN));
			content.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
