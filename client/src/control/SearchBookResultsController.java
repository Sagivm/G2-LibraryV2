package control;

import java.io.IOException;
import java.util.ArrayList;

import control.PendingRegistrationController.pendingUser;
import entity.Author;
import entity.Book;
import entity.Domain;
import entity.Subject;
import enums.ActionType;
import interfaces.ScreensIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/** SearchBookResultsController. Responsible show users their search book results.
 * @author itain
 */

public class SearchBookResultsController implements ScreensIF{
	
	@FXML private TableView resultsTable;
	@FXML private TableColumn bookCol;
	@FXML private TableColumn authorsCol;
	@FXML private TableColumn languageCol;
	@FXML private TableColumn domainsCol;
	@FXML private TableColumn subjectsCol;
	
	public static ArrayList<String> resultList;
	private ObservableList<Book> data = FXCollections.observableArrayList();
	
	@FXML
	private void initialize()
	{
		try{
			int j;
			for(int i=0;i<resultList.size();i++)
			{
				Book book=new Book();
				int size=countItems(resultList.get(i),"^");
				String[] tmp=new String[size];
				tmp = resultList.get(i).split("\\^");
				book.setSn(Integer.parseInt(tmp[0]));
				book.setTitle(tmp[1]);
				book.setLanguage(tmp[2]);
				book.setSummary(tmp[3]);
				book.setTableOfContent(tmp[4]);
				book.setKeywords(tmp[5]);
				int authorsCount=Integer.parseInt(tmp[6]);
				ArrayList<Author> authors = new ArrayList<Author>();
				for(j=0; j<authorsCount;j++)
				{
					Author author = new Author();
					String[] tmp2 = new String[2];
					tmp2 = tmp[7+j].split(" ");
					author.setFirstname(tmp2[1]);
					author.setLastname(tmp2[2]);
					authors.add(author);
				}
				book.setAuthors(authors);
				int continue_index=7+j;
				
				int subjectsCount=Integer.parseInt(tmp[continue_index]);
				ArrayList<Domain> domains = new ArrayList<Domain>();
				ArrayList<Subject> subjects = new ArrayList<Subject>();
				for(j=0; j<subjectsCount*2;j+=2)
				{
					Domain domain = new Domain();
					Subject subject = new Subject();
					
					domain.setName(tmp[continue_index+1+j]);
					subject.setName(tmp[continue_index+1+j+1]);
					domains.add(domain);
					subjects.add(subject);
				}
				book.setDomains(domains);
				book.setSubjects(subjects);
				
				continue_index=continue_index+1+subjectsCount;
				book.setPrice(Integer.parseInt(tmp[continue_index]));
				
				data.add(book);
			}
			
		
		
		resultsTable.setEditable(true);
		
		bookCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("title"));
		
		authorsCol.setCellValueFactory(
                new PropertyValueFactory<Book, ArrayList<Author>>("authors"));
		
		languageCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("language"));

		domainsCol.setCellValueFactory(
                new PropertyValueFactory<Book, ArrayList<Domain>>("domains"));
		
		subjectsCol.setCellValueFactory(
                new PropertyValueFactory<Book, ArrayList<Subject>>("subjects"));
		
		
		resultsTable.setItems(data);
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
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

}


