package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import control.PendingRegistrationController.pendingUser;
import entity.Author;
import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import enums.ActionType;
import img.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * PendingRegistrationController is the controller that shows a list of all the registration requests.
 * @author Idan
 */
public class BookManagementController {
	
	// Books Tab - main pane
	@FXML
    private TableView<PropertyBook> BooksTableView;
	
	@FXML
    private TableColumn BookSn;
    
	@FXML
    private TableColumn BookTitle;
	
	@FXML
    private TableColumn BookAuthors;
	
	@FXML
    private TableColumn BookKeywords;
	
	@FXML
    private TableColumn BookHide;
	
	@FXML
    private Label InfoTitle;
	
	@FXML
    private Label InfoAuthors;
	
	@FXML
    private Label InfoKeywords;
	
	@FXML
    private TextArea BookSummary;
	
	@FXML
    private Label TitleLabel;
	
	@FXML
    private Label AuthorsLabel;
	
	@FXML
    private Label KeywordsLabel;
	
	@FXML
    private Label SummaryLabel;
	
	@FXML
    private Button delBtn;
	
	@FXML
    private Button editBtn;
	
	@FXML
    private Button hideBtn;
	
	@FXML
    private ImageView imageView;
	
	@FXML
    private TextField filterField;
	
	@FXML
    private AnchorPane mainPane;
	
	@FXML
    private Button addBookBtn;
	
	@FXML
    private AnchorPane addBookPane;
	// end mainpane
	
	// books tab - addbookpane
	@FXML
    private Button choosePicBtn;
	
	@FXML
    private ImageView picBook;
	
	@FXML
    private Button submitAddBook;
	
	@FXML
    private Button backAddBook;
	
	@FXML
    private Button clearAddBook;
	
	@FXML
    private TextField addBookTitle;
	
	@FXML
    private ListView<String> addBookAuthorsList;
	
	@FXML
    private TextArea addBookKeywordsText;
	
	@FXML
    private ListView<String> addBookLanguageList;
	
	@FXML
    private ListView<String> addBookSubjectsList;
	
	@FXML
    private TextArea addBookTableOfContent;
	
	@FXML
    private TextArea addBookSummary;
	
	

	public static ArrayList<String> BooksList;
	public static ArrayList<Author> authorList;
	public static ArrayList<String> subjectList;
    
