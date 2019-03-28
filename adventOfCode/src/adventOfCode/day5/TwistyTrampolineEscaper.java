package adventOfCode.day5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import util.IOUtils;

public class TwistyTrampolineEscaper {
	
	/** The 'playing field' of this problem. */
	private Integer[] jumpMaze;
	
	private boolean isDay5b;
	
	private BufferedWriter logger;
	
	public TwistyTrampolineEscaper(String inputFile) {
		jumpMaze = IOUtils.readIntegerInputToArray(inputFile);
	}
	
	public int calculateStepsToEscape() {
		
		int nrOfSteps = 0;
		
		int position = 0;
		
		Path logFile = Paths.get("C:/Temp/AdventOfCode/log.txt");
		
		try {
			
			if (isDay5b)
				logger = Files.newBufferedWriter(logFile);
			
			while (position != -1) {
				
				position = calculateNextPosition(position);
				nrOfSteps++;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (logger != null)
					logger.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
		return nrOfSteps;
	}
	
	/**
	 * Calculates the position with which we will start the next round.
	 * This is achieved by getting the instruction specified on the current 
	 * position, adding it to the current position index and updating the
	 * {@link #jumpMaze} array.
	 * @throws IOException 
	 */
	private int calculateNextPosition(int position) throws IOException {
		// Get the instruction at the current position and update the
		// original value.
		int instruction = getAndUpdateInstructionAtPosition(position);
		
		int nextPosition = position + instruction;
		
		logMove(position, instruction);
		
		if (nextPosition >= jumpMaze.length)
			// This jump will move us out of the maze. Return -1 to end
			// the loop.
			return -1;
		else 
			// The position after this jump is still inside array bounds.
			// Return the next position.			
			return nextPosition;
	}
	
	protected int getAndUpdateInstructionAtPosition(int position) {
		// If this is a 'b' run, check whether the offset is 3 or more.
		if (isDay5b && jumpMaze[position] >= 3) {
			// The offset is three or more. Return it, then decrement it.
			return jumpMaze[position]--;
		}
		return jumpMaze[position]++;
	}
		
	/**
	 * Logs the move that is currently being made. 
	 * @throws IOException 
	 */
	private void logMove(int position, int instruction) throws IOException {
		
		String message = "";
		if (position + instruction >= jumpMaze.length)
			message += "Exiting the maze by moving ";
		else
			message += "Moving ";
		
		message += instruction + " squares from position ";
		message += position + "/" + (jumpMaze.length - 1);
		
		if (isDay5b && Math.abs(instruction) < 50) {
			// If this is a 'b' run, write jumps with offsets smaller than 
			// 50 to a file.
			logger.write(message);
			logger.newLine();
		} else {
			// Write the jump to the console.
			System.out.println(message);
		}
	}

	public void setIsDay5b(boolean isDay5b) {
		this.isDay5b = isDay5b;
	}
}
