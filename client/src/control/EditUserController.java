package control;

import java.io.IOException;
import java.util.ArrayList;

import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/** EditUserController. Responsible to enable a librarian or a manager to edit user details.
 * @author itain
 */


public class EditUserController implements ScreensIF{
	
	/**
	 * user information
	 */
	private String userInfo;
	
	@FXML private Label usernameLable;
	@FXML private TextField fNameTextField;
	@FXML private TextField lNameTextField;
	
	@FXML private Button submitButton;
	@FXML private Button cancelButton;

	/**
	 * Book page constructor store the data.
	 * @param userInfo - Gets user's information.
	 */
	public EditUserController(String userInfo) {
		this.userInfo = userInfo;
	}

	@FXML
	public void initialize()
	{
		String [] tmp = new String[3]; 
		usernameLable.setText(tmp[0]);
		fNameTextField.setText(tmp[1]);
		lNameTextField.setText(tmp[2]);
		
		fNameTextField.setEditable(true);
		lNameTextField.setEditable(true);
	}
	
	
	public void submitButtonPressed(ActionEvent event) 
	{
		if (fNameTextField.equals("") || lNameTextField.equals(""))
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.EMPTY_FIELDS);
			return;
		}
		
		User user = new User();
		user.setId(usernameLable.getText());
		user.setFirstname(fNameTextField.getText());
		user.setLastname(lNameTextField.getText());
		
		Message message = prepareEditUser(ActionType.EDIT_USER,user);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
	}
	
	@Override
	public void backButtonPressed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pressedCloseMenu(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		
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
	 * @param user - Gets the class with the user information.
	 * @return - message that will send to server.
	 */
	public Message prepareEditUser(ActionType type, User user)
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
	
	
	

}
