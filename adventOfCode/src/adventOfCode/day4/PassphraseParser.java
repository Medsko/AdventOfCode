package adventOfCode.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PassphraseParser {

	/** Reader that reads in the input file line by line. */
	private BufferedReader reader;
	
	/** Scans the input line for separate words. */
//	private Scanner scanner;
	
	/** A set holding the separate words of the current passphrase. */
//	private Set<String> currentPassphrase;
	
	public PassphraseParser(Path inputFile) {
		
		try {
			
			reader = Files.newBufferedReader(inputFile);
			
		} catch (IOException ioex) {
			
			System.out.println("Failed to initialize the reader.");
		}
	}
	
	public int countValidPassphrases() throws IOException {
		
		int validPassphrases = 0;
		
		String currentLine = null;
		
		while ((currentLine = reader.readLine()) != null) {
			
			if (isValidPassphrase(currentLine)) {
				validPassphrases++;
			}
		}
		
		return validPassphrases;
	}
	
	protected boolean isValidPassphrase(String line) {
		
		Set<String> currentPassphrase = new HashSet<>();
		
		Scanner scanner = new Scanner(line);
		
		String currentWord;
		
		while (scanner.hasNext()) {
			
			currentWord = scanner.next();
			
			if (currentPassphrase.contains(currentWord) || isAnagramOfPassphraseWord(currentWord)) {
				
				scanner.close();
				return false;
			}
			
			currentPassphrase.add(currentWord);
		}
		
		scanner.close();
		
		return true;
	}
	
	protected boolean isAnagramOfPassphraseWord(String currentWord) {
		// Just a stub, implement in sub for additional String magic.
		return false;
	}
	
}
