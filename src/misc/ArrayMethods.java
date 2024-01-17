package misc;

import java.util.ArrayList;
import java.util.Arrays;

import database.Filter;

/**
 * Houses many different methods regarding arrays/arraylists
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public class ArrayMethods {
	/**
	 * @summary finds first occurrence of a value in an array
	 * @param {arr} array to search through
	 * @param {value} value to find index of
	 * @return {int} index of first occurrence
	 */
	public static <T> int indexOf(T[] arr, T value) {
		return indexOf(arr, value, 1);
	}
	
	/**
	 * @summary let n = instance, finds nth occurrence of a value
	 * @param {arr} array to search through
	 * @param {value} value to find index of
	 * @param {instance} occurrence to find
	 * @return {int} index of nth occurrence
	 */
	public static <T> int indexOf(T[] arr, T value, int instance) {
		for (int i = 0; i < arr.length; i++) {
			// subtracts one from instance every time the value is found
			if (arr[i].equals(value))
				instance -= 1;
			
			// if the nth instance has been found, return
			if (instance == 0)
				return i;
		}
		
		// returns -1 to signify that value was not present in the array
		return -1;
	}

	/**
	 * @summary finds all occurrences of a value in a list
	 * @param {ArrayList<T> list} ArrayList to search through
	 * @param {value} value to search for
	 * @param {equal} lambda to check if the element and value are equal
	 * @return {ArrayList<Integer>} indexes of occurrences
	 */
	public static <T> ArrayList<Integer> search(ArrayList<T> list, Object value, Filter equal) {
		ArrayList<Integer> r = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
			if (equal.func.apply(value, list.get(i)))
				r.add(i);
		return r;
	}

	/**
	 * @summary finds index of any equal value in a list
	 * @param {list} ArrayList to search through
	 * @param {value} value to search for
	 * @param {equal} lambda to check if the element and value are equal
	 * @param {compare} lambda to compare an element and value
	 * @return {int} index of value
	 */
	public static <T> int search(ArrayList<T> list, Object value, Filter equal, Filter compare) {
		// casts to array then sorts that instead
		Object[] array = list.toArray();
		return search(array, value, equal, compare, 0, list.size());
	}
	
	/**
	 * @summary finds index of any equal value in a list
	 * @param {array} array to search through
	 * @param {value} value to search for
	 * @param {equal} lambda to check if the element and value are equal
	 * @param {compare} lambda to compare an element and value
	 * @return {int} index of value
	 */
	public static <T> int search(T[] array, Object value, Filter equal, Filter compare) {
		return search(array, value, equal, compare, 0, array.length);
	}
	
	/**
	 * @summary uses binary search to find index of any equal value in a list
	 * @param {array} array to search through
	 * @param {value} value to search for
	 * @param {equal} lambda to check if the element and value are equal
	 * @param {compare} lambda to compare an element and value
	 * @param {min} starting index of array to search through
	 * @param {max} ending index of array to search through
	 * @return {int} index of value
	 */
	private static <T> int search(T[] array, Object value, Filter equal, Filter compare, int min, int max) {
		// ends search if there is no part of the array left to search 
		if (min > max)
			return -1;
		
		// finds the the part of array to search
		int mid = (min + max) / 2;
		
		//checks if this array[mid] is equal to the value
		T temp; 
		if (equal.func.apply(value, (temp = array[mid])))
			return mid;
		
		// otherwise narrows the part of array to search and searches again
		if (compare.func.apply(value, temp))
			return search(array, value, equal, compare, min, mid - 1);		
		return search(array, value, equal, compare, mid + 1, max);
	}

	/**
	 * @summary circularly shifts array to the left by specified amount
	 *          algorithm from: https://www.geeksforgeeks.org/array-rotation/
	 * @param {array} array to shift
	 * @param {amount} ending index of array to search through
	 */
	public static <T> void shift(T[] array, int amount) {
		// one length shift is equal to no shift at all
		amount %= array.length;
		
		// does not shift if shifting won't change resulting array
		if (array.length <= 1 || amount == 0)
			return;
		
		// algorithm uses gcd to split array into different parts
		int gcd = Common.gcd(array.length, amount);
		
		int i, j, k;
		for (i = 0; i < gcd; i++) {
			// each part is then shifted by the amount
			T temp = array[i];
			for (j = i; true; j = k) {
				k = j + amount;
				k %= array.length;
				if (k == i) break;
				array[j] = array[k];
			}
			array[j] = temp;
		}
		
		// this ends up with the whole array shifted by amount
	}

	/**
	 * @summary sorts an ArrayList
	 * @param {list} ArrayList to sort
	 * @param {compare} lambda to compare an element and value
	 * @return {ArrayList<T>} returns sorted ArrayList
	 */
	public static <T> ArrayList<T> sort(ArrayList<T> list, Filter compare) {
		Object[] array;
		sort(array = list.toArray(), compare);
		return new ArrayList<>(Arrays.asList((T[])array));
	}
	
	/**
	 * @summary sorts an array using insertion sort
	 * @param {array} array to sort
	 * @param {compare} lambda to compare an element and value
	 */
	public static <T> void sort(T[] array, Filter compare) {
		// loops through array
		int j;
		for (int i = 1; i < array.length; i++) {
			// if an element is in the wrong order
			// the element gets shifted to the left until the order is corrected
			T temp = array[i];
			for (j = i - 1; j >= 0; j--) {
				if (compare.func.apply(array[j], temp))
					break;
				array[j + 1] = array[j];
			}
			array[j + 1] = temp;
		}
	}
}
