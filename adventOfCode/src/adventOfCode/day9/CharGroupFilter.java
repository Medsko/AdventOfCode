package adventOfCode.day9;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters a character stream, storing all valid groups of characters. 
 */
public class CharGroupFilter {
	
	/** The input character stream as a character array. */
	private char[] charArray;
	
	private List<CharGroup> charGroups;
	
	private List<String> garbageGroups;
	
	private boolean inCharGroup = false;
	private boolean inGarbage = false;
	
	private String currentGarbage = "";
	
	private boolean shouldSkipNextChar = false;
	
	private CharGroup currentCharGroup;

	private boolean isForB;

	/** The index of the character that is currently being processed. */
	private int index = 0;
	
	/**
	 * Constructor to create a reusable {@link CharGroupFilter}. 
	 */
	public CharGroupFilter(boolean isForB) {
		this.isForB = isForB;
	}
		
	/**
	 * Central method. Filters the provide character stream, saving the encountered groups into
	 * the {@link #charGroups} list. 
	 */
	public void filter(String charStream) {
		// (Re)set the input and output variables.
		charArray = charStream.toCharArray();
		charGroups = new ArrayList<>();
		garbageGroups = new ArrayList<>();
		
		// Initialize a loop that continues until the entire input string is processed.
		while (index < charArray.length) {
			
			char currentChar = charArray[index];
			// Process the character at the current index.
			if (inGarbage)
				processGarbageChar(currentChar);
			else if (inCharGroup)
				processGroupChar(currentChar);
			else
				processCharacter(currentChar);
			
			// No use to keep processing the same character, is it now?
			index++;
		}
	}
	
	private void processGarbageChar(char currentChar) {
		// Check if we should ignore the next character.
		if (currentChar == '!') {
			if (isForB)
				// If this is for the second problem of this day, skip this and the next character.
				index++;
			// Ignore the escaping character and the character that follows.
			shouldSkipNextChar = true;
		} else {
			// Add this character to the current garbage group.
			currentGarbage += currentChar;	
		}
		// If this is garbage inside a character group, add it to the currently active group.
		if (inCharGroup && !isForB)
			currentCharGroup.addGarbageCharacter(currentChar);
		
		if (!isForB && shouldSkipNextChar) {
			// Reset the variable and skip further processing.
			shouldSkipNextChar = false;
			return;
		}
		if (currentChar == '>') {
			// This is the end of the current garbage group. Save, then reset the current
			// garbage group, and set 'inGarbage' to false.
			System.out.println("Saving current garbage group: " + currentGarbage);
			garbageGroups.add(currentGarbage);
			currentGarbage = "";
			inGarbage = false;
		}
	}
	
	private void processGroupChar(char currentChar) {
		// Check whether we are entering a garbage group inside this character group.
		if (currentChar == '<') {
			// This is the start of a new garbage group.
			inGarbage = true;
			currentGarbage += currentChar;
		}
		// Add this character to the current character group.
		if (currentCharGroup.addCharacter(currentChar)) {
			// The method returned true, meaning the current (super) CharGroup has been closed.
			// Add it to the list and reset the flag to show we have left a character group.
			charGroups.add(currentCharGroup);
			inCharGroup = false;
		}
	}
	
	private void processCharacter(char currentChar) {		
		if (currentChar == '{') {
			// This is the start of a new group.
			currentCharGroup = new CharGroup(currentChar);
			inCharGroup = true;
		} else if (currentChar == '<') {
			// This is the start of a new garbage group.
			inGarbage = true;
			currentGarbage += currentChar;
		}
	}

	public List<CharGroup> getCharGroups() {
		return charGroups;
	}

	public List<String> getGarbageGroups() {
		return garbageGroups;
	}
}
