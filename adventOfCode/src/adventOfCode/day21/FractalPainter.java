package adventOfCode.day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.AbstractAlgorithm;
import util.Util;

public class FractalPainter extends AbstractAlgorithm {

	/** The pattern that the fractal always starts with. */
	private final static char[][] STARTING_PATTERN = new char[][] {
			new char[] {'.', '#', '.'},
			new char[] {'.', '.', '#'},
			new char[] {'#', '#', '#'}
	};
	
	/** The rules that are read from the input file. */
	private Map<FractalFragment, FractalFragment> ruleBook;
	
	/** The fractal that is being enhanced. */
	private char[][] fractal;
	
	private boolean inDebugMode;
	
	// Challenge B, first try: 2425195 (right again!!!)
	
	public FractalPainter(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public FractalPainter(boolean isCommandLineFine, boolean inDebugMode) {
		super(isCommandLineFine);
		this.inDebugMode = inDebugMode;
	}
	
	/**
	 * Enhances the starting pattern according to the enhancement rules, for the number of 
	 * iterations specified;
	 */
	public void enhance(int iterations) {
		// Set the fractal to the starting pattern.
		fractal = STARTING_PATTERN;
		
		timer.startAlgorithm("FractalPainter.enhance()");
		
		for (int i=0; i<iterations; i++) {
			
			List<FractalFragment> fragments = divide(fractal);
			
			for (int j=0; j<fragments.size(); j++) {
				
				// Update this fragment conform the rule book.
				FractalFragment enhancedFragment = ruleBook.get(fragments.get(j));
				
				if (inDebugMode) {
					System.out.print("Fragment " + System.lineSeparator() + fragments.get(j));
					System.out.println(" will be enhanced to: " + System.lineSeparator() 
						+ enhancedFragment);
				}
				fragments.set(j, enhancedFragment);
			}
			
			if (inDebugMode)
				Util.underline();
			
			fractal = join(fragments);
		}
		timer.stopAlgorithm();
	}
	
	private char[][] join(List<FractalFragment> fragments) {
		int fragmentSize = fragments.get(0).getGrid().length;
		int fragmentsPerRow = (int) Math.sqrt(fragments.size());
		int superSize = fragmentsPerRow * fragmentSize;
		
		char[][] joinedFragments = new char[superSize][superSize];
		
		// Loop over the rows in the grid.
		for (int i=0; i<fragmentsPerRow; i++) {
			// Loop over the sections of the row and create new fragments.
			for (int j=0; j<fragmentsPerRow; j++) {
				
				char[][] fragmentGrid = fragments.get(i * fragmentsPerRow + j).getGrid();
				
				for (int k=0; k<fragmentSize; k++) {
					int absoluteRowIndex = i * fragmentSize + k;
					int absoluteColumnIndex = j * fragmentSize;
					System.arraycopy(fragmentGrid[k], 0, 
							joinedFragments[absoluteRowIndex], absoluteColumnIndex, fragmentSize);
				}
			}
		}
		return joinedFragments;
	}
	
	/**
	 * Divides the given grid into grids of size 2 (if grid size is divisible by 2) or size 3 
	 * otherwise. For each of these grid fragments, a {@link FractalFragment} is constructed
	 * and added to the result list.
	 * 
	 * @param grid - the grid to divide into smaller grids.
	 * @return the resulting list of fragments.
	 */
	private List<FractalFragment> divide(char[][] grid) {
		
		List<FractalFragment> results = new ArrayList<>();
		
		int nrOfFragments;
		int fragmentSize;
		if (grid.length % 2 == 0) {
			nrOfFragments = grid.length / 2;
			fragmentSize = 2;
		} else {
			nrOfFragments = grid.length / 3;
			fragmentSize = 3;
		}
		
		// Loop over the rows in the grid.
		for (int i=0; i<nrOfFragments; i++) {
			// Loop over the sections of the row and create new fragments.
			for (int j=0; j<nrOfFragments; j++) {
				
				char[][] newGrid = new char[fragmentSize][fragmentSize];
				
				for (int k=0; k<fragmentSize; k++) {
					int absoluteRowIndex = i * fragmentSize + k;
					int absoluteColumnIndex = j * fragmentSize;
					System.arraycopy(grid[absoluteRowIndex], absoluteColumnIndex, 
							newGrid[k], 0, fragmentSize);
				}
				results.add(new FractalFragment(newGrid));
			}
		}
		return results;
	}
	
	/**
	 * Reads the rules from the input file and converts them into entries in the {@link #ruleBook}.
	 */
	public void readRules(String inputFile) {
		
		ruleBook = new HashMap<>();
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] flatGrids = line.split(" ");
				FractalFragment matcher = buildFractalFragment(flatGrids[0]);
				FractalFragment enhancedGrid = buildFractalFragment(flatGrids[2]);
				ruleBook.put(matcher, enhancedGrid);
			}
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	private FractalFragment buildFractalFragment(String input) {
		String[] rows = input.split("/"); 
		char[][] grid = new char[rows.length][];
		
		for (int i=0; i<grid.length; i++) {
			grid[i] = rows[i].toCharArray();
		}
		return new FractalFragment(grid);
	}
	
	/**
	 * After running {@link #enhance(int)}, this returns the total number of pixels of the fractal
	 * that are 'on'.
	 * @return the total. 
	 */
	public int getTotalPixelsOn() {
		timer.startAlgorithm(getClass().getSimpleName() + ".getTotalPixelsOn()");
		FractalFragment temp = new FractalFragment(fractal);
		int answer = temp.totalPixelsOn();
		timer.stopAlgorithm();
		return answer;
	}
	
	@Override
	public String toString() {
		FractalFragment temp = new FractalFragment(fractal);
		return temp.toString();
	}	
}
