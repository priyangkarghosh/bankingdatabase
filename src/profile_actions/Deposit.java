package profile_actions;

import database.Action;
import database.Bank;
import misc.Common;
import products.Account;
import products.Customer;
import products.Transaction;
import user_interface.Menu;

/**
 * deposit to account
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class Deposit extends Action {
	private static String descFormat = "Deposit of $%.2f to %s account";

	public Deposit() {
		super("Deposit");
	}

	@Override
	public void execute() {
		Customer cust = Bank.getCurrentProfile();
		
		// asks user to choose where to deposit to between their chequing and savings account
		Account.Type type = Action.accountChoice("Choose which account to deposit to: ", true, new boolean[] {true, true, false});
		Account acc = cust.getAccount(type);
		
		// this is just a safety precaution
		if (acc == null) return;
		
		// asks user for amount then deposits it, adding it to transaction history
		double amount = Action.amountInput("How much do you want to deposit: ");
		Customer.process(new Transaction(null, acc, String.format(descFormat, amount, type.name()), amount));
		Action.finalize("Deposited successfully", false);
	}
}