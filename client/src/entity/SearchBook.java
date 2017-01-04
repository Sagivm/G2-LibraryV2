package entity;

import java.awt.TextArea;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This entity store the search book details.
 * @author itain
 */
public class SearchBook {
	
	private String title;
	private ArrayList<String> authors;
	private String language;
	private String summary;
	private String toc;
	private ArrayList<String> domains;
	private String keyWords;
	
	
	/**
	 * The constructor store the data into the entity.
	 * @param title - title.
	 * @param authors - authors.
	 * @param language - language.
	 * @param summary - summary.
	 * @param toc - table of contents.
	 * @param domains - domains.
	 * @param keyWords - key words.
	 */
	public SearchBook(String title, ArrayList<String> authors, String language, String summary, String toc,
			ArrayList<String> domains, String keyWords) {
		super();
		this.title = title;
		this.authors = authors;
		this.language = language;
		this.summary = summary;
		this.toc = toc;
		this.domains = domains;
		this.keyWords = keyWords;
	}
	

	
	/**
	 * Getter for title
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/** Setter for title.
	 * @param title - Set the title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for authors
	 * @return the authors.
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}


	/** Setter for authors.
	 * @param authors - Set the authors.
	 */
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	



	/**
	 * Getter for language
	 * @return the language.
	 */
	public String getLanguage() {
		return language;
	}


	/** Setter for language.
	 * @param language - Set the language.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Getter for summary
	 * @return the summary.
	 */
	public String getSummary() {
		return summary;
	}

	/** Setter for summary.
	 * @param summary - Set the summary.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Getter for toc
	 * @return the table of contents.
	 */
	public String getToc() {
		return toc;
	}

	
	/** Setter for toc.
	 * @param toc - Set the table of contents.
	 */
	public void setToc(String toc) {
		this.toc = toc;
	}



	/**
	 * Getter for domains
	 * @return the domains.
	 */

	public ArrayList<String> getDomains() {
		return domains;
	}




	/** Setter for domain.
	 * @param domain - Set the domain.
	 */
	public void setDomains(ArrayList<String> domains) {
		this.domains = domains;
	}




	/**
	 * Getter for keyWords
	 * @return the key words.
	 */
	public String getKeyWords() {
		return keyWords;
	}




	/** Setter for keyWords.
	 * @param keyWords - Set the key words.
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}


	/**
	 * Getter for number of authors
	 * @return the number of authors.
	 */
	public int getAuthorsNumber() {
		return authors.size();
	}
	
	
	/**
	 * Getter for domainsNumber
	 * @return the number of domains.
	 */
	public int getDomainsNumber() {
		return domains.size();
	}	
	
}