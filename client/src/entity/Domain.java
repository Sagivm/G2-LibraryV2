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
	 * counter for domain id
	 */
	private static int domainsIdCounter=0;
	
	
	/**
	 * Domain constructor store the data.
	 * @param domainsIdCounter - add 1 to domainsIdCounter.
	 * @param id - Gets domainsIdCounter.
	 * @param name - Gets the name.
	 */
	public Domain(String name) {
		domainsIdCounter++;
		this.id = domainsIdCounter;
		this.name = name;
		this.subjects=new ArrayList<Subject>();
		
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
	

	/** Getter for domainsIdCounter
	 * @return domainsIdCounter
	 */
	public static int getdomainsIdCounter() {
		return domainsIdCounter;
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
