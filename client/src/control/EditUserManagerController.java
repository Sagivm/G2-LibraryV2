package control;

import java.io.IOException;

import entity.SearchUserResult;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** EditUserManagerController. Responsible to enable a manager to edit user account type and freeze account.
 * @author itain
 */
public class EditUserManagerController implements ScreensIF{


	@FXML private Label usernameLable;
	@FXML private ComboBox accountTypeComboBox;
	@FXML private CheckBox freezedCheckBox;
	
	@FXML private Button submitButton;
	@FXML private Button cancelButton;

	/**
	 * user information
	 */
	public static SearchUserResult searchedUserPageManager;
	
	/**
	 * static reference of user home page.
	 */
	private static HomepageUserController userMain;
	
	/**
	 * static reference of librarian home page.
	 */
	private static HomepageLibrarianController librarianMain;
	
	/**
	 * static reference of manager home page.
	 */
	private static HomepageManagerController managerMain;
	
	@FXML
	public void initialize()
	{
		/*
		String [] tmp = new String[3]; 
		usernameLable.setText(tmp[0]);
		fNameTextField.setText(tmp[1]);
		lNameTextField.setText(tmp[2]);
		
		fNameTextField.setEditable(true);
		lNameTextField.setEditable(true);
		*/
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
