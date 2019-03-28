package aoc2018.day06;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import aoc2018.AbstractAocAlgorithm;
import aoc2018.AdaptiveCharGrid;
import util.ArrayUtil;
import util.StdFunctions;

public class Coordinator extends AbstractAocAlgorithm {

	private AdaptiveCharGrid grid;
	
	private Map<Character, Integer> areas;
	
	private Set<Character> infiniteAreas;
	
	private int safeSpaceSize;
	
	// First try: 10819 (too high)
	// Second try: 8873 (too high)

	public Coordinator(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public boolean initialize(boolean isTest) {
		if (!super.initialize(2018, 6, isTest)) {
			return false;
		}
		infiniteAreas = new HashSet<>();
		
		grid = new AdaptiveCharGrid();
		areas = new HashMap<>();
		// Name the first node 'A'.
		char safeDangerName = 'A';
		// Fill the grid with the input.
		for (String safeDangerNode : fetcher.getInputLines()) {
			int[] position = ArrayUtil.stringToInt(safeDangerNode.split(","));
			grid.setNodeAtPosition(safeDangerName, position);
			// Add an entry for the new node. Set the initial area size to zero.
			areas.put(safeDangerName, 0);
			logger.log("Node " + safeDangerName + " is at position " + Arrays.toString(position));
			// Move on to the next letter of the alphabet for the node name.
			safeDangerName = (char) (safeDangerName + 1);
		}
		
		return true;
	}
	
	public int determineSafeSpaceSize() {
		
		safeSpaceSize = 0;
		
		for (int y=0; y<grid.getYLength(); y++) {
			for (int x=0; x<grid.getXLength(); x++) {
				
				int[] position = new int[] {x, y};
				
				int totalManhattanDistance = totalDistanceToAllNodes(position);
				// If the total is smaller than 10000, this position is safe.
				if (totalManhattanDistance < 10_000) {
					grid.setAtPosition('#', position);
					safeSpaceSize++;
				}
			}
		}
		logger.log(grid.toString());
		return safeSpaceSize;
	}
	
	
	/**
	 * Traverses the grid, determining the closest node for each position, marking it with the lower
	 * case variant of that node's name. If two or more nodes are equally close, the position is 
	 * given a '.' marker. 
	 */
	public void marco() {
		for (int y=0; y<grid.getYLength(); y++) {
			for (int x=0; x<grid.getXLength(); x++) {
				int[] position = new int[] {x, y};
				char closestNode = determineClosestNode(position);
				// If one node is closest, add this coordinate to its area.
				if (closestNode != '.') {
					areas.put(closestNode, areas.get(closestNode) + 1);
				}
				grid.setAtPosition(closestNode, position);
			}
		}
		
		logger.log(grid.toString());
	}
	
	private char determineClosestNode(int[] position) {
		// Start at the greatest possible Manhattan distance.
		int smallestManhattanDistance = grid.getXLength() + grid.getYLength();
		Character closestNode = null;
		
		for (char nodeName : grid.getNodeNames()) {
			// Determine the Manhattan distance for this node to the given position.
			int[] nodePosition = grid.getNodePosition(nodeName);
			int manhattanDistance = calculateManhattanDistance(position, nodePosition);
			
			if (manhattanDistance < smallestManhattanDistance) {
				smallestManhattanDistance = manhattanDistance;
				closestNode = nodeName;
			} else if (manhattanDistance == smallestManhattanDistance) {
				// Two nodes are equally close to this coordinate. Set the neutral node name.
				closestNode = '.';
			}
		}
		
		return closestNode;
	}
	
	private int totalDistanceToAllNodes(int[] position) {
		int totalDistance = 0;
		for (char nodeName : grid.getNodeNames()) {
			int[] nodePosition = grid.getNodePosition(nodeName);
			int manhattanDistance = calculateManhattanDistance(position, nodePosition);
			totalDistance += manhattanDistance;
		}
		return totalDistance;
	}
	
	private int calculateManhattanDistance(int[] position, int[] otherPosition) {
		int manhattanDistance = StdFunctions.absoluteDifference(position[0], otherPosition[0]);
		manhattanDistance += StdFunctions.absoluteDifference(position[1], otherPosition[1]);
		return manhattanDistance;
	}
	
	private void determineInfiniteAreas() {
		char infinite;
		for (int i=0; i<grid.getXLength(); i++) {
			infinite = grid.charAtPosition(new int[] {i, 0});
			infiniteAreas.add(infinite);
			// Now the one on the lowest row.
			infinite = grid.charAtPosition(new int[] {i, grid.getYLength() - 1});
			infiniteAreas.add(infinite);
		}
		
		for (int i=0; i<grid.getYLength(); i++) {
			infinite = grid.charAtPosition(new int[] {0, i});
			infiniteAreas.add(infinite);
			infinite = grid.charAtPosition(new int[] {grid.getXLength() - 1, i});
			infiniteAreas.add(infinite);
		}
	}
	
	/**
	 * Finds the node that has the biggest area of Manhattan claimable coordinates, while still
	 * being finite, and returns its size.
	 * 
	 * @return the size of the finite node with the biggest area.
	 */
	public int polo() {
		
		determineInfiniteAreas();
		
		int biggestArea = 0;
		for (char node : grid.getNodeNames()) {
			if (infiniteAreas.contains(node)) {
				// Skip infinite areas.
				continue;
			}
			if (areas.get(node) > biggestArea) {
				System.out.println("Biggest area: " + node);
				biggestArea = areas.get(node);
			}
		}
		return biggestArea;
	}
}
