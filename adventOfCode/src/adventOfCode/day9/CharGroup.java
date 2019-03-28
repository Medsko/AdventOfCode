package adventOfCode.day9;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a closed group of characters that is encountered in a character stream.
 */
public class CharGroup {

	/** The characters in this group as a String. */
	private String characters = "";
	
	private List<CharGroup> subGroups;
	
	private CharGroup currentActiveSubGroup;
	
	public CharGroup(String characters) {
		this.characters = characters;
	}
	
	public CharGroup(char firstChar) {
		characters += firstChar;
		subGroups = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return a flag indicating whether this char group has been closed.
	 */
	public boolean addCharacter(char nextCharacter) {
		if (nextCharacter == '{')
			startNewSubGroup(nextCharacter);
		else if (nextCharacter == '}')
			return closeLowestActiveGroup(nextCharacter);
		else if (currentActiveSubGroup != null) {
			characters += nextCharacter;
			currentActiveSubGroup.addCharacter(nextCharacter);
		} else {
			// If this is the lowest active sub group and the character neither opens or closes
			// a sub group, just add it to the current group.
			characters += nextCharacter;
		}
		// If this is not a closing character, it will not close this char group.
		return false;
	}
	
	public void addGarbageCharacter(char garbageCharacter) {
		characters += garbageCharacter;
		if (currentActiveSubGroup != null)
			currentActiveSubGroup.addGarbageCharacter(garbageCharacter);
	}
	
	private void startNewSubGroup(char startingCharacter) {
		characters += startingCharacter;
		if (currentActiveSubGroup == null)
			currentActiveSubGroup = new CharGroup(startingCharacter);
		else
			currentActiveSubGroup.startNewSubGroup(startingCharacter);
	}
	
	/**
	 * Attempts to recursively close the lowest active group in this {@link CharacterGroup}.
	 * If this is the lowest active group, true is returned, so the caller knows this group
	 * is complete.
	 * 
	 * @return true if the provided character closes this group, false otherwise. 
	 */
	private boolean closeLowestActiveGroup(char endingCharacter) {
		characters += endingCharacter;
		if (currentActiveSubGroup == null) {
			return true;
		}
		if (currentActiveSubGroup.closeLowestActiveGroup(endingCharacter)) {
			// The currently active sub group of this group is closed by this character.
			// Add it to the list, then set the variable to null.
			subGroups.add(currentActiveSubGroup);
			// Log the sub group that we are closing before resetting the variable.
			System.out.println("Now closing sub group: " + currentActiveSubGroup);
			currentActiveSubGroup = null;
		}
		return false;
	}
	
	/**
	 * Calculates the score of this {@link CharGroup} by recursively calculating the score of
	 * all groups it holds. The score of a group is equal to that of the group that directly
	 * contains it plus one.
	 */
	public int calculateAndGetScore() {
		return calculateAndGetScore(0);
	}
	
	private int calculateAndGetScore(int containingGroupScore) {
		
		int groupScore = containingGroupScore + 1;
		// Initialize this group's total score to the score of this group.
		int totalScore = groupScore;
		
		if (subGroups == null)
			// If this group contains no other groups, return this group's score.
			return totalScore;
		
		for (CharGroup subGroup : subGroups) {
			// Recursive power activated - call this method for each sub group.
			totalScore += subGroup.calculateAndGetScore(groupScore);
		}
		return totalScore;
	}
	
	@Override
	public String toString() {
		return characters;
	} 
}
