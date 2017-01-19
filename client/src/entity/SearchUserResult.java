package entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * This entity store the search user result details.
 * @author itain
 */
public class SearchUserResult {

    private final SimpleStringProperty username;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty accountType;
    private final SimpleStringProperty accountStatus;
    private final SimpleStringProperty isBlocked;
    private final SimpleStringProperty password;
    private final SimpleStringProperty credits;
    private final SimpleStringProperty endSubscription;

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

    public String getUsername() {
        return username.get();
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public String getAccountType() {
        return accountType.get();
    }
    
    public String getAccountStatus() {
        return accountStatus.get();
    }
    
    public String getIsBlocked() {
        return isBlocked.get();
    }
    
    public String getPassword() {
        return password.get();
    }
        
    public String getCredits() {
        return credits.get();
    }
    
    public String getEndSubscription() {
        return endSubscription.get();
    }
    
    public void setFirstName(String fName) {
    	firstName.set(fName);
    }

    public void setLastName(String lName) {
        lastName.set(lName);
    }
    
    public void setAccountType(String aType) {
    	accountType.set(aType);
    }
    
    public void setAccountStatus(String aStatus) {
    	accountStatus.set(aStatus);
    }
    
    public void setIsBlocked(String blocked) {
    	isBlocked.set(blocked);
    }
    
    public void setPassword(String pswrd) {
    	password.set(pswrd);
    }
    
    public void setCredits(String crdts) {
    	credits.set(crdts);
    }
    
    public void setEndSubscription(String endSub) {
    	endSubscription.set(endSub);
    }
    

}