package adventOfCode;

import java.util.HashMap;

/**
 * Represents a matrix that consists of values that start in the middle (position 0, 0)
 * and extend from there in a spiral. Can be used to generate a matrix which follows this
 * pattern. During this process, the value of the next square can be determined in different
 * ways, depending on the implementation of {@link #updateValues()}.
 * 
 * Positions and values are stored in a Map, as respectively integer arrays and integers.
 * 
 * Also contains several methods to print the resulting data structure.
 */
public class SpiralMatrix {

	// Constants to keep track of the direction.
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int LEFT = 3;
	public static final int DOWN = 4;
	public static final int FULL_CIRCLE = 5;
	
	// Variables that are only needed in this super class.
	
	/** Index to keep track of how many moves were made in current direction. */ 
	private int movesMadeInCurrentDirection = 0;

	/** Number of steps to take on the short side of the cycle (i.e. right and up). */
	private int maxStepsShortSide = 1;
	
	/** Keeps track of the direction we're currently moving in. */
	private int direction;
	
	// Variables that the sub class has to be able to directly manipulate.

	/** The puzzle input. */
	protected int input;
	
	/** The current position on the matrix. */
	protected int[] currentPosition;

	/** The value of the current square (only interesting for day 3b challenge). */
	protected int currentValue;
	
	/** The stored array (position)/Integer (value) value pairs of the current cycle. */ 
	protected HashMap<int[], Integer> currentCycle;
	
	/** The stored array (position)/Integer (value) value pairs of the previous cycle. */
	protected HashMap<int[], Integer> previousCycle;
	
	/** All value pairs accumulated throughout the entire process. */
	protected HashMap<int[], Integer> totalMatrix;
	
	/** In both scenarios, the process starts with the step from square one to square two, so this should initialize to one. */
	private int iterations = 1;

	/** Takes one step in the current direction, and changes direction afterwards if needed. */
	public void moveOneSquare() {
		
		// Move the position right (increase x) FOR maxStepsShortSide times.
		if (direction == RIGHT && movesMadeInCurrentDirection < maxStepsShortSide)
			currentPosition[0]++;
		
		// Move the position up (increase y) FOR maxStepsShortSide times
		if (direction == UP && movesMadeInCurrentDirection < maxStepsShortSide)
			currentPosition[1]++;
		
		// Move the position left (decrease x) FOR one more than maxStepsShortSide times.
		if (direction == LEFT && movesMadeInCurrentDirection < maxStepsShortSide + 1)
			currentPosition[0]--;
		
		// Move the position down (decrease y) FOR one more than maxStepsShortSide times.
		if (direction == DOWN && movesMadeInCurrentDirection < maxStepsShortSide + 1)
			currentPosition[1]--;
		
		// We've made our move (ugh). Increment the index keeping track.
		movesMadeInCurrentDirection++;
		
		// If we've reached maximum number of steps for the current side, change direction and reset the index.
		if ( (direction <= 2 && movesMadeInCurrentDirection == maxStepsShortSide) 
				|| (direction >= 3 && movesMadeInCurrentDirection == maxStepsShortSide + 1) ) {
			direction++;
			movesMadeInCurrentDirection = 0;
		}
		
		if (direction == FULL_CIRCLE) {
			// We've come full circle. Reset the direction to right.
			direction = RIGHT;
			// Increment the number of steps to take on each side.
			maxStepsShortSide += 2;
			
			// Store the position/value pairs of this cycle in the map representing the previous cycle and reset the current cycle.
			previousCycle = currentCycle;
			currentCycle = new HashMap<>();
		}
		
		// We've made exactly one move in the right direction! Increment the currentSquare index to celebrate.
		updateValues();
		
		// Store the current value and position.
		saveValueAtPostion();
		
		iterations++;
		
		//  Just for fun: log that shit.
		System.out.println("Moved onto square " + iterations + ". Value: " + currentValue + ". Current position: [" + currentPosition[0] + ", " + currentPosition[1] + "]");
	}
	
	/** Updates the value of the current square. */
	protected void updateValues() {
		// In this implementation, the value is only increased by 1 with each move.
		currentValue++;
	}
	
	/** Stores the value of the current square in the map representing the current cycle. */
	protected void saveValueAtPostion() {
		// Since an array is an object, make a deep copy of current position to a new array.
//		int[] previousPostion = new int[2];
//		previousPostion[0] = currentPosition[0];
//		previousPostion[1] = currentPosition[1];
//
//		// Store the current value in the 'current cycle' map, with the current position as key.
//		currentCycle.put(previousPostion, currentValue);
//		totalMatrix.put(previousPostion, currentValue);
		
		// Reset the current value to its starting value: 0.
//		startingValue = 0;
	}
	
}
