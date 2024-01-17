package profile_actions;

import database.Action;
import database.Bank;
import misc.Common;
import products.Account;
import products.Customer;
import products.Transaction;
import user_interface.Menu;
import user_interface.States;

/**
 * holds different types of processing actions
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-27
 */
public class Processor {
	/**
	 * process cheque
	 *
	 * @author Priyangkar Ghosh
	 * @version 2022-11-27
	 */
	public static class Cheque extends Action {
		private static String descFormat = "Cheque of $%.2f processed";

		public Cheque() {
			super("Process a cheque");
		}

		@Override
		public void execute() {
			// gets current profile
			Customer cust = Bank.getCurrentProfile();
			Account acc = cust.getAccount(Account.Type.Chequing);
			
			// if customer does not have chequing account, they cant process a cheque
			if (acc == null) {
				States.error("Customer does not have chequing account");
				return;
			}
			
			// asks user for how much the cheque has
			double amount = Action.amountInput("Enter cheque amount: ");
			
			// processes cheque, adding $0.15 if balance < 1000
			if (acc.getBalance() < 1000)
				Customer.process(new Transaction(null, acc, String.format(descFormat, amount) + " with fee of $0.15", amount + 0.15));
			else
				Customer.process(new Transaction(null, acc, String.format(descFormat, amount), amount));
			Action.finalize("Cheque processed successfully", false);
		}
	}
	
	/**
	 * process a credit card payment
	 *
	 * @author Priyangkar Ghosh
	 * @version 2022-11-27
	 */
	public static class Payment extends Action {
		private static String descFormat = "Credit card payment of $%.2f using %s account";

		public Payment() {
			super("Process payment for credit card");
		}

		@Override
		public void execute() {
			Customer cust = Bank.getCurrentProfile();
			
			// makes sure customer has a credit card
			Account card = cust.getAccount(Account.Type.Credit);
			if (card == null) {
				States.error("Customer does not have a credit card");
				return;
			}
			
			// asks what account to use to pay
			Account.Type type = Action.accountChoice("Choose which account to use to pay: ", true,
					new boolean[] { true, true, false });
			Account acc = cust.getAccount(type);
			
			// precaution
			if (acc == null)
				return;
			
			// asks for amount to pay
			double amount = Action.amountInput("How much do you want to pay: ");
			
			// pays it to credit card
			Customer.process(new Transaction(acc, card, String.format(descFormat, amount, type.name()), amount));
			Action.finalize("Payment processed successfully", false);
		}
	}
	
	/**
	 * process a credit card purchase
	 *
	 * @author Priyangkar Ghosh
	 * @version 2022-11-27
	 */
	public static class Purchase extends Action {
		private static String descFormat = "%s purchased for $%.2f";

		public Purchase() {
			super("Process a purchase");
		}

		@Override
		public void execute() {
			Customer cust = Bank.getCurrentProfile();
			Account acc = cust.getAccount(Account.Type.Credit);
			
			// makes sure customer has a credit card
			if (acc == null) {
				States.error("Customer does not have a credit card");
				return;
			}
			
			// asks for item user purchased
			System.out.println(Menu.SCENE_DIVIDER);
			System.out.print("Enter name of item purchased: ");
			
			// asked for cost of the item
			String item = Common.input.nextLine();
			double price = Action.amountInput("Enter item price: ");;
			
			// processes the purchases
			Customer.process(new Transaction(acc, null, String.format(descFormat, item, price), price));
			Action.finalize("Purchase processed successfully", false);
		}
	}
}
