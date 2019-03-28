package adventOfCode.day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads in the discs described in the input file in a flat structure. 
 */
public class RecursiveDiscReader {
	
	private Map<String, RecursiveDisc> recursiveDiscMap;
	
	public boolean readInput(Path inputFile) {
		
		recursiveDiscMap = new HashMap<>();
		
		try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				// Read the name of the disc - everything up to the first whitespace.
				String name = line.substring(0, line.indexOf(" "));
				// Read the weight of the disc - the digits between the brackets.
				String weightString = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				Integer weight = Integer.parseInt(weightString);
				
				String[] upperDiscsNames = null;
				// If the line contains a '->', read the names of the upper discs.
				if (line.contains("-"))
					upperDiscsNames = readUpperDiscsNames(line);
				
				RecursiveDisc disc = new RecursiveDisc(name, weight);
				disc.setUpperDiscNames(upperDiscsNames);
				recursiveDiscMap.put(name, disc);
			}
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return false;
		}
		
		return true;
	}

	private String[] readUpperDiscsNames(String line) {
		// Get the sub String containing the upper discs' names.
		String udNames = line.substring(line.indexOf(">") + 1);
		// Filter out the whites.
		udNames = udNames.replace(" ", "");
		// Split into separate names.
		String[] upperDiscNames = udNames.split(",");
		
		return upperDiscNames;
	}

	public Map<String, RecursiveDisc> getRecursiveDiscMap() {
		return recursiveDiscMap;
	}
}
