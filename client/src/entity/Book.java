package entity;

import java.util.ArrayList;

/**
 * This entity stores book's details.
 * @author itain
 */
public class Book {
	
	/**
	 * serial number of book
	 */
	private int sn;
	
	/**
	 * title of book
	 */
	private String title;
	
	/**
	 * language of book
	 */
	private String language;
	
	/**
	 * summary of book
	 */
	private String summary;
	
	/**
	 * table Of Content of book
	 */
	private String tableOfContent;
	
	/**
	 * key words of book
	 */
	private String keywords;
	
	/**
	 * price of book
	 */
	private float price;
	
	/**
	 * book is hidden from catalog or not
	 */
	private boolean hide;
	
	
	/**
	 *authors of book
	 */
	private ArrayList <String> authors;
	
	
	/**
	 *domains of book
	 */
	private ArrayList <String> domains;
	
	/**
	 *subjects of book
	 */
	private ArrayList <String> subjects;
	
	/**
	 * counter for books id
	 */
	private static int booksIdCounter=0;
	
	public static void setbooksIdCounter(int booksIdCounter) {
		Book.booksIdCounter = booksIdCounter;
	}

	/**
	 * Book constructor store the data.
	 * @param booksIdCounter - adds 1 to booksIdCounter.
	 * @param sn - Gets the booksIdCounter.
	 * @param title - Gets the title.
	 * @param language - Gets the title.
	 * @param summary - Gets the title.
	 * @param tableOfContent - Gets the tableOfContent.
	 * @param keywords - Gets the keywords.
	 * @param price - Gets the price.
	 * @param authors - Gets the authors.
	 * @param domains - Gets the domains.
	 * @param subjects - Gets the subjects.
	 */
	public Book(String title, String language, String summary, String tableOfContent, String keywords,
			float price, ArrayList <String> authors, ArrayList <String> domains, ArrayList <String> subjects) 
	{
		booksIdCounter++;
		
		this.sn = booksIdCounter;
		this.title = title;
		this.language = language;
		this.summary = summary;
		this.tableOfContent = tableOfContent;
		this.keywords = keywords;
		this.price = price;
		this.hide = false;
		this.authors=authors;
		this.domains=domains;
		this.subjects=subjects;
	}
	
	
	/**
	 * Book constructor store the data.
	 * @param booksIdCounter - adds 1 to booksIdCounter.
	 * @param sn - Gets the booksIdCounter.
	 * @param title - Gets the title.
	 * @param language - Gets the title.
	 * @param summary - Gets the title.
	 * @param tableOfContent - Gets the tableOfContent.
	 * @param keywords - Gets the keywords.
	 * @param price - Gets the price.
	 * @param authors - Gets the authors.
	 */
	public Book(int sn,String title, String language, String summary, String tableOfContent, String keywords,
			float price, ArrayList <String> authors, ArrayList <String> domains, ArrayList <String> subjects) 
	{
		this.sn = sn;
		this.title = title;
		this.language = language;
		this.summary = summary;
		this.tableOfContent = tableOfContent;
		this.keywords = keywords;
		this.price = price;
		this.hide = false;
		this.authors=authors;
		this.domains=domains;
		this.subjects=subjects;
	}
	

	/**
	 * empty Book constructor
	 */
	public Book() {

	}

	/** Getter for sn
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}
	
	

	/** Setter for sn
	 *@ sn - the sn of book
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

	/** Getter for title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/** Setter for title
	 * @title - the title of book
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** Getter for language
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}
	
	/** Setter for language
	 * @language - the language of book
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/** Getter for summary
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/** Setter for summary
	 * @summary - the summary of book
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/** Getter for TableOfContents
	 * @return summary
	 */
	public String getTableOfContent() {
		return tableOfContent;
	}
	
	/** Setter for TableOfContents
	 * TableOfContents - the Table Of Contents of book
	 */
	public void setTableOfContent(String tableOfContent) {
		this.tableOfContent = tableOfContent;
	}
	
	/** Getter for keywords
	 * @return keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	
	/** Setter for keywords
	 * keywords - the keywords of book
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	/** Getter for price
	 * @return price
	 */
	public float getPrice() {
		return price;
	}
	
	/** Setter for price
	 * price - the price of book
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/** Getter for hide
	 * @return hide
	 */
	public boolean isHide() {
		return hide;
	}
	
	/** Setter for hide
	 * hide - if book is hidden from catalog or not
	 */
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	
	/** Getter for booksIdCounter
	 * @return booksIdCounter
	 */
	public static int getbooksIdCounter() {
		return booksIdCounter;
	}

	/** Getter for authors
	 * @return authors
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}

	/** Setter for authors
	 * authors - list of authors
	 */
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	/** Getter for domains
	 * @return domains
	 */
	public ArrayList<String> getDomains() {
		return domains;
	}

	/** Setter for domains
	 * domains - list of domains
	 */
	public void setDomains(ArrayList<String> domains) {
		this.domains = domains;
	}

	/** Getter for subjects
	 * @return subjects
	 */
	public ArrayList<String> getSubjects() {
		return subjects;
	}

	/** Setter for subjects
	 * domains - list of subjects
	 */
	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}
	
	
	
	
}
