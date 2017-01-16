package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import control.PendingRegistrationController.pendingUser;
import entity.GeneralMessages;
import entity.Message;
import entity.Register;
import enums.ActionType;
import img.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * PendingRegistrationController is the controller that shows a list of all the registration requests.
 * @author Idan
 */
public class BookManagementController {
	
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
    private Button delBtn;
	
	@FXML
    private ImageView imageView;
	
	@FXML
    private TextField filterField;

	
	
	
	public static ArrayList <String> BooksList;

    
	private ObservableList<PropertyBook> data = FXCollections.observableArrayList();;
	private ObservableList<PropertyBook> filteredData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize(){
        BookSummary.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");

		Message message = prepareGetBooksList(ActionType.GET_BOOK_LIST);
		try {
			ClientController.clientConnectionController.sendToServer(message);
		} catch (IOException e) {	
			actionOnError(ActionType.TERMINATE,GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
		}
		
		Platform.runLater(new Runnable() {
			String authors="";
			@Override
			public void run() {
				for(int i=0;i<BooksList.size();i+=8){
					if(i+8<BooksList.size() && BooksList.get(i).equals(BooksList.get(i+8)))
					{
						authors=authors+BooksList.get(i+5)+",";
					}
					else{
						if(authors.equals("")){
							data.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), BooksList.get(i+3), BooksList.get(i+4), BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
							filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), BooksList.get(i+3), BooksList.get(i+4), BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
						}
						else{
							data.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), BooksList.get(i+3), BooksList.get(i+4), authors+BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
							filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i+1), BooksList.get(i+2), BooksList.get(i+3), BooksList.get(i+4), BooksList.get(i+5), BooksList.get(i+6), BooksList.get(i+7)));
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
		    BooksTableView.getItems().remove(selectedItem);
		    data.remove(selectedItem);
		});
		
		
		
		 filterField.textProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable,
	                    String oldValue, String newValue) {

	                updateFilteredData();
	            }
	        });	
		
		
		
		
			}
		});
	
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

        if (book.getBookTitle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
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
	
	
	public Message prepareUpdatePropertyBooks(ActionType type,String username)
	{
		Message message = new Message();
		ArrayList<String> elementsList = new ArrayList<String>();
		elementsList.add(username);
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