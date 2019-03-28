package adventOfCode.day3;

import java.util.HashMap;

import util.Util;

public class ManhattanTaxicab {

	// Constants to keep track of the direction.
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int LEFT = 3;
	public static final int DOWN = 4;
	public static final int FULL_CIRCLE = 5;

	// Variables that are only needed in this super class.
	
	/** Index to keep track of how many moves were made in current direction. */ 
	private int movesMadeInCurrentDirection = 0;

	/** Flag to keep track of which side we're on (omg these are getting worse). */
	private boolean onTheShortSide = true;
	
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
	
	/** Helper to print the resulting matrix. */
	private SpiralMatrixPrintHelper printHelper = new SpiralMatrixPrintHelper();
	
	public ManhattanTaxicab(int input) {
		
		this.input = input;
		// Conform example, spiral starts moving in the right direction (heheh) / starts off going right.
		direction = RIGHT;
		
		// Start at position 0,0 with value/square one.
		currentPosition = new int[] {0,0};
		currentValue = 1;
		
		currentCycle = new HashMap<>();
		previousCycle = new HashMap<>();
		totalMatrix = new HashMap<>();
		
		// Set the starting position and value in the result Map.
		totalMatrix.put(currentPosition, currentValue);
	}
	
	// TODO: bonus points are awarded for making the algorithm more efficient, i.e. ordering the position/key arrays
	// in a way that decreases iteration length.
	
	public void moveToTarget() {
		
		while (currentValue < input) {
			moveOneSquare();
		}
		
		Util.underline();
		
		printHelper.printMatrix(totalMatrix);
	}
		
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
		if ( (onTheShortSide && movesMadeInCurrentDirection == maxStepsShortSide) || (!onTheShortSide && movesMadeInCurrentDirection == maxStepsShortSide + 1) ) {
			direction++;
			movesMadeInCurrentDirection = 0;
			// If we are going to start moving left now, we are no longer on the short side of the cycle.
			if (direction == LEFT)
				onTheShortSide = false;
		}
		
		if (direction == FULL_CIRCLE) {
			// We've come full circle. Reset the direction to right.
			direction = RIGHT;
			// Increment the number of steps to take on each side.
			maxStepsShortSide += 2;
			// Since we are just starting to go right again, we are back on the short side.
			onTheShortSide = true;
			
			// Store the position/value pairs of this cycle in the map representing the previous cycle and reset the current cycle.
			previousCycle = currentCycle;
			currentCycle = new HashMap<>();
		}
		
		// We've made exactly one move in the right direction! Increment the currentSquare index to celebrate.
		incrementValues();
		
		// Store the current value and position.
		saveValueAtPostion();
		
		iterations++;
		
		//  Just for fun: log that shit.
		System.out.println("Moved onto square " + iterations + ". Value: " + currentValue + ". Current position: [" + currentPosition[0] + ", " + currentPosition[1] + "]");
	}

	/** Updates the value of the current square. */
	protected void incrementValues() {
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
	
	public void reset() {
		// Start at position 0,0 with value/square one.
		currentPosition = new int[] {0,0};
		currentValue = 1;
		
		currentCycle = new HashMap<>();
		previousCycle = new HashMap<>();
		totalMatrix = new HashMap<>();
	}
	
	public void setInput(int input) {
		this.input = input;
	}	
}
