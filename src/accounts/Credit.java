package accounts;

import products.Account;
import products.Customer;

/**
 * implementation of credit account/card
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-22
 */
public class Credit extends Account {
	public Credit(Customer host, double balance) {
		super(host, balance, Account.Type.Credit);
	}

	@Override
	public boolean cancel() {
		return (this.balance >= 0);
	}

	@Override
	public boolean validate() {
		return (balance >= 0 && this.host.getAge() >= 18);
	}

	@Override
	public boolean withdraw(double amount) {
		// credit card can always withdraw money
		this.balance -= amount;
		return true;
	}
}
