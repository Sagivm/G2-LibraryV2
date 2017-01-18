package enums;

/**
 * This include enums for specific known words.
 * @author nire
 *
 */
public enum ActionType {

	/**
	 * Login code.
	 */
	LOGIN,
	
	/**
	 * Registration code.
	 */
	REGISTER,
	
	/**
	 * Terminate the program code.
	 */
	TERMINATE, 
	
	/**
	 * Continue without end.
	 */
	CONTINUE, 
	
	/**
	 * Authorize user.
	 */
	AUTH,
	
	/**
	 * Add to SQL.
	 */
	ADD,
	
	/**
	 * Remove from SQL.
	 */
	REMOVE,
	/**
	 * Updates SQL.
	 */
	UPDATE,
	
	/**
	 * Search in SQL.
	 */
	SEARCH,
	
	/**
	 * Search book (AND style) in SQL.
	 */
	SEARCH_BOOK_AND,
	
	/**
	 * Search book (OR style) in SQL.
	 */
	SEARCH_BOOK_OR,
	
	/**
	 * Search user in SQL.
	 */
	SEARCH_USER,
	
	/**
	 * edit user information in SQL (for librarian).
	 */
	EDIT_USER_LIBRARIAN,
	
	/**
	 * edit user information in SQL (for manager).
	 */
	EDIT_USER_MANAGER,
	
	/**
	 * Get pending users from SQL.
	 */
	GET_PENDING_USERS,
	
	/**
	 * Accept pending users from SQL.
	 */
	ACCEPT_PENDING_USERS,
	
	/**
	 * Decline and delete pending users from SQL.
	 */
	DECLINE_PENDING_USERS,
	
	/**
	 * finalize connected login entity in the server.
	 */
	LOGOUT,
	/**
	 * Request to change account type
	 */
	ACCOUNTTYPEREQ,
	
	/**
	 * Get pending reviews from SQL.
	 */
	PENDING_REVIEWS,
	/**
	 * Get authors from server
	 */
	GET_AUTHORS,
	
	/**
	 * Get domains from server
	 */
	GET_DOMAINS,
	
	/**
	 * Get subjects from server
	 */
	GET_SUBJECTS,
	
	/**
	 * Make a User Report from the server
	 */
	USEREPORT,
	/**
	 * Make a popularity Report from the server
	 */

	POPULARITYREPORT,
	/**
	 * Get Domains from DB given a specific book
	 */
	GETDOMAINSSPECIFIC,

	
	/**
	 * Update the review status to "Approve" or "Decline".
	 */
	UPDATE_REVIEW_STATUS,

	/**
	 * Get data from DB for book report
	 */
	BOOKREPORT,
	
	/**
	 * Get data from DB for book reviews
	 */
	BOOK_REVIEWS,
	
	/**
	 * Add new review to DB.
	 */
	WRITE_REVIEW,
	
	/**
	 * Get data from DB for list of books
	 */
	GET_BOOK_LIST,
	
	/**
	 * Add Book to DB
	 */
	ADD_BOOK,

	/**
	 * Delete Book from DB
	 */
	DELETE_BOOK,
	
	/**
	 * update Book from DB by set hide
	 */
	HIDE_BOOK, 
	
	
	/**
	 * Get messages constant from
	 */
	GET_MESSAGES,
	
	
	/**
	 * Get messages constant from
	 */
	GET_PENDING_ACCOUNTS,
	
	/**
	 * Get messages constant from
	 */

	UPDATE_PENDING_ACCOUNT, 
	
	
	/**
	 * This method sends the message to all clients.
	 */
	BROADCAST, 
	
	/**
	 * This method sends the message to specific client.
	 */
	UNICAST,

	/**
	 * Return if the user eligible to write a review.
	 */
	CHECK_WRITE_REVIEW,
	
	/**
	 * Get the image of a specific book.
	 */
	GET_BOOK_IMG,
	

	/**
	 * Gets the total amount of money that was paid for each book
	 */
	GET_TOTAL_PRICE,
	
	/**
	 * Gets if the user bought this book or if the user allowed to buy it now.
	 */
	GET_BUY_STATUS,

	/**
	 * Add bought book to DB.
	 */
	BUY_BOOK,


}
