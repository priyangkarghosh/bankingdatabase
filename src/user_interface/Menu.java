package user_interface;

import misc.StringMethods;

/**
 * Generic menu class
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-25
 */
public class Menu {
	// constants
	public static final int MAX_LENGTH = 75;
	public static final String LINE_DIVIDER = StringMethods.repeat("-", MAX_LENGTH);
	public static final String SCENE_DIVIDER = LINE_DIVIDER.replace("-", "=");
	
	/**
	 * @summary displays a header
	 * @param {text} text of header
	 */
	public static void displayHeader(String text) {
		System.out.println(SCENE_DIVIDER + "\n" + text + "\n" + LINE_DIVIDER);
	}

	private Actions[] actions;
	private String title, desc;
	
	public Menu(String title, String desc, Actions[] actions) {
		this.title = StringMethods.cutoff(title, MAX_LENGTH);
		if (desc != null)
			this.desc = StringMethods.margin(desc, MAX_LENGTH);
		this.actions = actions;
	}
	
	// getter
	public Actions getAction(int index) {
		return this.actions[index];
	}

	@Override
	public String toString() {
		// init return string
		String r = new String();

		// top divider
		r += SCENE_DIVIDER;

		// print the title
		r += "\n" + this.title;

		// print the divider
		r += "\n" + LINE_DIVIDER;

		// print the description
		if (this.desc != null)
			r += "\n" + this.desc;

		// print the actions
		for (int i = 0; i < this.actions.length; i++)
			r += String.format("\n\t%d: %s", i + 1, this.actions[i].ref.getName());

		// if the previous line wasn't a divider, add a divider
		int l = r.length();
		if (r.charAt(l - 3) != '-')
			r += "\n" + SCENE_DIVIDER;
		else
			r = r.substring(0, l - (MAX_LENGTH + 1));

		// return the class to string
		return r;
	}
}
