package adventOfCode.day3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import adventOfCode.SpiralMatrix;
import util.MapSortingUtil;

/**
 * This helper contains methods that facilitate printing out a {@link SpiralMatrix} in a
 * neat and readable way.
 */
public class SpiralMatrixPrintHelper {

	/** The length of the longest row. */
	private int maxRowLength;
	
	/** Holds the direction of the last move, conform SpiralMatrix.direction constants. */
	private int directionLastMove;
	
	/** The number of digits in the highest value in the matrix. */
	private int digitsInMaxValue;
	
	/** The format used to print a field. The number of spaces included is based on the length of the highest value. */
	private String fieldFormat;
	
	/** Number of line breaks that should trail each printed row. */
	private int lineBreaksPerRow;
	
	/** The number of positions on each row. */
	private ArrayList<Integer> positionsPerRow;

	protected void printMatrix(Map<int[], Integer> matrix) {
		
		// Sort the map representing the matrix in such a way that the y axis is ordered from lowest to highest - so 
		// the top row can easily be printed first - and the x axis from highest to lowest.
		TreeMap<int[], Integer> sorted = MapSortingUtil.sortMap(matrix, new MapSortingUtil.SpiralMatrixComparator());

		// Calculate the number of rows and the number of positions per row, and set the current row variable to the
		// value of the lowest value for the y axis.
		int yValueForThisRow = determineProcessingVariables(sorted);
		
		int rowIndex = 0;
		
		String anotherLine = "";
		
		for (int[] key : sorted.keySet()) {
			
			if (yValueForThisRow > key[1]) {
				// If the y position of the current key is lower than that of the current row, we've reached the next
				// row. Print it to the console... and update the row number.
				prettyPrintLine(anotherLine, positionsPerRow.get(rowIndex));
				// ...empty the line String variable...
				anotherLine = "";
				// ...update value of the y axis to that of the next row...
				yValueForThisRow = key[1];
				// ...and let the row index show that we've moved on to the next row.
				rowIndex++;
			}
			
			anotherLine += sorted.get(key) + " ";
		}
		prettyPrintLine(anotherLine, positionsPerRow.get(rowIndex));
		
	}
	
	/**
	 * Helper method to {@link #printResultMatrix()}. Prints a line of positions separated by spaces, formatted in
	 * rows of equal length. 
	 */
	protected void prettyPrintLine(String line, int rowLength) {
		String[] tokens = line.split(" ");
		
		String lineFormat = "";
		
		switch(directionLastMove) {
			
			case SpiralMatrix.RIGHT:
				// If the direction of the last move was right, the upper side of the matrix might be incomplete.
				// Since we're printing from left to right, this row will be printed correctly either way.
				break;
			case SpiralMatrix.UP:
				// If the last direction was up, the right side might be incomplete for one or more rows.
				// We can just stop printing when there are no more tokens.
				break;
			case SpiralMatrix.LEFT:
				// If the last direction was left, the lower side might be incomplete. To ensure the last row
				// is printed correctly, the last line should lead with (maxRowLength - rowLength) empty positions.
				// This can be handled by the case for direction == down (since only last row is shorter). 
			case SpiralMatrix.DOWN:
				// If the last direction was down, the left side might be incomplete. Account for this by adding
				// (maxRowLength - rowLength) empty fields at the start of the line.
				for (int i=0; i<(maxRowLength - rowLength) * (digitsInMaxValue + 2); i++) {
					lineFormat += " ";
				}
		}
		// Add one placeholder for each field in this line.
		for (int i=0; i<tokens.length; i++) {
			lineFormat += fieldFormat;
		}
		// Add the determined number of line breaks to ensure consistent vertical positioning.
		for (int i=0; i<lineBreaksPerRow; i++) {
			lineFormat += System.lineSeparator();
		}
		
		System.out.printf(lineFormat, (Object[]) tokens);
	}
	
