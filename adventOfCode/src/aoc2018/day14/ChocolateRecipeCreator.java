package aoc2018.day14;

import java.util.ArrayList;
import java.util.List;

import aoc2018.AAoCA;

public class ChocolateRecipeCreator extends AAoCA {

	private int puzzleInput;
	
	private String puzzleInputString;
	
	/**
	 * Holds the indices of the elves that are combining recipes. 
	 */
	private int[] elves;
	
	private List<Integer> recipes;
	
	private int nrOfRecipesToTheLeft;
	
	public ChocolateRecipeCreator(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	// Challenge B first try: 20471523 (too high).

	@Override
	public boolean initialize(boolean isTest) {
		this.isTest = isTest;
		if (isChallengeA)
			puzzleInput = isTest ? 2018 : 286051;
		else {
			puzzleInputString = isTest ? "5941429882" : "286051";
		}
		elves = new int[]{ 0 , 1 };
		recipes = new ArrayList<>();
		recipes.add(3);
		recipes.add(7);
		return true;
	}

	@Override
	public boolean run() {
		// For challenge A, run loop until we can determine the ten recipes after the one specified
		// in the puzzle input. For challenge B, run until loop is broken because target sequence
		// can be matched.
		while (!isChallengeA || recipes.size() < puzzleInput + 10) {
			
			createRecipe();
			moveElves();
			
			if (recipes.size() >= puzzleInputString.length() && matchSequence()) {
				printableAnswer = "Number of recipes to the left of target score sequence: " 
						+ nrOfRecipesToTheLeft;
				return true;
			}
		}
		printableAnswer = "Next ten recipes after " + puzzleInput + " recipes: ";
		for (int i=0; i<10; i++) {
			printableAnswer += recipes.get(puzzleInput + i);
		}
		
		return true;
	}
	
	/**
	 * Attempts to match the last x recipes to the target sequence, where x is the length
	 * of the target sequence. If there are enough recipes available, this search is then also
	 * performed on the x recipes from one index further to the left, to account for the 
	 * possibility of two recipes being created in one round.
	 * @return {@code true} if the target sequence could be matched.
	 */
	private boolean matchSequence() {
		String recipeSequence = "";
		int inputLength = puzzleInputString.length();
		for (int i=recipes.size()-inputLength; i<recipes.size(); i++) {
			recipeSequence += recipes.get(i);
		}

		if (recipeSequence.equals(puzzleInputString)) {
			nrOfRecipesToTheLeft = recipes.size() - inputLength;
			return true;
		}
		
		if (recipes.size() < inputLength + 1) {
			// If the total number of recipes is exactly the same as the length of the target 
			// sequence, we won't be able to scan one further back. No match this round.
			return false;
		}
		
		recipeSequence = "";
		for (int i=recipes.size()-inputLength-1; i<recipes.size()-1; i++) {
			recipeSequence += recipes.get(i);
		}
		
		if (recipeSequence.equals(puzzleInputString)) {
			nrOfRecipesToTheLeft = recipes.size() - inputLength - 1;
			return true;
		}
		
		return false;
	}
	
	private void createRecipe() {
		
		int newRecipe = recipes.get(elves[0]);
		newRecipe += recipes.get(elves[1]);
		
		if (newRecipe >= 10) {
			String recipeString = "" + newRecipe;
			String firstNewRecipe = recipeString.substring(0, 1);
			String secondNewRecipe = recipeString.substring(1, 2);
			// Add the new recipe to the list of recipes.
			recipes.add(Integer.parseInt(firstNewRecipe));
			// Add the second new recipe to the list of recipes.
			recipes.add(Integer.parseInt(secondNewRecipe));
			
			logForTest("Two new recipes created at once: " 
					+ firstNewRecipe + " and " + secondNewRecipe);
		} else {
			recipes.add(newRecipe);
		}
	}
	
	private void moveElves() {
		for (int i=0; i<elves.length; i++) {
			// The new index is equal to the current index...
			int newIndex = elves[i];
			// ...plus the score of the recipe at the elf's index, plus one.
			newIndex += recipes.get(elves[i]) + 1;
			// Make sure the new index is not out of range of the list.
			while (newIndex >= recipes.size())
				newIndex = newIndex - recipes.size();
			elves[i] = newIndex;
		}
	}
}
