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
	 * Get pending users from SQL.
	 */
	GET_PENDING_USERS,
	
	/**
	 * Accept pending users from SQL.
	 */
	ACCEPT_PENDING_USERS,
	
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


}
