package adventOfCode.day6;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MemoryReallocator {

	private Set<int[]> pastStates = new TreeSet<>(new IntArrayComparator());
	
	private Map<int[], Integer> pastStatesPerCycle = new TreeMap<>(new IntArrayComparator());
	
	private boolean isDay6b = false;
	
	public MemoryReallocator(boolean isDay6b) {
		this.isDay6b = isDay6b;
	}
	
	public int reallocate(int[] memoryBank) {
		
		if (memoryBank.length != 16)
			throw new UnsupportedOperationException("The array should "
					+ "contain sixteen values!");
		
		int nrOfCycles = 0;
		
		boolean isNewState = true;
		
		while (isNewState) {
			
			nrOfCycles++;
			
			// Determine the index of the entry with the most blocks.
			int indexOfMostBlocks = findIndexMostBlocks(memoryBank);
			
			// Get the number of blocks and set the entry to zero.
			int mostBlocks = memoryBank[indexOfMostBlocks];
			memoryBank[indexOfMostBlocks] = 0;
			
			// Redistribute all blocks over the entries, starting at the 
			// entry after the one that held the most blocks.
			redistributeMemory(memoryBank, mostBlocks, ++indexOfMostBlocks);
			
			// Check if the memory blocks are now evenly distributed 
			// OR whether the resulting state was encountered before. 
			if (isEvenlyDistributed(memoryBank)
					|| (!isDay6b && isReoccuringState(memoryBank))
					|| (isDay6b && isReoccuringState(memoryBank, nrOfCycles))
					) {
				
				System.out.println("Exiting the loop! "
						+ "Current state: " + Arrays.toString(memoryBank));
				break;
			}
			// Log the state we just encountered.
			if (nrOfCycles % 1000 == 0) 
				System.out.println("Just went through a thousand cycles! "
						+ "Current state: " + Arrays.toString(memoryBank));			
		}
		
		if (isDay6b)
			return nrOfCycles - pastStatesPerCycle.get(memoryBank);
		else
			return nrOfCycles;
	}

	/**
	 * Finds the index of the entry that currently holds the most memory
	 * blocks. In case of a tie, the lowest index is returned.
	 * 
	 * @return the index of the entry with the most memory blocks.
	 */
	private int findIndexMostBlocks(int[] memoryBank) {
		
		int mostBlocks = 0;
		int indexOfMostBlocks = 0;
		
		for (int i=0; i<memoryBank.length; i++) {
			if (memoryBank[i] > mostBlocks) {
				indexOfMostBlocks = i;
				mostBlocks = memoryBank[i];
			}
		}
		return indexOfMostBlocks;
	}
	
	/**
	 * Evenly redistributes the given memory block over the entries in
	 * the array, starting at the provided index.
	 */
	private void redistributeMemory(int[] memoryBank, int blocks, int index) {
		while (blocks != 0) {
			// If the index exceeds the length of the array, reset it.
			if (index == memoryBank.length)
				index = 0;
			// Add the block to the current entry.
			memoryBank[index]++;
			// Update the index and blocks variables.
			blocks--;
			index++;
		}
	}

	/**
	 * Makes a deep copy of the current memory bank and adds it to the 
	 * {@link #pastStates} set. If the set already contains the given state,
	 * false is returned.
	 *   
	 * @param currentMemoryBank - the the current state of the memory bank.
	 */
	private boolean isReoccuringState(int[] currentMemoryBank) {
		int[] copy = new int[16];
		System.arraycopy(currentMemoryBank, 0, copy, 0, 16);
		return !pastStates.add(copy);
	}
	
	private boolean isReoccuringState(int[] currentMemoryBank, int currentCycle) {
		int[] copy = new int[16];
		System.arraycopy(currentMemoryBank, 0, copy, 0, 16);
		
		if (pastStatesPerCycle.containsKey(copy))
			return true;
		
		pastStatesPerCycle.put(copy, currentCycle);
		return false;
	}
	
	private boolean isEvenlyDistributed(int[] currentState) {
		
		boolean isEvenlyDistributed = true;
		int firstEntry = currentState[0];
		
		for (int i : currentState) {
			if (i != firstEntry) {
				isEvenlyDistributed = false;
				break;
			}
		}
		return isEvenlyDistributed;
	}	
}
