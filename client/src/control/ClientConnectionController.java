package control;

import java.io.IOException;

import entity.GeneralMessages;
import entity.Replay;
import entity.ScreensInfo;
import entity.User;
import entity.Worker;
import enums.ActionType;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;

//TODO: Auto-generated Javadoc
/**
* ClientConnectionController create instance to client,
* and presave into ClientController with static variable for communication later
* with the GUI and clients.
* @author nire
*/
public class ClientConnectionController extends AbstractClient
{


/** ClientConnectionController constructor initialize the hostname, and then the port
 * for establish connection to server. it also open new connection physically for it.
 * @param host - The hostname/ip for connection.
 * @param port - The port for establish connection 
 */
public ClientConnectionController(String host, int port) {
	super(host, port);
	try {
		openConnection();
		} catch (IOException e) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(GeneralMessages.SERVER_OFFLINE);
			alert.showAndWait();
			Platform.exit();
			System.exit(0);
		}
	}

/* (non-Javadoc)
 * @see ocsf.client.AbstractClient#handleMessageFromServer(java.lang.Object)
 */
@Override
protected void handleMessageFromServer(Object msg) {
	
	Replay replay = (Replay)msg;
	actionToPerform(replay);
}

/** This function gets the replay from server and decrypt to action by his information.
 * @param replay - Gets the replay with the data.
 */
public void actionToPerform(Replay replay) {
	
	ActionType type = replay.getType();
	boolean sucess = replay.getSucess();
	
	if (type == ActionType.REGISTER)
	{
		if (sucess == true) {			
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
		        ScreenController screenController = new ScreenController();
		        try {
		        	actionToDisplay(ActionType.CONTINUE,GeneralMessages.PENDING_FOR_LIBRARIAN);
					screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN,ScreensInfo.CLIENT_TITLE);
					} catch (Exception e) {
					// COMPELETE
					e.printStackTrace();
					}
				}
		});
	}
		else
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					
			        ScreenController screenController = new ScreenController();
			        try {
			        	actionToDisplay(ActionType.CONTINUE,GeneralMessages.USER_ALREADY_EXISTS);
						screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN,ScreensInfo.CLIENT_TITLE);
						} catch (Exception e) {
						// COMPELETE
						e.printStackTrace();
						}
					}
			});	
		}
}
	if (type == ActionType.LOGIN)
	{
		if (sucess == true) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
		        ScreenController screenController = new ScreenController();
		        try {
		        	int action = replay.getAction();
		        	//System.out.println(action);
	        		actionToDisplay(ActionType.CONTINUE,GeneralMessages.USER_LOGGED_IN_SUCESSFULLY);
		        	if(action==1)
		        	{
		        		User user = new User(replay.getElementsList().get(1).toString(),replay.getElementsList().get(2).toString(),replay.getElementsList().get(0).toString(),replay.getElementsList().get(4).toString(),replay.getElementsList().get(5).toString());
		        		screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,ScreensInfo.HOMEPAGE_USER_TITLE);
						HomepageUserController userPage = new HomepageUserController();
						userPage.setConnectedUser(user);
		        		
		        	}
		        	else if(action==2)
		        	{
		        		Worker worker = new Worker(replay.getElementsList().get(1).toString(),replay.getElementsList().get(2).toString(),replay.getElementsList().get(0).toString(),replay.getElementsList().get(4).toString(),replay.getElementsList().get(5).toString(),replay.getElementsList().get(6).toString());
		        		screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);
		        	}
		        	else if(action==3)
		        	{
		        		Worker worker = new Worker(replay.getElementsList().get(1).toString(),replay.getElementsList().get(2).toString(),replay.getElementsList().get(0).toString(),replay.getElementsList().get(4).toString(),replay.getElementsList().get(5).toString(),replay.getElementsList().get(6).toString());
		        		screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,ScreensInfo.HOMEPAGE_MANAGER_TITLE);
		        	}
		        	Stage primaryStage = screenController.getStage();
					ScreenController.setStage(primaryStage);
					Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
					primaryStage.show();
					primaryStage.setX(primaryScreenBounds.getMaxX()/2.0 - primaryStage.getWidth()/2.0);
					primaryStage.setY(primaryScreenBounds.getMaxY()/2.0 - primaryStage.getHeight()/2.0);
		        } catch (Exception e) {
					// COMPELETE
					e.printStackTrace();
					}
				}
		});	
	}
		else
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					
			        ScreenController screenController = new ScreenController();
			        try {
			        	//actionToDisplay(ActionType.CONTINUE,"The password and Username not match!");
			        	actionToDisplay(ActionType.CONTINUE,replay.getGnrlMsg().toString());
			        	
						} catch (Exception e) {
						// COMPELETE
						e.printStackTrace();
						}//
					}
			});
		}
	}
}

/** This function choose what to display the user.
 * @param type - Gets the type of action after display.
 * @param message - Gets the message to display in popup.
 */
public void actionToDisplay(ActionType type, String message) {
	
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Info");
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.showAndWait();
	if (type == ActionType.TERMINATE)
	{
		Platform.exit();
		System.exit(1);
	}
	if (type == ActionType.CONTINUE)
		return;
}
}
	
	

		