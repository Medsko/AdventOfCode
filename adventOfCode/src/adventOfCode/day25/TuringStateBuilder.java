package adventOfCode.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TuringStateBuilder {

	public static List<int[]> readStates(String inputFile) {
		
		List<int[]> states = new ArrayList<>();
		
		try {
			
			List<String> lines = Files.readAllLines(Paths.get(inputFile));
			int lineIndex = 0;
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return null;
		}
		
		return states;
	}
	
	
}
