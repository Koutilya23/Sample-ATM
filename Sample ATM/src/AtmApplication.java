import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author koutilyareddykommula
 *
 */
public class AtmApplication {

	static Scanner input = new Scanner(System.in);
	static Map<String, UserAccount> userMap = new HashMap<>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		loadUsers();
		atmDemo();
	}

	private static void atmDemo() {
		System.out.println("Welcome to ABC bank ATM");
		System.out.println("Please Enter Your ID : ");
		String id = input.next();
		boolean isUserFound = checkId(id, 0);
		if (!isUserFound) {
			atmDemo();
		} else {
			long diff = 61;
			if(null!=userMap.get(id).getLockTimeStamp()) {
				LocalDateTime now = LocalDateTime.now();
				diff = ChronoUnit.SECONDS.between(userMap.get(id).getLockTimeStamp(), now);
			}
			if(userMap.get(id).getInvalidLoginCount()>=5 && diff<60) {
				System.out.println("You have been locked out, Please try again after a minute");
				atmDemo();
			}else {
				System.out.println("Please Enter Your 4 Digit PIN : ");
				String pinStr = input.next();
				int pin = userMap.get(id).getPin();
				if (isValidPin(pinStr, pin, userMap.get(id).getInvalidLoginCount(), userMap.get(id))) {
					System.out.println("Hi " + userMap.get(id).getFullName() + ", Welcome to ABC Bank");
					accountMenu(userMap.get(id), 0);
				} else {
					System.out.println("You have been locked out, Please try again after a minute");
					atmDemo();
				}
			}
		}
	}

	private static boolean checkId(String id, int count) {

		if (null == userMap.get(id)) {
			System.out.println("User Not Found ");
			return false;
		} else {
			System.out.println("User Found!!");
			return true;
		}
	}

	private static boolean isValidPin(String userEnteredPinStr, int pin, int count, UserAccount user) {

		try {
			int userEnteredPin = Integer.parseInt(userEnteredPinStr);
			
			if (userEnteredPinStr.length() != 4 || userEnteredPin != pin) {
				count++;
				user.setInvalidLoginCount(count);
				System.out.println(count + " Invalid Attempt/s. ");
				if (count >= 5) {
					LocalDateTime now = LocalDateTime.now();
					user.setLockTimeStamp(now);
					System.out.println("Max Invalid Pin Attempts reached");
					return false;
				} else {
					System.out.println("Invalid PIN, Please enter again : ");
					return isValidPin(input.next(), pin, count, user);
				}
			} else {
				user.setInvalidLoginCount(0);
				return true;
			}
			
		} catch (NumberFormatException e) {
			count++;
			user.setInvalidLoginCount(count);
			System.out.println("Invalid Format - Enter Digits only");
			return isValidPin(input.next(), pin, count, user);
		}
	}

	private static void accountMenu(UserAccount user, int count) {
		
		System.out.println("Please select an option below");
		System.out.println("Enter 1 to View Account Balance");
		System.out.println("Enter 2 to Withdraw Money");
		System.out.println("Enter 3 to Deposit Balance");
		System.out.println("Enter 4 to SignOut");
		System.out.println("Enter 5 to Exit");

		try {
			int choice = Integer.parseInt(input.next());
			switch (choice) {
			case 1:
				System.out.println();
				System.out.println("Your balance for " +user.getType().toString()+" account (" + String.valueOf(user.getAccountNumber()) + ") is : "
						+ user.getBalance());
				System.out.println("Do you want to continue : Y/N");
				userAction(user, input.next(), 0);
				break;

			case 2:
				System.out.println();
				System.out.println("Enter Amount you would like to withdraw : ");
				if (userWithdraw(user, input.next(), 0)) {
					System.out.println("Withdraw success  you new balance is : " + user.getBalance());
					System.out.println("Do you want to continue : Y/N");
					userAction(user, input.next(), 0);
				}
				break;
			case 3:
				System.out.println();
				System.out.println("Enter Amount you would like to deposit : ");
				if (userDeposit(user, input.next(), count)) {
					System.out.println("Deposit success  you new balance is : " + user.getBalance());
					System.out.println("Do you want to continue : Y/N");
					userAction(user, input.next(), 0);
				}
				break;
			case 4:
				System.out.println();
				System.out.println("You've been Signed Out!!");
				System.out.println();
				atmDemo();
				break;
			case 5:
				System.out.println();
				System.out.println("Thank you!");
				System.out.println();
				break;

			default:
				System.out.println();
				System.out.println("Invalid Choice");
				System.out.println();
				count++;
				if (count >= 5) {
					System.out.println("Max Invalid Attempts reached, Try again later");
					System.out.println();
					atmDemo();
				} else {
					System.out.println();
					System.out.println("Invalid Format - Enter a valid choice");
					accountMenu(user, count);
				}
				break;
			}

		} catch (NumberFormatException e) {
			count++;
			if (count >= 5) {
				System.out.println("Max Invalid Attempts reached, Try again later");
				System.out.println();
				atmDemo();
			} else {
				System.out.println();
				System.out.println("Invalid Format - Enter a valid choice");
				accountMenu(user, count);
			}
		}

	}

	private static boolean userDeposit(UserAccount user, String amount, int count) {
		try {
			user.setBalance(user.getBalance() + Long.valueOf(amount));
		} catch (NumberFormatException e) {
			count++;
			if (count >= 5) {
				System.out.println("Max Invalid Attempts reached, Try again later");
				System.out.println();
				atmDemo();
			}
			System.out.println();
			System.out.println("Invalid Format - Try again");
			System.out.println();
			System.out.println("Enter Amount you would like to Deposit : ");
			userDeposit(user, input.next(), count);
			accountMenu(user, count);
		}
		return true;
	}

	private static boolean userWithdraw(UserAccount user, String amount, int count) {

		if (count >= 5) {
			System.out.println("Max Invalid Attempts reached");
			return false;
		}
		try {
			long amountRequested = Long.valueOf(amount);
			if (amountRequested > user.getBalance()) {
				count++;
				System.out.println("Insufficient Balance, Please enter valid amount : ");
				userWithdraw(user, input.next(), count);
			} else {
				user.setBalance(user.getBalance() - Long.valueOf(amount));
			}

		} catch (NumberFormatException e) {
			count++;
			if (count >= 5) {
				System.out.println("Max Invalid Attempts reached, Try again later");
				System.out.println();
				atmDemo();
			}
			System.out.println();
			System.out.println("Invalid Format - Try again");
			System.out.println();
			System.out.println("Enter Amount you would like to withdraw : ");
			userWithdraw(user, input.next(), count);
		}
		return true;
	}

	private static void userAction(UserAccount user, String response, int count) {

		if ("Y".equalsIgnoreCase(response)) {
			accountMenu(user, 0);
		} else if ("N".equalsIgnoreCase(response)) {
			System.out.println("Thank you");
			atmDemo();
			return;
		} else {
			if (count >= 5) {
				System.out.println("Max Invalid Attempts reached");
				atmDemo();
			} else {
				count++;
				System.out.println("Please try again");
				System.out.println("Do you want to continue : Y/N");
				userAction(user, input.next(), count);
			}

		}
	}

	/**
	 * @param userMap Map
	 */
	private static void loadUsers() {
		UserAccount user1 = new UserAccount();
		UserAccount user2 = new UserAccount();
		UserAccount user3 = new UserAccount();
		UserAccount user4 = new UserAccount();

		user1.setId("user1");
		user1.setPin(1111);
		user1.setBalance(100000);
		user1.setAccountNumber(9090808012L);
		user1.setFullName("John Doe");
		user1.setType(AccountType.CHECKING);
		user1.setInvalidLoginCount(0);

		user2.setId("user2");
		user2.setPin(2222);
		user2.setBalance(200000);
		user2.setAccountNumber(9876543210L);
		user2.setFullName("John Appleseed");
		user2.setType(AccountType.CHECKING);
		user2.setInvalidLoginCount(0);

		user3.setId("user3");
		user3.setPin(3333);
		user3.setBalance(300000);
		user3.setAccountNumber(1234567891L);
		user3.setFullName("Selma Hines");
		user3.setType(AccountType.SAVINGS);
		user3.setInvalidLoginCount(0);

		user4.setId("user4");
		user4.setPin(4444);
		user4.setBalance(400000);
		user4.setAccountNumber(1234567890L);
		user4.setFullName("Dayna Smith");
		user4.setType(AccountType.CHECKING);
		user4.setInvalidLoginCount(0);

		userMap.put("user1", user1);
		userMap.put("user2", user2);
		userMap.put("user3", user3);
		userMap.put("user4", user4);
	}

}
