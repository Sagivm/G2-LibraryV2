package control;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.text.AbstractDocument.ElementEdit;

import com.mysql.jdbc.Connection;

import entity.CurrentDate;
import entity.GeneralMessages;
import entity.Login;
import entity.Message;
import entity.Registration;
import entity.Replay;
import enums.ActionType;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * ServerController is controller of the GUI window. the controller creates log
 * in files and open connection to server, after auth via database.
 * 
 * @author nire
 *
 */
/**
 * @author givi
 *
 */
public class ServerController extends AbstractServer {

	/**
	 * Default port
	 */
	private static int dPort = 1337;

	/**
	 * Log
	 */
	private Logger logger;

	/**
	 * Text area for log.
	 */
	@FXML
	private TextArea logField;

	/**
	 * Text field for user.
	 */
	@FXML
	private TextField userField;

	/**
	 * Password field for password.
	 */
	@FXML
	private PasswordField passField;

	/**
	 * Username label for username.
	 */
	@FXML
	private Text usernameLabel;

	/**
	 * Password label for password.
	 */
	@FXML
	private Text passLabel;

	/**
	 * Button for connect/disconnect.
	 */
	@FXML
	private Button connectButton;

	/**
	 * ArrayList for all of the connected users
	 */
	private static ArrayList<Login> connectedList = new ArrayList<Login>();

