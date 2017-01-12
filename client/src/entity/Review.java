package entity;

/**
 *  This entity stores Review's details.
 * @author ork
 *
 */
public class Review {
	
	private String reviewID;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String bookTitle;
	
	private String reviewContent;
	
	private String reviewDate;
	
	public Review(String reviewID,String username,String firstName,String lastName,String bookTitle,String reviewContent,String reviewDate)
	{
		this.reviewID = reviewID;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bookTitle = bookTitle;
		this.reviewContent = reviewContent;
		this.reviewDate = reviewDate;
	}
	
	public String getBookTitle()
	{
		return bookTitle;
	}

}
