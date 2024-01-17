package database;

import java.util.Arrays;
import java.util.function.BiFunction;

import products.Customer;

/**
 * Houses lambda statements for sorting/finding algorithms
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-22
 */
public enum Filter {
	// generic lambdas
	NAME_C((a, b) -> (((String) a).compareTo((String) b)) < 0), NAME_E((a, b) -> ((String) a).equals(b)),
	SIN_C((a, b) -> (int) a <= (int) b), SIN_E((a, b) -> (int) a == (int) b),

	// S = SORTING, F = SEARCH, E = EQUALS, C = COMPARE
	NAME_FE((a, b) -> Arrays.stream(((Customer) b).name).anyMatch(x -> (x.contains((String) a)))),
	NAME_SC((a, b) -> NAME_C.func.apply(((Customer) a).getName(), ((Customer) b).getName())),

	SIN_FC((a, b) -> SIN_C.func.apply(a, ((Customer) b).sin)),
	SIN_FE((a, b) -> SIN_E.func.apply(a, ((Customer) b).sin)),
	SIN_SC((a, b) -> SIN_C.func.apply(((Customer) a).sin, ((Customer) b).sin));

	public final BiFunction<Object, Object, Boolean> func;
	private Filter(BiFunction<Object, Object, Boolean> func) {
		this.func = func;
	}
}
