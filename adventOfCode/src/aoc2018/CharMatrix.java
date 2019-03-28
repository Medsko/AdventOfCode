package aoc2018;

import java.util.ArrayList;
import java.util.List;

public class CharMatrix {

	private char[][] matrix;
	
	public CharMatrix(int capacity) {
		matrix = new char[capacity][];
	}
	
	/**
	 * Scours the matrix for occurrences of the given character.
	 * @return a list containing integer arrays specifying the locations of occurrences.  
	 */
	public List<int[]> getOccurrences(char target) {
		List<int[]> occurrences = new ArrayList<>();
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				if (matrix[i][j] == target) {
					occurrences.add(new int[]{ j, i });
				}
			}
		}
		return occurrences;
	}
		
	/**
	 * Returns the character that is present at the given position in the matrix. If the position
	 * is outside of the matrix' bounds, a space character is returned. 
	 */
	public char safeGetCharAt(int[] position) {
		if (position[1] > matrix.length || position[0] > matrix[1].length) {
			return ' ';
		}
		return matrix[position[1]][position[0]];
	}
	
	public void setCharAt(int[] position, char value) {
		matrix[position[1]][position[0]] = value;
	}
	
	public void setRow(int rowIndex, char[] row) {
		matrix[rowIndex] = row;
	}
	
	public char[][] getMatrix() {
		return matrix;
	}
	
	public int getYLength() {
		return matrix.length;
	}
	
	public int getXLength() {
		return getXLengthAt(0);
	}
	
	public int getXLengthAt(int row) {
		return matrix[row].length;
	}
}
