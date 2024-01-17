package profile_actions;

import database.Action;
import database.Bank;
import database.Handler;
import user_interface.Menu;

/**
 * summarizes current customer's activity
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Activity extends Action {
	public Activity() {
		super("View account activity");
	}

	@Override
	public void execute() {
		Handler.displayBalance();
		Menu.displayHeader("Transaction History: ");
		
		// gets the profile
		String[] activity = Bank.getCurrentProfile().getActivity();
		
		// loops through it and prints to console
		for (int i = 4; i >= 0; i--)
			if (activity[i] != null)
				System.out.println(activity[i]);
	}
}