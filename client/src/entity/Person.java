package entity;

/**
 * Person class represents a human in the system 
 * @author Sagivm
 *
 */
public class Person {
	private String firstname;
	private String lastname;
	private long id;
	/**
	 * Initialize person without parameters
	 */
	public Person()
	{
		
	}
	/**
	 * Initialize person with parameters
	 * @param firstname
	 * @param lastname
	 * @param id
	 */
	public Person(String firstname, String lastname, long id) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = id;
	}
	/**
	 * @return Persons's first Name
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Sets person's first name
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return Persons's last name
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * Sets person's last name
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return Persons's Id
	 */
	public long getId() {
		return id;
	}
	/**
	 * Sets person's id
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	

}
