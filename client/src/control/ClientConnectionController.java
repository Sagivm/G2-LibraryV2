package control;

import java.io.IOException;
import java.util.ArrayList;

import boundry.ClientUI;
import entity.Author;
import entity.GeneralMessages;
import entity.Replay;
import entity.ScreensInfo;
import entity.User;
import entity.Worker;
import enums.ActionType;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ocsf.client.AbstractClient;

//TODO: Auto-generated Javadoc
/**
 * ClientConnectionController create instance to client, and presave into
 * ClientController with static variable for communication later with the GUI
 * and clients.
 * 
 * @author nire
 */
public class ClientConnectionController extends AbstractClient {

	/**
	 * The main
	 */
	private static ClientUI clientMain = null;

	/**
	 * ClientConnectionController constructor initialize the hostname, and then
	 * the port for establish connection to server. it also open new connection
	 * physically for it.
	 * 
	 * @param host
	 *            - The hostname/ip for connection.
	 * @param port
	 *            - The port for establish connection
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see ocsf.client.AbstractClient#handleMessageFromServer(java.lang.Object)
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		Replay replay = (Replay) msg;
		actionToPerform(replay);
	}

	/**
	 * This function gets the replay from server and decrypt to action by his
	 * information.
	 * 
	 * @param replay
	 *            - Gets the replay with the data.
	 */
	public void actionToPerform(Replay replay) {

		ActionType type = replay.getType();
		boolean success = replay.getSucess();

		switch (type) {
		case REGISTER: {
			if (success == true) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						ScreenController screenController = new ScreenController();
						try {
							actionToDisplay(ActionType.CONTINUE, GeneralMessages.PENDING_FOR_LIBRARIAN);
							screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN, ScreensInfo.CLIENT_TITLE);
						} catch (Exception e) {
							// COMPELETE
							e.printStackTrace();
						}
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						ScreenController screenController = new ScreenController();
						try {
							actionToDisplay(ActionType.CONTINUE, GeneralMessages.USER_ALREADY_EXISTS);
							screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN, ScreensInfo.CLIENT_TITLE);
						} catch (Exception e) {
							// COMPELETE
							e.printStackTrace();
						}
					}
				});
			}
			break;
		}
		case LOGIN: {
			if (success == true) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						ScreenController screenController = new ScreenController();
						try {
							int action = replay.getAction();
							// System.out.println(action);
							actionToDisplay(ActionType.CONTINUE, GeneralMessages.USER_LOGGED_IN_SUCESSFULLY);
							if (action == 1) {
								User user = new User(replay.getElementsList().get(1).toString(),
										replay.getElementsList().get(2).toString(),
										replay.getElementsList().get(0).toString(),
										replay.getElementsList().get(3).toString(),
										replay.getElementsList().get(4).toString(),
										replay.getElementsList().get(5).toString());
								screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_USER_SCREEN,
										ScreensInfo.HOMEPAGE_USER_TITLE);
								HomepageUserController userPage = new HomepageUserController();
								userPage.setConnectedUser(user);
								// userPage.setUsernameLabel("fdf");

							} else if (action == 2) {
								Worker worker = new Worker(replay.getElementsList().get(1).toString(),
										replay.getElementsList().get(2).toString(),
										replay.getElementsList().get(0).toString(),
										replay.getElementsList().get(3).toString(),
										replay.getElementsList().get(4).toString(),
										replay.getElementsList().get(5).toString(),
										replay.getElementsList().get(6).toString());
								screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_LIBRARIAN_SCREEN,
										ScreensInfo.HOMEPAGE_LIBRARIAN_TITLE);
								HomepageLibrarianController liberianPage = new HomepageLibrarianController();
								liberianPage.setConnectedlibrarian(worker);
							} else if (action == 3) {
								Worker worker = new Worker(replay.getElementsList().get(1).toString(),
										replay.getElementsList().get(2).toString(),
										replay.getElementsList().get(0).toString(),
										replay.getElementsList().get(3).toString(),
										replay.getElementsList().get(4).toString(),
										replay.getElementsList().get(5).toString(),
										replay.getElementsList().get(6).toString());
								screenController.replaceSceneContent(ScreensInfo.HOMEPAGE_MANAGER_SCREEN,
										ScreensInfo.HOMEPAGE_MANAGER_TITLE);
								HomepageManagerController managerPage = new HomepageManagerController();
								managerPage.setConnectedManager(worker);
							}
							if (clientMain == null)
								clientMain = new ClientUI();
							clientMain.setIsConnected(true);
							if (action == 1)
								clientMain.setTypeOfUser("User");
							else if (action == 2)
								clientMain.setTypeOfUser("Librarian");
							else if (action == 3)
								clientMain.setTypeOfUser("Manager");
							centerWindow(screenController);
						} catch (Exception e) {
							// COMPELETE
							e.printStackTrace();
						}
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						ScreenController screenController = new ScreenController();
						try {
							// actionToDisplay(ActionType.CONTINUE,"The password
							// and Username not match!");
							actionToDisplay(ActionType.CONTINUE, replay.getGnrlMsg().toString());

						} catch (Exception e) {
							// COMPELETE
							e.printStackTrace();
						} //
					}
				});
			}
			break;
		}
		case LOGOUT: {
			if (success == true) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ScreenController screenController = new ScreenController();
						try {
							// actionToDisplay(ActionType.CONTINUE,GeneralMessages.USER_LOGGED_IN_successFULLY);
							screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN, ScreensInfo.CLIENT_TITLE);
							centerWindow(screenController);
							if (clientMain == null)
								clientMain = new ClientUI();
							clientMain.setIsConnected(false);
							clientMain.setTypeOfUser(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			break;
		}
		case ACCOUNTTYPEREQ: {
			if (success == true)
			{
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						ScreenController screenController = new ScreenController();
						try {
							// actionToDisplay(ActionType.CONTINUE,"The password
							// and Username not match!");
							actionToDisplay(ActionType.CONTINUE, GeneralMessages.PENDING_FOR_LIBRARIAN);

						} catch (Exception e) {
							// COMPELETE
							e.printStackTrace();
						} //
					}
				});
			}
				
			break;
		}

		case GET_PENDING_USERS: {
			PendingRegistrationController.pendingUsersList = replay.getElementsList();
			break;
		}
		
