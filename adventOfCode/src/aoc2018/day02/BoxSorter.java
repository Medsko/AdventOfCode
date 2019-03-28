package aoc2018.day02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc2018.AbstractAocAlgorithm;

public class BoxSorter extends AbstractAocAlgorithm {

	private List<String> boxIds;

	/** The number of times a certain letter occurs two times in one box id. */
	private int timesTwo;

	/** The number of times a certain letter occurs three times in one box id. */
	private int timesThree;
	

	private int indexLastDifferentChar;
	
	private String commonLetters;
	
	// First try: 15528 (too high).
	
	public BoxSorter(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public boolean initialize() {
		if (!super.initialize(2018, 2)) {
			return false;
		}
		boxIds = fetcher.getInputLines();
		timesTwo = 0;
		timesThree = 0;
		
		return true;
	}
	
	public void calculateChecksum() {
	
		for (String boxId : boxIds) {
			Map<Character, Integer> occurrences = new HashMap<>();
			char[] boxIdLetters = boxId.toCharArray();
			
			for (char letter : boxIdLetters) {
				if (occurrences.containsKey(letter)) {
					occurrences.put(letter, occurrences.get(letter) + 1);
				} else {
					occurrences.put(letter, 1);
				}
			}
			
			boolean countsForTwo = false;
			boolean countsForThree = false;
			
			for (int value : occurrences.values()) {
				if (!countsForTwo && value == 2) {
					countsForTwo = true;
					timesTwo++;
				} else if (!countsForThree && value == 3) {
					countsForThree = true;
					timesThree++;
				}
				if (countsForTwo && countsForThree) {
					break;
				}
			}
		}
	}
	
	public void findCommonality() {
		for (int i=0; i<boxIds.size(); i++) {
			
			char[] first = boxIds.get(i).toCharArray();
			
			for (int j=i+1; j<boxIds.size(); j++) {
				
				char[] second = boxIds.get(j).toCharArray();
				
				if (differOneLetter(first, second)) {
					// Found the two correct boxes!
					commonLetters = boxIds.get(i).substring(0, indexLastDifferentChar);
					commonLetters += boxIds.get(i).substring(indexLastDifferentChar + 1);
					return;
				}
			}
		}
	}
	
	private boolean differOneLetter(char[] first, char[] second) {
		int differences = 0;
		for (int i=0; i<first.length; i++) {
			if (first[i] != second[i]) {
				indexLastDifferentChar = i;
				differences++;
			}
		}
		return differences == 1;
	}
	
	public int getChecksum() {
		return timesTwo * timesThree;
	}
	
	public String getCommonLetters() {
		return commonLetters;
	}
	
}
