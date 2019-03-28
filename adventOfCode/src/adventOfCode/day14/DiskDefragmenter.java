package adventOfCode.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.AlgorithmTimer;
import adventOfCode.day10.HashKnot;
import util.Util;

public class DiskDefragmenter {

	/** The puzzle input: the key used to generate the hash knots. */
	private final static String KEY = "hfdlxzhv";
	
//	private final static String TEST_KEY = "flqrgnkx";
	
	private AlgorithmTimer timer;

	// Since this challenge involves some operations on hash knots based on the puzzle input,
	// reusing the HashKnot class from day 10 might be a good idea.
	private HashKnot gordias;

	private int totalCountUsedSquares;
	
	private List<String> binaryGrid;

	private int[] lastRow;
	
	private int yIndex;
	
	private Map<Integer, Region> regionMap;
	
	private int maxRegionId;
	
	// total count first run: 8252 (too high)
	// voorbeeld komt precies 100 te hoog uit...
	// total count second run: 8152 (too low)
	// ...werkt voor input alleen net niet :P
	// GODDAMN STATE
	// Vond het al zo raar dat die eerste rij perfect klopte en de rest totaal niet...
	
	public DiskDefragmenter() {
		gordias = new HashKnot();
		gordias.setIsDebug(false);
		timer = new AlgorithmTimer(false);
		binaryGrid = new ArrayList<>();
	}
	
	
	public void defragment() {
		List<String> hashKnots = computeHashKnots();
		
		totalCountUsedSquares = 0;
		for (String hashKnot : hashKnots) {
			String binary = hexDoubleToBinaryRow(hashKnot);
			int totalCountRow = countUsedSquares(binary);
			System.out.println("Total count of hash knot " + hashKnot + ": " + totalCountRow);
			System.out.println("Binary representation: " + binary);
			binaryGrid.add(binary);
			Util.underline(75);
			totalCountUsedSquares += totalCountRow;
		}
		printFirstEightByEight();		
	}
	
	public void registerRegions() {
		maxRegionId = 1;
		lastRow = new int[128];
		regionMap = new HashMap<>();
		for (int i=0; i<binaryGrid.size(); i++) {
			yIndex = i;
			processRegionsOnRow(binaryGrid.get(i).toCharArray());
		}
		
	}
	
	private void processRegionsOnRow(char[] row) {

		for (int i=0; i<row.length; i++) {
			
			if (row[i] == '1') {
				// This is a used square. Check if whether it belongs to an existing region.
				if (i - 1 >= 0 && lastRow[i-1] > 0) {
					// We're past the first square on this row, and the previous square is used.
					// This square belongs to that region.
					int regionMark = lastRow[i-1];
					
					Region region = regionMap.get(regionMark);
					
					if (region == null)
						System.out.println("Breakpoint!");
					
					region.addSquare(new int[] {i, yIndex});
					
					int regionMarkOfAboveSquare = lastRow[i];
					// Set the region mark on this spot in the array.
					lastRow[i] = regionMark;

					if (regionMarkOfAboveSquare > 0
							&& regionMarkOfAboveSquare != regionMark) {
						
						// The square above this one is also used and these two squares belong to
						// different regions at this point, so we have to merge these regions.	
						Region firstRegion = regionMap.get(regionMarkOfAboveSquare);
						System.out.println("About to merge regions " 
								+ firstRegion.getMark() + " and " + region.getMark());
						mergeRegions(region, firstRegion);
						
						System.out.println("Last row after merge: " + Arrays.toString(lastRow));
						System.out.println("Merge initiated at index: " + i);
					}
				} else if (lastRow[i] > 0) {
					// The square above this one is also used. Add this square to that region.
					int regionMark = lastRow[i];
					Region region = regionMap.get(regionMark);
					
					region.addSquare(new int[] {i, yIndex});
					// Since the region stays the same, no need to update lastRow.
					
				} else {
					// This used square is not connected to any other used squares. 
					// That means this is the start of a new Region.
					int newRegionMark = maxRegionId++;
					Region newRegion = new Region(newRegionMark);
					newRegion.addSquare(new int[] {i, yIndex});
					regionMap.put(newRegionMark, newRegion);
					lastRow[i] = newRegionMark;
				}
			} else {
				// Set this entry on the lastRow array to zero, to mark it as unused.
				lastRow[i] = 0;
			}
		}
	}
	
	private Region mergeRegions(Region first, Region second) {

		int mergedMark = first.getMark() < second.getMark() ? first.getMark() : second.getMark();
		int obsoleteRegionMark = first.getMark() > second.getMark() ? first.getMark() : second.getMark();
		Region mergedRegion = new Region(mergedMark); 
		mergedRegion.addSquares(first.getSquares());
		mergedRegion.addSquares(second.getSquares());
		mergedRegion.setObsoleteRegionMark(obsoleteRegionMark);

		regionMap.put(mergedRegion.getMark(), mergedRegion);
		regionMap.remove(mergedRegion.getObsoleteRegionMark());

		// Overwrite the region mark for this square and the other squares belonging
		// to the obsolete region with the mark of the new region.
		System.out.println("Last row before merge: " + Arrays.toString(lastRow));
		for (int j=0; j<lastRow.length; j++) {
			if (lastRow[j] == obsoleteRegionMark)
				lastRow[j] = mergedRegion.getMark();
		}
		// ...this is fucked up
		return first;
	}
	
	private void printFirstEightByEight() {
		for (int i=0; i< 8; i++) {
			System.out.println(binaryGrid.get(i).substring(0, 8));
		}
	}

	private String hexDoubleToBinaryRow(String hashKnot) {
		int i = 0;
		String binaryString = "";
		while (i < hashKnot.length()) {
			
			String hexNumber = hashKnot.substring(i, i + 2);
			int hexToDecimal = Integer.parseInt(hexNumber, 16);
			String binary = Integer.toBinaryString(hexToDecimal);
			
			while (binary.length() < 8)
				binary = "0" + binary;
			
			binaryString += binary;
			// Increment by two, so next double digit hex will be selected next round.
			i += 2;
		}
		return binaryString;
	}
	
	private int countUsedSquares(String toCount) {
		int answer = 0;
		for (char square : toCount.toCharArray()) {
			if (square == '1')
				answer++;
		}
		return answer;
	}
	

	private List<String> computeHashKnots() {
		List<String> hashKnots = new ArrayList<>();
		// The hash inputs are composed of the key, a dash, and row number (128 rows total). 
		for (int i=0; i<128; i++) {
			gordias = new HashKnot(256);
			gordias.setIsDebug(false);
			timer.startAlgorithm("HashKnot.computeHashKnot()");
			String input = KEY + "-" + i;
			System.out.println("Input: " + input);
			String hashKnot = gordias.computeHashKnot(input.toCharArray());
			timer.stopAlgorithm();
			hashKnots.add(hashKnot);
		}
		return hashKnots;
	}

	public int getTotalCountUsedSquares() {
		return totalCountUsedSquares;
	}
	
	public int getNrOfRegions() {
		System.out.println("Last row after process: " + Arrays.toString(lastRow));
		return regionMap.size();
	}
}
