package aoc2018.day01;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import aoc2018.AocInputFetcher;

public class ChronoCalibrator {
	
	private List<String> inputLines;
	
	private int frequency;
	
	// Challenge b first try: 245 (correct)
	
	public boolean initialize() {
		
		AocInputFetcher fetcher = new AocInputFetcher(2018);
		if (!fetcher.fetchInput(1)) {
			return false;
		}
		inputLines = fetcher.getInputLines();
		frequency = 0;
		
		return true;
	}
	
	public void calibrate() {
		
		for (String line : inputLines) {
			int change = Integer.parseInt(line);
			frequency += change;
		}	
	}
	
	public void findDoubleFrequency() {
		
		Set<Integer> previousFrequencies = new LinkedHashSet<>();
		
		boolean onlyUniqueFrequenciesEncountered = true;
		
		while (onlyUniqueFrequenciesEncountered) {
			
			for (String line : inputLines) {
				int change = Integer.parseInt(line);
				frequency += change;
				
				if (!previousFrequencies.add(frequency)) {
					
					onlyUniqueFrequenciesEncountered = false;
					break;
				}
			}
		}	
	}
	
	public int getFrequency() {
		return frequency;
	}
}