	private ObservableList<PropertyBook> data = FXCollections.observableArrayList();;
	private ObservableList<PropertyBook> filteredData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize(){
		addBookPane.setVisible(false);
        BookSummary.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
        TitleLabel.setVisible(false);
        AuthorsLabel.setVisible(false);
        KeywordsLabel.setVisible(false);
        SummaryLabel.setVisible(false);
        BookSummary.setVisible(false);
        delBtn.setVisible(false);
        editBtn.setVisible(false);
        hideBtn.setVisible(false);
		Message message = prepareGetBooksList(ActionType.GET_BOOK_LIST);
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {	
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		

		Service<Void> service = new Service<Void>() {
	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {           
	                @Override
	                protected Void call() throws Exception {
	                    //Background work                       
	                    final CountDownLatch latch = new CountDownLatch(1);
	                    Platform.runLater(new Runnable() {                          
	                        @Override
	                        public void run() {
							
					String authors="";		
							
							
						
							
					for(int i=0;i<BooksList.size();i+=8){
		if(i+8<BooksList.size() && BooksList.get(i).equals(BooksList.get(i+8)))
		{
			authors=authors+BooksList.get(i+5)+",";
		}
		else{
			if(authors.equals("")){
				String hide;
				if(BooksList.get(i+3).equals("000")) hide="no";
				else hide="yes";
				data.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), hide, BooksList.get(i+4), BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
				filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), hide, BooksList.get(i+4), BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
			}
			else{
				String hide;
				if(BooksList.get(i+3).equals("000")) hide="no";
				else hide="yes";
				data.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), hide, BooksList.get(i+4), authors+BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
				filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), hide, BooksList.get(i+4), authors+BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
			}
			authors = "";
		}
	}
		
	
BooksTableView.setEditable(true);


BookSn.setCellValueFactory(
    new PropertyValueFactory<pendingUser, String>("bookSn"));

BookTitle.setCellValueFactory(
    new PropertyValueFactory<pendingUser, String>("bookTitle"));

BookKeywords.setCellValueFactory(
    new PropertyValueFactory<pendingUser, String>("bookKeywords"));

BookHide.setCellValueFactory(
    new PropertyValueFactory<pendingUser, String>("bookHide"));

BookAuthors.setCellValueFactory(
    new PropertyValueFactory<pendingUser, String>("authorName"));



BookSn.setStyle( "-fx-alignment: CENTER;");
BookTitle.setStyle( "-fx-alignment: CENTER;");
BookKeywords.setStyle( "-fx-alignment: CENTER;");
BookHide.setStyle( "-fx-alignment: CENTER;");
BookAuthors.setStyle( "-fx-alignment: CENTER;");




BooksTableView.setItems(filteredData);

BooksTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
if (newSelection != null) {
	TitleLabel.setVisible(true);
    AuthorsLabel.setVisible(true);
    KeywordsLabel.setVisible(true);
    SummaryLabel.setVisible(true);
    BookSummary.setVisible(true);
    delBtn.setVisible(true);
    editBtn.setVisible(true);
    hideBtn.setVisible(true);
    
    if(newSelection.getBookHide().equals("yes"))
    	hideBtn.setText("Unhide Book");
    else
    	hideBtn.setText("Hide Book");
    
    InfoTitle.setText(newSelection.getBookTitle());
    InfoAuthors.setText(newSelection.getAuthorName());
    InfoKeywords.setMaxWidth(170);
    InfoKeywords.setWrapText(true);
    InfoKeywords.setText(newSelection.getBookKeywords());
    BookSummary.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
    BookSummary.setMaxWidth(280);
    BookSummary.setWrapText(true);
    BookSummary.setText(newSelection.getBookSummary());
    BookSummary.setEditable(false);
    
    //show picture
    String data = newSelection.getBookImage();
    String base64EncodedImage = data.split(",")[1];
    byte[] imageInBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64EncodedImage);
    BufferedImage imgbuf;
	try {
		imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
		Image image = SwingFXUtils.toFXImage(imgbuf, null);
		imageView.setImage(image);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    //end show picture

}
});


delBtn.setOnAction(e -> {
PropertyBook selectedItem = BooksTableView.getSelectionModel().getSelectedItem();
//BooksTableView.getItems().remove(selectedItem);
//Message message = prepareDeleteBook(ActionType.DELETE_BOOK ,selectedItem.getBookSn());
/*try {
	ClientController.clientConnectionController.sendToServer(message);
} catch (IOException e1) {	
	actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
}
Platform.runLater(new Runnable() {
	@Override
	public void run() {
			data.remove(selectedItem);
	}
});*/
//delete book
});

hideBtn.setOnAction(e -> {
PropertyBook selectedItem = BooksTableView.getSelectionModel().getSelectedItem();
if(selectedItem.getBookHide().equals("yes")){
	
	Message message = prepareHideBook(ActionType.HIDE_BOOK , selectedItem.getBookSn(), "000");
	try {
		ClientController.clientConnectionController.sendToServer(message);
	} catch (IOException e1) {	
		actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
	}
	Platform.runLater(new Runnable() {
		@Override
		public void run() {
				selectedItem.setBookHide("no");
				hideBtn.setText("Hide Book");
		}
	});
}
else{
	Message message = prepareHideBook(ActionType.HIDE_BOOK , selectedItem.getBookSn(), "001");
	try {
		ClientController.clientConnectionController.sendToServer(message);
	} catch (IOException e1) {	
		actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
	}
	Platform.runLater(new Runnable() {
	@Override
	public void run() {
				selectedItem.setBookHide("yes");
				hideBtn.setText("Unhide Book");
		}
	});
}
BookHide.setVisible(false);
BookHide.setVisible(true);
});



filterField.textProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {

        updateFilteredData();
    }
});	


addBookBtn.setOnAction(e -> {
 	mainPane.setVisible(false);
 	addBookPane.setVisible(true);	

 	Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {           
                @Override
                protected Void call() throws Exception {
                    //Background work                       
                    final CountDownLatch latch = new CountDownLatch(1);
                    Platform.runLater(new Runnable() {                          
                        @Override
                        public void run() {
						ObservableList<String> languages =FXCollections.observableArrayList (
						"English", "Hebrew", "Russian", "Arabic");
						addBookLanguageList.setItems(languages);
						}
                        });
                     latch.await();                      
                     //Keep with the background work
                     return null;
                   }
                };
            }
        };
        service.start();
		

 	
		
