package entity;

/**
 * This an entity stores domain's details.
 * @author itain
 */
public class Domain {
	private int id;
	private String name;
	private int subjectsCount;
	
	/**
	 * Domain constructor store the data.
	 * @param id - Gets the id.
	 * @param name - Gets the name.
	 * @param subjectsCount - Gets the number of subjects under this domain.
	 */
	public Domain(int id, String name, int subjectsCount) {
		this.id = id;
		this.name = name;
		this.subjectsCount = subjectsCount;
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
}
