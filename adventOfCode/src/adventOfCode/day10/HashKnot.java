package adventOfCode.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import util.Util;

public class HashKnot {

	private char[] inputValues;
	
	private Integer[] sparseHash;
	
	private Integer[] denseHash;
	
	private int skipSize;
	
	private int currentPosition;
	
	private int[] instructions;
	
	private int previousPosition;
	
	private int[] standardInstructions = new int[] {17, 31, 73, 47, 23};
	
	private boolean isDebug;
	
	public HashKnot() {
		this(256);
	}
	
	public HashKnot(int lengthOfHash) {
		// Initialize the hash.
		sparseHash = new Integer[lengthOfHash];
		for (int i=0; i<sparseHash.length; i++)
			sparseHash[i] = i;
		// Initialize skip size and currentPosition.
		skipSize = 0;
		currentPosition = 0;
		previousPosition = 0;
		isDebug = true;
	}
	
	public int knotIt(String inputFile) {
		
		Path filePath = Paths.get(inputFile);
		
		try (Scanner scanner = new Scanner(filePath)) {
			// Input values are separated by comma's. 
			scanner.useDelimiter(",");
			// Perform loop for each value in the input file.
			while (scanner.hasNextInt()) {
				// Get the next instruction.
				int length = scanner.nextInt();
				// Execute the instruction on the array.
				executeInstruction(length);
				// Log the result.
				System.out.println("Length: " + length + ", current position: " + currentPosition
						+ System.lineSeparator() + "Result: " + Arrays.toString(sparseHash));
				Util.underline();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sparseHash[0] * sparseHash[1];
	}
	
	public String fullKnotIt(String inputFile) {
		// Read the input characters and convert them to their integer value.
		readInputFromFile(inputFile);
		// Compute the hash knot based on the character input.
		return computeHashKnot(inputValues);
	}
	
	/**
	 * Core method. Takes a character array as input, computes the hash knot based on the derived
	 * instructions and returns it in (allegedly standard) hexadecimal format. 
	 */
	public String computeHashKnot(char[] input) {
		// Convert the input characters into their ASCII integer value.
		assembleInstructionsFromInput(input);

		// Run 64 full cycles.
		for (int i=0; i<64; i++)
			runCycle();
		
		// Sparse hash is now constructed. To create the dense hash:
		denseHash = new Integer[16];
		for (int i=0; i<16; i++) {
			// Create an array holding the next sixteen values of the sparse hash.
			Integer[] section = new Integer[16];
			System.arraycopy(sparseHash, i * 16, section, 0, 16);
			// Perform XOR evaluation on the elements.
			int denseElement = xorEvaluate(section);
			// Add the dense element to the dense hash.
			denseHash[i] = denseElement;
		}
		
		// Now create the hexadecimal string representation of the hash knot.
		String hexString = "";
		for (int denseElement : denseHash)
			hexString += convertAsciiValueToHexadecimal(denseElement);
		
		if (hexString.length() != 32)
			throw new IllegalStateException("Output hex not long enough!");
		
		return hexString;
	}
	
	private String convertAsciiValueToHexadecimal(int asciiValue) {
		if (asciiValue > 255)
			throw new IllegalArgumentException("The input value " + asciiValue 
					+ " is too large to be a valid ASCII value!");
		String hexString = Integer.toHexString(asciiValue);
		
		while (hexString.length() < 2)
			hexString = "0" + hexString;
		
		return hexString;
	}
	
	private int xorEvaluate(Integer[] operands) {
		
		System.out.println("Now calculating dense element for: " + Arrays.toString(operands));
		
		// Get the first element from the input array.
		int result = operands[0];
		// Perform XOR evaluation with all other elements on it.
		for (int i=1; i<operands.length; i++)
			result = result ^ operands[i];
		
		return result;
	}
	
	private void readInputFromFile(String inputFile) {
		Path filePath = Paths.get(inputFile);
		String inputLine = null;
		// Get the input values as ASCII characters from the file and convert them to integers. 
		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			// Read the input from the file.
			inputLine = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (inputLine == null) {
			inputValues = new char[0];
		} else {
			inputValues = inputLine.toCharArray();
		}
	}
	
	private void assembleInstructionsFromInput(char[] input) {
		instructions = new int[input.length + 5];
		// Convert the input ASCII characters to their integer value.
		for (int i=0; i<input.length; i++) {
			instructions[i] = input[i];
		}
		// Insert the standard values into the instructions, after the input values.
		for (int i=input.length; i<instructions.length; i++) {
			instructions[i] = standardInstructions[i-input.length];
		}
	}

	private void runCycle() {
		for (int instruction : instructions) {
			if (isDebug)
				// Log the instruction and current position.
				System.out.println("Length: " + instruction + ", current position: " 
						+ currentPosition);
			
			// Execute the instruction on the array.
			executeInstruction(instruction);
			// Log the result.
			if (isDebug) {
				System.out.println("Result: " + Arrays.toString(sparseHash));
				Util.underline();
			}
		}
	}
	
	
	private void executeInstruction(int length) {
		
		Integer[] section = new Integer[length];
		
		// Copy the section into the temporary array.
		wrappedArrayCopyFrom(sparseHash, currentPosition, section);
		// Reverse the section.
		section = reverseSection(section);
		// Copy the reversed section back into the hash array.
		wrappedArrayCopyTo(section, currentPosition, sparseHash);
		
		previousPosition = currentPosition;
		
		// Update the current position.
		// Increase the current position by the skip size.
		currentPosition += skipSize + length;
		
		if (isDebug) {
			System.out.println("Current skipSize: " + skipSize + ", current length: " + length);
			System.out.println("Will now move pointer " + (skipSize + length) + " positions ahead.");
		}
		// If this moves us beyond the bounds of the array, wrap around.
		while (currentPosition >= sparseHash.length)
			currentPosition = currentPosition - sparseHash.length;
		
		if (isDebug)
			System.out.println("Moved from " + previousPosition + " to " + currentPosition);
		
		// Update the skip size.
		skipSize++;	
	}
	
	/**
	 * Copies a section of the source array to the target 'section' array. The target array will be
	 * completely filled with values obtained from the source array.
	 * 
	 * @return the number of indices that were out of range of the source array.
	 */
	private int wrappedArrayCopyFrom(Integer[] source, int srcPosition, Integer[] target) {
		// Determine the amount of overflow.
		int overflow = (srcPosition + target.length) - source.length;
		// If the overflow is negative...
		if (overflow < 0)
			// ...then the end of the section is in range of the source array.
			overflow = 0;
		// Copy the part that is in range of the source array to the section.
		System.arraycopy(source, srcPosition, target, 0, target.length - overflow);
		
		// Now copy the overflow part (if any) to the section.
		System.arraycopy(source, 0, target, target.length - overflow, overflow);
		
		return overflow;
	}

	/** Copies the 'section' array to the target array. The target array will be
	 * completely filled with values obtained from the source array.
	 * @return the number of indices that were out of range of the source array.
	 */
	private void wrappedArrayCopyTo(Integer[] source, int srcPosition, Integer[] target) {
		// Determine the amount of overflow.
		int overflow = (srcPosition + source.length) - target.length;
		// If the overflow is negative...
		if (overflow < 0)
			// ...then the end of the section is in range of the source array.
			overflow = 0;
		// Copy the part that is in range to the source array.
		System.arraycopy(source, 0, target, srcPosition, source.length - overflow);
		
		// Now copy the overflow part (if any) to the target.
		System.arraycopy(source, source.length - overflow, target, 0, overflow);
	}
	
	private Integer[] reverseSection(Integer[] section) {
		List<Integer> sectionAsList = Arrays.asList(section);
		Collections.reverse(sectionAsList);
		return sectionAsList.toArray(new Integer[0]);
	}

	public void setIsDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
}