	 	Message message = prepareGetAuthors(ActionType.GET_AUTHORS);
	 	try {
	 		ClientController.clientConnectionController.sendToServer(message);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 	Service<Void> service1 = new Service<Void>() {
	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {           
	                @Override
	                protected Void call() throws Exception {
	                    //Background work                       
	                    final CountDownLatch latch = new CountDownLatch(1);
	                    Platform.runLater(new Runnable() {                          
	                        @Override
	                        public void run() {
	                        		ArrayList<String> names=new ArrayList<String>();
	                        		addBookAuthorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	                        		//System.out.println(statush.get(1).getFirstname());
	                        		for(int i=0 ; i< authorList.size();i++)
	                        		{
	                        			names.add(i, "("+authorList.get(i).getId()+")"+"\t"+authorList.get(i).getFirstname()+" "+authorList.get(i).getLastname());
	                        		}
	                        		//System.out.println(names.get(0));
	                        		ObservableList<String> authors = FXCollections.observableArrayList(names);
	                        		addBookAuthorsList.setItems(authors);		
	                        }
	                        });
	                     latch.await();                      
	                     //Keep with the background work
	                     return null;
	                   }
	                };
	            }
	        };
	        service1.start();
		
		
	 	Message message2 = prepareGetSubjects(ActionType.GET_SUBJECTS);
	 	try {
	 		ClientController.clientConnectionController.sendToServer(message2);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 	Service<Void> service2 = new Service<Void>() {
	        @Override
	        protected Task<Void> createTask() {
	            return new Task<Void>() {           
	                @Override
	                protected Void call() throws Exception {
	                    //Background work                       
	                    final CountDownLatch latch = new CountDownLatch(1);
	                    Platform.runLater(new Runnable() {                          
	                        @Override
	                        public void run() {				
	                        addBookSubjectsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
							ObservableList<String> subjects = FXCollections.observableArrayList(subjectList);
							addBookSubjectsList.setItems(subjects);	
							}
	                        });
	                     latch.await();                      
	                     //Keep with the background work
	                     return null;
	                   }
	                };
	            }
	        };
	        service2.start();
		
	        

});//endbtn



//---------------------------------book add pane---------------------

final FileChooser fileChooser = new FileChooser();

choosePicBtn.setOnAction(e -> {
 configureFileChooser(fileChooser);
 File file = fileChooser.showOpenDialog(ScreenController.getStage());
 if (file != null) {
     //System.out.println(file.getAbsolutePath());
     ByteArrayOutputStream output = new ByteArrayOutputStream();
     BufferedImage image;
     byte[] imageInBytes;
	try {
		image = ImageIO.read(file);
		//System.out.println(file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(".")+1));
		ImageIO.write(image, "png", output);
        String base64EncodedImage = DatatypeConverter.printBase64Binary(output.toByteArray());
        imageInBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64EncodedImage);
	        System.out.println(base64EncodedImage);
        BufferedImage imgbuf;
			imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
			Image image1 = SwingFXUtils.toFXImage(imgbuf, null);
			picBook.setImage(image1);
	} catch (Exception e2) {
		actionOnError(ActionType.CONTINUE, "Your Picture format not suppoerted!");
		imageInBytes=null;
	}

 }
});


backAddBook.setOnAction(e -> {
 	addBookTitle.setText("");
 	addBookAuthorsList.getSelectionModel().clearSelection();
 	addBookKeywordsText.setText("");
 	addBookLanguageList.getSelectionModel().clearSelection();
 	addBookSubjectsList.getSelectionModel().clearSelection();
 	addBookTableOfContent.setText("");
 	addBookSummary.setText("");
 	picBook.setImage(null);
 	addBookPane.setVisible(false);	
 	mainPane.setVisible(true);
});

clearAddBook.setOnAction(e -> {
 addBookTitle.setText("");
 addBookAuthorsList.getSelectionModel().clearSelection();
 addBookKeywordsText.setText("");
 addBookLanguageList.getSelectionModel().clearSelection();
 addBookSubjectsList.getSelectionModel().clearSelection();
 addBookTableOfContent.setText("");
 addBookSummary.setText("");
 picBook.setImage(null);
});

