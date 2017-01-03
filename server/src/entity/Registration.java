package entity;

import java.io.Serializable;

import interfaces.StatementsIF;

/**
 * This entity store the user details.
 * @author nire
 */
public class Registration implements Serializable, StatementsIF {
	
/**
* 	The constant serialVersionUID
*/	
private static final long serialVersionUID = 1L;


/**
 * The username.
 */
private int Username;


/**
 * The password.
 */
private String Password;


/**
 * The first name.
 */
private String FirstName;


/**
 * The last name,
 */
private String LastName;


/**
 * The constructor store the data into the entity.
 * @param Username - The username.
 * @param Password - The password.
 * @param FirstName - The first name.
 * @param LastName - The last name.
 */
public Registration(int Username, String Password, String FirstName, String LastName)
{
	setUsername(Username);
	setPassword(Password);
	setFirstName(FirstName);
	setLastName(LastName);
}
//
/**
 * Getter for username
 * @return the username.
 */
public int getUsername() {
	return Username;
}


/** Setter for username.
 * @param username - Set the username.
 */
public void setUsername(int username) {
	Username = username;
}


/** Getter for password.
 * @return - The password.
 */
public String getPassword() {
	return Password;
}


/** Setter the password.
 * @param password - Set the password.
 */
public void setPassword(String password) {
	Password = password;
}


/** Getter for first name.
 * @return FirstName - The first name.
 */
public String getFirstName() {
	return FirstName;
}


/** Setter for first name.
 * @param firstName - Gets the first name.
 */
public void setFirstName(String firstName) {
	FirstName = firstName;
}


/** Getter for last name.
 * @return lastName - The last name.
 */
public String getLastName() {
	return LastName;
}


/** Setter for last name.
 * @param lastName - Gets the last name.
 */
public void setLastName(String lastName) {
	LastName = lastName;
}

@Override
public String PrepareAddStatement() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String PrepareDeleteStatement() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String prepareUpdateStatement() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String PrepareSelectStatement() {
	// TODO Auto-generated method stub
	return null;
}

}
