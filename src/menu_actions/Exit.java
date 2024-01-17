package menu_actions;

import database.Action;
import database.Handler;
import user_interface.Menu;

/**
 * "exit" action
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-26
 */
public class Exit extends Action {
	public Exit() {
		super("Save customer data, then close the program");
	}

	@Override
	public void execute() {
		// for looks, just tells the user that the data was saved and program is being closed
		Action.finalize("Data saved", false);
		System.out.println(Menu.SCENE_DIVIDER + "\nProgram closed successfully");
		
		// saves user data
		Handler.saveData();
		
		// exits program
		System.exit(0);
	}
}
