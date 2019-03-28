package aoc2018.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc2018.AbstractAocAlgorithm;

public class ChronalCharger extends AbstractAocAlgorithm {

	private int[][] powerGrid;
		
	private int gridSerialNumber;
	
	private int highestPowerLevel;
	
	/**
	 * Since optimization for challenge B, this variable now contains gibberish.  
	 */
	private int[][] highestPowerUnit;
	
	private int[][] gridToIterate;
	
	private List<ShrinkingPowerGrid> previousGrids;
	
	private ShrinkingPowerGrid currentGrid;
	
	/**
	 * The coordinate of the left upper corner of the unit with the highest power level.
	 */
	private int[] highestPowerUnitPosition;
	
	public ChronalCharger(boolean isCommandLineFine, int gridSerialNumber) {
		super(isCommandLineFine);
		// Grid size is 300 square.
		powerGrid = new int[300][300];
		this.gridSerialNumber = gridSerialNumber;
		previousGrids = new ArrayList<>();
	}
	
	// Total time for challenge B with enhanced (but so far broken) algorithm: 50 seconds.
	// Without optimization (but yielding correct result still): 258 seconds
	
	/**
	 * Challenge b implementation. Loops through all supported square sizes (from 1x1 to 300x300)
	 * and identifies the square with the highest total power level.
	 */
	public void findHighestPowerUnitAnySize() {
		timer.startAlgorithm("ChronalCharger.findHighestPowerUnitAnySize()");
		for (int i=1; i<=300; i++) {
//			currentGrid = new ShrinkingPowerGrid(i, 300);
			findHighestPowerUnit(i);
//			previousGrids.add(currentGrid);
		}
		timer.stopAlgorithm();
	}

	
	/**
	 * Determines the cell power for each fuel cell in the grid.
	 */
	public void fillGrid() {
		timer.startAlgorithm("ChronalCharger.fillGrid()");
		// From top to bottom...
		for (int i=0; i<powerGrid.length; i++) {
			// ...and from left to right...
			for (int j=0; j<powerGrid[0].length; j++) {
				// ...calculate each cell's power level.
				powerGrid[i][j] = calculateCellPower(j, i);
			}
		}
		timer.stopAlgorithm();
	}
	
	public void findHighestPowerUnit(int squareSize) {

		gridToIterate = powerGrid;
		int powerUnitSize = squareSize;
		
		timer.startSubAlgorithm();
				
		if (previousGrids.size() != 0) {
			// Challenge B has been activated. Go backwards through the list of previously found
			// power units and try to find one that has a size of which the current power unit size
			// is a multitude.
			for (int i=previousGrids.size() - 1; i>=0; i--) {
				if (squareSize % previousGrids.get(i).getUnitSize() == 0) {
					// Found the highest divider. Use its grid to iterate over.
					gridToIterate = previousGrids.get(i).getPowerGrid();
					// Adjust the power unit size, so that it now represents the size of the greater
					// power unit in number of smaller power units (e.g. a 4x4 now consists of four
					// 2x2 units instead of sixteen 1x1 units).
					powerUnitSize = squareSize / previousGrids.get(i).getUnitSize();
					break;
				}
			}
		}

		// Loop through all complete squares of the given square size...
		for (int i=0; i<=powerGrid.length - squareSize; i++) {
			for (int j=0; j<=powerGrid[0].length - squareSize; j++) {
				// ...and check whether the power level of this unit is the greatest yet. 
				checkUnitPowerLevel(j, i, powerUnitSize);
			}
		}
		timer.stopSubAlgorithm();
	}
	
	private void checkUnitPowerLevel(int xPosition, int yPosition, int powerUnitSize) {
		int unitPowerLevel = 0;
		int[][] powerUnit = new int[powerUnitSize][powerUnitSize];
		
		for (int i=0; i<powerUnitSize; i++) {
			for (int j=0; j<powerUnitSize; j++) {				
				int cellPower = gridToIterate[yPosition + j][xPosition + i];
				powerUnit[j][i] = cellPower;
				unitPowerLevel += cellPower;
			}
		}

		if (currentGrid != null) {
			// Challenge B activated. Set the total power level of this unit on the unit grid.
			currentGrid.setUnitPowerAt(unitPowerLevel, xPosition, yPosition);
		}
		
		if (unitPowerLevel > highestPowerLevel) {
			// This is the highest power level yet. Set the power level, the unit representation 
			// and the left upper position as the new highest.
			highestPowerLevel = unitPowerLevel;
			highestPowerUnit = powerUnit;
			highestPowerUnitPosition = new int[] { xPosition, yPosition, powerUnitSize };
		}
	}
	
	private int calculateCellPower(int xPosition, int yPosition) {
		// The rack id of each cell is it's x position plus ten.
		int rackId = xPosition + 10;
		// Power level starts at rack id times y coordinate.
		long calculatingCellPower = rackId * yPosition;
		// Add the grid serial number.
		calculatingCellPower += gridSerialNumber;
		// Multiply by the rack id.
		calculatingCellPower *= rackId;
		// Keep only the hundreds digit (e.g. '3' in 12345).
		String cellPowerString = Long.toString(calculatingCellPower); 
		char hundredsDigit = cellPowerString.charAt(cellPowerString.length() - 3);
		int cellPower = Character.digit(hundredsDigit, 10);
		// Subtract five to determine the actual cell power.
		return cellPower - 5;
	}
	
	public int getPowerLevelAt(int xPosition, int yPosition) {
		return powerGrid[yPosition][xPosition];
	}
	
	public int getHighestPowerLevel() {
		return highestPowerLevel;
	}

	public int[] getHighestPowerUnitPosition() {
		return highestPowerUnitPosition;
	}

	public int[][] getHighestPowerUnit() {
		return highestPowerUnit;
	}

	public String gridToString(int[][] grid) {
		String toString = "";
		for (int[] row : grid) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}
}
