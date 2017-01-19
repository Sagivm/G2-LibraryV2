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
 private TableView < PropertyBook > BooksTableView;

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
 private Label infoPrice;
 
 @FXML
 private Label PriceLabel;

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
 
 @FXML
 private AnchorPane editBookPane;
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
 private ListView < String > addBookAuthorsList;

 @FXML
 private TextArea addBookKeywordsText;

 @FXML
 private ListView < String > addBookLanguageList;

 @FXML
 private ListView < String > addBookSubjectsList;

 @FXML
 private TextArea addBookTableOfContent;

 @FXML
 private TextArea addBookSummary;
 
 @FXML
 private TextField priceTextField;
 
 
//books tab - editbookpane
@FXML
private Button editBookChoosePicBtn;

@FXML
private ImageView editBookpicBook;

@FXML
private Button submitEditBook;

@FXML
private Button backEditBook;

@FXML
private TextField editBookTitle;

@FXML
private ListView < String > editBookAuthorsList;

@FXML
private ListView < String > editBookAuthorsListSelected;

@FXML
private Button editBookAuthorsLeft;

@FXML
private Button editBookAuthorsRight;

@FXML
private ListView < String > editBookSubjectsList;

@FXML
private ListView < String > editBookSubjectsListSelected;

@FXML
private Button editBookSubjectsLeft;

@FXML
private Button editBookSubjectsRight;

@FXML
private TextArea editBookKeywordsText;

@FXML
private ListView < String > editBookLanguageList;


@FXML
private TextArea editBookTableOfContent;

@FXML
private TextArea editBookSummary;

