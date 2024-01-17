package menu_actions;

import database.Action;
import database.Bank;
import products.Customer;
import user_interface.Menu;

/**
 * summarizes all the customers
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Summary extends Action {
	public Summary() {
		super("Summarize all customer data");
	}

	@Override
	public void execute() {
		Menu.displayHeader("Users Summary");
		// loops through all customers and displays them
		for (Customer c : Bank.getCustomers())
			System.out.printf("\t❏ %s\n", c.display());
	}
}
