package adventOfCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import adventOfCode.day12.PipeInputConverter;
import adventOfCode.day8.JumpInstructionInputConverter;

public class InputFileReader {

	private InputConverter<?> converter;
	
	public enum Day {
		EIGHT,
		TWELVE
	}
	
	public InputFileReader(InputConverter<?> converter) {
		this.converter = converter;
	}
	
	/**
	 * This mechanism is stupid. Use the constructor that takes a specific InputConverter.
	 */
	@Deprecated
	public InputFileReader(Day day) {
		switch (day) {
			case EIGHT:
				converter = new JumpInstructionInputConverter();
				break;
			case TWELVE:
				converter = new PipeInputConverter();
				break;
		}
	}
	
	public boolean readInput(String inputFile) {
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				if (!converter.convertLine(line)) {
					// Something went wrong.
					System.out.println("The input conversion failed! Line that produced the "
							+ "error: " + line);
					return false;
				}
			}
			
		} catch (IOException ioex) {
			System.out.println("I/O error while trying to read the input file!");
			ioex.printStackTrace();
			return false;
		}
				
		return true;
	}
	
	public void setInputConverter(InputConverter<?> converter) {
		this.converter = converter;
	}	
}