@FXML
private TextField editBookPriceTextField;



 public static ArrayList < String > BooksList;
 public static ArrayList < String > subjectList;
 public ArrayList < String > authorsId = null;
 public ArrayList < String > SubjectsList = null;
 public String picStr="noPicture";
 public static ArrayList < Author > authorList;
 public static ArrayList < Author > selectedAuthorsString;
 public static ArrayList < String >subjectListOfBook;
 public static ArrayList < String > selectedSubjectString;
 public static String editBookLanguage;
 public static String editBookTableOfContant;

 private ObservableList < PropertyBook > data = FXCollections.observableArrayList();;
 private ObservableList < PropertyBook > filteredData = FXCollections.observableArrayList();

 @FXML
 private void initialize() {
  addBookPane.setVisible(false);
  editBookPane.setVisible(false);
  BookSummary.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ; -fx-background-insets: 0;-fx-background-color: transparent, white, transparent, white;");
  TitleLabel.setVisible(false);
  AuthorsLabel.setVisible(false);
  KeywordsLabel.setVisible(false);
  SummaryLabel.setVisible(false);
  PriceLabel.setVisible(false);
  BookSummary.setVisible(false);
  delBtn.setVisible(false);
  editBtn.setVisible(false);
  hideBtn.setVisible(false);
  
  
  Message message = prepareGetBooksList(ActionType.GET_BOOK_LIST);
  try {
   ClientController.clientConnectionController.sendToServer(message);
  } catch (IOException e) {
   actionOnError(ActionType.TERMINATE, GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
  }
  Platform.runLater(() -> {
   String authors = "";
   for (int i = 0; i < BooksList.size(); i += 9) {
    if (i + 9 < BooksList.size() && BooksList.get(i).equals(BooksList.get(i + 9))) {
     authors = authors + BooksList.get(i + 5) + ",";
    } else {
     if (authors.equals("")) {
      String hide;
      if (BooksList.get(i + 3).equals("0")) hide = "no";
      else hide = "yes";
      data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
      filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
     } else {
      String hide;
      if (BooksList.get(i + 3).equals("0")) hide = "no";
      else hide = "yes";
      data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
      filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
     }
     authors = "";
    }
   }
  });
  

  Message message2 = prepareGetAuthors(ActionType.GET_AUTHORS);
  try {
   ClientController.clientConnectionController.sendToServer(message2);
  } catch (Exception e1) {
   e1.printStackTrace();
  }
  Platform.runLater(() -> {
   ArrayList < String > names = new ArrayList < String > ();
   addBookAuthorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
   //System.out.println(statush.get(1).getFirstname());
   for (int i = 0; i < authorList.size(); i++) {
    names.add(i, "(" + authorList.get(i).getId() + ")" + "\t" + authorList.get(i).getFirstname() + " " + authorList.get(i).getLastname());
   }
   //System.out.println(names.get(0));
   ObservableList < String > authors = FXCollections.observableArrayList(names);
   addBookAuthorsList.setItems(authors);

  });



  Message message3 = prepareGetSubjects(ActionType.GET_SUBJECTS);
  try {
   ClientController.clientConnectionController.sendToServer(message3);
  } catch (Exception e1) {
   e1.printStackTrace();
  }
  Platform.runLater(() -> {
   addBookSubjectsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
   ObservableList < String > subjects = FXCollections.observableArrayList(subjectList);
   addBookSubjectsList.setItems(subjects);

  });

  BooksTableView.setEditable(true);

  BookSn.setCellValueFactory(
   new PropertyValueFactory < pendingUser, String > ("bookSn"));

  BookTitle.setCellValueFactory(
   new PropertyValueFactory < pendingUser, String > ("bookTitle"));

  BookKeywords.setCellValueFactory(
   new PropertyValueFactory < pendingUser, String > ("bookKeywords"));

  BookHide.setCellValueFactory(
   new PropertyValueFactory < pendingUser, String > ("bookHide"));

  BookAuthors.setCellValueFactory(
   new PropertyValueFactory < pendingUser, String > ("authorName"));



  BookSn.setStyle("-fx-alignment: CENTER;");
  BookTitle.setStyle("-fx-alignment: CENTER;");
  BookKeywords.setStyle("-fx-alignment: CENTER;");
  BookHide.setStyle("-fx-alignment: CENTER;");
  BookAuthors.setStyle("-fx-alignment: CENTER;");




  BooksTableView.setItems(filteredData);

  BooksTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
   if (newSelection != null) {
    TitleLabel.setVisible(true);
    AuthorsLabel.setVisible(true);
    KeywordsLabel.setVisible(true);
    SummaryLabel.setVisible(true);
    PriceLabel.setVisible(true);
    BookSummary.setVisible(true);
    delBtn.setVisible(true);
    editBtn.setVisible(true);
    hideBtn.setVisible(true);

    if (newSelection.getBookHide().equals("yes"))
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
    infoPrice.setText(newSelection.getBookPrice());

    //show picture
    if(newSelection.getBookImage()!=null){
    String base64EncodedImage = newSelection.getBookImage();
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
  }
    else imageView.setImage(null);
    //end show picture

   }
  });
  
  editBtn.setOnAction(e -> {
	  mainPane.setVisible(false);
	  addBookPane.setVisible(false);
	  editBookPane.setVisible(true);
	  PropertyBook selectedItem = BooksTableView.getSelectionModel().getSelectedItem();
	  editBookTitle.setText(selectedItem.getBookTitle());
	  editBookKeywordsText.setText(selectedItem.getBookKeywords());
	  editBookPriceTextField.setText(selectedItem.getBookPrice());
	  editBookSummary.setText(selectedItem.getBookSummary());  
	  if(selectedItem.getBookImage()!=null){
		  String base64EncodedImage = selectedItem.getBookImage();
		    byte[] imageInBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64EncodedImage);
		    BufferedImage imgbuf;
		    try {
		     imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
		     Image image = SwingFXUtils.toFXImage(imgbuf, null);
		   editBookpicBook.setImage(image);
		    } catch (Exception e1) {
		        // TODO Auto-generated catch block
		        e1.printStackTrace();
		       }
	  }

	  
	  
	  Message message4 = prepareGetAuthors(ActionType.GET_BOOK_AUTHORS,selectedItem.getBookSn());
	  Message message5 = prepareGetAuthors(ActionType.GET_AUTHORS);
	  Message message6 = prepareGetSubjects(ActionType.GET_BOOK_SUBJETCS,selectedItem.getBookSn());
	  Message message7 = prepareGetSubjects(ActionType.GET_SUBJECTS);
	  Message message8 = prepareGetLanguage(ActionType.GET_BOOK_LANGUAGE,selectedItem.getBookSn());
	  Message message9 = prepareGetTableOfContent(ActionType.GET_BOOK_TABLE_OF_CONTENT,selectedItem.getBookSn());

	  try {
		   System.out.println("1");
	    ClientController.clientConnectionController.sendToServer(message4);
	    ClientController.clientConnectionController.sendToServer(message5);
	    ClientController.clientConnectionController.sendToServer(message6);
	    ClientController.clientConnectionController.sendToServer(message7);
	    ClientController.clientConnectionController.sendToServer(message8);
	    ClientController.clientConnectionController.sendToServer(message9);
	   } catch (Exception e1) {
	    e1.printStackTrace();
	   }
		try {
		Thread.sleep(1500);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   Platform.runLater(() -> { 
	    ArrayList < String > names = new ArrayList < String > ();
	    editBookAuthorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    //System.out.println(statush.get(1).getFirstname());
	    for (int i = 0; i < authorList.size(); i++) {
	    		names.add(i, "(" + authorList.get(i).getId() + ")" + "\t" + authorList.get(i).getFirstname() + " " + authorList.get(i).getLastname());
	    }
	    ArrayList < String > namesselected = new ArrayList < String > ();
	    editBookAuthorsListSelected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    //System.out.println(statush.get(1).getFirstname());
	    for (int i = 0; i < selectedAuthorsString.size(); i++) {
	    	namesselected.add(i, "(" + selectedAuthorsString.get(i).getId() + ")" + "\t" + selectedAuthorsString.get(i).getFirstname() + " " + selectedAuthorsString.get(i).getLastname());
	    	names.remove(namesselected.get(i));
	    }
	    ArrayList < String > subjects = new ArrayList < String > ();
	    editBookSubjectsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    //System.out.println(statush.get(1).getFirstname());
	    for (int i = 0; i < subjectListOfBook.size(); i++) {
	    	subjects.add(i, subjectListOfBook.get(i));
	    }
	    ArrayList < String > subjectsselected = new ArrayList < String > ();
	    editBookSubjectsListSelected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    //System.out.println(statush.get(1).getFirstname());
	    for (int i = 0; i < selectedSubjectString.size(); i++) {
	    	subjectsselected.add(i, selectedSubjectString.get(i));
	    	subjects.remove(subjectsselected.get(i));
	    }
	    

	    //System.out.println(names.get(0));
	    ObservableList < String > authors = FXCollections.observableArrayList(names);
	    ObservableList < String > selectedAuthors = FXCollections.observableArrayList(namesselected);
	    editBookAuthorsList.setItems(authors);    
	    editBookAuthorsListSelected.setItems(selectedAuthors);
	    
		editBookAuthorsLeft.setOnAction((ActionEvent event) -> {
		        String potential = editBookAuthorsList.getSelectionModel().getSelectedItem();
		        if (potential != null) {
		        	editBookAuthorsList.getSelectionModel().clearSelection();
		        	authors.remove(potential);
		        	selectedAuthors.add(potential);
		        }
		      });
		
		editBookAuthorsRight.setOnAction((ActionEvent event) -> {
	        String potential = editBookAuthorsListSelected.getSelectionModel().getSelectedItem();
	        if (potential != null) {
	        	editBookAuthorsListSelected.getSelectionModel().clearSelection();
	        	selectedAuthors.remove(potential);
	        	authors.add(potential);
	        }
	      });
		
		
		
		
	    ObservableList < String > allSubjects = FXCollections.observableArrayList(subjects);
	    ObservableList < String > selectedSubjects = FXCollections.observableArrayList(subjectsselected);
	    editBookSubjectsList.setItems(allSubjects);    
	    editBookSubjectsListSelected.setItems(selectedSubjects);
	    
	    editBookSubjectsLeft.setOnAction((ActionEvent event) -> {
		        String potential = editBookSubjectsList.getSelectionModel().getSelectedItem();
		        if (potential != null) {
		        	editBookSubjectsList.getSelectionModel().clearSelection();
		        	allSubjects.remove(potential);
		        	selectedSubjects.add(potential);
		        }
		      });
		
	    editBookSubjectsRight.setOnAction((ActionEvent event) -> {
	        String potential = editBookSubjectsListSelected.getSelectionModel().getSelectedItem();
	        if (potential != null) {
	        	editBookSubjectsListSelected.getSelectionModel().clearSelection();
	        	selectedSubjects.remove(potential);
	        	allSubjects.add(potential);
	        }
	      });
		
	   });
	  
	   
	   ObservableList < String > languages = FXCollections.observableArrayList(
			     "English", "Hebrew", "Russian", "Arabic");
	   editBookLanguageList.setItems(languages);
	   editBookLanguageList.getSelectionModel().select(editBookLanguage);
	   
	   editBookTableOfContent.setText(editBookTableOfContant); 

	  }); //end edit button


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
   if (selectedItem.getBookHide().equals("yes")) {

    Message message4 = prepareHideBook(ActionType.HIDE_BOOK, selectedItem.getBookSn(), "0");
    try {
     ClientController.clientConnectionController.sendToServer(message4);
    } catch (IOException e1) {
     actionOnError(ActionType.TERMINATE, GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
    }
    Platform.runLater(() -> {
     selectedItem.setBookHide("no");
     hideBtn.setText("Hide Book");
    });
   } else {
    Message message5 = prepareHideBook(ActionType.HIDE_BOOK, selectedItem.getBookSn(), "1");
    try {
     ClientController.clientConnectionController.sendToServer(message5);
    } catch (IOException e1) {
     actionOnError(ActionType.TERMINATE, GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
    }
    Platform.runLater(() -> {
     selectedItem.setBookHide("yes");
     hideBtn.setText("Unhide Book");
    });
   }
   BookHide.setVisible(false);
   BookHide.setVisible(true);
  });



  filterField.textProperty().addListener(new ChangeListener < String > () {
   @Override
   public void changed(ObservableValue < ? extends String > observable,
    String oldValue, String newValue) {

    updateFilteredData();
   }
  });




   addBookBtn.setOnAction(e -> {
   mainPane.setVisible(false);
   addBookPane.setVisible(true);
   editBookPane.setVisible(false);
    ObservableList < String > languages = FXCollections.observableArrayList(
     "English", "Hebrew", "Russian", "Arabic");
   addBookLanguageList.setItems(languages);

   Message message4 = prepareGetAuthors(ActionType.GET_AUTHORS);
   try {
    ClientController.clientConnectionController.sendToServer(message4);
   } catch (Exception e1) {
    e1.printStackTrace();
   }
   Platform.runLater(() -> {
    ArrayList < String > names = new ArrayList < String > ();
    addBookAuthorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    //System.out.println(statush.get(1).getFirstname());
    for (int i = 0; i < authorList.size(); i++) {
     names.add(i, "(" + authorList.get(i).getId() + ")" + "\t" + authorList.get(i).getFirstname() + " " + authorList.get(i).getLastname());
    }
    //System.out.println(names.get(0));
    ObservableList < String > authors = FXCollections.observableArrayList(names);
    addBookAuthorsList.setItems(authors);

   });



   Message message5 = prepareGetSubjects(ActionType.GET_SUBJECTS);
   try {
    ClientController.clientConnectionController.sendToServer(message5);
   } catch (Exception e1) {
    e1.printStackTrace();
   }
   Platform.runLater(() -> {
    addBookSubjectsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    ObservableList < String > subjects = FXCollections.observableArrayList(subjectList);
    addBookSubjectsList.setItems(subjects);

   });

  }); //endbtn







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
     picStr = base64EncodedImage;
     System.out.println(base64EncodedImage);
     BufferedImage imgbuf;
     imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
     Image image1 = SwingFXUtils.toFXImage(imgbuf, null);
     picBook.setImage(image1);
    } catch (Exception e2) {
     actionOnError(ActionType.CONTINUE, "Your Picture format not suppoerted!");
     imageInBytes = null;
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
   priceTextField.setText("");
   picStr="noPicture";
   picBook.setImage(null);
   addBookPane.setVisible(false);
   editBookPane.setVisible(false);
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
   priceTextField.setText("");
   picBook.setImage(null);
   picStr="noPicture";
  });

  submitAddBook.setOnAction(e -> {
   String TitleBook = null;
   TitleBook = addBookTitle.getText();

   ObservableList < String > Authors = addBookAuthorsList.getSelectionModel().getSelectedItems();
   String authorsId = "";
   for (int i = 0; i < Authors.size(); i++)
    authorsId = Authors.get(i).substring(Authors.get(i).indexOf("(") + 1, Authors.get(i).indexOf(")")) + "^";

   String keywords = null;
   keywords = addBookKeywordsText.getText();

   String language = null;
   language = addBookLanguageList.getSelectionModel().getSelectedItem();

   ObservableList < String > Subjects = addBookSubjectsList.getSelectionModel().getSelectedItems();
   String SubjectsList = "";
   for (int i = 0; i < Subjects.size(); i++)
    SubjectsList += Subjects.get(i).substring(Subjects.get(i).indexOf("(") + 1, Subjects.get(i).indexOf(")")) + "^";

   String tableOfContent = null;
   tableOfContent = addBookTableOfContent.getText();

   String summary = null;
   summary = addBookSummary.getText();
   
   String price = null;
   price = priceTextField.getText();

   String picture=picStr;
   
   if(price.matches("[-+]?[0-9]*\\.?[0-9]+")==false)
	   actionOnError(ActionType.CONTINUE, "The price must be number!");

   else if (language == null || authorsId.length() == 0 || SubjectsList.length() == 0 || TitleBook == null || keywords == null || tableOfContent == null || summary == null)
    actionOnError(ActionType.CONTINUE, "You must to fill the all fields!");

   else {
    Message message6 = prepareAddBook(ActionType.ADD_BOOK, TitleBook, authorsId, keywords, language, SubjectsList, tableOfContent, summary, picture, price);
    try {
     ClientController.clientConnectionController.sendToServer(message6);
    } catch (Exception e1) {
     e1.printStackTrace();
    }
    Platform.runLater(() -> {
    actionOnError(ActionType.CONTINUE, "The book added successfully!");
    addBookTitle.setText("");
    addBookAuthorsList.getSelectionModel().clearSelection();
    addBookKeywordsText.setText("");
    addBookLanguageList.getSelectionModel().clearSelection();
    addBookSubjectsList.getSelectionModel().clearSelection();
    addBookTableOfContent.setText("");
    addBookSummary.setText("");
    picBook.setImage(null);
    picStr="noPicture";
    
    
    filteredData.clear();
    Message message7 = prepareGetBooksList(ActionType.GET_BOOK_LIST);
    try {
     ClientController.clientConnectionController.sendToServer(message7);
    } catch (IOException e2) {
     actionOnError(ActionType.TERMINATE, GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
    }
    Platform.runLater(() -> {
     String authors = "";
     for (int i = 0; i < BooksList.size(); i += 9) {
      if (i + 9 < BooksList.size() && BooksList.get(i).equals(BooksList.get(i + 9))) {
       authors = authors + BooksList.get(i + 5) + ",";
      } else {
       if (authors.equals("")) {
        String hide;
        if (BooksList.get(i + 3).equals("0")) hide = "no";
        else hide = "yes";
        data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
        filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
       } else {
        String hide;
        if (BooksList.get(i + 3).equals("0")) hide = "no";
        else hide = "yes";
        data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
        filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
       }
       authors = "";
      }
     }
    });
    try {
		Thread.sleep(1000);
	} catch (Exception e1) {
		e1.printStackTrace();
	}
    
    addBookPane.setVisible(false);
    editBookPane.setVisible(false);
    mainPane.setVisible(true);
    });
   }
  }); //end submit button

 //---------------------------------book edit pane---------------------
  
  
  editBookChoosePicBtn.setOnAction(e -> {
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
	     picStr = base64EncodedImage;
	     System.out.println(base64EncodedImage);
	     BufferedImage imgbuf;
	     imgbuf = ImageIO.read(new ByteArrayInputStream(imageInBytes));
	     Image image1 = SwingFXUtils.toFXImage(imgbuf, null);
	     editBookpicBook.setImage(image1);
	    } catch (Exception e2) {
	     actionOnError(ActionType.CONTINUE, "Your Picture format not suppoerted!");
	     imageInBytes = null;
	    }

	   }
	  });
  
  backEditBook.setOnAction(e -> {
	   editBookTitle.setText("");
	   editBookAuthorsList.getSelectionModel().clearSelection();
	   editBookKeywordsText.setText("");
	   editBookLanguageList.getSelectionModel().clearSelection();
	   editBookSubjectsList.getSelectionModel().clearSelection();
	   editBookTableOfContent.setText("");
	   editBookSummary.setText("");
	   editBookPriceTextField.setText("");
	   picStr="noPicture";
	   editBookpicBook.setImage(null);
	   addBookPane.setVisible(false);
	   editBookPane.setVisible(false);
	   mainPane.setVisible(true);
	  });
  
  submitEditBook.setOnAction(e -> {
	  
	   PropertyBook selectedItem = BooksTableView.getSelectionModel().getSelectedItem();  
	   String Sn= selectedItem.getBookSn();
	  
	   String TitleBook = null;
	   TitleBook = editBookTitle.getText();

	   ObservableList < String > Authors = editBookAuthorsListSelected.getItems();
	   String authorsId = "";
	   for (int i = 0; i < Authors.size(); i++)
	    authorsId = Authors.get(i).substring(Authors.get(i).indexOf("(") + 1, Authors.get(i).indexOf(")")) + "^";

	   String keywords = null;
	   keywords = editBookKeywordsText.getText();

	   String language = null;
	   language = editBookLanguageList.getSelectionModel().getSelectedItem();

	   ObservableList < String > Subjects = editBookSubjectsListSelected.getItems();
	   String SubjectsList = "";
	   for (int i = 0; i < Subjects.size(); i++)
	    SubjectsList += Subjects.get(i).substring(Subjects.get(i).indexOf("(") + 1, Subjects.get(i).indexOf(")")) + "^";

	   String tableOfContent = null;
	   tableOfContent = editBookTableOfContent.getText();

	   String summary = null;
	   summary = editBookSummary.getText();
	   
	   String price = null;
	   price = editBookPriceTextField.getText();

	   String picture=picStr;
	   
	   if(price.matches("[-+]?[0-9]*\\.?[0-9]+")==false)
		   actionOnError(ActionType.CONTINUE, "The price must be number!");

	   else if (language == null || authorsId.length() == 0 || SubjectsList.length() == 0 || TitleBook.equals("")==true || keywords.equals("")==true || tableOfContent.equals("")==true || summary.equals("")==true)
	    actionOnError(ActionType.CONTINUE, "You must to fill the all fields!");

	   else {
	    Message message6 = prepareEditBook(ActionType.EDIT_BOOK, Sn, TitleBook, authorsId, keywords, language, SubjectsList, tableOfContent, summary, picture, price);
	    try {
	     ClientController.clientConnectionController.sendToServer(message6);
	    } catch (Exception e1) {
	     e1.printStackTrace();
	    }
	    Platform.runLater(() -> {
	    actionOnError(ActionType.CONTINUE, "The book added successfully!");
	    addBookTitle.setText("");
	    addBookAuthorsList.getSelectionModel().clearSelection();
	    addBookKeywordsText.setText("");
	    addBookLanguageList.getSelectionModel().clearSelection();
	    addBookSubjectsList.getSelectionModel().clearSelection();
	    addBookTableOfContent.setText("");
	    addBookSummary.setText("");
	    picBook.setImage(null);
	    picStr="noPicture";
	    
	    filteredData.clear();
	    Message message7 = prepareGetBooksList(ActionType.GET_BOOK_LIST);
	    try {
	     ClientController.clientConnectionController.sendToServer(message7);
	    } catch (IOException e2) {
	     actionOnError(ActionType.TERMINATE, GeneralMessages.UNNKNOWN_ERROR_DURING_SEND);
	    }
	    Platform.runLater(() -> {
	     String authors = "";
	     for (int i = 0; i < BooksList.size(); i += 9) {
	      if (i + 9 < BooksList.size() && BooksList.get(i).equals(BooksList.get(i + 9))) {
	       authors = authors + BooksList.get(i + 5) + ",";
	      } else {
	       if (authors.equals("")) {
	        String hide;
	        if (BooksList.get(i + 3).equals("0")) hide = "no";
	        else hide = "yes";
	        data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
	        filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
	       } else {
	        String hide;
	        if (BooksList.get(i + 3).equals("0")) hide = "no";
	        else hide = "yes";
	        data.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
	        filteredData.add(new PropertyBook(BooksList.get(i), BooksList.get(i + 1), BooksList.get(i + 2), hide, BooksList.get(i + 4), authors + BooksList.get(i + 5), BooksList.get(i + 6), BooksList.get(i + 7), BooksList.get(i + 8)));
	       }
	       authors = "";
	      }
	     }
	    });
	    try {
			Thread.sleep(1000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
	    addBookPane.setVisible(false);
	    editBookPane.setVisible(false);
	    mainPane.setVisible(true);
	    });
	   }
	  
  });
  
  
 }

 // ---------------- functions -------------------
 
 private static void configureFileChooser(final FileChooser fileChooser) {
  fileChooser.setTitle("View Pictures");
  fileChooser.setInitialDirectory(
   new File(System.getProperty("user.home"))
  );
 }



 private void updateFilteredData() {
  filteredData.clear();

  for (PropertyBook b: data) {
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
  ArrayList < TableColumn < PropertyBook, ? >> sortOrder = new ArrayList < > (BooksTableView.getSortOrder());
  BooksTableView.getSortOrder().clear();
  BooksTableView.getSortOrder().addAll(sortOrder);
 }

 public Message prepareGetBooksList(ActionType type) {
  Message message = new Message();
  message.setType(type);
  return message;
 }

 public Message prepareAddBook(ActionType type, String titleBook, String authorsId2, String keywords, String language, String subjectsList2, String tableOfContent, String summary, String picture, String price) {
  Message message = new Message();
  ArrayList < String > elementsList = new ArrayList < String > ();
  elementsList.add(titleBook);
  elementsList.add(authorsId2);
  elementsList.add(keywords);
  elementsList.add(language);
  elementsList.add(subjectsList2);
  elementsList.add(tableOfContent);
  elementsList.add(summary);
  elementsList.add(picture);
  elementsList.add(price);
  message.setType(type);
  message.setElementsList(elementsList);
  return message;
 }
 
 public Message prepareEditBook(ActionType type, String Sn, String titleBook, String authorsId2, String keywords, String language, String subjectsList2, String tableOfContent, String summary, String picture, String price) {
	  Message message = new Message();
	  ArrayList < String > elementsList = new ArrayList < String > ();
	  elementsList.add(titleBook);
	  elementsList.add(authorsId2);
	  elementsList.add(keywords);
	  elementsList.add(language);
	  elementsList.add(subjectsList2);
	  elementsList.add(tableOfContent);
	  elementsList.add(summary);
	  elementsList.add(picture);
	  elementsList.add(price);
	  elementsList.add(Sn);
	  message.setType(type);
	  message.setElementsList(elementsList);
	  return message;
	 }

 public Message prepareGetAuthors(ActionType type) {
  Message message = new Message();
  message.setType(type);
  return message;
 }
 
 public Message prepareGetAuthors(ActionType type,String bookSn) {
	  Message message = new Message();
	  ArrayList < String > elementsList = new ArrayList < String > ();
	  elementsList.add(bookSn);
	  message.setType(type);
	  message.setElementsList(elementsList);
	  return message;
	 }
 
 public Message prepareGetLanguage(ActionType type,String bookSn) {
	  Message message = new Message();
	  ArrayList < String > elementsList = new ArrayList < String > ();
	  elementsList.add(bookSn);
	  message.setType(type);
	  message.setElementsList(elementsList);
	  return message;
	 }
 
 public Message prepareGetTableOfContent(ActionType type,String bookSn) {
	  Message message = new Message();
	  ArrayList < String > elementsList = new ArrayList < String > ();
	  elementsList.add(bookSn);
	  message.setType(type);
	  message.setElementsList(elementsList);
	  return message;
	 }
 

 public Message prepareGetSubjects(ActionType type,String bookSn) {
		  Message message = new Message();
		  ArrayList < String > elementsList = new ArrayList < String > ();
		  elementsList.add(bookSn);
		  message.setType(type);
		  message.setElementsList(elementsList);
		  return message;
		 }

 public Message prepareGetSubjects(ActionType type) {
  Message message = new Message();
  message.setType(type);
  return message;
 }


 public Message prepareUpdatePropertyBooks(ActionType type, String username) {
  Message message = new Message();
  ArrayList < String > elementsList = new ArrayList < String > ();
  elementsList.add(username);
  message.setType(type);
  message.setElementsList(elementsList);
  return message;
 }

 public Message prepareDeleteBook(ActionType type, String sn) {
  Message message = new Message();
  ArrayList < String > elementsList = new ArrayList < String > ();
  elementsList.add(sn);
  message.setType(type);
  message.setElementsList(elementsList);
  return message;
 }

 public Message prepareHideBook(ActionType type, String sn, String hide) {
  Message message = new Message();
  ArrayList < String > elementsList = new ArrayList < String > ();
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
  if (type == ActionType.TERMINATE) {
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
  private final SimpleStringProperty bookPrice;

  private PropertyBook(String bookSn, String bookTitle, String bookKeywords, String bookHide, String authorId, String authorName, String bookSummary, String bookImage, String bookPrice) {
   this.bookSn = new SimpleStringProperty(bookSn);
   this.bookTitle = new SimpleStringProperty(bookTitle);
   this.bookKeywords = new SimpleStringProperty(bookKeywords);
   this.bookHide = new SimpleStringProperty(bookHide);
   this.authorId = new SimpleStringProperty(authorId);
   this.authorName = new SimpleStringProperty(authorName);
   this.bookSummary = new SimpleStringProperty(bookSummary);
   this.bookImage = new SimpleStringProperty(bookImage);
   this.bookPrice = new SimpleStringProperty(bookPrice);
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
  
  public String getBookPrice() {
	   return bookPrice.get();
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
  
  public void setBookPrice(String bookPrice) {
   this.bookPrice.set(bookPrice);
  }


 }


}