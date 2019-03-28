package adventOfCode.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuringMachine {

	private List<int[]> states;
	
	private InfiniteTape infiniteTape;
	
	private int nextState;
	
	private int iterations;

	private final static Pattern digit = Pattern.compile("\\d+"); 
	
	// First try: 4387 (correct!!)
	
	public int performDiagnostics() {
				
		for (int i=1; i<=iterations; i++) {
			nextState = executeState(states.get(nextState));
			
			if (i % 1000 == 0) {
				System.out.println("Executed 1000 iterations! Current checksum: " 
						+ infiniteTape.getChecksum());	
			}
			if (i % 10000 == 0) {
				System.out.println(infiniteTape);
			}
		}
		
		return infiniteTape.getChecksum();
	}
	
	public boolean initialize(String inputFile) {
		
		infiniteTape = new InfiniteTape();
		states = new ArrayList<>();
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(inputFile));
			int lineIndex = 0;
			
			nextState = readStateName(lines.get(lineIndex++));
			iterations = readIntegerValue(lines.get(lineIndex));
			lineIndex += 2;
			
			// While a complete state definition is present in the input, keep creating states.
			while (lineIndex + 8 < lines.size()) {
				states.add(readState(lines, lineIndex));
				// Move the index to the start of the next state.
				lineIndex += 10;
			}
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private int[] readState(List<String> lines, int from) {
		int[] state = new int[6];
		state[0] = readIntegerValue(lines.get(from + 2));
		state[1] = readDirection(lines.get(from + 3));
		state[2] = readStateName(lines.get(from + 4));
		state[3] = readIntegerValue(lines.get(from + 6));
		state[4] = readDirection(lines.get(from + 7));
		state[5] = readStateName(lines.get(from + 8));
		return state;
	}
	
	private int readIntegerValue(String line) {
		Matcher matcher = digit.matcher(line);
		matcher.find();
		return Integer.parseInt(matcher.group());
	}
	
	private int readDirection(String line) {
		return line.contains("left") ? -1 : 1;
	}
	
	private int readStateName(String line) {
		// Read state as character. Subtract 'A' from it to get integer index value of that state.
		return line.charAt(line.indexOf(".") - 1) - 'A';
	}
	
	private int executeState(int[] state) {
		// Determine which values to use based on the value the cursor is currently on. 
		int start = infiniteTape.read() == 0 ? 0 : 3;
		// Perform the write and move operations, with the values specific to this state. 
		infiniteTape.write(state[start + 0]);
		infiniteTape.move(state[start + 1]);
		// Return the index of the next state, as specified in the current state.
		return state[start + 2];
	}
	
}
