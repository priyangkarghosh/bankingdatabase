package accounts;

import products.Account;
import products.Customer;

/**
 * implementation of savings account
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-22
 */
public class Savings extends Account {
	public Savings(Customer host, double balance) {
		super(host, balance, Account.Type.Savings);
	}

	@Override
	public boolean cancel() {
		return (this.balance >= 0);
	}

	@Override
	public boolean validate() {
		return (this.balance >= 0);
	}

	@Override
	public boolean withdraw(double amount) {
		// checks if there is enough funds to withdraw "amount"
		if ((this.balance -= amount) >= 0)
			return true;

		// if not, it adds the amount back to the balance
		balance += amount;
		return false;
	}
}