	/**
	 * Constructor to establish connection with server, and prepare log file.
	 */
	public ServerController() {
		super(dPort);

		logger = Logger.getLogger("ServerLog.log");
		FileHandler fh;
		try {
			fh = new FileHandler("ServerLogFile.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} //
	}

	/**
	 * Constructor to establish connection with server, and prepare log file.
	 * 
	 * @param port
	 *            - Gets the port.
	 */
	public ServerController(int port) {
		super(port);
		logger = Logger.getLogger("ServerLog.log");
		FileHandler fh;
		try {
			fh = new FileHandler("ServerLogFile.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * CloseApp close the application when function called by menu -> "close"
	 * 
	 * @param event
	 */
	@FXML
	public void CloseApp(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ocsf.server.AbstractServer#handleMessageFromClient(java.lang.Object,
	 * ocsf.server.ConnectionToClient)
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		// CurrentDate date=new CurrentDate();

		// dateInitialize(); //Exception - sagiv
		// newDay(); //Exception - sagiv
		try {
			client.sendToClient(actionToPerform(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a message from client actionToPerform decide what to perform and
	 * the reply to return
	 * 
	 * @param message
	 * @return
	 */
	private Replay actionToPerform(Message message) throws IOException {

		ActionType type = message.getType();
		ArrayList<String> data = message.getElementsList();
		boolean sqlResult = false;
		Replay replay = null;
		int action = 0;
		switch (type) {
		case REGISTER: {

			Registration registration = new Registration(Integer.parseInt(data.get(0)), data.get(1).toString(),
					data.get(2).toString(), data.get(3).toString());

			try {
				DatabaseController.addToDatabase(registration.PrepareAddStatement());
				sqlResult = true;
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062) { //// duplicate primary key
					System.out.println("username already exist");
				}
			}
			if (sqlResult == true)
				replay = new Replay(ActionType.REGISTER, true);
			else {
				replay = new Replay(ActionType.REGISTER, false);
				System.out.println(replay.getSucess());
			}
			writeToLog("Registration attempt");
			break;
		}

		case LOGIN: {
			try {
				boolean isConnected = false;
				ArrayList<String> elementsList = new ArrayList<String>();
				for (int i = 0; i < connectedList.size(); i++) {
					if (connectedList.get(i).getUsername().equals(data.get(0).toString())) {
						isConnected = true;
						break;
					}
				}
				if (!isConnected) {
					Login login = new Login(data.get(0).toString(), data.get(1).toString());
					Statement stmt = DatabaseController.connection.createStatement();
					ResultSet rs = stmt.executeQuery(login.PrepareSelectStatement(1));

					while (rs.next()) {
						if (rs.getString(4).equals(data.get(1).toString())) {
							System.out.println("login succssefully");
							sqlResult = true;
							action = 1;
							elementsList.add(0, rs.getString(1)); // username
							elementsList.add(1, rs.getString(2)); // first name
							elementsList.add(2, rs.getString(3)); // last name
							elementsList.add(3, rs.getString(4)); // password
							elementsList.add(4, rs.getString(5)); // account
																	// type
							elementsList.add(5, rs.getString(6)); // account
																	// status
							break;
						}
					}
					if (!sqlResult) {
						rs = stmt.executeQuery(login.PrepareSelectStatement(2));
						while (rs.next()) {
							if (rs.getString(4).equals(data.get(1).toString())) {
								System.out.println("login succssefully");
								sqlResult = true;
								if (rs.getString(6).toString().equals("Manager"))
									action = 3;
								else
									action = 2;

								elementsList.add(0, rs.getString(1)); // username
								elementsList.add(1, rs.getString(2)); // first
																		// name
								elementsList.add(2, rs.getString(3)); // last
																		// name
								elementsList.add(3, rs.getString(4)); // password
								elementsList.add(4, rs.getString(5)); // email
								elementsList.add(5, rs.getString(6)); // job
								elementsList.add(6, rs.getString(7)); // department
								break;
							}
						}
					}
					if (sqlResult == true) {
						replay = new Replay(ActionType.LOGIN, true, action, elementsList);
						connectedList.add(login);
					} else {
						replay = new Replay(ActionType.LOGIN, false, GeneralMessages.USER_LOGGED_IN_FAILED);
						System.out.println(replay.getSucess());
					}
				} else {
					replay = new Replay(ActionType.LOGIN, false, GeneralMessages.USER_ALREADY_LOGGED_IN);
					System.out.println(replay.getSucess());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error");
			}
			/*
			 * if (sqlResult == true) { replay = new
			 * Replay(ActionType.LOGIN,true,action); } else { replay = new
			 * Replay(ActionType.LOGIN,false);
			 * System.out.println(replay.getSucess()); }
			 */
			writeToLog("Login attempt");
			break;
		}

		case LOGOUT: {
			// ArrayList <String> elementsList = new ArrayList<String>();
			boolean succes = false;
			for (int i = 0; i < connectedList.size(); i++) {
				if (connectedList.get(i).getUsername().equals(data.get(0).toString())) {
					connectedList.remove(i);
					succes = true;
					break;
				}
			}
			if (succes) {
				// System.out.println("Logout");
				replay = new Replay(ActionType.LOGOUT, true);
			}
			break;
		}
		case ACCOUNTTYPEREQ: {
			try {
				DatabaseController
						.updateDatabase("UPDATE clients SET accountStatus=" + "'" + message.getElementsList().get(1)
								+ "'" + "  WHERE username=" + "'" + message.getElementsList().get(0) + "'");
				writeToLog(message.getElementsList().get(0) + " Changed accountStatus to"
						+ message.getElementsList().get(1));
				replay = new Replay(ActionType.ACCOUNTTYPEREQ, true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

		case GET_PENDING_USERS: {
			try {
				ArrayList<String> elementsList = new ArrayList<String>();
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT username,firstName,lastName FROM clients WHERE accountType='RegisterPending'");
				while (rs.next()) {
					elementsList.add(rs.getString(1)); // username
					elementsList.add(rs.getString(2)); // first name
					elementsList.add(rs.getString(3)); // last name
				}
				replay = new Replay(ActionType.GET_PENDING_USERS, true, elementsList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		
		case SEARCH_BOOK_AND: { //itai
			ArrayList<String> elementsList = new ArrayList<String>();
			elementsList=makeSearchBook();
			for(int i=0;i<elementsList.size();i++)
				System.out.println(elementsList.get(i));
			System.out.println(" ");
			replay = new Replay(ActionType.GET_AUTHORS, true, elementsList);
			break;
		}
		
		case SEARCH_BOOK_OR: { //itai
			break;
		}

		case GET_AUTHORS: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName FROM authors;");
				while (rs.next()) {
					elementsList.add(rs.getString(1) + "^" + rs.getString(2) + "^" + rs.getString(3));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			replay = new Replay(ActionType.GET_AUTHORS, true, elementsList);
			break;
		}
		
		case GET_DOMAINS: {
			ArrayList<String> domainList = new ArrayList<String>();
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT name FROM domains;");
				while (rs.next()) {
					domainList.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			replay = new Replay(ActionType.GET_DOMAINS, true, domainList);
			break;
		}
		
		

		case ACCEPT_PENDING_USERS: {
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				String username = data.get(0);
				stmt.executeUpdate("UPDATE clients SET accountType='Intrested' WHERE username=" + username);
				replay = new Replay(ActionType.ACCEPT_PENDING_USERS, true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case PENDING_REVIEWS: {
			try {
				ArrayList<String> elementsList = new ArrayList<String>();
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT reviews.id, clients.username,clients.firstName,clients.lastName,books.title,reviews.content,reviews.date FROM reviews, clients, books WHERE clients.username = reviews.userId and books.sn = reviews.bookId and reviews.status = 'pending'");
				while (rs.next()) {
					elementsList.add(rs.getString(1)); // review id
					elementsList.add(rs.getString(2)); // username
					elementsList.add(rs.getString(3)); // first name
					elementsList.add(rs.getString(4)); // last name
					elementsList.add(rs.getString(5)); // book title
					elementsList.add(rs.getString(6)); // review content
					elementsList.add(rs.getString(7)); // review date
				}
				/*for(int i=0;i<elementsList.size();i+=7)
					System.out.println("Review: " + elementsList.get(i) + " " + elementsList.get(i+1) + " " + elementsList.get(i+2) + " " + elementsList.get(i+3) + " " + elementsList.get(i+4) + " " + elementsList.get(i+5));
				*/
				replay = new Replay(ActionType.PENDING_REVIEWS, true, elementsList);
				
			} catch (SQLException e) {
				e.printStackTrace();
				//System.out.println("error2");
			}
			break;
		}
		case USEREPORT: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController.searchInDatabase(
						"Select bookId,title,authors.firstName,authors.lastName,language,purchaseDate,bought_book.price FROM book,bought_book,author,book_authors WHERE book.sn=bought_book.bookId and books.sn=book_authors.bookId and author.id=book_authors.authorid and bought_book.userId="
								+ data.get(0) + ";");
				if (!rs.isBeforeFirst())
					replay = new Replay(ActionType.USEREPORT, false);// no data
				else {
					while (rs.next()) {
						elementsList.add(String.valueOf(rs.getInt(1)) + "^" + rs.getString(2) + "^" + rs.getString(3)
								+ " " + rs.getString(4) + "^" + rs.getString(4) + "^" + String.valueOf(rs.getInt(5))
								+ "^" + rs.getString(6));
					}
					replay = new Replay(ActionType.USEREPORT, true, elementsList);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		case DOMAINS: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController.searchInDatabase(
						"SELECT domains.name FROM book,subject,book_subject,domain WHERE subject.domain=domain.id and subject.id=book_subject.subjectId and book_subject.bookId="
								+ data.get(0) + ";");
				while (rs.next()) {
					elementsList.add(rs.getString(1));

				}
				replay = new Replay(ActionType.DOMAINS, true, elementsList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case POPULARITYREPORT: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController.searchInDatabase(
						"SELECT sn,title,authors.firstName,authors.lastName,language,purchase FROM books,authors,book_authors_book_by_date WHERE books.sn=book_authos.bookId and authors.authorId=authors.id and book.sn=book_by_date.bookId;");
				if (!rs.isBeforeFirst())
					replay = new Replay(ActionType.USEREPORT, false);// no data
				else {
					while (rs.next()) {
						elementsList.add(String.valueOf(rs.getInt(1)) + "^" + rs.getString(2) + "^" + rs.getString(3)
								+ " " + rs.getString(4) + "^" + rs.getString(5) + "^" + String.valueOf(rs.getInt(6)));
					}
					replay = new Replay(ActionType.USEREPORT, true, elementsList);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

		}
		return replay;

	}

	/**
	 * When the user pressed menu -> "help" it displays the user the message
	 * about us.
	 * 
	 * @param event
	 */
	@FXML
	public void PressedHelpMenu(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library");
		alert.setHeaderText(null);
		alert.setContentText(GeneralMessages.ABOUT_US);
		alert.showAndWait();
	}

	/**
	 * This function called when the user pressed on the button connect or
	 * disconnect the function connect databse, and server.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void buttonPressed(ActionEvent event) throws IOException {
		if (connectButton.getText().equals("Connect")) {
			try {
				if (userField.getText().equals(""))
					throw new IOException();
				DatabaseController databaseController = new DatabaseController();
				databaseController.SetConnection(userField.getText(), passField.getText());
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("user or password are empty. cannot connect to server");
				alert.showAndWait();
				return;
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("user or password are incorrect. cannot connect to server");
				alert.showAndWait();
				return;
			} catch (Exception e) {
				writeToLog("Java driver not found.");
				return;
			}
			writeToLog("Connected to database");
			try {
				usernameLabel.setVisible(false);
				passLabel.setVisible(false);
				userField.setVisible(false);
				passField.setVisible(false);
				userField.clear();
				passField.clear();
				// problem
				connectButton.setText("Disconnect");
				this.listen();
			} catch (Exception e) {
				writeToLog("Cant connect server.");
				return;
			}
			return;
		}
		if (connectButton.getText().equals("Disconnect")) {
			writeToLog("Disconnected sucessfully");
			try {
				this.close();
				DatabaseController.CloseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			usernameLabel.setVisible(true);
			passLabel.setVisible(true);
			userField.setVisible(true);
			passField.setVisible(true);
			userField.clear();
			passField.clear();
			connectButton.setText("Connect");
		}

	}

	/**
	 * This function send the parameter to file, and to I/O after getting the
	 * time. it appends the string.
	 * 
	 * @param msg
	 *            - message that will be write in log file, and into server GUI.
	 */
	void writeToLog(String msg) {
		Date datelog = new Date();
		logger.info(msg);
		logField.appendText(datelog.toString() + " " + msg + "\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ocsf.server.AbstractServer#serverStarted()
	 */
	@Override
	protected void serverStarted() {
		writeToLog("Server listening for connections\non port: " + getPort());
	}
	/*
	 * protected void serverStopped() { //writeToLog("NIR"); }
	 * 
	 * writeToLog("Server stopped"); }
	 */
	
	
	private ArrayList<String> makeSearchBook() throws IOException
	{
		ArrayList<String> elementsList = new ArrayList<String>();
		
		try {
			
			ArrayList<String> book_sn = new ArrayList<String>();
			ArrayList<String> bookId_authorId = new ArrayList<String>();
			ArrayList<String> authors = new ArrayList<String>();
			ArrayList<String> bookId_authorName = new ArrayList<String>();
			
			ArrayList<String> book_subjects = new ArrayList<String>();
			ArrayList<String> domains = new ArrayList<String>();
			ArrayList<String> subjects = new ArrayList<String>();
			ArrayList<String> book_subjects_domains = new ArrayList<String>();
			
			/* add sn, title, language, authors count for each book */
			Statement stmt = DatabaseController.connection.createStatement();
			ResultSet rs_books = stmt.executeQuery("SELECT sn, title, language, authorsCount FROM books;");
			while(rs_books.next())
			{
				elementsList.add(rs_books.getString(1) + "^" + rs_books.getString(2) + "^" + rs_books.getString(3) + "^" + rs_books.getString(4));
				book_sn.add(rs_books.getString(1));
			}
			
			
			/* add authors names for each book */
			ResultSet rs_book_authors = stmt.executeQuery("SELECT * FROM book_authors;");
			while(rs_book_authors.next())
			{
				bookId_authorId.add(rs_book_authors.getString(1)); //bookId
				bookId_authorId.add(rs_book_authors.getString(2)); //authorId
			}
			

			
			ResultSet rs_authors = stmt.executeQuery("SELECT * FROM authors;");
			while(rs_authors.next())
			{
				authors.add(rs_authors.getString(1)); //author id
				authors.add(rs_authors.getString(2)+" "+rs_authors.getString(3)); //author name
			}
			

			
			for(int i=0;i<bookId_authorId.size();i=i+2)
			{
				for(int j=0;j<authors.size();j=j+2)
				{
					if(bookId_authorId.get(i+1).equals(authors.get(j)))
					{
						bookId_authorName.add(bookId_authorId.get(i)); //book id
						bookId_authorName.add(authors.get(j+1)); //book author full name
						break;
					}
				}
			}
			
			
			for(int i=0;i<bookId_authorName.size();i=i+2)
			{
				for(int j=0;j<elementsList.size();j++)
				{
					String bookid[] = new String[4];
					bookid = elementsList.get(j).split("\\^");
					if(bookId_authorName.get(i).equals(bookid[0]))
					{
						String tmp2 =elementsList.get(j);
						tmp2=tmp2+"^"+bookId_authorName.get(i+1);
						elementsList.add(j+1, tmp2);
						elementsList.remove(j);
						break;
						
					}
					
				}

			}
			
		
			/* add domains count for each book (domains count = subjects count) */
			ResultSet rs_books2 = stmt.executeQuery("SELECT domainsCount FROM books;");
			int k=0;
			while(rs_books2.next())
			{
				String tmp =elementsList.get(k);
				tmp=tmp+"^"+rs_books2.getString(1);
				elementsList.add(k+1, tmp);
				elementsList.remove(k);
				k++;
			}
			
			//download book_subjects table from DB
			ResultSet rs_bookSubjects= stmt.executeQuery("SELECT * from book_subjects");
			while(rs_bookSubjects.next())
			{
				book_subjects.add(rs_bookSubjects.getString(1)); //book id
				book_subjects.add(rs_bookSubjects.getString(2)); //subject id
			}
			
			//download subjects table from DB
			ResultSet rs_subjects= stmt.executeQuery("SELECT * from subjects");
			while(rs_subjects.next())
			{
				subjects.add(rs_subjects.getString(1)); //subject id
				subjects.add(rs_subjects.getString(2)); //subject name
				subjects.add(rs_subjects.getString(4)); //domain id
			}
			
			//download domains table from DB
			ResultSet rs_domains= stmt.executeQuery("SELECT * from domains");
			while(rs_domains.next())
			{
				domains.add(rs_domains.getString(1)); //domain id
				domains.add(rs_domains.getString(2)); //domain name
			}
		
			
			//build book_subjects_domains
			
			for(int i=0; i<book_sn.size();i++) //add book id
				book_subjects_domains.add("");
			
			
			for(int i=0; i<book_subjects_domains.size();i++) //
			{
				for(int j=0;j<book_subjects.size();j=j+2)
				{
					if(book_sn.get(i).equals(book_subjects.get(j))) //found book id in books_subjects, now find its name 
					{
						for(int m=0;m<subjects.size();m=m+3)
						{
							if(book_subjects.get(j+1).equals(subjects.get(m))) //subjects ids are equal, now take its name
							{
								String tmp = book_subjects_domains.get(i);
								tmp=tmp+"^"+subjects.get(m+1);
								
								book_subjects_domains.add(i+1, tmp);
								book_subjects_domains.remove(i);
								
								//now take domain's name
								for(int n=0;n<domains.size();n=n+2)
								{
									
									if((subjects.get(m+2).equals(domains.get(n)))) //domains ids are equal, now take its name
									{
										String tmp2 = book_subjects_domains.get(i);
										tmp2=tmp2+domains.get(n+1);
										book_subjects_domains.add(i+1, tmp2);
										book_subjects_domains.remove(i);
										break;
									}
								}
								break;
							}
						}
					}
				}
			}
							

			for(int i=0;i<elementsList.size();i++)
			{
				String tmp =elementsList.get(i);
				tmp=tmp+"^"+book_subjects_domains.get(i);
				elementsList.add(i+1, tmp);
				elementsList.remove(i);
			}
			/*
			for(int i=0;i<elementsList.size();i++)
				System.out.println(elementsList.get(i));
			System.out.println(" ");
			*/
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elementsList;
	}

}
