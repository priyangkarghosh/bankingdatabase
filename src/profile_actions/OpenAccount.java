package profile_actions;

import accounts.Chequing;
import accounts.Credit;
import accounts.Savings;
import database.Action;
import database.Bank;
import misc.Common;
import products.Account;
import products.Customer;
import user_interface.Menu;
import user_interface.States;

/**
 * action to open a new account
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class OpenAccount extends Action {
	private static String descFormat = "%s account created";

	public OpenAccount() {
		super("Open a new account");
	}

	@Override
	public void execute() {
		Customer cust = Bank.getCurrentProfile();
		Account.Type req = Action.accountChoice("Choose type of account to open");
		if (req == null) return;
		
		// makes sure that user does not already have account (precaution)
		if (cust.getAccount(req) != null) {
			States.error("Account already exists");
			return;
		}
		
		// asks for initial balance
		double amount = 0;
		if (req != Account.Type.Credit) 
			amount = Action.amountInput("Enter initial balance: ");
		
		// makes sure account is valid
		boolean valid = false;
		switch (req) {
			case Chequing:
				valid = new Chequing(cust, amount).validate();
				break;
			case Savings:
				valid = new Savings(cust, amount).validate();
				break;
			case Credit:
				valid = new Credit(cust, amount).validate();
				break;
		}
		
		// otherwise remove account
		if (!valid) {
			States.error("Account cannot be created for this customer");
			cust.removeAccount(req);
			return;
		}
		
		// add account properly to customer
		cust.addActivity(String.format(descFormat, req.name()), true);
		Action.finalize("Account created successfully", false);
	}
}