package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.Login;
import entity.Message;
import entity.ScreensInfo;
import entity.User;
import entity.Worker;
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
	
	/**
	 * save the connected librarian
	 */
	private static Worker connectedLliberian;
	
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
	
	/** Handler when pressed "Logout". this function log out the current librarian.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void logoutButtonPressed(ActionEvent event) throws IOException{    
		try{
			logout();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** This function log out the current liberarian from the server.
	 * @throws IOException
	 */
	public void logout() throws IOException
	{
		try{
			ClientController clientCtrl = new ClientController();
			if (clientCtrl.clientConnectionController == null)
				clientCtrl.clientConnectionController = new ClientConnectionController(clientCtrl.IP_ADDRESS,clientCtrl.DEFAULT_PORT);
			Login login = new Login(connectedLliberian.getId(),connectedLliberian.getPassword());
			Message message = prepareLogout(ActionType.LOGOUT,login);
			clientCtrl.clientConnectionController.sendToServer(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Send log out message to the server.
	 * @param type
	 * @param login
	 * @return
	 */
	public Message prepareLogout(ActionType type, Login login)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(0,login.getUsername());
		elementsList.add(1,login.getPassword());
		message.setElementsList(elementsList);
		return message;
	}
	
	
	
	
	/**
	 * @return the connected liberian.
	 */
	public Worker getConnectedLiberian()
	{
		return connectedLliberian;
	}
	
	/**Setter of the connected liberian.
	 * @param connectedUser - Set the connected liberian.
	 */
	public void setConnectedLiberian(Worker connectedLiberian)
	{
		this.connectedLliberian = connectedLiberian;
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
