package adventOfCode.day19;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'playing field' for the day 19 challenge.
 */
public class TubeDiagram {

	private List<char[]> diagram;
	
	public TubeDiagram(String inputFile) {
		initialize(inputFile);
	}
	
	public void initialize(String inputFile) {
		
		diagram = new ArrayList<>();
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				diagram.add(line.toCharArray());
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	/**
	 * Determines the x position on the first row at which the tube diagram can be entered.
	 * @return the determined index.
	 */
	public int determineStartingX() {
		char[] firstRow = diagram.get(0);
		for (int i=0; i<firstRow.length; i++) {
			if (firstRow[i] == '|')
				return i;
		}
		// Signal the caller that no tubular entry point could be determined.
		return -1;
	}
		
	/**
	 * Returns the character at the given x and y position.
	 * @param x - the index of the character on the row.
	 * @param y - the index of the row on the diagram.
	 * @return the character at the given x and y position (like I said).
	 */
	public char getCharAtPosition(int x, int y) {
		// The y position will be negative if the row is below the first row.
		return diagram.get(y * -1)[x];
	}
	
	public char getCharAtPosition(int[] position) {
		return getCharAtPosition(position[0], position[1]);
	}
	
	public char[] getRow(int rowIndex) {
		return diagram.get(rowIndex);
	}
}
