package products;

import user_interface.States;

/**
 * Parent class to all accounts
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public abstract class Account {
	// the different types of accounts a customer can make
	public static enum Type {
		Chequing, Savings, Credit;
	}
	
	/**
	 * @summary gets type of account from index
	 * @param {index} index to get type from
	 * @return {Account.Type} return the type
	 */
	public static Account.Type getType(int index) {
		switch (index) {
			case 0:
				return Account.Type.Chequing;
			case 1:
				return Account.Type.Savings;
			case 2:
				return Account.Type.Credit;
			
			// error if the index is not between 0-2
			default:
				States.error("Input not valid");
				return null;
			}
	}
	
	// these are all protected to make sure the child classes can access them
	protected double balance;
	protected Customer host = null;
	protected Type type = null;
	
	// constructor which sets all the fields of an account
	// *it also adds the account to the corresponding host
	public Account(Customer host, double balance, Type type) {
		this.host = host;
		this.balance = balance;
		this.type = type;

		host.accounts.put(type, this);
	}
	
	// getters
	public Type getType() {
		return this.type;
	}
	 
	public double getBalance() {
		return balance;
	}
	
	// abstract methods 	
	/**
	 * @summary method to check if an account can be cancelled
	 * @return {boolean} whether or not the account can be cancelled
	 */ 
	public abstract boolean cancel();
	
	/**
	 * @summary validates whether or not a customer can create this type of account
	 * @return {boolean} whether or not the account is valid
	 */ 
	public abstract boolean validate();
	
	/**
	 * @summary withdraw method for a specified account
	 * @param {amount} how much to withdraw
	 * @return {boolean} whether or not it was withdrawn successfully
	 */ 
	public abstract boolean withdraw(double amount);
	
	/**
	 * @summary deposits money to the balance
	 * @param {double} the amount to deposit
	 */
	public void deposit(double amount) {
		this.balance += amount;
	}
}