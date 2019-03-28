package aoc2018.day10;

import java.util.ArrayList;
import java.util.List;

import aoc2018.AbstractAocAlgorithm;
import aoc2018.AdaptiveCharGrid;
import util.Util;

public class StarWatcher extends AbstractAocAlgorithm {
	
	private List<Star> firmament;
	
	private int turn;
	
	private AdaptiveCharGrid sky;
	
	public StarWatcher(boolean isCommandLineFine) {
		super(isCommandLineFine);
		firmament = new ArrayList<>();
		sky = new AdaptiveCharGrid();
	}
	
	public boolean initialize(boolean isTest) {
		if (!super.initialize(2018, 10, isTest)) {
			return false;
		}
		
		turn = 0;
		
		for (String input : fetcher.getInputLines()) {
			Star star = new Star(input);
			firmament.add(star);
		}
		
		return true;
	}
	
	public void runUntilCoherent() {
		while (!isCoherent()) {
			logger.log("Starting turn: " + turn);
			
			for (Star star : firmament) {
				star.updatePosition();
			}
			turn++;
			
			if (isTest) {
				logger.log(Util.line(75));
			}
		}
		// The stars have formed a coherent message. Add them to a 'canvas' for display.
		for (Star star : firmament) {
			sky.setAtPosition('#', star.getPosition());
		}
		logger.log(sky.snugToString());
	}

	/**
	 * Tests whether the current alignment of stars is coherent, i.e. whether any given star has 
	 * another star next to it ('next' could also be diagonally). 
	 */
	private boolean isCoherent() {
		outer:
		for (int i=0; i<firmament.size(); i++) {
			Star star = firmament.get(i);
			for (int j=0; j<firmament.size(); j++) {
				if (star.isAdjacent(firmament.get(j))) {
					
					if (isTest) {
						logger.log(firmament.get(i) + " and " 
							+ firmament.get(j) + " are adjacent.");
					}
					continue outer;
				}
			}
			
			if (isTest) {
				logger.log(star + " is not adjacent to any other star!");
			}
			return false;
		}
		return true;
	}
	
	public int getTurn() {
		return turn;
	}
}
