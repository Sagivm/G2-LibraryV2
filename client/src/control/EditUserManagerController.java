package control;

import java.io.IOException;
import java.util.ArrayList;

import boundry.ClientUI;
import entity.GeneralMessages;
import entity.Message;
import entity.ScreensInfo;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** EditUserManagerController. Responsible to enable a manager to edit user account type and freeze account.
 * @author itain
 */
public class EditUserManagerController implements ScreensIF{
	/*
	@FXML private ImageView userImageView;
	@FXML private Label usernameLable;
	@FXML private Label fNameLable;
	@FXML private Label lNameLable;
	//@FXML private ComboBox accountTypeComboBox;
	 */
	@FXML private CheckBox blockedCheckBox;
	
	@FXML private Button submitButton;
	@FXML private Button cancelButton;

	//public static SearchUserResult searchedUserPageManager;
	
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
		Image userImagePath = new Image("/img/user.png");
		userImageView.setImage(userImagePath);
		usernameLable.setText(UserPageController.searchedUserPage.getUsername());
		fNameLable.setText(UserPageController.searchedUserPage.getFirstName().trim());
		lNameLable.setText(UserPageController.searchedUserPage.getLastName().trim());
		*/
		if(UserPageController.searchedUserPage.getIsBlocked()=="YES")
				blockedCheckBox.setSelected(true);
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

	@Override
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
	
	
	public void submitButtonPressed(ActionEvent event) 
	{		
		String isBlocked="0";
		if(blockedCheckBox.isSelected())
			isBlocked="1";
		SearchUserResult user = new SearchUserResult(UserPageController.searchedUserPage.getUsername(), UserPageController.searchedUserPage.getFirstName(), UserPageController.searchedUserPage.getLastName(), "", "", isBlocked, "", "", "");
		Message message = prepareEditUser(ActionType.EDIT_USER_MANAGER,user);
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {
					
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
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
		elementsList.add(user.getIsBlocked());
		message.setElementsList(elementsList);
		return message;
	}
	
}
