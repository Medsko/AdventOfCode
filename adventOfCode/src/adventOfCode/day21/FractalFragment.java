package adventOfCode.day21;

import java.util.Arrays;

import util.ArrayUtil;

/**
 * A square fragment of the fractal. 
 */
public class FractalFragment {

	private char[][] grid;
	
	public FractalFragment(char[][] grid) {
		this.grid = grid;
	}

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		// Add together all characters in the arrays in the array array (gehehe). Amore specific 
		// hash code will lead to trouble, since we will be rotating the arrays in equals(). 
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				result += grid[i][j];
			}
		}
		result = prime * result;
		return result;
	}
	
	/**
	 * Determines the total number of pixels on this fragment that have been turned on ('#').
	 * @return the total number of 'on' pixels in this fragment.
	 */
	public int totalPixelsOn() {
		int total = 0;
		// Double-loop through all arrays in the array.
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				if (grid[i][j] == '#')
					// This pixel is on. Increase the total.
					total += 1;
			}
		}
		return total;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FractalFragment other = (FractalFragment) obj;
		// Do some low-hanging-fruit checks.
		if (grid.length != other.grid.length)
			return false;
		if (totalPixelsOn() != other.totalPixelsOn())
			return false;
		// Perform the expensive total comparison.
		return equalsFlipRotated(other);
	}
	
	/**
	 * Determines whether this {@link FractalFragment} is equal to the provided 
	 * {@link FractalFragment} when their grids are rotated and flipped.
	 */
	public boolean equalsFlipRotated(FractalFragment other) {
		if (Arrays.deepEquals(grid, other.grid))
			return true;
		char[][] flippedRotatedOther = ArrayUtil.rotate(other.grid);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.rotate(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.rotate(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.flip(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.rotate(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.rotate(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		flippedRotatedOther = ArrayUtil.rotate(flippedRotatedOther);
		if (Arrays.deepEquals(grid, flippedRotatedOther))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (char[] row : grid) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}
	
	public char[][] getGrid() {
		return grid;
	}
}
