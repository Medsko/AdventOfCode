package adventOfCode.day22;

import java.util.Arrays;


public class ViralPlayground {

	private char[][] playground;

	// TODO: this might be reusable. Refactor this class and its variables to reflect this.
	
	public ViralPlayground(char[][] playground) {
		this.playground = playground;
	}
	
	/**
	 * Checks whether the given position is inside the playground's bounds. If not, increases the 
	 * size of the playground by one, i.e. adding one to each side.
	 * 
	 * @return {@code true} if playground was resized, {@code false} otherwise.
	 */
	public boolean resize(int[] position) {
		
		if (position[0] >= 0 && position[0] < playground.length
				&& position[1] >= 0 && position[1] < playground.length) {
			// Position is within playground bounds. No need to resize.
			return false;
		}
		// The given position falls outside playground bounds. We should resize.
		int newSize = playground.length + 2;
		int oldSize = playground.length;
		char[][] newGrid = new char[newSize][newSize];
		// Copy the old playground grid onto the new one.
		for (int i=0; i<oldSize; i++) {
			// Make sure that the new playground is one square bigger on each side.
			System.arraycopy(playground[i], 0, newGrid[i+1], 1, oldSize);
		}
		// Set the resulting grid as the new playground.
		playground = newGrid;
		
		return true;
	}
	
	public void setCharAtPosition(char character, int[] position) {
		playground[position[1]][position[0]] = character;
	}
	
	/**
	 * Returns the character at the specified position on the playground.
	 * @param position - an integer array of length two, with first integer representing the x axis  
	 * and the second the y axis.
	 */
	public char charAtPosition(int[] position) {
		return playground[position[1]][position[0]];
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (char[] row : playground) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}	
}
