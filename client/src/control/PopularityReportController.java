package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.GeneralMessages;
import entity.Message;
import entity.User;
import enums.ActionType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PopularityReportController implements Initializable {

	/**
	 * Main table in the Popularity report screen
	 */
	
	@FXML private TableView<String> table;
	public static ArrayList<String> data;
	private static User selectedUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializeTable();

	}

	private void initializeTable() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(selectedUser.getId());
		Message message = new Message(ActionType.POPULARITYREPORT, elementsList);
		try {
			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		receiver();

	}

	private void receiver() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
					for(int i=0;i<data.size()*5;i=i+5)
					{
						if(data!=null)
						{
							ArrayList<Purchase> list=arrangelist(data);
						}
						else
						{
							//actionToDisplay("Info", ActionType.CONTINUE,"No purchase data on user");
						}
								
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public static User getSelectedUser() {
		return selectedUser;
	}

	public static void setSelectedUser(User selectedUser) {
		PopularityReportController.selectedUser = selectedUser;

	}
	private ArrayList<Purchase> arrangelist(ArrayList<String> data)
	{
		ArrayList<Purchase> list=new ArrayList<Purchase>();
		String datasplit[]=new String[5];
		for(int i=0;i<data.size();i++)
		{
			datasplit=data.get(i).split("\\^");
			list.add(new Purchase(datasplit));
		}
		return list;
		
	}
	class Purchase 
	{
		public String id;
		public String title;
		public String language;
		public String price;
		public String date;
		public Purchase(String split[]) {
			this.id = split[0];
			this.title = split[1];
			this.language = split[2];
			this.price = split[3];
			this.date = split[4];
		}
		public Purchase() {
		}
	}

}
