package adventOfCode;

import java.util.Arrays;

public class TwoDimGrid<T> {

	private T[][] grid;

	@SuppressWarnings("unchecked")
	public TwoDimGrid() {
		grid = (T[][]) new Object[1][1];
	}
	
	public TwoDimGrid(T[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Checks whether the given position is inside the playground's bounds. If not, increases the 
	 * size of the playground by one, i.e. adding one to each side.
	 * 
	 * @return {@code true} if playground was resized, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean resize(int[] position) {
		
		if (position[0] >= 0 && position[0] < grid.length
				&& position[1] >= 0 && position[1] < grid.length) {
			// Position is within playground bounds. No need to resize.
			return false;
		}
		// The given position falls outside playground bounds. We should resize.
		int newSize = grid.length + 2;
		int oldSize = grid.length;
		T[][] newGrid = (T[][]) new Object[newSize][newSize];
		// Copy the old playground grid onto the new one.
		for (int i=0; i<oldSize; i++) {
			// Make sure that the new playground is one square bigger on each side.
			System.arraycopy(grid[i], 0, newGrid[i+1], 1, oldSize);
		}
		// Set the resulting grid as the new playground.
		grid = newGrid;
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void rotateClockwise() {
		T[][] rotated = (T[][]) new Object[grid.length][grid.length];
		
		for (int i=0; i<grid.length; i++) {
			int innerArrayLength = rotated[i].length;
			for (int j=0; j<innerArrayLength; j++) {
				// Convert left-to-right to top-to-bottom and top-to-bottom to right-to-left.
				rotated[j][innerArrayLength - i - 1] = grid[i][j];
			}
		}
		// Set the rotated grid as the new grid.
		grid = rotated;
	}
	
	private boolean isInsideBounds(int[] position) {
		return position[0] < grid[0].length && position[1] < grid.length;
	}
	
	public void setAtPosition(T value, int[] position) {
		// Check if given position is inside grid bounds and resize if necessary.
		if (!isInsideBounds(position)) {
			resize(position);
		}
		grid[position[1]][position[0]] = value;
	}
	
	/**
	 * Returns the object at the specified position on the playground.
	 * @param position - an integer array of length two, with first integer representing the x axis  
	 * and the second the y axis.
	 */
	public T charAtPosition(int[] position) {
		if (!isInsideBounds(position)) {
			return null;
		}
		return grid[position[1]][position[0]];
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (T[] row : grid) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}	

	
}
