package control;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

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
		
		case GET_MESSAGES: {
			
			String username = data.get(0);
			
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT date,time,msg FROM messages WHERE username =" + "'"+username+"'");
				while (rs.next())
				{
					elementsList.add(rs.getString(1) + "^" + rs.getString(2) + "^" + rs.getString(3));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}	
			replay = new Replay(ActionType.GET_MESSAGES, true, elementsList);
		}
		break;
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
			/*
			int flag=0;
			try {
			ResultSet rs1 = DatabaseController.searchInDatabase(
					"SELECT username FROM clients WHERE accountType='RegisterPending'");
			while (rs1.next()) {
				if (rs1.getString(1).equals(data.get(0).toString())) flag=1;
			}
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			*/
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
				
				DateFormat currentTime = new SimpleDateFormat("HH:mm");
				DateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
				String currTime = currentTime.format(date);
				String currDate = currentDate.format(date);
				String username = message.getElementsList().get(0);
				String msg = "Account status has been changed to: " + message.getElementsList().get(1);
				DatabaseController.addToDatabase("INSERT INTO messages VALUES('"+username+"', '"+currDate+"' , '"+currTime+"', '"+msg+"')");
				
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

		
		
		
		case SEARCH_BOOK_AND: { // itai
			ArrayList<String> elementsList = new ArrayList<String>();
			elementsList=makeSearchBook();
			int[] filterResults=new int[elementsList.size()]; //books that will be shown in results
			int i,j, continue_index;
			ArrayList<String> newSearch=message.getElementsList();

			//initiate filterResults
			for(i=0;i<filterResults.length;i++)
				filterResults[i]=1;
			
			for(i=0;i<elementsList.size();i++)
			{
				String bookRow[] = new String[countItems(elementsList.get(i),"^")];
				bookRow = elementsList.get(i).split("\\^");
				
				if(!newSearch.get(0).isEmpty()) //title
					if(!bookRow[1].toLowerCase().contains(newSearch.get(0).toLowerCase())) //searched title doesn't appear
						filterResults[i]=0;
					
				if(!newSearch.get(1).equals("")) //language
					if(!bookRow[2].equals(newSearch.get(1))) //searched language wansn't found
						filterResults[i]=0;
				
				if(!newSearch.get(2).isEmpty()) //summary
					if(!bookRow[3].toLowerCase().contains(newSearch.get(2).toLowerCase())) //searched summary doesn't appear
						filterResults[i]=0;
				
				if(!newSearch.get(3).isEmpty()) //toc
					if(!bookRow[4].toLowerCase().contains(newSearch.get(3).toLowerCase())) //searched toc doesn't appear
						filterResults[i]=0;
				
				if(!newSearch.get(4).isEmpty()) //key words
				{
					int size=countItems(newSearch.get(4), ",");
					String[] keyWords = new String[size];
					keyWords = newSearch.get(4).split("\\,");
					for(j=0;j<size;j++)
					{
						if(!bookRow[5].toLowerCase().contains(keyWords[j].toLowerCase().trim())) //searched key words don't appear
							filterResults[i]=0;
					}
				}
				
				continue_index=6; //in case there are no authors on search
				
				//authors
				int authorsCount=Integer.parseInt(newSearch.get(5)); //newSearch
				int authorsInRow=Integer.parseInt(bookRow[6]); //bookRow
				int[] authorsFound=new int[authorsCount];
				if(authorsCount!=0) 
				{
					for(j=0;j<authorsCount;j++)
					{
						for(int k=0;k<authorsInRow;k++)
						{
							int beginName=newSearch.get(6+j).lastIndexOf(")")+1;
							String aName=newSearch.get(6+j).substring(beginName).trim();
							if(bookRow[7+k].equals(aName))
								authorsFound[j]++;
						}
					}
					continue_index=6+authorsCount;
					
					for(j=0;j<authorsFound.length;j++)
						if(authorsFound[j]==0)
							filterResults[i]=0;

				}
					
				//domains
				int domainsCount=Integer.parseInt(newSearch.get(continue_index));
				int domainsInRow=Integer.parseInt(bookRow[7+authorsInRow]);
				int[] domainsFound=new int[domainsCount];
				
				
				if(domainsCount!=0) 
				{
					for(j=0;j<domainsCount;j++)
					{
						for(int k=0;k<domainsInRow;k=k+2)
						{						
							if(bookRow[9+authorsInRow+k].equals(newSearch.get(continue_index+1+j)))
								domainsFound[j]++;
						}
					}
					
					for(j=0;j<domainsFound.length;j++)
						if(domainsFound[j]==0)
							filterResults[i]=0;
				}
			}

			j=0;
			for(i=0;i<elementsList.size();i=i+2)
			{
				elementsList.add(i, Integer.toString(filterResults[j]));
				j++;
			}
			
			ArrayList<String> res = new ArrayList<>();
			for(i=0;i<elementsList.size();i=i+2)
			{
				if(Integer.parseInt(elementsList.get(i))==1)
					res.add(elementsList.get(i+1));
			}
			
			/*
			 for(i=0;i<res.size();i++)
				 System.out.println(res.get(i));
				*/  

			replay = new Replay(ActionType.SEARCH_BOOK_AND, true, res);
			break;
		}

		case SEARCH_BOOK_OR: { // itai -need to fix
			
			ArrayList<String> elementsList = new ArrayList<String>();
			elementsList=makeSearchBook();
			//int[] filterResults=new int[elementsList.size()]; //books that will be shown in results
			int i,j, continue_index;
			ArrayList<String> newSearch=message.getElementsList();

			ArrayList<String> res=new ArrayList<String>();
			
						
			for(i=0;i<elementsList.size();i++)
			{
				String bookRow[] = new String[countItems(elementsList.get(i),"^")];
				bookRow = elementsList.get(i).split("\\^");
				
				if(!newSearch.get(0).isEmpty()) //title
					if(bookRow[1].toLowerCase().contains(newSearch.get(0).toLowerCase())) //searched title doesn't appear
						res.add(elementsList.get(i));					
				
					
				if(!newSearch.get(1).equals("")) //language
					if(bookRow[2].equals(newSearch.get(1))) //searched language wansn't found
						res.add(elementsList.get(i));

				if(!newSearch.get(2).isEmpty()) //summary
					if(bookRow[3].toLowerCase().contains(newSearch.get(2).toLowerCase())) //searched summary doesn't appear
						res.add(elementsList.get(i));
				
				if(!newSearch.get(3).isEmpty()) //toc
					if(bookRow[4].toLowerCase().contains(newSearch.get(3).toLowerCase())) //searched toc doesn't appear
						res.add(elementsList.get(i));
				
				if(!newSearch.get(4).isEmpty()) //key words
				{
					int size=countItems(newSearch.get(4), ",");
					String[] keyWords = new String[size];
					keyWords = newSearch.get(4).split("\\,");
					for(j=0;j<size;j++)
					{
						if(bookRow[5].toLowerCase().contains(keyWords[j].toLowerCase().trim())) //searched key words don't appear
							res.add(elementsList.get(i));
					}
				}
				
				continue_index=6; //in case there are no authors on search
				
				//authors
				int authorsCount=Integer.parseInt(newSearch.get(5)); //newSearch
				int authorsInRow=Integer.parseInt(bookRow[6]); //bookRow
				if(authorsCount!=0) 
				{
					for(j=0;j<authorsCount;j++)
					{
						for(int k=0;k<authorsInRow;k++)
						{
							int beginName=newSearch.get(6+j).lastIndexOf(")")+1;
							String aName=newSearch.get(6+j).substring(beginName).trim();
							if(bookRow[7+k].equals(aName))
								res.add(elementsList.get(i));
							
						}
					}
					continue_index=6+authorsCount;
				}
					
				//domains
				int domainsCount=Integer.parseInt(newSearch.get(continue_index));
				int domainsInRow=Integer.parseInt(bookRow[7+authorsInRow]);

				if(domainsCount!=0) 
				{
					for(j=0;j<domainsCount;j++)
					{
						for(int k=0;k<domainsInRow;k=k+2)
						{
							if(bookRow[9+authorsInRow+k].equals(newSearch.get(continue_index+1+j)))
								res.add(elementsList.get(i));
						}
					}
					
	
				}
				
				
				
			}
			//res = res.stream().distinct().collect(Collectors.toList());

			/*
			 for(i=0;i<res.size();i++)
				 System.out.println(res.get(i));
				  */
			 
			replay = new Replay(ActionType.SEARCH_BOOK_OR, true, res);
			break;
		}
		
		case SEARCH_USER: {
			try
			{
				ArrayList<String> elementsList = new ArrayList<String>();
				ArrayList<String> res = new ArrayList<String>();
				
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT username, firstName,lastName, accountType, accountStatus FROM clients");
				while (rs.next())
				{
					elementsList.add(rs.getString(1)); // username
					elementsList.add(rs.getString(2)); // first name
					elementsList.add(rs.getString(3)); // last name
					elementsList.add(rs.getString(4)); // accountType
					elementsList.add(rs.getString(5)); // accountStatus
				}
				
				
				int[] filterResult = new int[elementsList.size()/5];
				
				for(int i=0;i<filterResult.length;i++)
					filterResult[i]=1;


				for(int i=0;i<elementsList.size();i+=5)
				{
					if(!message.getElementsList().get(0).isEmpty())
						if(!elementsList.get(i).contains(message.getElementsList().get(0).trim()))
							filterResult[i/5]=0;
						

					if(!message.getElementsList().get(1).isEmpty())
						if(!elementsList.get(i+1).toLowerCase().trim().contains(message.getElementsList().get(1).toLowerCase().trim()))
							filterResult[i/5]=0;


					if(!message.getElementsList().get(2).isEmpty())
						if(!elementsList.get(i+2).toLowerCase().trim().contains(message.getElementsList().get(2).toLowerCase().trim()))
							filterResult[i/5]=0;

					
				}
				
				/*
				for(int i=0;i<filterResult.length;i++)
					System.out.println(filterResult[i]);
				System.out.println(" ");
				*/
				
				for(int j=0;j<elementsList.size();j+=5)
				{
					if(filterResult[j/5]==1)
						res.add(elementsList.get(j)+"^"+elementsList.get(j+1)+"^"+elementsList.get(j+2)+"^"+elementsList.get(j+3)+"^"+elementsList.get(j+4));
				}
				
				
				/*
				for(int i=0;i<res.size();i++)
					System.out.println(res.get(i));
				*/
				
				replay = new Replay(ActionType.SEARCH_USER, true, res);
				
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		}
		
		case EDIT_USER: {
			try
			{
				DatabaseController.updateDatabase("UPDATE clients SET firstName=" + "'" + message.getElementsList().get(1)
						+ "'" + "and lastName=" + "'" + message.getElementsList().get(2) +"'" +  "WHERE username=" + "'" + message.getElementsList().get(0) + "'");
				writeToLog(message.getElementsList().get(0) + " Changed name to"
				+ message.getElementsList().get(1) + " " + message.getElementsList().get(2));
				replay = new Replay(ActionType.EDIT_USER, true);
				
			} 
			catch (Exception e) {
				e.printStackTrace();		
			}
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
				replay = new Replay(ActionType.GET_AUTHORS, true, elementsList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
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
				replay = new Replay(ActionType.GET_DOMAINS, true, domainList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		}

		case ACCEPT_PENDING_USERS: {
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				String username = data.get(0);
				stmt.executeUpdate("UPDATE clients SET accountType='Intrested' WHERE username=" + username);
				
				DateFormat currentTime = new SimpleDateFormat("HH:mm");
				DateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
				String currTime = currentTime.format(date);
				String currDate = currentDate.format(date);
				String msg = "Your account has been approved";
				DatabaseController.addToDatabase("INSERT INTO messages VALUES('"+username+"', '"+currDate+"' , '"+currTime+"', '"+msg+"')");
				
		
				replay = new Replay(ActionType.ACCEPT_PENDING_USERS, true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		case DECLINE_PENDING_USERS: {
			try {
				Statement stmt = DatabaseController.connection.createStatement();
				String username = data.get(0);
				stmt.executeUpdate("DELETE FROM clients WHERE username=" + username);
				replay = new Replay(ActionType.DECLINE_PENDING_USERS, true);
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
				/*
				 * for(int i=0;i<elementsList.size();i+=7)
				 * System.out.println("Review: " + elementsList.get(i) + " " +
				 * elementsList.get(i+1) + " " + elementsList.get(i+2) + " " +
				 * elementsList.get(i+3) + " " + elementsList.get(i+4) + " " +
				 * elementsList.get(i+5));
				 */
				replay = new Replay(ActionType.PENDING_REVIEWS, true, elementsList);

			} catch (SQLException e) {
				e.printStackTrace();
				// System.out.println("error2");
			}
			break;
		}
		case USEREPORT: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController.searchInDatabase(
						"Select books.sn,title,authors.firstName,authors.lastName,language,purchaseDate,bought_book.price "
						+ "FROM books,bought_book,authors,book_authors "
						+ "WHERE books.sn=bought_book.bookId and books.sn=book_authors.bookId and authors.id=book_authors.authorid and bought_book.userId="
								+ data.get(0) + ";");
				if (!rs.isBeforeFirst())
					replay = new Replay(ActionType.USEREPORT, false);// no data
				else {
					while (rs.next()) {
						elementsList.add(String.valueOf(rs.getInt(1)) + "^" + rs.getString(2) + "^" + rs.getString(3)
								+ " " + rs.getString(4) + "^" + rs.getString(5) + "^" +rs.getString(6) 
								+ "^" +String.valueOf(rs.getInt(7)));
					}
					replay = new Replay(ActionType.USEREPORT, true, elementsList);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		case GETDOMAINSSPECIFIC: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController.searchInDatabase(
						"SELECT domains.name FROM book,subject,book_subject,domain WHERE subject.domain=domain.id and subject.id=book_subject.subjectId and book_subject.bookId="
								+ data.get(0) + ";");
				while (rs.next()) {
					elementsList.add(rs.getString(1));

				}
				replay = new Replay(ActionType.GETDOMAINSSPECIFIC, true, elementsList);
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
						"SELECT sn,title,authors.firstName,authors.lastName,language,purchase,domains.name FROM books,authors,book_authors_book_by_date,subject,book_subject,domain WHERE books.sn=book_authos.bookId and authors.authorId=authors.id and book.sn=book_by_date.bookId and subject.domain=domain.id and subject.id=book_subject.subjectId and book_subject.bookId=books.sn;");
				if (!rs.isBeforeFirst())
					replay = new Replay(ActionType.USEREPORT, false);// no data
				else {
					while (rs.next()) {
						elementsList.add(String.valueOf(rs.getInt(1)) + "^" + rs.getString(2) + "^" + rs.getString(3)
								+ " " + rs.getString(4) + "^" + rs.getString(5) + "^" + String.valueOf(rs.getInt(6))
								+ "^" + rs.getString(7));
					}
					replay = new Replay(ActionType.USEREPORT, true, elementsList);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		case BOOKREPORT: {
			ArrayList<String> elementsList = new ArrayList<String>();
			try {
				ResultSet rs = DatabaseController
						.searchInDatabase("SELECT date,searchCount,purchaseCount FROM book_by_date WHERE bookId="
								+ Integer.valueOf(elementsList.get(0)) + ";");
				while (rs.next()) {
					elementsList.add(
							rs.getString(1) + "^" + String.valueOf(rs.getInt(2)) + "^" + String.valueOf(rs.getInt(3)));
				}
				replay = new Replay(ActionType.BOOKREPORT, true, elementsList);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		case UPDATE_REVIEW_STATUS: {
			try {
				char quotationMarks = '"';  
				Statement stmt = DatabaseController.connection.createStatement();
				//System.out.println("UPDATE project.reviews SET reviews.status = '" + data.get(2) + "' , reviews.content = " + quotationMarks + data.get(1) + quotationMarks + "  WHERE reviews.id =" + data.get(0));
				stmt.executeUpdate("UPDATE project.reviews SET reviews.status = '" + data.get(2) + "' , reviews.content = " + quotationMarks + data.get(1) + quotationMarks + " WHERE reviews.id =" + data.get(0));
				
				DateFormat currentTime = new SimpleDateFormat("HH:mm");
				DateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
				String currTime = currentTime.format(date);
				String currDate = currentDate.format(date);
				String msg;
				if (data.get(2).equals("approved"))
				msg = "Review num " + data.get(0) + " has been approved";
				else msg = "Review num " + data.get(0) + " has been declined";
				String username = data.get(3);
				
				DatabaseController.addToDatabase("INSERT INTO messages VALUES('"+username+"', '"+currDate+"' , '"+currTime+"', '"+msg+"')");
				
				
				replay = new Replay(ActionType.UPDATE_REVIEW_STATUS, true);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		case BOOK_REVIEWS: {
			try { 
				ArrayList<String> elementsList = new ArrayList<String>();
				Statement stmt = DatabaseController.connection.createStatement();
				//System.out.println("SELECT clients.firstName,clients.lastName,bought_book.purchaseDate,reviews.date,reviews.content FROM project.reviews,project.clients,project.bought_book WHERE reviews.userId=clients.username and reviews.userId=bought_book.userId and bought_book.bookId = " + data.get(0) + " and reviews.bookId = " + data.get(0));
				ResultSet rs = stmt.executeQuery("SELECT clients.firstName,clients.lastName,bought_book.purchaseDate,reviews.date,reviews.content FROM project.reviews,project.clients,project.bought_book WHERE reviews.status='approved' and reviews.userId=clients.username and reviews.userId=bought_book.userId and bought_book.bookId = " + data.get(0) + " and reviews.bookId = " + data.get(0));
				
				if(rs == null)
				{
					elementsList.add("null"); // no results
				}
				else
				{
					while (rs.next()) {
						elementsList.add(rs.getString(1)); // first name
						elementsList.add(rs.getString(2)); // last name
						elementsList.add(rs.getString(3)); // purchase date
						elementsList.add(rs.getString(4)); // review date
						elementsList.add(rs.getString(5)); // content
					}
				}
				replay = new Replay(ActionType.BOOK_REVIEWS, true, elementsList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		case WRITE_REVIEW: {
			
			//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			
			String sqlStmt = "INSERT INTO reviews (`userId`, `bookId`, `content`, `status`, `date`)"
					+ "VALUES ('"+data.get(0)+"','"+data.get(1)+"','"+data.get(2)+"','pending','"+dateFormat.format(date)+"')";
	
			try {
				DatabaseController.addToDatabase(sqlStmt);
				sqlResult = true;
			} catch (SQLException e) {
				System.out.println(e);
				if (e.getErrorCode() == 1062) { //// duplicate primary key
					System.out.println("duplicate primary key - Write a review");
				}
			}
			if (sqlResult == true)
				replay = new Replay(ActionType.WRITE_REVIEW, true);
			else {
				replay = new Replay(ActionType.WRITE_REVIEW, false);
				System.out.println(replay.getSucess());
			}
			break;
		}
		
		
		case GET_BOOK_LIST: {
			try {
				ArrayList<String> elementsList = new ArrayList<String>();
				Statement stmt = DatabaseController.connection.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT books.sn,books.title,books.keywords,books.hide,authors.id,CONCAT(authors.firstName,' ',authors.lastName) as authorName, books.summary, books.image FROM books,book_authors,authors "
						+ "WHERE books.sn=book_authors.bookId AND book_authors.authorId=authors.id "
						+ "ORDER BY sn");
				while (rs.next()) {
					elementsList.add(rs.getString(1)); // sn
					elementsList.add(rs.getString(2)); // title
					elementsList.add(rs.getString(3)); // keywords
					elementsList.add(rs.getString(4)); // hide
					elementsList.add(rs.getString(5)); // author id
					elementsList.add(rs.getString(6)); // author name
					elementsList.add(rs.getString(7)); // book summary
					elementsList.add(rs.getString(8)); // book image
				}
				replay = new Replay(ActionType.GET_BOOK_LIST, true, elementsList);
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

	
	
	/**
	 * This function returns relevant data for book search
	 * @author itain
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
			ResultSet rs_books = stmt.executeQuery("SELECT sn, title, language, summary, tableOfContent, keywords, authorsCount FROM books;");
			while(rs_books.next())
			{
				elementsList.add(rs_books.getString(1) + "^" + rs_books.getString(2) + "^" + rs_books.getString(3) + "^" + rs_books.getString(4)+ "^" + rs_books.getString(5)+ "^" + rs_books.getString(6)+ "^" + rs_books.getString(7));
				book_sn.add(rs_books.getString(1));
			}

			
			/* add authors names for each book */
			ResultSet rs_book_authors = stmt.executeQuery("SELECT * FROM book_authors;");
			while (rs_book_authors.next())
			{
				bookId_authorId.add(rs_book_authors.getString(1)); // bookId
				bookId_authorId.add(rs_book_authors.getString(2)); // authorId
			}

			ResultSet rs_authors = stmt.executeQuery("SELECT * FROM authors;");
			while (rs_authors.next())
			{
				authors.add(rs_authors.getString(1)); // author id
				authors.add(rs_authors.getString(2) + " " + rs_authors.getString(3)); // author name
			}

			for (int i = 0; i < bookId_authorId.size(); i = i + 2)
			{
				for (int j = 0; j < authors.size(); j = j + 2)
				{
					if (bookId_authorId.get(i + 1).equals(authors.get(j)))
					{
						bookId_authorName.add(bookId_authorId.get(i)); // book id
						bookId_authorName.add(authors.get(j + 1)); // book's author full name
						break;
					}
				}
			}

			for (int i = 0; i < bookId_authorName.size(); i = i + 2)
			{
				for (int j = 0; j < elementsList.size(); j++) 
				{
					String bookid[] = new String[4];
					bookid = elementsList.get(j).split("\\^");
					if (bookId_authorName.get(i).equals(bookid[0]))
					{
						String tmp2 = elementsList.get(j);
						tmp2 = tmp2 + "^" + bookId_authorName.get(i + 1);
						elementsList.add(j + 1, tmp2);
						elementsList.remove(j);
						break;

					}

				}

			}
			
		 
			/* add subjects count for each book */ 
			ResultSet rs_books2 = stmt.executeQuery("SELECT subjectsCount FROM books;");
			int k = 0;
			while (rs_books2.next()) {
				String tmp = elementsList.get(k);
				tmp = tmp + "^" + rs_books2.getString(1);
				elementsList.add(k + 1, tmp);
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

		

			// download subjects table from DB
			ResultSet rs_subjects = stmt.executeQuery("SELECT * from subjects");
			while (rs_subjects.next()) 
			{
				subjects.add(rs_subjects.getString(1)); // subject id
				subjects.add(rs_subjects.getString(2)); // subject name
				subjects.add(rs_subjects.getString(4)); // domain id
			}
			
	

			// download domains table from DB
			ResultSet rs_domains = stmt.executeQuery("SELECT * from domains");
			while (rs_domains.next())
			{
				domains.add(rs_domains.getString(1)); // domain id
				domains.add(rs_domains.getString(2)); // domain name
			}
			

			// build book_subjects_domains

			for (int i = 0; i < book_sn.size(); i++)
				book_subjects_domains.add("");

			for (int i = 0; i < book_subjects_domains.size(); i++) //
			{
				for (int j = 0; j < book_subjects.size(); j = j + 2)
				{
					if (book_sn.get(i).equals(book_subjects.get(j))) // found book id in books_subjects, now find its name
					{
						for (int m = 0; m < subjects.size(); m = m + 3) 
						{
							if (book_subjects.get(j + 1).equals(subjects.get(m))) // subjects ids are equal, now take its name
							{
								String tmp = book_subjects_domains.get(i);
								tmp = tmp + "^" + subjects.get(m + 1);

								book_subjects_domains.add(i + 1, tmp);
								book_subjects_domains.remove(i);

								// now take domain's name
								for (int n = 0; n < domains.size(); n = n + 2)
								{

									if ((subjects.get(m + 2).equals(domains.get(n)))) // domains ids are equal,now take its name
									{
										String tmp2 = book_subjects_domains.get(i);
										tmp2=tmp2+"^"+domains.get(n+1);
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

			for (int i = 0; i < elementsList.size(); i++)
			{
				String tmp = elementsList.get(i);
				tmp = tmp + book_subjects_domains.get(i);
				elementsList.add(i + 1, tmp);
				elementsList.remove(i);
			}

			
			
			
			ResultSet rs_books3 = stmt.executeQuery("SELECT price FROM books;");
			k = 0;
			while (rs_books3.next())
			{
				String tmp = elementsList.get(k);
				tmp = tmp + "^" + rs_books3.getString(1); //price
				elementsList.add(k + 1, tmp);
				elementsList.remove(k);
				k++;
			}
			
			/*
			 for(int i=0;i<elementsList.size();i++)
				 System.out.println(elementsList.get(i)); 
			 System.out.println(" ");
			 */
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return elementsList;
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
