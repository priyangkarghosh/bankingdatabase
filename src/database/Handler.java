package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import accounts.Chequing;
import accounts.Credit;
import accounts.Savings;
import misc.ArrayMethods;
import misc.StringMethods;
import products.Account;
import products.Customer;
import user_interface.Menu;
import user_interface.States;

/**
 * used to handle customer data
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-22
 */
public class Handler {
	/**
	 * @summary validates than adds a customer to the program
	 * @param {Customer} used to tell user what to input
	 * @return {boolean} returns whether or not the customer was added successfully
	 */
	public static boolean addCustomer(Customer c) {
		// makes sure that there is enough space for a new customer
		if (Bank.customers.size() > Bank.maxCustomers) {
			States.error("Too many customers.");
			return false;
		}
		
		boolean valid = true;
		// makes sure the customer has a first and last name at minimum
		if (c.name.length < 2) {
			States.error("\t> Customer name was not inputted properly");
			valid = false;
		}

		// gets age of customer, and makes sure birth-date is a real date
		int age = Customer.validateDate(c.birthdate);
		if (age < 0) {
			if (!valid)
				System.out.println("\t> Customer does not have a valid birthdate");
			else {
				States.error("\t-Customer does not have a valid birthdate");
				valid = false;
			}
		}
		
		// validates sin to make sure it is 9 digits (first digit cannot be 0)
		if (!Customer.validateSIN(c.sin)) {
			if (!valid)
				System.out.println("\t> Customer sin is not valid");
			else {
				States.error("\t> Customer sin is not valid");
				valid = false;
			}
		}
		
		// return if customer was not valid
		if (!valid) return false;

		// otherwise add the customer and return true
		c.setAge(age);
		Bank.customers.add(c);
		return true; 
	}
	
	/**
	 * @summary display the balances of the accounts of the current profile
	 */
	public static void displayBalance() {		
		Customer cust = Bank.getCurrentProfile();
		
		Account temp;
		Menu.displayHeader("Current balances: ");
		System.out.println(cust.display());
		System.out.printf("\t> Chequing: %s\n", (((temp = cust.getAccount(Account.Type.Chequing)) == null) ? "none" : String.format("$%.2f", temp.getBalance())));
		System.out.printf("\t> Savings: %s\n", (((temp = cust.getAccount(Account.Type.Savings)) == null) ? "none" : String.format("$%.2f", temp.getBalance())));
		System.out.printf("\t> Credit: %s\n", (((temp = cust.getAccount(Account.Type.Credit)) == null) ? "none" : String.format("$%.2f", temp.getBalance())));
	}

	/**
	 * @summary find a customer based on name
	 * @param {name} name to find
	 * @return {ArrayList<Integer>} returns a list of integers with indexes of found
	 *         values
	 */
	public static ArrayList<Integer> findNAME(String name) {
		sortNAME();
		return ArrayMethods.search(Bank.customers, name, Filter.NAME_FE);
	}

	/**
	 * @summary find a customer based on sin
	 * @param {sin} sin to find
	 * @return {int} returns found index
	 */
	public static int findSIN(int sin) {
		sortSIN();
		return ArrayMethods.search(Bank.customers, sin, Filter.SIN_FE, Filter.SIN_FC);
	}
	
	/**
	 * @summary load data from specified text file
	 * @param {path} path which holds bank data
	 * @return {boolean} whether or not the data was loaded properly
	 */
	protected static boolean loadData(String path) {
		Bank.customers = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			// loop through data, one line = one customer
			// customer data is in the format: [t stands for past account activity]
			// #ofnames~name(s)..~birthdate~sin~chequing~savings~credit~t1~t2~t3~t4~t5
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split("~");
				int index = Integer.parseInt(data[0]);
				
				//instantiate a new customer
				Customer c = new Customer(StringMethods.concat(data, 1, index + 1, " "), data[index + 1],
						Integer.parseInt(data[index += 2]));
				
				// add corresponding accounts to customer
				if (!data[index += 1].equals("none"))
					new Chequing(c, Double.parseDouble(data[index]));
				if (!data[index += 1].equals("none"))
					new Savings(c, Double.parseDouble(data[index]));
				if (!data[index += 1].equals("none"))
					new Credit(c, Double.parseDouble(data[index]));
				
				//update the transaction history
				for (int i = 0; i < 5; i++)
					if (!data[index += 1].equals("null"))
						c.addActivity(data[index], false);
				
				//if there is a single invalid customer, the whole data file is deemed invalid
				if (addCustomer(c)) continue;
				
				br.close();
				return false;
			}
			
			//if the program reads through the whole file without any errors, the data is deemed valid
			br.close();
			return true;
		}
		
		catch (FileNotFoundException e) {
			States.error("No file at the specified path.");
			return false;
		}
		
		catch (IOException e) {
			States.error("Error reading the file.");
			return false;
		}

		catch (ArrayIndexOutOfBoundsException e) {
			States.error("File data is not formatted correctly.");
			return false;
		}
	}
	
	/**
	 * @summary removes customer by element
	 */
	public static void removeCustomer(Customer customer) {
		Bank.customers.remove(customer);
	}
	
	/**
	 * @summary removes customer by index in the "customers" ArrayList
	 * @param {index} index of the customer to remove
	 * @return {boolean} whether or not the customer was removed successfully
	 */
	public static boolean removeCustomer(int index) {
		// index of -1 means the customer does not exist
		if (index == -1) {
			States.error("Customer not found.");
			return false;
		}
		
		// checks if a customer can cancel their account
		if (!Bank.getCustomer(index).cancel()) {
			States.error("Customer has outstanding debts on accounts.");
			return false;
		}
		
		// removed the customer if it passes all checks
		Bank.customers.remove(index);
		return true;
	}

	/**
	 * @summary saves data back to .txt file
	 * @return {boolean} if data was saved successfully
	 */
	public static boolean saveData() {
		try {
			// writes data to saved dataPath (the file which data was read from)
			BufferedWriter bw = new BufferedWriter(new FileWriter(Bank.getDataPath()));
			 
			for (Customer c : Bank.customers) {
				bw.write(c.toString());
				bw.newLine();
			}

			bw.close();
		}

		catch (FileNotFoundException e) {
			States.error("No file at the specified path.");
			return false;
		}

		catch (IOException e) {
			States.error("Error writing in the file.");
			return false;
		}

		return true;
	}

	/**
	 * @summary set the current profile
	 * @param {index} customer index to display
	 * @return {boolean} if customer was set successfully
	 */
	public static boolean setProfile(int index) {
		// if the index is -1, don't set the profile
		if (index == -1) {
			States.error("Customer not found.");
			return false;
		}

		// otherwise set the customer
		Bank.setCurrentProfile(Bank.customers.get(index));
		Bank.setState(States.PROFILE);
		return true;
	}
	
	/**
	 * @summary sorts the customers ArrasyList by name
	 */
	public static void sortNAME() {
		Bank.updateCustomers(ArrayMethods.sort(Bank.customers, Filter.NAME_SC));
	}
	
	/**
	 * @summary sorts the customers ArrayList by sin
	 */
	public static void sortSIN() {
		Bank.updateCustomers(ArrayMethods.sort(Bank.customers, Filter.SIN_SC));
	}
}