submitAddBook.setOnAction(e -> {
 System.out.println("ssss");
});




		
							
							
							
							}
	                        });
	                     latch.await();                      
	                     //Keep with the background work
	                     return null;
	                   }
	                };
	            }
	        };
	        service.start();
	
	}
	
    private static void configureFileChooser(final FileChooser fileChooser){                           
    fileChooser.setTitle("View Pictures");
    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home"))
    ); 
}
    
	
	
    private void updateFilteredData() {
        filteredData.clear();

        for (PropertyBook b : data) {
            if (matchesFilter(b)) {
                filteredData.add(b);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }
    
    private boolean matchesFilter(PropertyBook book) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (book.getBookSn().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        if (book.getBookTitle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        if (book.getAuthorName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        if (book.getBookKeywords().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<PropertyBook, ?>> sortOrder = new ArrayList<>(BooksTableView.getSortOrder());
        BooksTableView.getSortOrder().clear();
        BooksTableView.getSortOrder().addAll(sortOrder);
    }
	
	public Message prepareGetBooksList(ActionType type)
	{
		Message message = new Message();
		message.setType(type);
		return message;
	}
	
	public Message prepareGetAuthors(ActionType type)
	{
		Message message = new Message();
		message.setType(type);
		return message;
	}
	
	public Message prepareGetSubjects(ActionType type)
	{
		Message message = new Message();
		message.setType(type);
		return message;
	}
	
	
	public Message prepareUpdatePropertyBooks(ActionType type,String username)
	{
		Message message = new Message();
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(username);
		message.setType(type);
		message.setElementsList(elementsList);
		return message;
	}
	
	public Message prepareDeleteBook(ActionType type,String sn)
	{
		Message message = new Message();
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(sn);
		message.setType(type);
		message.setElementsList(elementsList);
		return message;
	}
	
	public Message prepareHideBook(ActionType type, String sn, String hide)
	{
		Message message = new Message();
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(sn);
		elementsList.add(hide);
		message.setType(type);
		message.setElementsList(elementsList);
		return message;
	}
	
	public void actionOnError(ActionType type, String errorCode) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(errorCode);
		alert.showAndWait();
		if (type == ActionType.TERMINATE)
		{
			Platform.exit();
			System.exit(1);
		}
		if (type == ActionType.CONTINUE)
			return;
	}
	
	
	public static class PropertyBook {

	    private final SimpleStringProperty bookSn;
	    private final SimpleStringProperty bookTitle;
	    private final SimpleStringProperty bookKeywords;
	    private final SimpleStringProperty bookHide;
	    private final SimpleStringProperty authorId;
	    private final SimpleStringProperty authorName;
	    private final SimpleStringProperty bookSummary;
	    private final SimpleStringProperty bookImage;

	    private PropertyBook(String bookSn, String bookTitle, String bookKeywords, String bookHide, String authorId, String authorName, String bookSummary, String bookImage) {
	        this.bookSn = new SimpleStringProperty(bookSn);
	        this.bookTitle = new SimpleStringProperty(bookTitle);
	        this.bookKeywords = new SimpleStringProperty(bookKeywords);
	        this.bookHide = new SimpleStringProperty(bookHide);
	        this.authorId = new SimpleStringProperty(authorId);
	        this.authorName = new SimpleStringProperty(authorName);
	        this.bookSummary = new SimpleStringProperty(bookSummary);
	        this.bookImage = new SimpleStringProperty(bookImage);
	    }

	    public String getBookSn() {
	        return bookSn.get();
	    }
	    
	    public String getBookTitle() {
	        return bookTitle.get();
	    }
	    
	    public String getBookKeywords() {
	        return bookKeywords.get();
	    }

	    public String getBookHide() {
	        return bookHide.get();
	    }
	    
	    public String getAuthorId() {
	        return authorId.get();
	    }
	    
	    public String getAuthorName() {
	        return authorName.get();
	    }
	    
	    public String getBookSummary() {
	        return bookSummary.get();
	    }
	    
	    public String getBookImage() {
	        return bookImage.get();
	    }


	    public void setBookSn(String bookSn) {
	    	this.bookSn.set(bookSn);
	    }
	    
	    public void setBookTitle(String bookTitle) {
	    	this.bookTitle.set(bookTitle);
	    }
	    
	    public void setBookKeywords(String bookKeywords) {
	    	this.bookKeywords.set(bookKeywords);
	    }
	    
	    public void setBookHide(String bookHide) {
	    	this.bookHide.set(bookHide);
	    }
	    
	    public void setAuthorId(String authorId) {
	    	this.authorId.set(authorId);
	    }
	    
	    public void setAuthorName(String authorName) {
	    	this.authorName.set(authorName);
	    }
	    
	    public void setBookSummary(String bookSummary) {
	    	this.bookSummary.set(bookSummary);
	    }
	    
	    public void setBookImage(String bookImage) {
	    	this.bookImage.set(bookImage);
	    }


	}

	
}