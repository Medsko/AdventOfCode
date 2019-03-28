package util;

public class StringUtil {

	// Ain't no-one gonna instantiate this baby!
	private StringUtil() {}
	
	/**
	 * Counts the number of occurrences of the target in the input String, ignoring case.
	 *  
	 * @param input - the String to search for occurrences.
	 * @param target - the target String to search for.
	 * @return the number of occurrences of the target in the given input. 
	 */
	public static int countOccurrences(String input, String target) {
		// Convert the input and target String to lower case.
		input = input.toLowerCase();
		target = target.toLowerCase();
		
		int nrOfOccurrences = 0;
		int index = 0;
		
		while ((index = input.indexOf(target, index)) != -1) {
			// Increment the index to prevent double hits.
			index++;
			nrOfOccurrences++;
		}
		return nrOfOccurrences;
	}
	
}
