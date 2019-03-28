package util;

import java.util.ArrayList;

public class Util {

	
	public static int calculateCheckSumOfRow(char[] currentRowAsChars) {
		
		int highestNumber = 0;
		int lowestNumber = 10;
		
		for (char c : currentRowAsChars) {
			
			int actualFuckingIntValueOfChar = Character.getNumericValue(c);
			
			if (actualFuckingIntValueOfChar < 0) {
				return 0;
			}
			
			if (actualFuckingIntValueOfChar > highestNumber) {
				highestNumber = actualFuckingIntValueOfChar;
			}
			
			if (actualFuckingIntValueOfChar < lowestNumber) {
				lowestNumber = actualFuckingIntValueOfChar;
			}
		}
		
		return highestNumber - lowestNumber;
	}
	
	public static int calculateCheckSumOfRow(ArrayList<Character> currentRowAsChars) {
		
		int highestNumber = 0;
		int lowestNumber = 10;
		
		for (char c : currentRowAsChars) {
			
			int actualFuckingIntValueOfChar = Character.getNumericValue(c);
			
			if (actualFuckingIntValueOfChar < 0) {
				continue;
			}
			
			if (actualFuckingIntValueOfChar > highestNumber) {
				highestNumber = actualFuckingIntValueOfChar;
			}
			
			if (actualFuckingIntValueOfChar < lowestNumber) {
				lowestNumber = actualFuckingIntValueOfChar;
			}
		}
		
		return highestNumber - lowestNumber;
	}

	public static int calculateCheckSumOfRow(int[] currentRowAsChars) {
		
		int highestNumber = currentRowAsChars[0];
		int lowestNumber = currentRowAsChars[0];
		
		for (int c : currentRowAsChars) {
			
			if (c > highestNumber) {
				highestNumber = c;
			}
			
			if (c < lowestNumber) {
				lowestNumber = c;
			}
		}
		
		return highestNumber - lowestNumber;
	}
	
	/** Convenience method to print a line of hyphens with a default width of 50. */
	public static void underline() {
		underline(50);
	}
	
	public static String line(int width) {
		String lineSeparator = "";
		for (int i=0; i<width; i++) 
			lineSeparator += "-";
		return lineSeparator;
	}
	
	/** 
	 * Convenience method to print a line of hyphens. Useful to separate results.
	 * @param width - the number of hyphens to print. 
	 */
	public static void underline(int width) {
		String lineSeparator = "";
		for (int i=0; i<width; i++) 
			lineSeparator += "-";
		System.out.println(lineSeparator);
	}

	
}
