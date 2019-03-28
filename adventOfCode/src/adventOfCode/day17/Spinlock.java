package adventOfCode.day17;

import java.util.ArrayList;

import adventOfCode.AlgorithmTimer;

public class Spinlock {

	/** The number of steps taken before each insertion. */
	private int stepSize;
	
	private int currentPosition;
	
	private int currentValue;
	
	private ArrayList<Integer> circularBuffer;
	
	private int totalNrOfValues;
	
	private int nextToNothing;
	
	private AlgorithmTimer timer;
	
	// First try challenge B: 1869358 -- too low
	// Second try (correct): 10242889
	
	public Spinlock(int stepSize) {
		// Default value, for challenge A implementation.
		totalNrOfValues = 2018;
		this.stepSize = stepSize;
		timer = new AlgorithmTimer(false);
	}
	
	public Spinlock(int stepSize, int totalNrOfValues) {
		this(stepSize);
		this.totalNrOfValues = totalNrOfValues;
	}
	
	public void run() {
		
		initialize();
		
		timer.startAlgorithm("Spinlock.run()");
		
		while (currentValue < totalNrOfValues) {
			determineNextPosition();
			// Add the current value to the buffer, right after the current position.
			System.out.println("Adding value: " + currentValue + " at index: " + currentPosition);
			
			if (totalNrOfValues == 2018) {
				circularBuffer.add(currentPosition, currentValue);
			}
			
			if (currentPosition == 0) {
				nextToNothing = currentValue;
			}
			
			currentValue++;			
		}
		timer.stopAlgorithm();
	}
	
	private void initialize() {
		currentPosition = 0;
		currentValue = 1;
		// Initialize the buffer and add the first value to it.
		circularBuffer = new ArrayList<>();
		circularBuffer.add(0);
	}
	
	/**
	 * Determines the next position at which to insert the next value.
	 */
	private void determineNextPosition() {
		
		// Improvement idea 1: only keep track of the current position of (value/ground) zero, 
		// increasing it if the next value would land to the left of it. If the next value ends up
		// directly to the right of the zero, update the 'nextToNothing'/answer variable.
		// ...it appears that wouldn't work, as in the current algorithm, the zero is always in the
		// last position in the list - which means keeping track of the value inserted at position
		// 0 would be enough...
		
		int nrOfNonWrappedSteps = stepSize % currentValue;
		
		currentPosition += nrOfNonWrappedSteps + 1;

		if (currentPosition >= currentValue) {
			currentPosition = currentPosition - currentValue;
		}
	}

	public int getNextValue() {
		return circularBuffer.get(currentPosition + 1);
	}
	
	public Integer getValueAfterZero() {
		return nextToNothing;
	}
}
