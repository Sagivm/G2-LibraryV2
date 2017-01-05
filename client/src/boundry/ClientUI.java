package boundry;

import java.io.IOException;

import control.HomepageUserController;
import control.LogoutController;
import control.ScreenController;
import entity.GeneralMessages;
import entity.ScreensInfo;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

//TODO: Auto-generated Javadoc
/**
 * ClientUI executes the application in the first time with GUI interface.
 * @author nire
 */
public class ClientUI extends Application {

	private static boolean isConnected = false;
	
	
	/** main function executes the client by calling the method launch (method of Application)
	* @param args
	*/
	public static void main(String args[]) {
			Application.launch(ClientUI.class, args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
		     ScreenController.setStage(primaryStage);
			 Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	
		     ScreenController screenController = new ScreenController();
		     screenController.replaceSceneContent(ScreensInfo.CLIENT_SCREEN,ScreensInfo.CLIENT_TITLE);
		     primaryStage.show();
			 primaryStage.setX(primaryScreenBounds.getMaxX()/2.0 - primaryStage.getWidth()/2.0);
			 primaryStage.setY(primaryScreenBounds.getMaxY()/2.0 - primaryStage.getHeight()/2.0);
		     } catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText(GeneralMessages.UNNKNOWN_ERROR);
				alert.showAndWait();
				return;
		        }
	}

	@Override
	public void stop() throws IOException{
	    System.out.println("Stage is closing");
	    if (isConnected)
	    {
	    	HomepageUserController userPage = new HomepageUserController();
			//LogoutController logoutCtrl = new LogoutController();
			//logoutCtrl.logout();ωγγγ
	    	userPage.logout();
	    }
	// Save file
	}
	
	/** Setter for isConnected.
	 * @param isConnected
	 */
	public void setIsConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}
	
	/** Getter for isConnected.
	 * @return
	 */
	public boolean getIsConnected()
	{
		return isConnected;
	}
}
