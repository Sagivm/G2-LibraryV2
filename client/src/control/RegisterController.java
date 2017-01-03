package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import entity.ScreensInfo;
import entity.Validate;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/** This class manage the actions when the guest forward to registration.
 * some of the functions here listen to actions in the menu and some include some
 * algorithms for registration.
 * @author nire
 */
public class RegisterController implements ScreensIF {

	@FXML private PasswordField passField;
	@FXML private TextField userField;
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private PasswordField confirmPassField;
	
	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#pressedCloseMenu(javafx.event.ActionEvent)
	 */
	public void pressedCloseMenu(ActionEvent event)
	{
		Platform.exit();
		System.exit(0);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#backButtonPressed(javafx.event.ActionEvent)
	 */
	@FXML
	public void backButtonPressed(ActionEvent event)
	{
        ScreenController screenController = new ScreenController();
        try {
				screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN,ScreensInfo.CLIENT_TITLE);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
        try {
			screenController.finalize();
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}	
	
	/** This function called when the guest press on submit. the function tests the
	 * integrity of the fields, and send the message to the server.
	 * @param event
	 */
	@FXML
	public void submitButtonPressed(ActionEvent event)
	{
		String password = passField.getText();
		String confirmPassword = confirmPassField.getText();
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String username = userField.getText();
		if (password.equals("") || confirmPassword.equals("") ||
			firstName.equals("") || lastName.equals("") || username.equals(""))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.EMPTY_FIELDS);
			return;
		}
		if (Validate.usernameValidate(username) == false)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.MUST_INCLUDE_ONLY_DIGITS);
			return;
		}
		
		if (password.length() < 5)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.PASSWORD_TOO_SHORT);
			return;
		}
		
		if (!password.equals(confirmPassword))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.PASSWORD_NOT_MATCH);
			return;
		}
		// register session
		
		Register register = new Register(username,password,firstName,lastName);
		Message message = prepareRegistration(ActionType.REGISTER,register);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		
		
		
	}
	
	/** When clear button pressed the function clear the fields.
	 * @param event
	 */
	@FXML
	public void clearButtonPressed(ActionEvent event)
	{
		passField.setText("");
		userField.setText("");
		firstNameField.setText("");
		lastNameField.setText("");
		confirmPassField.setText("");
	}
	
	/* (non-Javadoc)
	 * @see interfaces.ScreensIF#actionOnError(enums.ActionType, java.lang.String)
	 */
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
	
	/** This function prepare message that will be send to the server with arraylist,
	 * and the action.
	 * @param type - Gets the type of the action
	 * @param register - Gets the class with the registration information.
	 * @return - message that will send to server.
	 */
	public Message prepareRegistration(ActionType type, Register register)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(0,register.getUsername());
		elementsList.add(1,register.getPassword());
		elementsList.add(2,register.getFirstName());
		elementsList.add(3,register.getLastName());
		message.setElementsList(elementsList);
		return message;
	}
	
}
