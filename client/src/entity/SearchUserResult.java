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

    public SearchUserResult(String username, String firstName, String lastName, String accountType, String accountStatus) {
        this.username = new SimpleStringProperty(username);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.accountType = new SimpleStringProperty(accountType);
        this.accountStatus = new SimpleStringProperty(accountStatus);
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
    
    
    

}