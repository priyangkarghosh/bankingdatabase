package user_interface;

import database.Action;
import menu_actions.Add;
import menu_actions.Exit;
import menu_actions.Find;
import menu_actions.Remove;
import menu_actions.Return;
import menu_actions.Sort;
import menu_actions.Summary;
import profile_actions.Activity;
import profile_actions.CancelAccount;
import profile_actions.Deposit;
import profile_actions.OpenAccount;
import profile_actions.Processor;
import profile_actions.Transfer;
import profile_actions.Withdraw;

/**
 * Enum holding every action that the customer can do
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public enum Actions {
	ACTIVITY(new Activity()), ADD(new Add()), ADD_ACCOUNT(new OpenAccount()),

	DEPOSIT(new Deposit()), NAME_REMOVE(new Remove.Name()), NAME_SEARCH(new Find.Name()),

	NAME_SORT(new Sort.Name()), PROCESS_CHEQUE(new Processor.Cheque()), PROCESS_PAYMENT(new Processor.Payment()),

	PROCESS_PURCHASE(new Processor.Purchase()), QUIT(new Exit()), REMOVE(new Remove()),

	REMOVE_ACCOUNT(new CancelAccount()), RETURN(new Return()), SEARCH(new Find()),

	SIN_REMOVE(new Remove.Sin()), SIN_SEARCH(new Find.Sin()),

	SIN_SORT(new Sort.Sin()), SORT(new Sort()), SUMMARY(new Summary()),

	TRANSFER(new Transfer()), WITHDRAW(new Withdraw());

	public final Action ref;
	private Actions(Action action) {
		this.ref = action;
	}
}
