package profile_actions;

import database.Action;
import database.Bank;
import misc.Common;
import products.Account;
import products.Customer;
import products.Transaction;
import user_interface.Menu;

/**
 * withdraws funds from chequing or savings accounts
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class Withdraw extends Action {
	private static String descFormat = "Withdrawal of $%.2f from %s account";

	public Withdraw() {
		super("Withdraw");
	}

	@Override
	public void execute() {
		Customer cust = Bank.getCurrentProfile();
		
		// asks user for account to withdraw from 
		Account.Type type = Action.accountChoice("Choose which account to withdraw from: ", true, new boolean[] {true, true, false});
		
		// makes sure user has the account (precaution)
		Account acc = cust.getAccount(type);
		if (acc == null)
			return;
		
		// asks user for how much they want to withdraw
		double amount = Action.amountInput("How much do you want to withdraw: ");
		
		// processes the withdrawal
		if (Customer.process(
				new Transaction(acc, (Account) null, String.format(descFormat, amount, type.name()), amount)))
			Action.finalize("Withdrawn successfully", false);
	}
}