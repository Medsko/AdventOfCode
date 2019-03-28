package aoc2018.day11;

public class ShrinkingPowerGrid {

	private int[][] powerGrid;

	private int unitSize;
	
	public ShrinkingPowerGrid(int unitSize, int parentSize) {
		this.unitSize = unitSize;
		int reducedSize = parentSize - (unitSize - 1);
		powerGrid = new int[reducedSize][reducedSize];
	}
	
	public void setUnitPowerAt(int power, int xPosition, int yPosition) {
		powerGrid[yPosition][xPosition] = power;
	}
	
	public int getUnitPowerAt(int xPosition, int yPosition) {
		return powerGrid[yPosition][xPosition];
	}

	public int getUnitSize() {
		return unitSize;
	}

	public int[][] getPowerGrid() {
		return powerGrid;
	}
}
