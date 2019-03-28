package aoc2018;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdaptiveCharGrid {

	private char[][] grid;
	
	private int[] smallestEntry;
	
	private Map<Character, int[]> nodePositions;
	
	/**
	 * Default constructor. Initializes a grid of 1 X 1.
	 */
	public AdaptiveCharGrid() {
		grid = new char[1][1];
		grid[0][0] = ' ';
		nodePositions = new HashMap<>();
		smallestEntry = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE};
	}
	
	/**
	 * Checks whether the given position is inside the grid's bounds. If not, increases the 
	 * size of the grid so the given position will fit into it exactly.
	 * 
	 * @return {@code true} if the grid was resized, {@code false} otherwise.
	 */
	private void resize(int[] position) {		
		int newY = grid.length;
		int newX = grid[0].length;
		
		if (position[1] >= grid.length) {
			newY = position[1] + 1;
		}
		
		if (position[0] >= grid[0].length) {
			newX = position[0] + 1;
		}
		
		char[][] newGrid = new char[newY][newX];
		for (int i=0; i<newGrid.length; i++) {
			for (int j=0; j<newGrid[0].length; j++) {
				newGrid[i][j] = ' ';
			}
		}
		
		// Copy the old playground grid onto the new one.
		for (int i=0; i<grid.length; i++) {
			System.arraycopy(grid[i], 0, newGrid[i], 0, grid[0].length);
		}
		
		// Set the resulting grid as the new playground.
		grid = newGrid;
	}

	/**
	 * Set the given character at the given position. If the given position is outside the bounds
	 * of the grid, it is resized to snugly fit the new outer coordinate.
	 */
	public void setAtPosition(char value, int[] position) {
		if (position[0] < smallestEntry[0]) {
			smallestEntry[0] = position[0];
		}
		if (position[1] < smallestEntry[1]) {
			smallestEntry[1] = position[1];
		}
		
		if (!isWithinBounds(position)) {
			resize(position);
		}
		grid[position[1]][position[0]] = value;
	}
	
	public void setNodeAtPosition(char node, int[] position) {
		nodePositions.put(node, position);
		setAtPosition(node, position);
	}
	
	/**
	 * Returns the character at the specified position on the grid. If the requested position is
	 * out of grid bounds, null is returned.
	 * 
	 * @param position - an integer array of length two, with first integer representing the x axis  
	 * and the second the y axis.
	 * @return the character at the given position, or {@code null} if position is out of bounds.
	 */
	public Character charAtPosition(int[] position) {
		if (isWithinBounds(position)) {
			return grid[position[1]][position[0]];
		} 
		return null;
	}
	
	private boolean isWithinBounds(int[] position) {
		return position[0] < grid[0].length && position[1] < grid.length;
	}
	
	public int getXLength() {
		return grid[0].length;
	}
	
	public int getYLength() {
		return grid.length;
	}
	
	public Set<Character> getNodeNames() {
		return nodePositions.keySet();
	}
	
	public int[] getNodePosition(char key) {
		return nodePositions.get(key);
	}

	/**
	 * Returns a String representation of this grid, resized so it snugly contains all fields for
	 * which a value has been set.  
	 */
	public String snugToString() {
		String toString = "";
		for (int i=smallestEntry[1]; i<grid.length; i++) {
			toString += "[ ";
			for (int j=smallestEntry[0]; j<grid[0].length; j++) {
				toString += grid[i][j] + ",";
			}
			toString = toString.substring(0, toString.lastIndexOf(','));
			toString += " ]" + System.lineSeparator();
		}
		return toString;
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (char[] row : grid) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}
}
