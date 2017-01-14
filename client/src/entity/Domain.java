package entity;

/**
 * This entity stores domain's details.
 * @author itain
 */
public class Domain {
	
	/**
	 * id of domain
	 */
	private int id;
	
	/**
	 * name of domain
	 */
	private String name;
	
	/**
	 * number of subjects under this domain
	 */
	private int subjectsCount;
	
	
	/**
	 * counter for domain id
	 */
	private static int domainsIdCounter=0;
	
	
	/**
	 * Domain constructor store the data.
	 * @param id - Gets the id.
	 * @param name - Gets the name.
	 * @param subjectsCount - adds 1 to subjectsCount.
	 */
	public Domain(String name) {
		domainsIdCounter++;
		this.id = domainsIdCounter;
		this.name = name;
		this.subjectsCount = 0;
		
	}

	/** Getter for id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/** Setter for id
	 * @id - the id of domain
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
	 * @name - the name of domain
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Getter for subjectsCount
	 * @return subjectsCount
	 */
	public int getSubjectsCount() {
		return subjectsCount;
	}

	/** Setter for subjectsCount
	 * subjectsCount - the number of subjects under this domain.
	 */
	public void setSubjectsCount(int subjectsCount) {
		this.subjectsCount = subjectsCount;
	}	
	
	/** Getter for domainsIdCounter
	 * @return domainsIdCounter
	 */
	public static int getdomainsIdCounter() {
		return domainsIdCounter;
	}
}
