package entity;

/**
 *  This entity stores Review's details.
 * @author ork
 *
 */
public class Review {
	
	/**
	 * The id of the review.
	 */
	private String reviewID;
	
	/**
	 * The username of the user that wrote the review.
	 */
	private String username;
	
	/**
	 * The first name of the user that wrote the review.
	 */
	private String firstName;
	
	/**
	 * The last name of the user that wrote the review.
	 */
	private String lastName;
	
	/**
	 * The book title.
	 */
	private String bookTitle;
	
	/**
	 * The content of the review.
	 */
	private String reviewContent;
	
	/**
	 * The date of the review.
	 */
	private String reviewDate;
	
	/**
	 * The constructor of the review entity.
	 * 
	 * @param reviewID The ID of the review.
	 * @param username The username.
	 * @param firstName The first name.
	 * @param lastName The last name.
	 * @param bookTitle The book title.
	 * @param reviewContent The review content.
	 * @param reviewDate The review date.
	 */
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
	
	/** 
	 * Getter for reviewID.
	 * @return The ID of the review.
	 */
	public String getReviewID()
	{
		return reviewID;
	}
	
	/**
	 * Getter for username.
	 * @return The username.
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Getter for firstName.
	 * @return The first name.
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/**
	 * Getter for lastName.
	 * @return The last name.
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * Getter for bookTitle.
	 * @return The book title.
	 */
	public String getBookTitle()
	{
		return bookTitle;
	}
	
	/**
	 * Getter for reviewContent.
	 * @return The review content.
	 */
	public String getReviewContent()
	{
		return reviewContent;
	}
	
	/**
	 * Getter for reviewDate.
	 * @return The review date.
	 */
	public String getReviewDate()
	{
		return reviewDate;
	}

}
