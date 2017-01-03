package entity;

/**
 * This entity stores subject's details.
 * @author itain
 */
public class Subject {
	/**
	 * id of subject
	 */
	private int id;
	
	/**
	 * name of domain
	 */
	private String name;
	
	/**
	 * number of books under this subject
	 */
	private int booksCount;
	
	/**
	 * subject's domain id
	 */
	private int domain;
	
	/**
	 * number of subjects in DB
	 */
	private static int subjectsIdCounter=0;

	
	/**
	 * Subject constructor store the data.
	 * @param id - Gets the id.
	 * @param name - Gets the name.
	 * @param booksCount - Gets the number of books under this subject.
	 * @param domain - Gets the number of books under this subject.
	 * @param subjectsIdCounter - adds 1 to subjectsIdCounter.
	 */
	public Subject(int id, String name, int booksCount, int domain) {
		this.id = id;
		this.name = name;
		this.booksCount = booksCount;
		this.domain = domain;
		
		subjectsIdCounter++;
	}

	/** Getter for id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/** Setter for id
	 * @id - the id of subject
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Getter for name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/** Setter for name
	 * @name - the name of subject
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for booksCount
	 * @return booksCount
	 */
	public int getBooksCount() {
		return booksCount;
	}

	/** Setter for booksCount
	 * booksCount - number of books under this subject
	 */
	public void setBooksCount(int booksCount) {
		this.booksCount = booksCount;
	}

	/** Getter for domain
	 * @return domain
	 */
	public int getDomain() {
		return domain;
	}

	/** Setter for domain
	 * domain - id of subject's domain
	 */
	public void setDomain(int domain) {
		this.domain = domain;
	}
	
	
	/** Getter for subjectsIdCounter
	 * @return subjectsIdCounter
	 */
	public static int getSubjectsIdCounter() {
		return subjectsIdCounter;
	}


	
	
	
	
}
