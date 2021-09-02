import java.time.LocalDateTime;

/**
 * @author koutilyareddykommula
 *
 */
public class UserAccount {

	private String id;
	private int pin;
	private String fullName;
	private long accountNumber;
	private AccountType type;
	private long balance;
	private int invalidLoginCount;
	private LocalDateTime lockTimeStamp;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the pin
	 */
	public int getPin() {
		return pin;
	}
	/**
	 * @param pin the pin to set
	 */
	public void setPin(int pin) {
		this.pin = pin;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the accountNumber
	 */
	public long getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the type
	 */
	public AccountType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(AccountType type) {
		this.type = type;
	}
	/**
	 * @return the balance
	 */
	public long getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(long balance) {
		this.balance = balance;
	}
	/**
	 * @return the invalidLoginCount
	 */
	public int getInvalidLoginCount() {
		return invalidLoginCount;
	}
	/**
	 * @param invalidLoginCount the invalidLoginCount to set
	 */
	public void setInvalidLoginCount(int invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}
	/**
	 * @return the lockTimeStamp
	 */
	public LocalDateTime getLockTimeStamp() {
		return lockTimeStamp;
	}
	/**
	 * @param lockTimeStamp the lockTimeStamp to set
	 */
	public void setLockTimeStamp(LocalDateTime lockTimeStamp) {
		this.lockTimeStamp = lockTimeStamp;
	}

	
}
