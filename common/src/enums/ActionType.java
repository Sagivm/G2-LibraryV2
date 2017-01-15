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
	 * edit user information in SQL.
	 */
	EDIT_USER,
	
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



}
