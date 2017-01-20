package control;

import java.io.IOException;
import java.util.ArrayList;

import boundry.ClientUI;
import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import entity.ScreensInfo;
import entity.SearchBookResult;
import entity.SearchUserResult;
import entity.User;
import entity.Validate;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;

/** EditUserLibrarianController. Responsible to enable a librarian to edit user first name and last name.
 * @author itain
 */


public class EditUserLibrarianController implements ScreensIF{
	
	//@FXML private ImageView userImageView;
	//@FXML private Label usernameLable;
	@FXML private TextField fNameTextField;
	@FXML private TextField lNameTextField;
	
	@FXML private Button submitButton;
	@FXML private Button cancelButton;


	//public static SearchUserResult searchedUserPageLibrarian;
	
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
		//Image userImagePath = new Image("/img/user.png");
		//userImageView.setImage(userImagePath);
		//usernameLable.setText(UserPageController.searchedUserPage.getUsername());
		fNameTextField.setText(UserPageController.searchedUserPage.getFirstName().trim());
		lNameTextField.setText(UserPageController.searchedUserPage.getLastName().trim());
		
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
		
		if (Validate.nameValidateCharactersOnly(fNameTextField.getText()) == false)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.MUST_INCLUDE_ONLY_CHARACTERS);
			return;
		}
		
		if (Validate.nameValidateCharactersOnly(lNameTextField.getText()) == false)
		{
			actionOnError(ActionType.CONTINUE,GeneralMessages.MUST_INCLUDE_ONLY_CHARACTERS);
			return;
		}
		
		//System.out.println("change to:" + usernameLable.getText() + " " + fNameTextField.getText() + " " + lNameTextField.getText());
		
		SearchUserResult user = new SearchUserResult(UserPageController.searchedUserPage.getUsername(), fNameTextField.getText(), lNameTextField.getText(), "", "", "", "", "", "");
		Message message = prepareEditUser(ActionType.EDIT_USER_LIBRARIAN,user);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
	}
	
	
	public void cancelButtonPressed(ActionEvent event)
	{
		if(ClientUI.getTypeOfUser()=="Librarian")
    	{
        	if (librarianMain == null)
        		librarianMain = new HomepageLibrarianController();
        	librarianMain.setPage(ScreensInfo.SEARCH_USER_RESULTS_SCREEN);
    	}
    	else if(ClientUI.getTypeOfUser()=="Manager")
    	{
        	if (managerMain == null)
        		managerMain = new HomepageManagerController();
        	managerMain.setPage(ScreensInfo.SEARCH_USER_RESULTS_SCREEN);
    	}
		
		ScreenController screenController = new ScreenController();
		try{
			if(ClientUI.getTypeOfUser()=="Librarian")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);						
			else if(ClientUI.getTypeOfUser()=="Manager")
				screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);

		} 
		catch (Exception e) {
			e.printStackTrace();
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
	public Message prepareEditUser(ActionType type, SearchUserResult user)
	{
		Message message = new Message();
		message.setType(type);
		ArrayList <String> elementsList = new ArrayList<String>();
		elementsList.add(user.getUsername());
		elementsList.add(user.getFirstName());
		elementsList.add(user.getLastName());
		message.setElementsList(elementsList);
		return message;
	}
	
	
	

}
