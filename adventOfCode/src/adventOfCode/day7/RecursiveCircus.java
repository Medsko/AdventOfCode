package adventOfCode.day7;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import util.IOUtils;

/**
 * This class is nothing, yet has it all... 
 */
public class RecursiveCircus {

	private boolean isForB = false;
	
	public String findNameOfLowestDisc() {
		
		Path file = Paths.get(IOUtils.PATH_TO_INPUT + "inputDay7.txt");
		RecursiveDiscReader reader = new RecursiveDiscReader();
		
		if (!reader.readInput(file)) {
			return "I/O FAIL!";
		}
		
		Map<String, RecursiveDisc> recursiveDiscMap = reader.getRecursiveDiscMap();
		
		RecursiveDiscOrganizer organizer = new RecursiveDiscOrganizer();
		RecursiveDisc theOne = organizer.sortDiscs(recursiveDiscMap);
		
		if (isForB)
			return findNameOfUnevenlyWeightedDisc(theOne);
		else
			return theOne.getName();
	}
	
	private String findNameOfUnevenlyWeightedDisc(RecursiveDisc theOne) {
		// Call the recursive find method on the disc.
		Optional<String> naughtyDisc = theOne.findUnevenlyWeightedUpperDisc();
		
		if (!naughtyDisc.isPresent())
			return "The unevenly weighted disc could not be found!";
		else
			return "The name of the unevenly weighted disc is: " + naughtyDisc.get();
	}

	public void setForB(boolean isForB) {
		this.isForB = isForB;
	}
}
