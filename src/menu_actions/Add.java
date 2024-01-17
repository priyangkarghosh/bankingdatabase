package menu_actions;

import database.Action;
import database.Bank;
import database.Handler;
import misc.Common;
import products.Customer;
import user_interface.Actions;
import user_interface.Menu;
import user_interface.States;

/**
 * "add" action
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Add extends Action {
	public Add() {
		super("Add a customer");
	}

	@Override
	public void execute() {
		// gets customer information
		Menu.displayHeader("Customer information required: ");

		System.out.print("\t◻ Customer Name (First Middle(s) Last)\n" + "\t\t-> Seperate each name with a space: ");
		String name = Common.input.nextLine();

		System.out.print("\t◻ Birthdate(DD/MM/YYYY): ");
		String birthdate = Common.input.nextLine();

		System.out.print("\t◻ Customer SIN (---------): ");
		int sin = Integer.parseInt(Common.input.nextLine());
		
		// adds customer, then tries to make account
		Customer c = new Customer(name, birthdate, sin);
		if (Handler.addCustomer(c)) {
			Action.finalize("Customer added succesfully", false);
			
			Bank.setCurrentProfile(c);
			while (!c.hasAccount())
				try {
					Actions.ADD_ACCOUNT.ref.execute();
				}
				catch (Exception e) {
					States.error("Invalid input");
				}
			return;
		}
		
		// if account was not created successfully prompts user to try again
		States.RETRY.display();
		System.out.println(Menu.LINE_DIVIDER);

		System.out.println("\t1: Yes");
		System.out.println("\t*: ANY INPUT THAT IS NOT 1 WILL BE INTERPRETED AS NO");

		System.out.println(Menu.SCENE_DIVIDER);
		System.out.print("Enter action code here: ");
		if (Integer.parseInt(Common.input.nextLine()) == 1)
			this.execute();
	}
}
