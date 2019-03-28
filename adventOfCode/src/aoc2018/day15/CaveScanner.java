package aoc2018.day15;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aoc2018.CharMatrix;
import log.Fallible;

public class CaveScanner implements Fallible {

	private CharMatrix cave;

	public CaveScanner(CharMatrix cave) {
		this.cave = cave;
	}
	
	// 1) Determine all adjacent open squares to the given position.
	// 2) For each of these squares, determine the adjacent open squares that have not been 
	// selected so far (first round, the given position is the only one).
	// 3) Repeat 2) until a valid square adjacent to an enemy is found.
	
	/**
	 * This method's name is pretty descriptive. Reading order is observed.
	 * @param inRangeSquares - a list of the squares that are next to an enemy.
	 * @param fromPosition - the starting position (first square in resulting path).
	 */
	public PathSection determinePathToClosestReachableSquare(List<int[]> inRangeSquares, 
			int[] fromPosition) {
		
		List<int[]> reachableSquares = determineAdjacentOpenSquares(fromPosition);
		List<int[]> encounteredSquares = new ArrayList<>();
		// This is dodgy. The PathSection's logic depends heavily on the logic of this method.
		List<PathSection> validPaths = new ArrayList<>();
		for (int[] adjacentOpenSquare : reachableSquares) {
			validPaths.add(new PathSection(adjacentOpenSquare));
		}
		
		encounteredSquares.add(fromPosition);
		
		while (reachableSquares.size() > 0) {
			// Initialize a temporary list to which next rounds reachable squares will be added.
			List<int[]> nextRoundSquares = new ArrayList<>();

			for (int[] square : reachableSquares) {
				if (containsPosition(encounteredSquares, square)) {
					// We've already been here. Skip to avoid repeating ourselves.
					continue;
				}
				
				if (containsPosition(inRangeSquares, square)) {
					// Found the closest in range square. Return this position.
					for (PathSection path : validPaths) {
						if (path.addLink(new PathSection(square))) {
//							logChosenPath(path, validPaths);
							return path;
						}
					}
					logger.log("determineClosestReachableSquare - failed to link the closest in "
							+ "range square to a path!");
					return null;
				}
				// Found a new linkable section. Add it to the collection. 
				for (PathSection possibleLink : validPaths) {
					possibleLink.addLink(new PathSection(square));
				}
				encounteredSquares.add(square);
				nextRoundSquares.addAll(determineAdjacentOpenSquares(square));
			}
			// Loop through all squares in this layer around the fromPosition completed. 
			// No square in range of an enemy encountered yet, so prepare for another round.
			reachableSquares = nextRoundSquares;
		}
		
		// No reachable square found.
		return null;
	}
	
	public boolean containsPosition(List<int[]> squares, int[] position) {
		for (int[] pos : squares) {
			if (pos[0] == position[0] && pos[1] == position[1]) {
				return true;
			}
		}
		return false;
	}
	
	private void logChosenPath(PathSection chosen, List<PathSection> options) {
		String message = "Chosen path: " + chosen + System.lineSeparator();
		for (PathSection section : options) {
			if (chosen == section) {
				continue;
			}
			message += "Rejected: " + section + System.lineSeparator();
		}
		logger.log(message);
	}
	
	/**
	 * Composes a list of all open squares that are directly adjacent to the square at the given 
	 * position. If more than one squares are found, the results are ordered in reading order.
	 */
	public List<int[]> determineAdjacentOpenSquares(int[] position) {
		List<int[]> openSquares = determineAdjacentSquares(position);
		// Only return the open squares.
		return openSquares.stream()
			.filter((adjacentPosition)-> cave.safeGetCharAt(adjacentPosition) == '.')
			.collect(Collectors.toList());
	}

	public List<int[]> determineAdjacentSquares(int[] position) {
		List<int[]> adjacentSquares = new ArrayList<>();
		adjacentSquares.add(new int[] { position[0], position[1] - 1 }); // top
		adjacentSquares.add(new int[] { position[0] - 1, position[1] }); // left
		adjacentSquares.add(new int[] { position[0] + 1, position[1] }); // right
		adjacentSquares.add(new int[] { position[0], position[1] + 1 }); // bottom
		return adjacentSquares;
	}
	
}
