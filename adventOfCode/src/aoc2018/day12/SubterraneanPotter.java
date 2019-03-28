package aoc2018.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc2018.AbstractAocAlgorithm;
import aoc2018.TwoWayCharArray;

public class SubterraneanPotter extends AbstractAocAlgorithm {

	// INPUT
	private TwoWayCharArray pots;
	
	private List<char[]> plantProducingCombos;	

	// PROCESSING
	private int startIndex;
	
	private int previousGenTotal;
	
	public SubterraneanPotter(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public boolean initialize(boolean isTest) {
		if (!super.initialize(2018, 12, isTest)) {
			return false;
		}
		startIndex = 0;
		
		List<String> input = fetcher.getInputLines();
		// The first line specifies the initial state.
		String initialState = input.get(0).trim().split(" ")[2];
		pots = new TwoWayCharArray('.', initialState.toCharArray(), 0);
		// Now determine the plant producing combinations. 
		plantProducingCombos = new ArrayList<>();
		
		for (int i=2; i<input.size(); i++) {
			String[] lineSegments = input.get(i).split(" ");
			if ("#".equals(lineSegments[2])) {
				// This is a plant producing combination.
				plantProducingCombos.add(lineSegments[0].toCharArray());
			}
		}
		
		return true;
	}
	
	public void runGenerations(int nrOfGenerations) {
		for (int i=1; i<=nrOfGenerations; i++) {
			
			determineRange();
			
			logForTest("Starting generation " + i + ". Evaluation will range from " + 
					startIndex + " to " + (pots.getUpperLimit() - 4));

			// Make a copy of the pot array to set the outcomes of the evaluation on.
			TwoWayCharArray temp = new TwoWayCharArray('.', 
					pots.portion(pots.getLowerLimit(), pots.getUpperLimit()), -pots.getLowerLimit());
			
			for (int j=startIndex; j<pots.getUpperLimit()-5; j++) {
				if (producesPlantNextGen(j)) {
					// Set a plant at the central position of the evaluated section of 5 pots.
					temp.setAt('#', j + 2);
				} else {
					temp.setAt('.', j + 2);
				}
			}
			pots = temp;
			logForTest("State after running generation: " + pots);
			int total = sumOfPotNumbersContainingPlants();
			logger.log("Generation " + i + " total: " + total + ", "
					+ "difference from previous generation: " + (total - previousGenTotal));
			previousGenTotal = total;
		}
	}
	
	/**
	 * After running {@link #initialize(boolean)} and {@link #runGenerations(int)}, this will 
	 * return the answer to the A challenge. 
	 */
	public int sumOfPotNumbersContainingPlants() {
		int answer = 0;
		for (int i=startIndex; i<pots.getUpperLimit(); i++) {
			if (pots.getAt(i) == '#') {
				// This pot contains a plant. Its number counts towards the total. 
				answer += i;
			}
		}
		return answer;
	}
	
	/**
	 * Determines the starting and end point for the pot evaluation. A buffer area of 4 pots is
	 * used for both sides of the pot array.
	 */
	private void determineRange() {
		int firstPlant = startIndex;
		while (pots.getAt(firstPlant) != '#') {
			firstPlant++;
		}
		int newLeftMostIndex = firstPlant - 4;
		pots.fit(newLeftMostIndex);
		startIndex = newLeftMostIndex;
		
		firstPlant = pots.getUpperLimit() - 1;
		while (pots.getAt(firstPlant) != '#') {
			firstPlant--;
		}
		pots.fit(firstPlant + 5);		
	}
	
	/**
	 * Determines whether the pot at the given index will grow a plant next generation. 
	 */
	private boolean producesPlantNextGen(int index) {
		// Make a copy of the portion of the array that we will be evaluating.
		char[] section = pots.portion(index, index + 5);
		for (char[] plantProducingCombo : plantProducingCombos) {
			if (Arrays.equals(section, plantProducingCombo)) {
				logForTest("Portion " + Arrays.toString(section) 
					+ " produces a plant next generation!");
				return true;
			}
		}
		return false;
	}

	public List<char[]> getPlantProducingCombos() {
		return plantProducingCombos;
	}

	public TwoWayCharArray getPots() {
		return pots;
	}
}