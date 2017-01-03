package entity;
/**
 * User class store the Data of users in system
 * @author Sagivm
 *
 */
public class User extends Person {
	private long  Userid;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private boolean connected;
	/**
	 * Initialize User without parameters
	 */
	public User()
	{
		super();
		
	}
	/**
	 * Initialize User with parameters. firstname,lastname,id are defined in parent
	 * class Person 
	 * @param firstname
	 * @param lastname
	 * @param id
	 * @param accountType
	 * @param accountStatus
	 */
	public User(String firstname, String lastname, long id, String accountType, String accountStatus) {
		super(firstname,lastname,id);
		setAccountType(accountType);
		setAccountStatus(accountStatus);
	}
	
	/**
	 * @return Users's account type
	 */
	public AccountType getAccountType() {
		return accountType;
	}
	
	/**
	 * Given a String object setAccountType defines user's account
	 * type using Enum class
	 * @param accountTypes
	 */
	public void setAccountType(String accountTypes) {
		switch(accountTypes)
		{
		case "Intrested":
			this.accountType=AccountType.Intrested;
			break;
		case "Monthly":
			this.accountType=AccountType.Monthly;
			break;
		case "Yearly":
			this.accountType=AccountType.Yearly;
			break;
		case "Blocked":
			this.accountType=AccountType.Blocked;
			break;
		case "PerBook":
			this.accountType=AccountType.PerBook;
			break;
		}
	}
	/**
	 * @return Users's account status
	 */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	/**
	 * Given a String object setAccountType defines user's account
	 * type using Enum class
	 * @param accountStatuss
	 */
	public void setAccountStatus(String accountStatuss) {
		switch(accountStatuss)
		{
		case "Standard":
			this.accountStatus=AccountStatus.Standard;
			break;
		case "PendingPerBook":
			this.accountStatus=AccountStatus.PendingPerBook;
			break;
		case "PendingMonthly":
			this.accountStatus=AccountStatus.PendingMonthly;
			break;
		case "PendingYearly":
			this.accountStatus=AccountStatus.PendingYearly;
			break;
		}
	}
	/**
	 * @return if user is connected
	 */
	public boolean getConnected()
	{
		return this.connected;
	}
	
	/**
	 * Define user's connection
	 * @param connected
	 */
	public void setConnected(boolean connected)
	{
		this.connected=connected;
	}
	

}
