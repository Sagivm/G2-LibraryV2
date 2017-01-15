package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import control.DatabaseController;

/**
 * @author sagivm
 *Generates each passing day and each time the Db is starting to run new tuple for each book in 
 *book by date table representing book's stats in this day
 */
public class CurrentDate   {
	/**
	 * Date format for create new rows in sql table Book_by_date
	 */
	private String date;
	/**
	 * Constructor
	 * Calls the relevant functions for generating the new tuples
	 */
	public CurrentDate()
	{
		dateInitialize();
		newDay();
	}
	/**
	 * Checks if the date was already initialize in this session of the Db
	 * and if not checks the current date and will follow to generate tuples
	 */
	private void dateInitialize() {
		// fix multiply occurrence per date
		if (date == null) {
			Date Currentdate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
			this.date = (String) dateFormat.format(Currentdate);
			createNewDay();
		}
	}
//
	/**
	 *Checks if the day has changed and if was 
	 *create new rows in sql table book by date for each book 
	 */
	private void newDay() {
		String date;
		Date Currentdate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		date = (String) dateFormat.format(Currentdate);
		// compare to return 0 if equal
		if ((this.date.compareTo(date)) != 0 && date.compareTo("00") == 0) {
			createNewDay();
			this.date=date;
		}

	}

	/**
	 * Insert new rows in sql table book by date for each book 
	 */
	private void createNewDay()
	{
		ResultSet rs = null;
		try {
		rs=DatabaseController.searchInDatabase("SELECT * FROM books;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next())
			{
				insertBookDateRow(rs,0,0);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**\
	 * Insert a single row to book by date with the current date
	 * @param rs- ResultSet of book by date  table with sn in the first index of rs
	 * @param search - number of searches for the new book
	 * @param purchase-number of purchases for the new book
	 * @throws SQLException
	 */
	public static void insertBookDateRow(ResultSet rs,int search,int purchase) throws SQLException
	{
		Date Currentdate=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=(String)dateFormat.format(Currentdate);
		DatabaseController.addToDatabase("INSERT INTO book_by_date VALUES ("+rs.getInt(1)+",'"+date+"',"+search+","+purchase+");");
	}

}
