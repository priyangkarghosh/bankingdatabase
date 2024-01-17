package user_interface;

import java.util.InputMismatchException;

import misc.Common;

/**
 * Class which holds all the different states the program can be in
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public enum States {
	MAIN(new Menu("Main menu", "Please choose an action from the following:",
			new Actions[] { Actions.ADD, Actions.REMOVE, Actions.SORT, Actions.SUMMARY, Actions.SEARCH,
					Actions.QUIT, })),

	PROFILE(new Menu("Profile menu", "Please choose an action from the following:",
			new Actions[] { Actions.ACTIVITY, Actions.DEPOSIT, Actions.WITHDRAW, Actions.TRANSFER,
					Actions.PROCESS_PURCHASE, Actions.PROCESS_PAYMENT, Actions.PROCESS_CHEQUE, Actions.ADD_ACCOUNT,
					Actions.REMOVE_ACCOUNT, Actions.RETURN, })),

	REMOVE(new Menu("Customer Removal", "Please choose an action from the following:",
			new Actions[] { Actions.SIN_REMOVE, Actions.NAME_REMOVE, Actions.RETURN })),

	RETRY(new Menu("Would you like to try again: ", null, new Actions[] {})),

	SEARCH(new Menu("Find a customer", "Please choose an action from the following:",
			new Actions[] { Actions.SIN_SEARCH, Actions.NAME_SEARCH, Actions.RETURN })),

	SORT(new Menu("Data Sorting", "Please choose an action from the following:",
			new Actions[] { Actions.SIN_SORT, Actions.NAME_SORT, Actions.RETURN }));

	// other constants
	public static final String ERROR = "Error.";
	public static final String SUCCESS = "Action Successful!";
	
	/**
	 * @summary displays an error message
	 * @param {msg} error message
	 */
	public static void error(String msg) {
		Menu.displayHeader(States.ERROR);
		System.out.println(msg);
	}

	public final Menu menu;
	private States(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * @summary displays the current state
	 */
	public void display() {
		System.out.println(this.menu);
	}
	
	/**
	 * @summary gets user input
	 */
	public void input() {
		try {
			System.out.print("Enter action code here: ");
			this.menu.getAction(Integer.parseInt(Common.input.nextLine()) - 1).ref.execute();
		}

		catch (ArrayIndexOutOfBoundsException e) {
			States.error("That is not a valid action");
		}

		catch (NumberFormatException e) {
			States.error("Invalid input ");
		}

		catch (InputMismatchException e) {
			States.error("Information was not valid");
		}
	}
}