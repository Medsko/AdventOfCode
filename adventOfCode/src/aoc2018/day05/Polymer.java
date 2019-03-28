package aoc2018.day05;

import aoc2018.AbstractAocAlgorithm;
import aoc2018.AocInputFetcher;

public class Polymer extends AbstractAocAlgorithm {

	private char[] units;

	private char mostBlocking;
	
	// First try A: 11408 (too low).
	
	public Polymer(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public boolean initialize() {
		
		fetcher = new AocInputFetcher(2018, false);
		if (!fetcher.fetchInput(5)) {
			return false;
		}
		
		String total = "";
		for (String line : fetcher.getInputLines()) {
			total += line;
		}
		units = total.toCharArray();
		
		return true;
	}
	
	public int reduce() {
		return reduce(units).length;
	}
	
	public int findShortestPolymerAfterRemovingOneType() {
		
		// First reduce the polymer without removing any blocking units.
		char[] reducedPolymer = reduce(units);
		
		int shortestPolymerLength = reducedPolymer.length;
		
		timer.startAlgorithm("Polymer.findShortestPolymerAfterRemovingOneType()");

		// For each letter of the alphabet...
		for (int i=0; i<26; i++) {
			timer.startSubAlgorithm();
			// ...prepare a polymer from which that letter has been removed.
			String subPolymer = "";
			
			char toRemove = (char) ('a' + i);
			subPolymer = new String(reducedPolymer).replace("" + toRemove, "");
			subPolymer = subPolymer.replace("" + Character.toUpperCase(toRemove), "");

			int polymerLengthAfterReactions = reduce(subPolymer.toCharArray()).length;

			if (polymerLengthAfterReactions < shortestPolymerLength) {
				shortestPolymerLength = polymerLengthAfterReactions;
				mostBlocking = (char) ('a' + i);
			}
			timer.stopSubAlgorithm();
		}
		timer.stopAlgorithm();

		System.out.println("Most blocking type: " + mostBlocking);
		
		return shortestPolymerLength;
	}
	
	private char[] reduce(char[] polymer) {

		for (int i=1; i<polymer.length; i++) {
			
			if (arePolars(polymer[i - 1], polymer[i])) {				
				// Remove the units at the given indices.
				char[] temp = new char[polymer.length - 2];
				System.arraycopy(polymer, 0, temp, 0, i - 1);
				System.arraycopy(polymer, i + 1, temp, i - 1, polymer.length - i - 1);
				polymer = temp;
				// Reset the index and range variables.
				i = 0;
			}
		}
		
		return polymer;
	}
	
	private boolean arePolars(char a, char b) {
		return (Character.isLowerCase(a) && Character.toUpperCase(a) == b) 
				|| (Character.isLowerCase(b) && Character.toUpperCase(b) == a);
	}
	
	public char[] getUnits() {
		return units;
	}	
}
