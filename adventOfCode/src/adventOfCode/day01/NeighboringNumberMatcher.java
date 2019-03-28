package adventOfCode.day01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Given a file containing a sequence of integers, this matcher calculates the sum of all numbers that
 * match the number next to it. The sequence is perceived as circular, so if the first and last number 
 * match, they are also counted towards the total.
 */
public class NeighboringNumberMatcher {

	public final static String INPUT = "C:/Users/Medsko/Documents/CaptchaForAdventOfCode.txt";
	
	private char[] circularList;
	
	public int calculateSumOfMatchingNeighbors(String inputFile) {
		int sumOfMatchingNeighbors = 0;
		int previousNumber = 0;
		int currentNumber = 0;
		
		File input = new File(inputFile);
				
		try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
			
			char[] inputCharacters = new char[5000];
			reader.read(inputCharacters, 0, 5000);
			
			int firstNumber = Character.getNumericValue(inputCharacters[0]);
			previousNumber = firstNumber;
			
			int index = 1;
			
			while (index < 5000 && inputCharacters[index] > 0) {
				
				currentNumber = Character.getNumericValue(inputCharacters[index]);
				
				if(currentNumber == previousNumber) {
					sumOfMatchingNeighbors += currentNumber;
				}
				
				previousNumber = currentNumber;
				
				index++;
			}
			
			if (firstNumber == currentNumber) {
				sumOfMatchingNeighbors += currentNumber;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Input file could not be found (or something).");
		} catch (IOException e) {
			System.out.println("Some freaky I/O-shit happened.");
		}
		
		return sumOfMatchingNeighbors;
	}
	
	public boolean initialize(String inputFile) {
		
		try {
			List<String> inputLines = Files.readAllLines(Paths.get(inputFile));
			String aggregate = "";
			for (String line : inputLines)
				aggregate += line;

			circularList = aggregate.toCharArray();
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public int calculateSumMatchingHalfwayRound() {
		int matches = 0;
		int halfway = circularList.length / 2;
		// We only need to iterate through half the list: if a match occurs once, it will also
		// occur when we reach the matching entry (i.e. the one halfway further down the array).
		for (int i=0; i<halfway; i++) {
			// Check if this entry matches the one halfway further down the list.
			if (circularList[i] == circularList[i + halfway]) {
				// Count the match both ways (see previous comment).
				matches += 2 * Character.getNumericValue(circularList[i]);
				// Without conversion: 12094 matches.
				// With conversion: 1054 matches...
			}
		}
		return matches;
	}
	
	public int calculateSumOfMatchingNeighbors() {
		return calculateSumOfMatchingNeighbors(INPUT);
	}
}
