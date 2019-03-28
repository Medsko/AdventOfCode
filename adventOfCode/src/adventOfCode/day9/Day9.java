package adventOfCode.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import util.IOUtils;
import util.Util;

public class Day9 {

	public static int calculateTotalScore(boolean isForB) {
		
		String input = null;
		
		try {
			
			Path inputFile = Paths.get(IOUtils.PATH_TO_INPUT + "inputDay9.txt");
			// Lovely way to read an entire file as one String.
			input = new String(Files.readAllBytes(inputFile));

		} catch (IOException ioex) {
			ioex.printStackTrace();
			return 0;
		}
		
		CharGroupFilter filter = new CharGroupFilter(isForB);
		filter.filter(input);
		List<CharGroup> charGroups = filter.getCharGroups();
		
		for (int i=0; i<10; i++)
			Util.underline(125);
		
		int totalScore = 0;
		
		if (isForB) {
			
			List<String> garbageGroups = filter.getGarbageGroups();
			
			for (String garbage : garbageGroups) {
				System.out.println("Garbage group: " + garbage);
				// Correct the garbage score by deducting the opening and closing tag.
				int garbageScore = garbage.length() - 2;
				System.out.println("Length: " + garbageScore);
				totalScore += garbageScore;
			}
		} else {
			for (CharGroup group : charGroups) {
				int groupScore = group.calculateAndGetScore();
				System.out.println("Group: " + group.toString() + System.lineSeparator() 
				+ "Score: " + groupScore);
				totalScore += groupScore ;
			}
		}
		return totalScore;
	}
}
