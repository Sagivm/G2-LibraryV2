package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import entity.User;
import entity.Validate;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/** SearchUserController. Responsible to enable a librarian or a manager to search users.
 * @author itain
 */


public class SearchUserController implements ScreensIF{

	@FXML private TextField idTextField;
	@FXML private TextField fNameTextField;
	@FXML private TextField lNameTextField;
	
	
	/** This function called when the worker press on search. the function tests the
	 * integrity of the fields, and send the message to the server.
	 * @param event
	 */
	@FXML
	public void searchButtonPressed(ActionEvent event)
	{
		String id = idTextField.getText();
		String firstName = fNameTextField.getText();
		String lastName = lNameTextField.getText();
		
		if (id.equals("") && firstName.equals("") && lastName.equals(""))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.EMPTY_FIELDS);
			return;
		}
		
		if (Validate.usernameValidate(id) == false)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.MUST_INCLUDE_ONLY_DIGITS);
			return;
		}
		
		User user = new User();
		user.setId(id);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		
		Message message = prepareUserSearch(ActionType.SEARCH_USER,user);
		
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
		idTextField.setText("");
		fNameTextField.setText("");
		lNameTextField.setText("");
	}
	
	/** This function prepare message that will be send to the server with arraylist,
	 * and the action.
	 * @param type - Gets the type of the action
	 * @param user - Gets the class with the user information.
	 * @return - message that will send to server.
	 */
	public Message prepareUserSearch(ActionType type, User user)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(user.getId());
		elementsList.add(user.getFirstname());
		elementsList.add(user.getLastname());
		message.setElementsList(elementsList);
		return message;
	}
	
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionOnError(ActionType type, String errorCode) {
		// TODO Auto-generated method stub
		
	}

}
