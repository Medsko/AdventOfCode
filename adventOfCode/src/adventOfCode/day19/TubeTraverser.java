package adventOfCode.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adventOfCode.Direction.FourWay;

public class TubeTraverser {

	private TubeDiagram tubeDiagram;
	
	private List<Character> encounteredLetters;
	
	private int[] currentPosition;
	
	private FourWay currentDirection;
	
	public TubeTraverser(TubeDiagram tubeDiagram) {
		this.tubeDiagram = tubeDiagram;
	}
	
	// Answer challenge a: NDWHOYRUEA (in one try! (after initial debugging))
	
	// Challenge b first try: 17530 (too low).
	
	/** Determines the starting values. */
	public void initialize() {
		currentPosition = new int[2];
		// Determine and set the starting x index.
		currentPosition[0] = tubeDiagram.determineStartingX();
		currentPosition[1] = 0;
		currentDirection = FourWay.DOWN;
		encounteredLetters = new ArrayList<>();
	}
	
	public char readNextCharacter() {
		currentDirection.apply(currentPosition);
		return tubeDiagram.getCharAtPosition(currentPosition[0], currentPosition[1]);
	}
	
	public boolean processCharacter(char currentChar) {
		
		switch(currentChar) {
			case ' ': 
			// If a whitespace character has been read, we have reached the end of the challenge. 
				return false;
			case '+':
			// A plus sign indicates a change in direction.
				determineNewDirection();
				return true;
			case '|':
			case '-':
			// The straight pipe characters do not influence the direction, no processing required.
				return true;
			default:
				// A letter has been read. Add it to the list.
				encounteredLetters.add(currentChar);
				return true;
		}
	}
	
	private void determineNewDirection() {
		
		if (currentDirection.isVertical()) {
			// We are going to switch the direction to horizontal. Check if left is a valid option.
			char left = tubeDiagram.getCharAtPosition(currentPosition[0] - 1, currentPosition[1]);			
			if (left != ' ') {
				currentDirection = FourWay.LEFT;
			} else {
				currentDirection = FourWay.RIGHT;
			}
		} else {
			// We are going to switch the direction to vertical. Check if up is a valid option.
			char up = tubeDiagram.getCharAtPosition(currentPosition[0], currentPosition[1] +1);
			if (up != ' ') {
				currentDirection = FourWay.UP;
			} else {
				currentDirection = FourWay.DOWN;
			}
		}
	}
	
	public String getEncounteredLetterString() {
		String encounteredLettersString = "";
		for (char c : encounteredLetters)
			encounteredLettersString += c;
		return encounteredLettersString;
	}
	
	@Override
	public String toString() {
		String toString = "";
		toString += "current position: " + Arrays.toString(currentPosition);
		toString += ", character at current position: " 
				+ tubeDiagram.getCharAtPosition(currentPosition);
		return toString;
	}
}
