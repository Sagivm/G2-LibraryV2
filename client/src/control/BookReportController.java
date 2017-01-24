package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.BookPopularityReportController.Popularity;
import entity.Book;
import entity.Message;
import enums.ActionType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * @author sagivm
 *Displays book's number of searches and purchases for each date in the book's 
 *life span displayed in an histographic graph
 */
public class BookReportController implements Initializable {

	/**
	 * Title containing the name of the book
	 */
	@FXML
	private Label titleLabel;
	/**
	 * The book that the report will be done on
	 */
	@FXML
	private Book SelectedBook;
	/**
	 * The data that was retrieved from the DB
	 */
	public static ArrayList<String> data;
	/**
	 * BarChart to display the date
	 */
	private BarChart<String,Integer> barChart;
	/**
	 * Search bar containing the number of searches for a book for a specific date
	 */
	@FXML private XYChart.Series search ;
	/**
	 * Purchase bar containing the number of searches for a book for a specific date
	 */
	@FXML private XYChart.Series purchase ;
	/**
	 * A list containing the data in BookByDate form
	 */
	private ArrayList<BookByDate> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeLabel();
		initializeChart();
		// TODO Auto-generated method stub

	}

	/**
	 * Displays the book's name in the title
	 */
	private void initializeLabel() {
		this.titleLabel.setText(SelectedBook.getTitle() + " Book Report");

	}

	/**
	 * Send a request to the DB For the relevant fields for the chart.
	 * Forwards to receive for displaying
	 */
	private void initializeChart() {
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(String.valueOf(SelectedBook.getSn()));
		Message message = new Message(ActionType.BOOKREPORT, elementsList);
		try {
			ClientController.clientConnectionController.sendToServer(message);

		} catch (IOException e) {

			// actionToDisplay("Warning",ActionType.CONTINUE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		receive();
	}

	/**
	 * Receive the data arrange it in BookByDate form and transfer to displayChart
	 */
	private void receive() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				try {
					arrangelist();
					displayChart();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
		});
		
	}
	/**
	 * Fill the Chart bars with the relevant data containing number of searches and purchases for each date
	 */
	private void displayChart() {
		for(int i=0;i<list.size();i++)
		{
			BookByDate temp=new BookByDate();
			temp=list.get(i);
			 search.getData().add(new XYChart.Data(temp.date,temp.search));
			 purchase.getData().add(new XYChart.Data(temp.date,temp.purchase));
		}
		barChart.getData().addAll(search,purchase);
	}
	/**
	 * Transfer the list from the DB to ArrayList<BookByDate> 
	 */
	private void arrangelist()
	{
		list=new ArrayList<BookByDate>();
		String datasplit[]=new String[3];
		for(int i=0;i<data.size();i++)
		{
			datasplit=data.get(i).split("^");
			list.add(new BookByDate(datasplit));
		}
		
	}
	/**
	 * Data type for containing the data from DB
	 * @author sagivm
	 *
	 */
	class BookByDate
	{
		/**
		 * Date
		 */
		private String date;
		/**
		 * number of searches for the book on a specific date
		 */
		private int search;
		/**
		 * number of searches for the book on a specific date
		 */
		private int purchase;
		/**
		 * constructor
		 * @param split String array containing the 6 attributes of Purchase in the order
		 * date,search,purchase
		 */
		public BookByDate(String split[])
		{
			this.date=split[0];
			this.search=Integer.parseInt(split[1]);
			this.purchase=Integer.parseInt(split[2]);
		}
		
		/**
		 * Contractor
		 */
		public BookByDate() {
			// TODO Auto-generated constructor stub
		}
	}

}
