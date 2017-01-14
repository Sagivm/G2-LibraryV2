package control;

import java.io.IOException;

import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/** BookPageController. Responsible to show a specific book page.
 * @author itain
 */


public class BookPageController implements ScreensIF
{
	/**
	 * book row data
	 */
	private String bookRow;
	
	@FXML private Label bookLable;
	@FXML private Label authorsLable;
	@FXML private Label languageLable;
	@FXML private Label domainsLable;
	@FXML private Label subjectsLable;
	@FXML private Label keyWordsLable;
	@FXML private Label tocLable;
	@FXML private Label summaryLable;
	@FXML private Label priceLable;
	
	@FXML
	public void initialize() 
	{
		int i;
		int size=countItems(bookRow,"^");
		String bookData[] = new String[size];
		bookData = bookRow.split("\\^");
		bookLable.setText(bookData[1]);
		languageLable.setText(bookData[2]);
		summaryLable.setText(bookData[3]);
		tocLable.setText(bookData[4]);
		keyWordsLable.setText(bookData[5]);
		
		int authorsCount=Integer.parseInt(bookData[6]);
		String authors=bookData[7]; //first author
		for(i=1;i<authorsCount;i++)
			authors+=", "+bookData[7+i];
		authorsLable.setText(authors);
		int continue_index=7+authorsCount;
		
		int domainsCount=Integer.parseInt(bookData[continue_index]);
		String subjects=bookData[continue_index+1]; //first subject
		String domains=bookData[continue_index+2]; //first domain
		
		for(i=1;i<domainsCount;i+=2)
		{
			subjects+=", "+bookData[continue_index+1+i];
			domains+=", "+bookData[continue_index+2+i];
		}
		subjectsLable.setText(subjects);
		domainsLable.setText(domains);
		continue_index=continue_index+domainsCount;
		priceLable.setText(bookData[continue_index]);
	}
	
	
	
	/**
	 * This function returns number of items on each book row on search
	 * @author itain
	 * @param str- string to be checked
	 */
	private int countItems(String str, String lookFor)
	{
		String findStr = lookFor;
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

		    lastIndex = str.indexOf(findStr, lastIndex);

		    if (lastIndex != -1) {
		        count++;
		        lastIndex += findStr.length();
		    }
		}
		return count+1;
	}
	
	/**
	 * Book page constructor store the data.
	 * @param bookRow - Gets a book row from search.
	 */
	public BookPageController(String bookRow) {
		this.bookRow = bookRow;
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

}
