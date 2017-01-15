package entity;

import java.util.ArrayList;

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
	 * subjects under this domain
	 */
	private ArrayList <Subject> subjects;
	
	
	/**
	 * Domain constructor store the data.
	 * @param domainsIdCounter - add 1 to domainsIdCounter.
	 * @param id - Gets domainsIdCounter.
	 * @param name - Gets the name.
	 * @param subjects - initiate empty array
	 */
	public Domain(int id, String name) {
		this.id = id;
		this.name = name;
		this.subjects=new ArrayList<Subject>();
		
	}
	
	/**
	 * Domain empty constructor
	 * @param subjects - initiate empty array
	 */
	public Domain() {

		this.subjects=new ArrayList<Subject>();
		
	}

	/** Getter for id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/** Setter for id
	 * @id - id of domain
	 */
	public void setId(int id) {
		this.id=id;
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
	

	
	/** Getter for subjects
	 * @return subjects
	 */
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	/** Setter for subjects
	 * subjects - subjects under this domain
	 */
	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}
	
	/** Getter for subjects count
	 * @return subjects.size()
	 */
	public int getSubjectsCount() {
		return subjects.size();
	}
	
	
	
}
