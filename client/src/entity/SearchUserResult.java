package entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * This entity store the search user result details.
 * @author itain
 */
public class SearchUserResult {

	/**
	 * username.
	 */
    private final SimpleStringProperty username;
    
	/**
	 * first name
	 */
    private final SimpleStringProperty firstName;
    
	/**
	 * last name
	 */
    private final SimpleStringProperty lastName;
    
	/**
	 * account Type
	 */
    private final SimpleStringProperty accountType;
    
	/**
	 * account Status
	 */
    private final SimpleStringProperty accountStatus;
    
	/**
	 * block Status
	 */
    private final SimpleStringProperty isBlocked;
    
	/**
	 * password
	 */
    private final SimpleStringProperty password;
    
	/**
	 * left credits in account
	 */
    private final SimpleStringProperty credits;
    
	/**
	 * end of subscription date
	 */
    private final SimpleStringProperty endSubscription;
    
    
    
	/**
	 * Initialize User with parameters.
	 * @author itain
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param accountType
	 * @param accountStatus
	 * @param isBlocked - whether user is blocked or not
	 * @param password
	 * @param credits
	 * @param endSubscription
	 */
    public SearchUserResult(String username, String firstName, String lastName, String accountType, String accountStatus, String isBlocked, String password, String credits, String endSubscription) {
        this.username = new SimpleStringProperty(username);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.accountType = new SimpleStringProperty(accountType);
        this.accountStatus = new SimpleStringProperty(accountStatus);
        this.isBlocked = new SimpleStringProperty(isBlocked);
        this.password = new SimpleStringProperty(password);
        this.credits = new SimpleStringProperty(credits);
        this.endSubscription = new SimpleStringProperty(endSubscription);
    }

	/** Getter for username
	 * @author itain
	 * @return username of user
	 */
    public String getUsername() {
        return username.get();
    }
    
	/** Getter for first name
	 * @author itain
	 * @return first name of user
	 */
    public String getFirstName() {
        return firstName.get();
    }
    
	/** Getter for last name
	 * @author itain
	 * @return last name of user
	 */
    public String getLastName() {
        return lastName.get();
    }
    
	/** Getter for Account Type
	 * @author itain
	 * @return Account Type of user
	 */
    public String getAccountType() {
        return accountType.get();
    }
    
	/** Getter for Account Status
	 * @author itain
	 * @return Account Status of user
	 */
    public String getAccountStatus() {
        return accountStatus.get();
    }
    
	/** Getter for block status
	 * @author itain
	 * @return block status of user
	 */
    public String getIsBlocked() {
        return isBlocked.get();
    }
    
    
	/** Getter for password
	 * @author itain
	 * @return password of user
	 */
    public String getPassword() {
        return password.get();
    }
        
	/** Getter for credits
	 * @author itain
	 * @return left credits of user
	 */
    public String getCredits() {
        return credits.get();
    }
    
	/** Getter for End Subscription
	 * @author itain
	 * @return End date of Subscription
	 */
    public String getEndSubscription() {
        return endSubscription.get();
    }
    
    
    
	/** Setter for First Name
	 * @author itain
	 * @param fName
	 */
    public void setFirstName(String fName) {
    	firstName.set(fName);
    }

	/** Setter for Last Name
	 * @author itain
	 * @param lName
	 */
    public void setLastName(String lName) {
        lastName.set(lName);
    }
    
	/** Setter for account Type
	 * @author itain
	 * @param aType
	 */
    public void setAccountType(String aType) {
    	accountType.set(aType);
    }
    
	/** Setter for account Status
	 * @author itain
	 * @param aStatus
	 */
    public void setAccountStatus(String aStatus) {
    	accountStatus.set(aStatus);
    }
    
    
	/** Setter for block status
	 * @author itain
	 * @param blocked
	 */
    public void setIsBlocked(String blocked) {
    	isBlocked.set(blocked);
    }
    
	/** Setter for password
	 * @author itain
	 * @param pswrd
	 */
    public void setPassword(String pswrd) {
    	password.set(pswrd);
    }
    
	/** Setter for credits
	 * @author itain
	 * @param crdts
	 */
    public void setCredits(String crdts) {
    	credits.set(crdts);
    }
    
	/** Setter for End Subscription data
	 * @author itain
	 * @param endSub
	 */
    public void setEndSubscription(String endSub) {
    	endSubscription.set(endSub);
    }
    

}