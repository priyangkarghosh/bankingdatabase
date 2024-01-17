package menu_actions;

import database.Action;
import database.Bank;
import user_interface.States;

/**
 * returns program to main menu
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Return extends Action {
	public Return() {
		super("Return to main menu");
	}

	@Override
	public void execute() {
		Bank.setState(States.MAIN);
	}
}
