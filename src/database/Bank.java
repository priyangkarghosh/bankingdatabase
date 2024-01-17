package database;

import java.util.ArrayList;

import misc.Common;
import products.Customer;
import user_interface.Menu;
import user_interface.States;

/**
 * Main program, serves to run the program and house data
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-21
 */
public class Bank {
	protected static final int maxCustomers = 50;
	protected static ArrayList<Customer> customers;

	private static States state;
	private static String dataPath;
	private static Customer currentProfile;

	// getters
	public static States getState() {
		return state;
	}
	
	public static Customer getCurrentProfile() {
		return currentProfile;
	}

	public static Customer getCustomer(int index) {
		return customers.get(index);
	}

	public static ArrayList<Customer> getCustomers() {
		return customers;
	}

	protected static String getDataPath() {
		return dataPath;
	}
	
	// setters
	public static void setState(States newState) {
		state = newState;
	}

	public static void updateCustomers(ArrayList<Customer> list) {
		customers = list;
	}
	
	public static void setCurrentProfile(Customer newProfile) {
		currentProfile = newProfile;
	}

	// driver code
	public static void main(String[] args) {
		// repeatedly asks for file name until a valid one is entered
		// << FOR TESTING PURPOSES, THE FILE NAME "data" WAS USED >>
		do {
			System.out.print(Menu.SCENE_DIVIDER + "\nEnter data storage path: ");
		} while (!Handler.loadData((dataPath = Common.input.nextLine() + ".txt")));

		// welcome message
		System.out.println(Menu.SCENE_DIVIDER);
		System.out.println("> Welcome to VP bank <");

		// main loop
		state = States.MAIN;
		while (true) {
			state.display(); // displays the current state
			state.input(); // then gets action input from user
		}
	}
}
