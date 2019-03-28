package adventOfCode.day3;

import util.StdFunctions;

public class SecondTaxicab extends ManhattanTaxicab {
	
	public SecondTaxicab(int input) {
		super(input);
		// Store the first value before taking the first step.
		currentCycle.put(new int[] {0, 0}, 1);
		totalMatrix.put(new int[] {0, 0}, 1);
	}
	
	
	/** Updates the value of the current square. */
	@Override
	protected void incrementValues() {
		
		int sumAdjacentValues = 0;
		// For each key in both maps, check whether both the x and y position differ one or less from the current position.
		// If the check is passed, increment the current value with the value at the stored position.
		for (int[] key : currentCycle.keySet()) {
			if (checkSquaresAdjacency(currentPosition, key))
				sumAdjacentValues += currentCycle.get(key);
		}
		
		for (int[] key : previousCycle.keySet()) {
			if (checkSquaresAdjacency(currentPosition, key))
				sumAdjacentValues += previousCycle.get(key);
		}
		// Set save the result in the variable that is used to determine whether the process is finished.
		currentValue = sumAdjacentValues;
	}
	
	/**
	 * Checks whether two given positions are adjacent. The absolute difference between both x positions
	 * and both y positions are calculated, then it is checked whether both differences are smaller than 
	 * or equal to 1.
	 * @return <code>true</code> if the two squares are adjacent, <code>false</code> otherwise
	 */
	private boolean checkSquaresAdjacency(int[] currentPosition, int[] storedPosition) {
		// Calculate the absolute differences for the x and y between the current and stored position.
		int XabsoluteDifference = StdFunctions.absoluteDifference(currentPosition[0], storedPosition[0]);
		int YabsoluteDifference = StdFunctions.absoluteDifference(currentPosition[1], storedPosition[1]);
		
		// Check whether both of these are equal to 1 or 0.
		return XabsoluteDifference <= 1 && YabsoluteDifference <= 1;
	}
	
	/** Stores the value of the current square in the map representing the current cycle. */
	@Override
	protected void saveValueAtPostion() {
		
		// Since an array is an object, make a deep copy of current position to a new array.
		int[] previousPostion = new int[2];
		previousPostion[0] = currentPosition[0];
		previousPostion[1] = currentPosition[1];

		// Store the current value in the 'current cycle' map, with the current position as key.
		currentCycle.put(previousPostion, currentValue);
		totalMatrix.put(previousPostion, currentValue);
	}
	
	public static void main(String[] args) {
		SecondTaxicab cab = new SecondTaxicab(368078);
		cab.moveToTarget();
	}

}
