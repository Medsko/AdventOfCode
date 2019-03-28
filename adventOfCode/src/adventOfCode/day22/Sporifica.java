package adventOfCode.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import adventOfCode.Direction;
import adventOfCode.Direction.TwoDimGrid;
import util.Util;

public class Sporifica {

	private int[] currentPosition;

	private Direction.TwoDimGrid currentDirection;
	
	private ViralPlayground playground;
	
	/** Most important variable: holds the answer for challenge A - and B, it turns out. */
	private int infectionsCaused;
	
	private boolean isChallengeB;
	
	// After switching from buggy FourWay Direction to more intuitive TwoDimGrid, right in one try!
	// Challenge B also went way too smooth, number of infections caused: 2512225.
	
	/**
	 * Initializes this {@link Sporifica}. Can also be used to reset it. 
	 */
	public void initialize(String inputFile) {
		currentDirection = TwoDimGrid.UP;
		infectionsCaused = 0;
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line;
			char[][] grid = null;
			int rowIndex = 0;
			int size = 0;
			// Read the initial playground grid from the input file.
			while ((line = reader.readLine()) != null) {
				
				char[] row = line.toCharArray();
				if (grid == null) {
					size = row.length;
					grid = new char[size][size];
				}
				grid[rowIndex] = row;
				
				rowIndex++;
			}
			playground = new ViralPlayground(grid);
			// Determine the starting position, which will be in the middle of the playground.
			int middle = size / 2;
			currentPosition = new int[] { middle, middle };

		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	public int activate(int iterations, boolean isChallengeB) {
		
		this.isChallengeB = isChallengeB;
		
		for (int i=1; i<=iterations; i++) {

			burst();
			
			if (i < 8 || i == 70) {
				System.out.println("State of the grid after " + i + " iterations: ");
				System.out.println(playground);
				System.out.println("Current position: " + Arrays.toString(currentPosition));
				Util.underline();
			}
		}
		return infectionsCaused;
	}
	
	private void elaborateUpdateSquareAndDirection(char currentChar) {
		
		if (currentChar == 'W') {
			// Weakened nodes become infected and do not influence carrier direction.
			playground.setCharAtPosition('#', currentPosition);
			// Register the new infestation.
			infectionsCaused++;
		} else if (currentChar == '#') {
			// Infected nodes are flagged ('F') for cleaning, after which carrier turns right.
			playground.setCharAtPosition('F', currentPosition);
			currentDirection = currentDirection.rotateClockwise();
		} else if (currentChar == 'F') {
			// Flagged nodes are cleaned of infection, after which carrier reverses direction.
			playground.setCharAtPosition('.', currentPosition);
			currentDirection = currentDirection.opposite();
		} else {
			// Clean nodes become weakened ('W') and make carrier turn left.
			playground.setCharAtPosition('W', currentPosition);
			currentDirection = currentDirection.rotateCounterClockwise();
		}
	}
	
	private void updateSquareAndDirection(char currentChar) {
		// Change direction: right if current square is infected, left otherwise.
		// It appears the squares that are infected initially should be treated differently.
		if (currentChar == '#') {
			// Current square is one of the originally infected. Turn right and clean the square.
			currentDirection = currentDirection.rotateClockwise();
			playground.setCharAtPosition('.', currentPosition);
		} else {
			// Current square is clean. Turn left and infect current square.
			currentDirection = currentDirection.rotateCounterClockwise();
			playground.setCharAtPosition('#', currentPosition);
			// Update the variable that holds track of the number of infections the carrier caused.
			infectionsCaused++;
		}
	}
	
	/**
	 * Executes one 'burst' of activity. 
	 */
	private void burst() {
		char currentChar = playground.charAtPosition(currentPosition);
		// Update the current square and direction according to the rules of the challenge, be it
		// A or B.
		if (!isChallengeB) {
			updateSquareAndDirection(currentChar);
		} else {
			elaborateUpdateSquareAndDirection(currentChar);
		}
		
		// Move the carrier one square in the current direction.
		currentDirection.apply(currentPosition);
		// Check whether this moves the carrier out of range of the playground.
		if (playground.resize(currentPosition)) {
			// Playground was increased in size on all sides. Update current position accordingly.
			currentPosition[0]++; 
			currentPosition[1]++;
		}
	}	
}
