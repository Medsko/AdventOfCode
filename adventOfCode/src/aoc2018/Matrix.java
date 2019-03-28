package aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.MatrixUtil;

public class Matrix<T> {
	
	private T[][] matrix;
	
//	Indicates whether the matrix will be automatically resized whenever a field that is currently
//	outside the bounds of the matrix is set.
	private boolean autoResize;
	
	@SuppressWarnings("unchecked")
	public Matrix() {
		matrix = (T[][]) new Object[1][1];
	}
	
	public Matrix(T[][] grid) {
		this.matrix = grid;
	}
	
	/**
	 * Checks whether the given position is inside the playground's bounds. If not, increases the 
	 * size of the playground by one, i.e. adding one to each side.
	 * 
	 * @return {@code true} if playground was resized, {@code false} otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean resize(int[] position) {
		
		if (position[0] >= 0 && position[0] < matrix.length
				&& position[1] >= 0 && position[1] < matrix.length) {
			// Position is within playground bounds. No need to resize.
			return false;
		}
		// The given position falls outside playground bounds. We should resize.
		int newSize = matrix.length + 2;
		int oldSize = matrix.length;
		T[][] newGrid = (T[][]) new Object[newSize][newSize];
		// Copy the old playground grid onto the new one.
		for (int i=0; i<oldSize; i++) {
			// Make sure that the new playground is one square bigger on each side.
			System.arraycopy(matrix[i], 0, newGrid[i+1], 1, oldSize);
		}
		// Set the resulting grid as the new playground.
		matrix = newGrid;
		
		return true;
	}
	
	/**
	 * Rotates the entire {@link Matrix} clockwise.
	 */
	@SuppressWarnings("unchecked")
	public void rotateClockwise() {
		T[][] rotated = (T[][]) new Object[matrix.length][matrix.length];
		for (int i=0; i<matrix.length; i++) {
			int innerArrayLength = rotated[i].length;
			for (int j=0; j<innerArrayLength; j++) {
				// Convert left-to-right to top-to-bottom and top-to-bottom to right-to-left.
				rotated[j][innerArrayLength - i - 1] = matrix[i][j];
			}
		}
		// Set the rotated grid as the new grid.
		matrix = rotated;
	}
	
	
	/**
	 * Scours the matrix for occurrences of the given object. Relies on 
	 * {@link Object#equals(Object)}.
	 * 
	 * @return a list containing integer arrays specifying the locations of occurrences.  
	 */
	public List<int[]> getOccurrences(Object target) {
		List<int[]> occurrences = new ArrayList<>();
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				if (target.equals(matrix[i][j])) {
					occurrences.add(new int[]{ j, i });
				}
			}
		}
		return occurrences;
	}
	
	public void setAtPosition(T value, int[] position) {
		// Check if given position is inside grid bounds and resize if necessary.
		if (autoResize && !isInsideBounds(position)) {
			resize(position);
		}
		matrix[position[1]][position[0]] = value;
	}
	
	/**
	 * Returns the object at the specified position on the playground.
	 * 
	 * @param position - an integer array of length two, with first integer representing the x axis  
	 * and the second the y axis.
	 * @return the object present at {@code position} in the matrix, or null the given position
	 * is outside matrix bounds. 
	 */
	public T charAtPosition(int[] position) {
		if (!isInsideBounds(position)) {
			return null;
		}
		return matrix[position[1]][position[0]];
	}
	
	private boolean isInsideBounds(int[] position) {
		return position[0] < matrix[0].length && position[1] < matrix.length;
	}
	
	/**
	 * Specifies whether this matrix should be automatically resized whenever a field that is 
	 * currently outside the bounds of the matrix is specified, thereby preventing any 
	 * {@link ArrayIndexOutOfBoundsException}s. Default is {@code true}.
	 * 
	 * @param autoResize - flag specifying whether this {@link Matrix} should automatically resize.
	 */
	public void setAutoResize(boolean autoResize) {
		this.autoResize = autoResize;
	}
	
	@Override
	public String toString() {
		String toString = "";
		for (T[] row : matrix) {
			toString += Arrays.toString(row) + System.lineSeparator();
		}
		return toString;
	}	

	/**
	 * More sophisticated version of {@link #toString()}. Output does not contain square bracket at
	 * start and end of row, nor comma's between values. Instead, spaces separate the values.
	 * If {@code selectedPositions} contains valid positions in the form of { x, y } integer arrays,
	 * the values at these locations will be flanked by square brackets to highlight them.
	 * 
	 * @param selectedPositions - a list of positions that should be highlighted.
	 * @return {@link String} representation of the {@link Matrix}. 
	 */
	public String toString(List<int[]> selectedPositions) {
		return MatrixUtil.toString(matrix, selectedPositions);
	}
	
}
