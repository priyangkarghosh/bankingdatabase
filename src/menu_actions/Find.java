package menu_actions;

import java.util.ArrayList;

import database.Action;
import database.Bank;
import database.Handler;
import misc.Common;
import misc.StringMethods;
import products.Account;
import products.Customer;
import user_interface.Menu;
import user_interface.States;

/**
 * holds the actions related to finding a user
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Find extends Action {
	/**
	 * find customer by name action
	 *
	 * @author Priyangkar Ghosh
	 * @version 2022-11-26
	 */
	public static class Name extends Action {
		public Name() {
			super("Find customer by name");
		}

		@Override
		public void execute() {
			// asks user for name to find
			System.out.print(Menu.SCENE_DIVIDER + "\nEnter name of customer to find: ");
			String name = StringMethods.filter(Common.input.nextLine());
			
			// finds the options, and gets users selection
			ArrayList<Integer> indices = Handler.findNAME(name);
			int index = Action.options(indices, "name", name);
			if (index == -1)
				return;
			
			// uses user input to proceed
			try {
				if (index == indices.size())
					Action.finalize("Quit find menu", true);
				
				else if (Handler.setProfile(indices.get(index))) {
					Action.finalize("Customer found", false);
					Handler.displayBalance();
				}
			}
			
			// if users input is invalid, it returns back to main menu
			catch (Exception e) {
				States.error("That is not a valid action");
			}
		}
	}
	
	/**
	 * find customer by sin action
	 *
	 * @author Priyangkar Ghosh
	 * @version 2022-11-26
	 */
	public static class Sin extends Action {
		public Sin() {
			super("Find customer using SIN");
		}

		@Override
		public void execute() {
			// asks user which sin number to find
			System.out.print(Menu.SCENE_DIVIDER + "\nEnter SIN of customer to find: ");
			int sin = Integer.parseInt(Common.input.nextLine());
			
			// finds profile based on sin
			System.out.println(Menu.SCENE_DIVIDER + "\nSearching for customer...");
			if (Handler.setProfile(Handler.findSIN(sin))) {
				Action.finalize("Customer found", false);
				Handler.displayBalance();
			}			
		}
	}

	public Find() {
		super("Find a customer profile");
	}
	
	@Override
	public void execute() {
		// sets the state to ask for what to find using
		Bank.setState(States.SEARCH);
	}
}