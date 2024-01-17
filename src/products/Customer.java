package products;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;

import misc.ArrayMethods;
import misc.StringMethods;
import user_interface.States;

/**
 * Customer class
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public class Customer {
	/**
	 * @summary process a transaction
	 * @param {t} transaction that should be processed
	 * @return {boolean} whether or not the transaction was processed successfully
	 */
	public static boolean process(Transaction t) {
		// dont process if the transaction is invalid
		if (!Transaction.validate(t))
			return false;

		Account temp;
		Customer host = null;
		
		// checks to see if the account is null
		if ((temp = t.from()) != null) {
			
			// makes sure money can be withdrawn from the account properly
			if (temp.withdraw(t.amount()))
				// adds transaction to the transaction history
				(host = temp.host).addActivity(t.desc(), temp.balance, true);
			else {
				// otherwise stops process and returns false
				States.error("Cannot withdraw, not enough funds");
				return false;
			}
		}
		
		// checks to see if the account is null
		if ((temp = t.to()) != null) {
			// deposits to account
			temp.deposit(t.amount());
			
			// then adds to transaction history
			if (temp.host != host)
				temp.host.addActivity(t.desc(), temp.balance, true);
		}
		return true;
	}

	/**
	 * @summary validates whether or not a date is a real
	 * @param {String} the birth date
	 * @return {int} years that have passed since the date
	 */
	public static int validateDate(String date) {
		try {
			return Period.between(
					LocalDate.parse(date,
							DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT)),
					LocalDate.now()).getYears();
		} catch (DateTimeParseException e) {
			return -1;
		}
	}

	/**
	 * @summary validates a sin
	 * @param {int} the sin to validate
	 * @return {boolean} if the sin is valid
	 */
	public static boolean validateSIN(int sin) {
		return (sin > 99999999 && sin < 1000000000);
	}
	
	public final String[] name; // first, middles, last
	public final String birthdate; // day, month, year
	public final int sin; 
	
	private int age;
	private String[] activity = new String[5]; // transaction history
	
	protected Map<Account.Type, Account> accounts = new HashMap<>() {
		{
			put(Account.Type.Chequing, null); // chequing
			put(Account.Type.Savings, null); // savings
			put(Account.Type.Credit, null); // credit
		}
	};

	public Customer(String name, String birthdate, int sin) {
		this.name = name.split(" ");
		this.birthdate = birthdate;
		this.sin = sin;
	}
	
	/** 
	 * @summary adds to transaction history
	 */
	public void addActivity(String desc, boolean addDate) {
		addActivity(desc, Double.NaN, addDate);
	}
	
	/** 
	 * @summary adds to transaction history
	 * @param {bal} balance of account after activity
	 * @param {addDate} adds date before activity if checked off
	 */
	public void addActivity(String desc, double bal, boolean addDate) {
		// sets suffix if balance is a number
		String suffix = "";
		if (!Double.isNaN(bal))
			suffix = String.format(", balance: $%.2f", bal);
		
		// shifts the activity array by 1 to the left
		ArrayMethods.shift(this.activity, 1);
		
		// sets the date to the corresponding string
		if (addDate)
			this.activity[4] = String.format("(%s) %s%s", LocalDate.now().toString(), desc, suffix);
		else
			this.activity[4] = desc + suffix;
	}
	
	/** 
	 * @summary checks to see if a customer can be removed
	 * @return {boolean} if the customer can be removed
	 */
	public boolean cancel() {
		Account temp;
		// checks through all accounts and checks if each individual account can be cancelled 
		return ((temp = accounts.get(Account.Type.Chequing)) == null) ? true : temp.cancel() && 
			   ((temp = accounts.get(Account.Type.Savings)) == null) ? true : temp.cancel() && 
			   ((temp = accounts.get(Account.Type.Credit)) == null) ? true : temp.cancel();
	}
	
	/** 
	 * @summary checks to see if a customer has any account
	 * @return {boolean} if the customer has any accounts
	 */
	public boolean hasAccount() {
		return !((accounts.get(Account.Type.Chequing) == null) && 
				 (accounts.get(Account.Type.Savings) == null) && 
				 (accounts.get(Account.Type.Credit) == null));
	}
	
	/** 
	 * @summary checks to see if a customer has a specific account
	 * @return {boolean} if the customer has the account
	 */
	public boolean hasAccount(Account.Type type) {
		return accounts.get(type) != null;
	}
	
	/** 
	 * @summary string of a customer when it should be displayed
	 * @return {String} the string
	 */
	public String display() {
		return String.format("%s, %d", StringMethods.concat(this.name, " "), this.sin);
	}
	
	// getters
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return this.name[this.name.length - 1] + this.name[0];
	}
	
	public String[] getActivity() {
		return activity;
	}
	
	public Account getAccount(Account.Type type) {
		return accounts.get(type);
	}
	
	// setters
	public void setAge(int age) {
		this.age = age;
	}
	
	public void removeAccount(Account.Type type) {
		accounts.put(type, null);
	}
	
	// this is how the string is present in the .txt data file
	@Override
	public String toString() {
		Account acc;
		return String.format("%d~%s~%s~%d~%s~%s~%s~%s~%s~%s~%s~%s", this.name.length,
				StringMethods.concat(this.name, "~"), this.birthdate, this.sin,
				((acc = accounts.get(Account.Type.Chequing)) == null) ? "none" : String.format("%.2f", acc.balance),
				((acc = accounts.get(Account.Type.Savings)) == null) ? "none" : String.format("%.2f", acc.balance),
				((acc = accounts.get(Account.Type.Credit)) == null) ? "none" : String.format("%.2f", acc.balance),
				activity[0], activity[1], activity[2], activity[3], activity[4]);
	}
}
