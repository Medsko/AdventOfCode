package adventOfCode.day15;

import adventOfCode.AlgorithmTimer;

public class Judge {

	private Generator generatorA;
	
	private Generator generatorB;
	
	private static int FORTY_MILLION = 40_000_000;

	private static int FIVE_MILLION = 5_000_000;
	
	private int nrOfLoops;
	
	private AlgorithmTimer timer;
	
	// Output
	
	private int nrOfMatchingPairs;

	/** Constructor for challenge A variant. */
	public Judge(int startingValueA, int startingValueB) {
		generatorA = new Generator(startingValueA, 16807);
		generatorB = new Generator(startingValueB, 48271);
		nrOfLoops = FORTY_MILLION;
		timer = new AlgorithmTimer(false);
	}
	
	/** Constructor for challenge B variant. */
	public Judge(int startingValueA, int criterionA, 
				int startingValueB, int criterionB) {
		generatorA = new Generator(startingValueA, 16807, criterionA);
		generatorB = new Generator(startingValueB, 48271, criterionB);
		nrOfLoops = FIVE_MILLION;
		timer = new AlgorithmTimer(false);
	}

	// FIRST TRY:
	// Number of matching pairs: 4882
	
	public void judge() {
		timer.startAlgorithm("Judge.judge()");
		timer.startSubAlgorithm();
		
		nrOfMatchingPairs = 0;
		
		for (int i=0; i<nrOfLoops; i++) {
			String binaryA = generatorA.nextValueAsBinary();
			String binaryB = generatorB.nextValueAsBinary();
			
			if (isMatchingPair(binaryA, binaryB)) {
				System.out.println("Found match at rotation: " + (i + 1));
				nrOfMatchingPairs++;
			}
			
			if (i==10)
				timer.stopSubAlgorithm();
		}
		
		timer.stopAlgorithm();
	}
	
	private boolean isMatchingPair(String binaryA, String binaryB) {
		// If the lower 16 bits match, the pair is considered a match. 
		for (int i=1; i<=16; i++) {
			
			char a = determineBinaryAt(binaryA, i);
			char b = determineBinaryAt(binaryB, i);
			
			if (a != b)
				// No match. 
				return false;
		}
		System.out.println("Found a match for binaries: " + binaryA + " and " + binaryB);
		return true;
	}
	
	/**
	 * Returns the binary value at 'rotation' number of indices from the right end of the given
	 * String. If this index would be out of range, a zero is returned. Using this method, it should
	 * no longer be necessary to zero-pad binary Strings.
	 */
	private char determineBinaryAt(String binaryString, int rotation) {
		// Using this method of determining the binary value does not significantly influence
		// algorithm performance...but since it allows for binary strings that have not been
		// zero-padded, it can decrease the entire completion time 10 times!
		int index = binaryString.length() - rotation;
		
		if (index < 0)
			return '0';
		else
			return binaryString.charAt(index);
	}

	public int getNrOfMatchingPairs() {
		return nrOfMatchingPairs;
	}
}
