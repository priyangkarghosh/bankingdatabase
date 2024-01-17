package products;

/**
 * Transaction record (mostly just to hold information)
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public record Transaction(Account from, Account to, String desc, double amount) {
	@Override
	public String toString() {
		return desc;
	}
	
	/**
	 * @summary validates a transaction
	 * @param {t} transaction that should be validated
	 * @return {boolean} whether or not the transaction is valid
	 */
	public static boolean validate(Transaction t) {
		// makes sure the amount is not negative or 0
		if (t.amount <= 0)
			return false;
		
		// one part of the transaction is null, it will always be valid
		if (t.from == null || t.to == null)
			return true;
		
		switch (t.from.type) {
			// cannot be a transaction from a chequing account to another chequing account
			case Chequing:
				if (t.to.type == Account.Type.Chequing)
					return false;
				break;
				
			// cannot be a transaction from a savings account to another savings account
			case Savings:
				if (t.to.type == Account.Type.Savings)
					return false;
				break;
			
			// cannot be a transaction from a credit card to another credit card
			case Credit:
				if (t.to.type == Account.Type.Credit)
					return false;
				break;
			
			// cannot be a transaction from no account to a credit card
			default:
				if (t.to.type == Account.Type.Credit)
					return false;
				break;
		}
		
		// returns true if passes all the above precautions
		return true;
	}
}
