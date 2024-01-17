package database;

import java.util.ArrayList;

import misc.ArrayMethods;
import misc.Common;
import products.Account;
import products.Account.Type;
import products.Customer;
import user_interface.Actions;
import user_interface.Menu;
import user_interface.States;

/**
 * Parent class to all actions -implements name, basic constructor, generic
 * static methods and abstract "execute()" method
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-21
 */
public abstract class Action {
	/**
	 * @summary user input for an double amount
	 * @param {prompt} used to tell user what to input
	 * @return {double} returns amount user inputs
	 */
	public static double amountInput(String prompt) {
		double amount;
		while (true) {
			try {
				System.out.println(Menu.SCENE_DIVIDER);
				System.out.print(prompt);
				amount = Double.parseDouble(Common.input.nextLine());
				return amount;
			}
			
			catch (NumberFormatException e) {
				States.error("Not a valid amount to input.");
			}
		} 
	}
	
	/**
	 * @summary user input for an account type if only unowned
	 * @param {prompt} used to tell user what to input
	 * @return {Type} returns the type of account the user selects
	 */
	public static Type accountChoice(String prompt) {
		Customer cust = Bank.getCurrentProfile();
		return accountChoice(prompt, false, new boolean[] 
				{ !cust.hasAccount(Account.Type.Chequing), 
				  !cust.hasAccount(Account.Type.Savings), 
				  !cust.hasAccount(Account.Type.Credit), 
				}
		);
	}
	
	/**
	 * @summary user input for an account type
	 * @param {prompt} used to tell user what to input
	 * @param {ownedOnly} whether or not to only display owned accounts as options
	 * @return {Type} returns the type of account the user selects
	 */
	public static Type accountChoice(String prompt, boolean ownedOnly) {
		return accountChoice(prompt, ownedOnly, new boolean[] { true, true, true });
	}

	/**
	 * @summary user input for an account type
	 * @param {prompt}    used to tell user what to input
	 * @param {ownedOnly} whether or not to only display owned accounts as options
	 * @param {show} which accounts to show/hide
	 * @return {Type} returns the type of account the user selects
	 */
	public static Type accountChoice(String prompt, boolean ownedOnly, boolean[] show) {
		// prints out the question
		Menu.displayHeader(prompt);

		// stores which accounts the user has
		// false means that the user does not have the account
		Boolean[] accs = { false, false, false };

		// loops through types of accounts and prints it if the user has it
		// updates the "accs" array accordingly
		int i = 0, j = 1;
		for (Account.Type t : Account.Type.values()) {
			if ((!ownedOnly || Bank.getCurrentProfile().getAccount(t) != null) && show[i]) {
				System.out.println("\t" + j + ": " + t.name());
				accs[i] = true;
				j++;
			}
			i++;
		}

		// gets user input for account type
		System.out.print(Menu.SCENE_DIVIDER + "\nEnter action index: ");
		int n = Integer.parseInt(Common.input.nextLine());

		// finds the corresponding index/type by finding the nth occurrence of true in
		// the "accs" array
		int index = ArrayMethods.indexOf(accs, true, n);
		return Account.getType(index);
	}

	/**
	 * @summary marks the end of the execution of an action
	 * @param {msg}          what an action displays at the end of its execution
	 * @param {returnToMain} whether or not the action should return to the main
	 *                       menu after
	 */
	public static void finalize(String msg, boolean returnToMain) {
		Menu.displayHeader(States.SUCCESS);
		System.out.println(msg);

		if (returnToMain)
			Actions.RETURN.ref.execute();
	}

	/**
	 * @summary ArrayList implementation of "options" method
	 */
	public static int options(ArrayList<Integer> indices, String param, String value) {
		return options(indices.stream().mapToInt(Integer::intValue).toArray(), param, value);
	}

	/**
	 * @summary displays a list of customers as options for the user then returns
	 *          user's choice
	 * @param {indices} the indexes of the customers which should be presented as
	 *                  options
	 * @param {param}   what the options present
	 * @param {value}   the value which was used to find options
	 */
	public static int options(int[] indices, String param, String value) {
		// check to make sure there are options
		if (indices.length == 0) {
			States.error("No customers with this information were found.");
			return -1;
		}

		// print the options found
		Menu.displayHeader(String.format("%d customer%s with the %s %s %s found:", indices.length,
				(indices.length == 1) ? "" : "s", param, value, (indices.length == 1) ? "was" : "were"));

		for (int i = 0; i < indices.length; i++)
			System.out.printf("\t%d: %s\n", i + 1, Bank.getCustomer(indices[i]).display());
		System.out.printf("\t%d: %s\n", indices.length + 1, "Return to main menu");

		// return the user input
		System.out.print(Menu.SCENE_DIVIDER + "\nEnter action index: ");
		return Integer.parseInt(Common.input.nextLine()) - 1;
	}

	// this class only stores the name of the action
	// the execute method is configured by child classes
	private String name = "";

	public Action(String name) {
		this.setName(name);
	}

	// abstract method which runs when an action is supposed to be run
	public abstract void execute();

	// getters
	public String getName() {
		return name;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}
}