<<<<<<< HEAD
		case PENDING_REVIEWS: {
/*			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					PendingReviewsController.pendingReviewList = replay.getElementsList();
					System.out.print(replay.getElementsList().get(0).toString());
				}
			});*/
			PendingReviewsController.pendingReviewList = replay.getElementsList();
			break;
=======
//		case GET_AUTHORS: {
//			ListView<Author> list=new ListView<Author>();
//			for(int i=0;i<replay.getElementsList().size();i++)
//			{
//				String tmp[] = new String[3];
//				
//				
//				tmp=replay.getElementsList().get(i).split("$");
//				Author author = new Author(tmp[0],tmp[1], tmp[2]);
//				list.getItems().add(author);		
//			}
//			SearchBookController search =new SearchBookController();
//			search.setAuthorListView(list);
//			break;
//		}
		
>>>>>>> branch 'master' of https://github.com/Sagivm/G2-Library.git
		}
		
	}

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

	/**
	 * Set the window in the center of the screen.
	 * 
	 * @param screenController
	 */
	public void centerWindow(ScreenController screenController) {
		Stage primaryStage = screenController.getStage();
		ScreenController.setStage(primaryStage);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.show();
		primaryStage.setX(primaryScreenBounds.getMaxX() / 2.0 - primaryStage.getWidth() / 2.0);
		primaryStage.setY(primaryScreenBounds.getMaxY() / 2.0 - primaryStage.getHeight() / 2.0);
	}

}
