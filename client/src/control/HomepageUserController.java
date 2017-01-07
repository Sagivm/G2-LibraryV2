package control;

import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Login;
import entity.Message;
import entity.ScreensInfo;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * HomepageController is the controller after user logged in. this the main menu
 * of the user, here he manages the actions in system.
 * 
 * @author ork
 */
public class HomepageUserController implements ScreensIF {

	/**
	 * the main content frame
	 */
	@FXML private AnchorPane content;
	@FXML private Label userFullName;
	//@FXML private TextField testTextField;
	private entity.User user;
	
	/**
	 * save the connected user
	 */
	private static User connectedUser;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#pressedCloseMenu(javafx.event.ActionEvent)
	 */
	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException{
		try{
			//ClientController clientCtrl = new ClientController();
			//clientCtrl.logout(connectedUser.getId(),connectedUser.getPassword());
			//LogoutController logoutCtrl = new LogoutController();
			//logoutCtrl.logout();
			logout();
			Platform.exit();
			System.exit(0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType,
	 * java.lang.String)
	 */
	@Override
	public void actionOnError(ActionType type, String errorCode) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE) {
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}

	/**
	 * Handler when pressed "search book". this function open the search book
	 * form.
	 * 
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void searchBookButtonPressed(ActionEvent event) throws IOException {
		try {
			if(content.getChildren().size()>0)
				content.getChildren().remove(0);
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.SEARCH_BOOK_SCREEN));
			content.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handler when pressed "set Account Type". this function open the account
	 * type request form.
	 * 
	 * @param event
	 *            - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void settingsButtonPressed(ActionEvent event) throws IOException {
		try {
			// help: https://www.youtube.com/watch?v=Y-NjIPV1kLQ
			if(content.getChildren().size()>0)
				content.getChildren().remove(0);
			Parent root = FXMLLoader.load(getClass().getResource(ScreensInfo.HOMEPAGE_SET_ACCOUNT_TYPE_SCREEN));
			content.getChildren().add(root);
			//setUsernameLabel("sada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Handler when pressed "Logout". this function log out the current user.
	 * @param event - gets the ActionEvent when the function called.
	 * @throws IOException
	 */
	@FXML
	public void logoutButtonPressed(ActionEvent event) throws IOException{    
		try{
			//LogoutController logoutCtrl = new LogoutController();
			//logoutCtrl.logout();
			logout();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** This function log out the current user from the server.
	 * @throws IOException
	 */
	public void logout() throws IOException
	{
		try{
			ClientController clientCtrl = new ClientController();
			if (clientCtrl.clientConnectionController == null)
				clientCtrl.clientConnectionController = new ClientConnectionController(clientCtrl.IP_ADDRESS,clientCtrl.DEFAULT_PORT);
			Login login = new Login(connectedUser.getId(),connectedUser.getPassword());
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
	
	
    @FXML
    private void initialize() {
    	//userFullName.setText("fullName");
    	//testTextField.setText("ddd");
    }
	
	@FXML
	public void setUsernameLabel(String fullName){
			System.out.println(fullName);
			
			//System.out.println(userFullName.getText());
			
			userFullName.setText(fullName);
			
	}
	
/*	@Override
	 (non-Javadoc)
	 * @see interfaces.ScreensIF#initialize(java.net.URL, java.util.ResourceBundle)
	 
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
    	System.out.println("eee");
    	setUsernameLabel("dsa sd");
        // initialize your logic here: all @FXML variables will have been injected

    }*/
	//
	
	/**
	 * @return the connected user.
	 */
	public static User getConnectedUser()
	{
		return connectedUser;
	}
	
	/**Setter of the connected user.
	 * @param connectedUser - Set the connected user.
	 */
	public void setConnectedUser(User connectedUser)
	{
		this.connectedUser = connectedUser;
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
