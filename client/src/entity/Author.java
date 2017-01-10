package entity;

/**
 * Author class store the Data of Authors in system
 * @author itain
 *
 */
public class Author extends Person{
	/**
	 * number of author's books
	 */
	private int booksCount;
	
	/**
	 * counter for author id
	 */
	private static int authorsIdCounter=0;

	/**
	 * Author constructor store the data.
	 * @param firstname - Gets the firstname.
	 * @param lastname - Gets the lastname.
	 * @param id - Gets id.
	 * @param authorsIdCounter - adds 1 to authorsIdCounter.
	 */
	public Author(String id, String firstname, String lastname) {
		super(id, firstname, lastname);
		authorsIdCounter++;
	}
	
	
	/** Setter for booksCount
	 * booksCount - number of author's books
	 */
	public int getBooksCount() {
		return booksCount;
	}
}



