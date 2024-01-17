package misc;

import java.util.Random;
import java.util.Scanner;

/**
 * Houses constants/common methods for the program
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public class Common {
	// for input
	public static final Scanner input = new Scanner(System.in);
	
	// for random numbers
	public static final Random rng = new Random();
	
	/**
	 * @summary calculates the gcd of two integers
	 *          algorithm from: https://www.geeksforgeeks.org/steins-algorithm-for-finding-gcd/
	 * @param {a} first integer
	 * @param {b} second integer
	 * @return gcd
	 */
	public static int gcd(int a, int b) {
		// if either integer is 0, the gcd is the other integer
		if (a == 0)
			return b;
		if (b == 0)
			return a;
		
		// finds c, which is the greatest 2^n which divides both a and b
		int c;
		for (c = 0; ((a | b) & 1) == 0; c++) {
			a >>= 1;
			b >>= 1;
		}
		
		// divides a by 2 until it is odd
		while ((a & 1) == 0)
			a >>= 1;
		
		do {
			// if b is even, remove all factors of 2 from it
			while ((b & 1) == 0)
				b >>= 1;
			
			// switch the 2 variables if a > b
			if (a > b) {
				int temp = a;
				a = b;
				b = temp;
			}

			b = (b - a);

		} while (b != 0);
		
		// restore common factors of 2
		return a << c;
	}
}
