package control;

import java.io.IOException;
import java.util.ArrayList;

import boundry.ClientUI;
import entity.SearchBookResult;
import entity.SearchUserResult;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserPageController implements ScreensIF{

	@FXML private Label userLable;
	@FXML private Label usernameLable;
	@FXML private Label passwordLable;
	@FXML private Label fNameLable;
	@FXML private Label lNameLable;
	@FXML private CheckBox blockedCheckBox;
	@FXML private Label accountStatusLable;
	@FXML private Label accountTypeLable;
	@FXML private Label endSubscroptionLable;
	@FXML private Label eosTextLable;
	
	@FXML private TabPane userTabPane;
	@FXML private Tab editUserLibrarianTab;
	@FXML private Tab editUserManagerTab;
	@FXML private Tab userReportTab;
	
	@FXML private AnchorPane editUserLibrarianContent;
	@FXML private AnchorPane editUserManagerContent;
	@FXML private AnchorPane userReportContent;
	
	@FXML private ImageView userImageView;
	
	
		/**
	 * user information
	 */
	public static SearchUserResult searchedUserPage;
	
	@FXML
	public void initialize()
	{ 
		userLable.setText(searchedUserPage.getFirstName()+" "+searchedUserPage.getLastName()+ " ("+searchedUserPage.getUsername()+")");
		Image userImagePath = new Image("/img/user.png");
		userImageView.setImage(userImagePath);
		usernameLable.setText(searchedUserPage.getUsername());
		passwordLable.setText(searchedUserPage.getPassword());
		fNameLable.setText(searchedUserPage.getFirstName());
		lNameLable.setText(searchedUserPage.getLastName());
		if(searchedUserPage.getIsBlocked()=="YES")
			blockedCheckBox.setSelected(true);
		blockedCheckBox.setDisable(true);
		accountStatusLable.setText(searchedUserPage.getAccountStatus());
		accountTypeLable.setText(searchedUserPage.getAccountType());
		endSubscroptionLable.setText(searchedUserPage.getEndSubscription());
		
		
		if(!searchedUserPage.getAccountType().equals("Yearly") && !searchedUserPage.getAccountType().equals("Monthly"))
		{
			endSubscroptionLable.setVisible(false);
			eosTextLable.setVisible(false);
		}
		
		if(ClientUI.getTypeOfUser()=="Librarian")
		{
			userTabPane.getTabs().remove(3); //remove user report tab
			userTabPane.getTabs().remove(2); //remove block user tab
		}
		else if(ClientUI.getTypeOfUser()=="Manager")
			userTabPane.getTabs().remove(1); //remove edit user tab
			
	}

	
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
