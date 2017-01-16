package control;

import java.io.IOException;

import entity.SearchBookResult;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;



/** BookPageController. Responsible to show a specific book page.
 * @author itain
 */


public class BookPageController implements ScreensIF
{

	@FXML private Label bookLable;
	@FXML private Label authorsLable;
	@FXML private Label languageLable;
	@FXML private Label domainsLable;
	@FXML private Label subjectsLable;
	@FXML private Label keyWordsLable;
	@FXML private Label tocLable;
	@FXML private Label summaryLable;
	@FXML private Label priceLable;
	
	@FXML private TabPane bookTabPane;
	@FXML private Tab readReviewsTab;
	@FXML private Tab writeReviewTab;
	@FXML private Tab bookReportTab;
	@FXML private Tab popularityReportTab;
	
	public static SearchBookResult searchedBookPage;
	
	@FXML
	public void initialize() 
	{
		int i;
		System.out.println("test3");
		bookLable.setText(searchedBookPage.getBookTitle());
		languageLable.setText(searchedBookPage.getBookLanguage());
		summaryLable.setText(searchedBookPage.getBookSummary());
		tocLable.setText(searchedBookPage.getBookToc());
		keyWordsLable.setText(searchedBookPage.getBookKeywords());
		/*
		int authorsCount=Integer.parseInt(bookData[6]);
		String authors=bookData[7]; //first author
		for(i=1;i<authorsCount;i++)
			authors+=", "+bookData[7+i];
			*/
		authorsLable.setText(searchedBookPage.getBookAuthors());
		/*
		int continue_index=7+authorsCount;
		
		int domainsCount=Integer.parseInt(bookData[continue_index]);
		String subjects=bookData[continue_index+1]; //first subject
		String domains=bookData[continue_index+2]; //first domain
		
		for(i=1;i<domainsCount;i+=2)
		{
			subjects+=", "+bookData[continue_index+1+i];
			domains+=", "+bookData[continue_index+2+i];
		}
		*/
		subjectsLable.setText(searchedBookPage.getBookSubjects());
		domainsLable.setText(searchedBookPage.getBookDomains());
		//continue_index=continue_index+domainsCount;
		priceLable.setText(searchedBookPage.getBookPrice());
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
