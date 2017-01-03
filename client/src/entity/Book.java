package entity;

/**
 * This an entity stores book's details.
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
	private String tableOfContents;
	
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
	 * Book constructor store the data.
	 * @param sn - Gets the sn.
	 * @param title - Gets the title.
	 * @param language - Gets the title.
	 * @param summary - Gets the title.
	 * @param TableOfContents - Gets the title.
	 * @param keywords - Gets the keywords.
	 * @param price - Gets the price.
	 */
	public Book(int sn, String title, String language, String summary, String tableOfContents, String keywords,
			float price) {
		super();
		this.sn = sn;
		this.title = title;
		this.language = language;
		this.summary = summary;
		this.tableOfContents = tableOfContents;
		this.keywords = keywords;
		this.price = price;
		this.hide = false;
	}
	
	/** Getter for sn
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}
	
	/** Setter for sn
	 * @sn - the serial number of book
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
	public String getTableOfContents() {
		return tableOfContents;
	}
	
	/** Setter for TableOfContents
	 * TableOfContents - the Table Of Contents of book
	 */
	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
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
	
	
}
