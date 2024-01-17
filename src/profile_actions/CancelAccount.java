package profile_actions;

import database.Action;
import database.Bank;
import database.Handler;
import products.Account;
import products.Customer;
import user_interface.Menu;
import user_interface.States;

/**
 * cancel account action
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class CancelAccount extends Action {
	// message to add to transaction history
	private static String descFormat = "%s account removed";

	public CancelAccount() {
		super("Cancel a pre-existing account");
	}

	@Override
	public void execute() {
		Customer cust = Bank.getCurrentProfile();
		
		// allows user to choose an account to cancel from the accounts they already have
		Account.Type req = Action.accountChoice("Choose account to cancel", true);
		if (req == null) return;
		
		// although this error cannot occur, it is still here as a safe-guard
		Account acc;
		if ((acc = cust.getAccount(req)) == null) {
			States.error("Account doesn't exist");
			return;
		}
		
		// makes sure the account can be cancelled
		if (!acc.cancel()) {
			States.error("Account cannot be cancelled because of outstanding debts");
			return;
		}
		
		// removes the account and adds the cancellation to customer history
		cust.removeAccount(req);
		cust.addActivity(String.format(descFormat, req.name()), true);
		Action.finalize("Account removed successfully", false);
		
		// checks to see if customer still has any accounts
		if (cust.hasAccount())
			return;
		
		// if they dont then remove the customer
		Handler.removeCustomer(cust);
		System.out.println(Menu.SCENE_DIVIDER + "\nCustomer removed since they have no accounts in the bank");
	}
}