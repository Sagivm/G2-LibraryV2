package control;

import java.io.IOException;
import java.util.ArrayList;

import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;



/** SearchUserResultsController. Responsible to show user search results and do actions on them.
 * @author itain
 */


public class SearchUserResultsController implements ScreensIF{

	public static ArrayList<String> resultList;
	
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
