package entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * This entity store the search book result details.
 * @author itain
 */
public class SearchBookResult {

    private SimpleStringProperty bookSn;
    private SimpleStringProperty bookTitle;
    private SimpleStringProperty bookLanguage;
    private SimpleStringProperty bookSummary;
    private SimpleStringProperty bookToc;
    private SimpleStringProperty bookKeywords;
    private SimpleStringProperty bookAuthors;
    private SimpleStringProperty bookSubjects;
    private SimpleStringProperty bookDomains;
    private SimpleStringProperty bookPrice;

    public SearchBookResult(String bookSn, String bookTitle, String bookLanguage, String bookSummary, String bookToc, String bookKeywords, String bookAuthors, String bookSubjects, String bookDomains, String bookPrice) {
        this.bookSn = new SimpleStringProperty(bookSn);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.bookLanguage = new SimpleStringProperty(bookLanguage);
        this.bookSummary = new SimpleStringProperty(bookSummary);
        this.bookToc = new SimpleStringProperty(bookToc);
        this.bookKeywords = new SimpleStringProperty(bookKeywords);
        this.bookAuthors = new SimpleStringProperty(bookAuthors);
        this.bookSubjects = new SimpleStringProperty(bookSubjects);
        this.bookDomains = new SimpleStringProperty(bookDomains);
        this.bookPrice = new SimpleStringProperty(bookPrice);
    }

	public String getBookSn() {
		return bookSn.get();
	}

	public String getBookTitle() {
		return bookTitle.get();
	}

	public String getBookLanguage() {
		return bookLanguage.get();
	}

	public String getBookSummary() {
		return bookSummary.get();
	}

	public String getBookToc() {
		return bookToc.get();
	}

	public String getBookKeywords() {
		return bookKeywords.get();
	}

	public String getBookAuthors() {
		return bookAuthors.get();
	}

	public String getBookSubjects() {
		return bookSubjects.get();
	}

	public String getBookDomains() {
		return bookDomains.get();
	}
	
	public String getBookPrice() {
		return bookPrice.get();
	}

	public void setBookSn(String bookSn) {
		this.bookSn.set(bookSn);
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle.set(bookTitle);
	}

	public void setBookLanguague(String bookLanguage) {
		this.bookLanguage.set(bookLanguage);
	}

	public void setBookSummary(String bookSummary) {
		this.bookSummary.set(bookSummary);
	}

	public void setBookToc(String bookToc) {
		this.bookToc.set(bookToc);
	}

	public void setBookKeywords(String bookKeywords) {
		this.bookKeywords.set(bookKeywords);
	}

	public void setAuthors(String bookAuthors) {
		this.bookAuthors.set(bookAuthors);
	}

	public void setSubjects(String bookSubjects) {
		this.bookSubjects.set(bookSubjects);
	}

	public void setDomains(String bookDomains) {
		this.bookDomains.set(bookDomains);
	}

	public void setPrice(String bookPrice) {
		this.bookPrice.set(bookPrice);
	}


}
