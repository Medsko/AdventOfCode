package adventOfCode.day16;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.IOUtils;

/**
 * ...because it's interpretive dance. Yeah. 
 */
public class Interpreter {
	
	private List<DanceInstruction> instructions;
	
	public Interpreter() {
		instructions = new ArrayList<>();
	}
	
	public List<DanceInstruction> interpretInstructions() {
		
		Path inputFile = Paths.get(IOUtils.PATH_TO_INPUT).resolve("inputDay16.txt");

		try (Scanner scanner = new Scanner(inputFile)) {
			// Input consists of comma separated values.
			scanner.useDelimiter(",");
			
			while (scanner.hasNext()) {
				DanceInstruction instruction = new DanceInstruction(scanner.next());
				instructions.add(instruction);
			}
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		
		return instructions;
	}
}