	// TODO: split this method in a part that determines the fields per row (using sorted matrix)
	// TODO: and a part that determines the direction of the last move and the number of digits in the highest value
	// TODO: (or, for easy mode, determine these last two values in the taxicab-classes and pass them to printMatrix())
	private int determineProcessingVariables(Map<int[], Integer> sortedMatrix) {
		
		// Initialize the variable in which we will store the number of positions in the row to zero.
		Integer positionsInRow = 0;
		positionsPerRow = new ArrayList<>();
		
		// Variables used to determine the direction of the last move.
		int entryIndex = 0;
		int[] secondLastPosition = new int[2];
		
		// Set the row number (representing y axis) to that of the first position in the map, which should 
		// be the highest integer value for y in the 'positions' key set. Save the value, so it can be returned
		// to be used in printMatrix().
		int currentRow = Collections.min(sortedMatrix.keySet(), new MapSortingUtil.SpiralMatrixComparator())[1];
		int firstRow = currentRow;
		
		for (int[] key : sortedMatrix.keySet()) {

			// Count the field towards the total of the current row.

			if (currentRow > key[1]) {
				// If the y position of the current key is lower than that of the current row, we've reached the next
				// row. Save the number of positions for this row, reset the variable and update the row number.
				positionsPerRow.add(positionsInRow);
				positionsInRow = 0;
				currentRow = key[1];
			}
			
			positionsInRow++;

			if (entryIndex == sortedMatrix.size() - 2) {
				// We've reached the second last entry. Save the key so we can determine the direction of the last
				// move when the last entry comes around on the next iteration.
				secondLastPosition = key;
			}
			
			if (entryIndex == sortedMatrix.size() -1) {
				// TODO: this logic should use the unsorted version of the matrix-map. Right now, it uses the last
				// TODO: two fields in the last row, instead of the last two in the spiral (the two highest values)
				
				// We've reached the last entry. Determine the direction of the last move by evaluating the last and
				// second last position.
				directionLastMove = determineDirectionLastMove(secondLastPosition, key);
				// Determine the length of the highest value, so a sane number of spaces can be determined for use
				// in the printing format for a field.
				digitsInMaxValue = Collections.max(sortedMatrix.values()).toString().length();
				// Determine how much white space should be included when printing a field. Add two so the longest
				// numbers are also clearly separated.
				fieldFormat = "%" + (digitsInMaxValue + 2) + "s";
				// For every four digits in length of the highest value, include another line break. Minimum is one.
				lineBreaksPerRow = digitsInMaxValue / 4 + 1; 
			}
			// Since we are moving on to the next entry, increment the index.
			entryIndex++;
		}
		// Add the length of the last row to the list.
		positionsPerRow.add(positionsInRow);

		// We've reached the end of the spiral matrix. Determine the length of the longest row.
		maxRowLength = Collections.max(positionsPerRow);
		
		return firstRow;
	}
	
	/**
	 * Determines the direction of the last move, given the last and second last positions.
	 * @return the direction of the last move as an integer, conform the SpiralMatrix.directions constants.  
	 */
	private int determineDirectionLastMove(int[] secondLastPosition, int[] lastPosition) {
		
		int direction;
		
		if (secondLastPosition[0] < lastPosition[0] && secondLastPosition[1] == lastPosition[1])
			// If the x of the last position is smaller than second last, and both y's are equal,
			// the direction of the last move was right.
			direction = SpiralMatrix.RIGHT;
		else if (secondLastPosition[0] == lastPosition[0] && secondLastPosition[1] < lastPosition[1])
			// If the x's are equal, but the y increased, direction was up.
			direction = SpiralMatrix.UP;
		else if (secondLastPosition[0] > lastPosition[0] && secondLastPosition[1] == lastPosition[1])
			// If the y's are equal, but x decreased, direction was left.
			direction = SpiralMatrix.LEFT;
		else
			// Otherwise, the direction was down (x's are equal, y decreased).
			direction = SpiralMatrix.DOWN;
		
		return direction;
	}	
}
