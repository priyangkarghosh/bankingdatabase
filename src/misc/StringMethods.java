package misc;

/**
 * Houses many different methods regarding strings
 *
 * @author Priyangkar Ghosh
 * @version 2022-11-23
 */
public class StringMethods {
	/**
	 * @summary concatenates a list of strings together
	 * @param {strings} list of strings
	 * @param {start} index which string to start with
	 * @param {end} index of which string to end with
	 * @param {divider} what to put between each string in list
	 * @return {string} the resulting string
	 */
	public static String concat(String[] strings, int start, int end, String divider)
			throws ArrayIndexOutOfBoundsException {

		if (start < 0 || start > strings.length - 1)
			throw new ArrayIndexOutOfBoundsException("The start index is not valid.");

		if (end < 0 || end > strings.length || end < start)
			throw new ArrayIndexOutOfBoundsException("The end index is not valid.");

		String r = new String();
		for (int i = start; i < end; i++)
			r += strings[i] + ((i == end - 1) ? "" : divider);
		return r;
	}
	
	/**
	 * @summary concatenates a list of strings together
	 * @param {strings} list of strings
	 * @param {start} which string to start with
	 * @param {divider} what to put between each string in list
	 * @return {string} the resulting string
	 */
	public static String concat(String[] strings, int start, String divider) {
		return concat(strings, start, strings.length, divider);
	}

	/**
	 * @summary concatenates a list of strings together
	 * @param {strings} list of strings
	 * @param {divider} what to put between each string in list
	 * @return {string} the resulting string
	 */
	public static String concat(String[] strings, String divider) {
		return concat(strings, 0, strings.length, divider);
	}
	
	/**
	 * @summary limits a string to a certain length
	 * @param {string} string to limit
	 * @param {maxLength} max amount of characters the string can be
	 * @return {string} the resulting string
	 */
	public static String cutoff(String string, int maxLength) {
		if (string.length() > maxLength)
			return string.substring(0, maxLength);
		return string;
	}
	
	/**
	 * @summary filters any punctuation/spaces out of a string
	 * @param {string} string to filter
	 * @return {string} the resulting string
	 */
	public static String filter(String a) {
		int c;
		a = a.replace(" ", "");
		
		//uses StringBuilder for simple character removal
		StringBuilder filtered = new StringBuilder(a.toLowerCase());
		for (int j = 0; j < filtered.length(); j++)
			//checks to see if a character is not a valid letter between 'a' - 'z'
			if ((c = filtered.charAt(j)) < 97 || c > 122)
				filtered.deleteCharAt(j);
		return filtered.toString();
	}

	/**
	 * @summary adds a new line to a string if it's longer than a certain length
	 * @param {string} string to filter
	 * @param {maxLength} maximum length of one line of the string
	 * @return {string} the resulting string
	 */
	public static String margin(String string, int maxLength) {
		int l; // to store length of string
		if ((l = string.length()) <= maxLength)
			return string;

		// use string builder for simpler char inserting
		StringBuilder sb = new StringBuilder(string);
		for (int i = 0; i < l; i += maxLength) {
			// find space between words
			i = sb.lastIndexOf(" ", i + 1);

			// if a space was found insert new line there
			if (i != -1)
				sb.insert(i + 1, '\n');
			else
				sb.insert(i, '\n');
		}
		return sb.toString();
	}
	
	/**
	 * @summary repeats a string n amount of times
	 * @param {string} string to repeat
	 * @param {int} amount of times to repeat it
	 * @return {string} the resulting string
	 */
	public static String repeat(String string, int times) {
		// creates string of n "0's" and replaces them with given string
		return String.format("%0" + times + "d", 0).replace("0", string);
	}
}
