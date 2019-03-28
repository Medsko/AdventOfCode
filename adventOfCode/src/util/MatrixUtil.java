package util;

import java.util.List;

/**
 * Collection of utility methods for handling of matrices.
 */
public class MatrixUtil {

	private MatrixUtil() {}
	
	public static String toString(char[][] matrix) {
		return toString(matrix, null);
	}
	
	/**
	 * Loops over the given {@code matrix}, marking positions that are included in 
	 * {@code selectedPositions} with square brackets, calling toString on each field in the matrix.
	 * 
	 * @return a String representation of the given matrix.
	 */
	public static String toString(char[][] matrix, List<int[]> selectedPositions) {
		String toString = "";
		// Loop through all fields in the matrix.
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				final int y = i, x = j;
				if (selectedPositions != null && selectedPositions.stream()
						.anyMatch((position)-> position[1] == y && position[0] == x)) {
					// This position was included in the list of selected positions. Mark it.
					toString += "[" + String.valueOf(matrix[i][j]) + "]";
				} else {
					toString += ' ' + String.valueOf(matrix[i][j]) + " "; 
				}					
			}
			toString += System.lineSeparator();
		}
		return toString;
	}

	public static String toString(Object[][] matrix, List<int[]> selectedPositions) {
		String toString = "";
		// Loop through all fields in the matrix.
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				final int y = i, x = j;
				if (selectedPositions != null && selectedPositions.stream()
						.anyMatch((position)-> position[1] == y && position[0] == x)) {
					// This position was included in the list of selected positions. Mark it.
					toString += "[" + String.valueOf(matrix[i][j]) + "]";
				} else {
					toString += ' ' + String.valueOf(matrix[i][j]) + " "; 
				}					
			}
			toString += System.lineSeparator();
		}
		return toString;
	}

	public static boolean isAdjacent(int[] square, int[] other) {
		if (square[0] == other[0] 
				&& (square[1] + 1 == other[1] || square[1] - 1 == other[1])) {
			return true;
		}
		return square[1] == other[1] 
				&& (square[0] + 1 == other[0] || square[0] - 1 == other[0]);
	}
}
