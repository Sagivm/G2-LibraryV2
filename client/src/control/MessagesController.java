package control;

import java.io.IOException;
import java.util.ArrayList;

import control.BookManagementController.PropertyBook;
import control.PendingRegistrationController.pendingUser;
import control.PendingReviewsController.pendingReview;
import entity.GeneralMessages;
import entity.Message;
import entity.SearchUserResult;
import entity.User;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MessagesController implements ScreensIF
{
	@FXML
    private TableView<Messages> table;
    
	/**
	 * Column in the table that shows the review id.
	 */
	@FXML
    private TableColumn dateCol;
	
	
	@FXML 
	private TableColumn timeCol;
	
	@FXML
	private TableColumn msgCol;
	
	
	private ObservableList<Messages> data = FXCollections.observableArrayList();

	
	public static ArrayList<String> messagesResult;
	
	
	@FXML
	private void initialize(){
		ArrayList<String> elementsList = new ArrayList<String>();
		User user = HomepageUserController.getConnectedUser();
		String username = user.getId();
		
		elementsList.add(username);
		Message message = new Message(ActionType.GET_MESSAGES,elementsList);
		
		
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {	
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		
		
		Platform.runLater(() -> {
			table.setEditable(true);
			
			dateCol.setCellValueFactory(
	                new PropertyValueFactory<pendingUser, String>("date"));

			timeCol.setCellValueFactory(
	                new PropertyValueFactory<pendingUser, String>("time"));

			msgCol.setCellValueFactory(
	                new PropertyValueFactory<pendingUser, String>("msg"));
			
			
				for(int i=0;i<messagesResult.size();i++)
				{
					String[] tmp=new String[5];
					tmp = messagesResult.get(i).split("\\^");
					data.add(new Messages(tmp[0],tmp[1],tmp[2]));
					table.setItems(data);
				}
		});
		
		
		
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
	
	
	public static class Messages {

	    private final SimpleStringProperty date;
	    private final SimpleStringProperty time;
	    private final SimpleStringProperty msg;

	    

	    private Messages(String date, String time, String msg) {
	    	this.date = new SimpleStringProperty(date);
	        this.time = new SimpleStringProperty(time);
	        this.msg = new SimpleStringProperty(msg);       
	    	
	    }



		public String getDate() {
	        return date.get();
	    }
	    public String getTime() {
	        return time.get();
	    }
	    public String getMsg() {
	        return msg.get();
	    }
	    
	    

	    public void setDate(String date) {
	    	this.date.set(date);
	    }
	    public void setTime(String time) {
	    	this.time.set(time);
	    }
	    public void setMsg(String msg) {
	    	this.msg.set(msg);
	    }

	}
	
}



