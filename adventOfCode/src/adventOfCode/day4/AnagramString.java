package adventOfCode.day4;

import java.util.Arrays;

/**
 * Wrapper class for a String. Its special powers reside in the modified equals() method, 
 * which checks whether the other AnagramString is an anagram of <code>this</code> and
 * returns true if that is the case. 
 * 
 * The functionality to check whether two Strings are anagrams of each other can also be
 * accessed directly by calling static method {@link #isAnagram(String, String)}.
 */
public class AnagramString {

	private String theString;
	
	public AnagramString(String theString) {
		this.theString = theString;
	}

	/**
	 * Check whether the given String is an anagram of {@link #theString}. The comparison only includes
	 * word characters of both Strings.
	 */
	public static boolean isAnagram(String theString, String other) {
		
		// Filter out all non-word characters and digits.
		other = other.replaceAll("\\W\\d\\_", "");
		theString = theString.replaceAll("\\W\\d\\_", "");
		
		// If the Strings do not contain an equal number of characters...
		if (other.length() != theString.length())
			// ...they can't be anagrams.
			return false;
		
		// Convert both Strings to lower case character arrays.
		char[] theseChars = theString.toLowerCase().toCharArray();
		char[] otherChars = other.toLowerCase().toCharArray();
		
		// Sort both arrays.
		Arrays.sort(theseChars);
		Arrays.sort(otherChars);
		
		// Walk through both sorted arrays.
		for (int i=0; i<theseChars.length; i++) {
			// Since the arrays are now alphabetically sorted, if at any point the characters don't match...
			if (theseChars[i] != otherChars[i])
				// ...that means that the two Strings are not anagrams of each other. 
				return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((theString == null) ? 0 : theString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// Perform generic comparative checks.
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnagramString other = (AnagramString) obj;
		if (theString == null) {
			if (other.theString != null)
				return false;
		}
		// Perform anagram check.
		return isAnagram(theString, other.theString);
	}	
}
