package adventOfCode.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * This class does the heavy lifting in solving the RecursiveCircus problem.
 * It converts a flat list of {@link RecursiveDisc}s into a single disc
 * holding all upper discs in a tree structure. 
 */
public class RecursiveDiscOrganizer {
	
	public RecursiveDisc sortDiscs(Map<String, RecursiveDisc> recursiveDiscMap) {
		
		RecursiveDisc theOne = findTheOne(recursiveDiscMap);
		// Even though this solves the problem, let's order the discs into a composite
		// tree structure, just for fun :)
		theOne.addUpperDiscs(recursiveDiscMap);
		
		return theOne;
	}
	
	private RecursiveDisc findTheOne(Map<String, RecursiveDisc> recursiveDiscMap) {
		
		ArrayList<String> allUpperDiscNames = new ArrayList<>();
		
		// Determine all the names of discs that are held by other discs.
		for (RecursiveDisc disc : recursiveDiscMap.values()) {
			String[] upperDiscNames = disc.getUpperDiscNames();
			if (upperDiscNames != null) {
				Collections.addAll(allUpperDiscNames, upperDiscNames);
			}
		}
		
		// Find the disc that is not held by any other discs and return it.
		for (String discName : recursiveDiscMap.keySet()) {
			if (!allUpperDiscNames.contains(discName))
				return recursiveDiscMap.remove(discName);
		}
		
		return null;
	}
}
