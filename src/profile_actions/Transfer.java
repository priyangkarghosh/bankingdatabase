package profile_actions;

import database.Action;
import database.Bank;
import misc.Common;
import products.Account;
import products.Customer;
import products.Transaction;
import user_interface.Menu;
import user_interface.States;

/**
 * transfers funds between chequing and savings accounts
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class Transfer extends Action {
	private static String descFormat = "Transfer of $%.2f from %s account to %s account";

	public Transfer() {
		super("Transfer funds between Savings and Chequing accounts");
	}
	
	@Override
	public void execute() {
		Customer cust = Bank.getCurrentProfile();
		
		// makes sure user has a chequing and savings account
		Account to = cust.getAccount(Account.Type.Chequing);
		Account from = cust.getAccount(Account.Type.Savings);
		if (from == null || to == null) {
			States.error("User cannot use action because of missing accounts.");
			return;
		}
		
		// asks which account to transfer from
		Account.Type choice = Action.accountChoice("Which account do you want to transfer money to: ", false, new boolean[] {true, true, false});
		
		// switches variables around to match user input
		if (choice != Account.Type.Chequing) {
			Account temp = from;
			from = to; to = temp;
		}
		
		// asks user for how much they want to transfer
		double amount = Action.amountInput("How much do you want to transfer: ");
		
		// processes the transfer
		if (Customer.process(
				new Transaction(from, to, String.format(descFormat, amount, from.getType().name(), to.getType().name()), amount)))
			Action.finalize("Transferred successfully", false);
	}
}
