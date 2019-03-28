package adventOfCode.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RecursiveDisc implements Comparable<RecursiveDisc> {

	private String name;
	
	private Integer weight;
	
	private Integer stackWeight;
	
	private String[] upperDiscNames;
	
	private List<RecursiveDisc> upperDiscs;
	
	public RecursiveDisc(String name, Integer weight) {
		this.name = name;
		this.weight = weight;		upperDiscs = new ArrayList<>();
	}

	public void addUpperDisc(RecursiveDisc upperDisc) {
		upperDiscs.add(upperDisc);
	}
	
	/**
	 * The recursive power of this composite class hides in this method.
	 * When calling this method on the lowest disc, it will search through
	 * the given map to find all its upper discs by {@link #upperDiscNames}.
	 * When finding an upper disc, this method is called on it, with the same
	 * map as input, thereby kicking off the recursion.
	 * 
	 * @param recursiveDiscMap - the map holding all the name-RecursiveDisc
	 * pairs that should be sorted.
	 */
	public void addUpperDiscs(Map<String, RecursiveDisc> recursiveDiscMap) {
		// If this disc holds no upper discs, we are done.
		if (upperDiscNames == null)
			return;
		
		for (String upperDiscName : upperDiscNames) {
			RecursiveDisc upperDisc = recursiveDiscMap.remove(upperDiscName);
			addUpperDisc(upperDisc);
			upperDisc.addUpperDiscs(recursiveDiscMap);
		}
	}
	
	/**
	 * More recursive power. Finds the unevenly weighted upper disc by comparing
	 * the weight of all its upper discs, and if no unevenly weighted disc is found, 
	 * calling this method for each upper disc, returning the faulty program when it 
	 * is found.
	 * 
	 * @return the name of the unevenly weighted disc in {@link Optional} wrapper.
	 */
	public Optional<String> findUnevenlyWeightedUpperDisc() {
		
		// Check whether all this disc's upper discs are of equal weight.
		// To do this, sort the upper discs by weight and compare the first and second discs.
		if (upperDiscs.size() < 2) {
			// This disc does not have enough upper discs to compare.
			if (upperDiscs.size() == 1)
				// This disc is holding one disc, which might be holding more.
				return upperDiscs.get(0).findUnevenlyWeightedUpperDisc();
			else
				// This disc is not holding any discs.
				return Optional.empty();
		}
		
		Collections.sort(upperDiscs);
		
		int weightStackOne = upperDiscs.get(0).sumStack();
		int weightStackTwo = upperDiscs.get(1).sumStack();
		
		if (weightStackOne != weightStackTwo)
			// The first disc is heavier than the others.
			return upperDiscs.get(0).findUnevenlyWeightedUpperDisc();

		// This disc's upper discs'stack are even, so this disc must be the problem.
		// TODO: this is not the solution to the problem: the desired weight of the uneven disc should be returned.
		// TODO: while pondering the solution to the day 9 problem, I got the feeling that an 
		// TODO: external RecursiveDiscBalancer might be better suited for this task...but I don't know how yet.
		return Optional.of(name);
	}
	
	/**
	 * Computes the sum of this disc's stack, i.e. the weight of all its upper discs plus its own
	 * weight.
	 * @return the sum of the weight of all this stack's upper discs, plus its own.
	 */
	public int sumStack() {
		int sum = 0;	
		for (RecursiveDisc upperDisc : upperDiscs) {
			sum += upperDisc.sumStack();
		}
		stackWeight = sum + weight;
		System.out.println("Sum of stack of disc " + name + ": " + stackWeight);
		return sum + weight;
	}
	
	// Standard getters and setters.
	public List<RecursiveDisc> getUpperDiscs() {
		return upperDiscs;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String[] getUpperDiscNames() {
		return upperDiscNames;
	}

	public void setUpperDiscNames(String[] upperDiscNames) {
		this.upperDiscNames = upperDiscNames;
	}

	/**
	 * Compares this disc with other {@link RecursiveDisc} o, so that the disc with the highest
	 * stack weight will precede the disc with lower stack weight.
	 */
	@Override
	public int compareTo(RecursiveDisc o) {
		
		if (sumStack() > o.sumStack())
			return -1;
		if (sumStack() < o.sumStack())
			return 1;
		
		return 0;
	}
	
	public String recursiveToString(int level) {
		
		String spaces = "";
		
		for (int i=0; i<level; i++) {
			spaces += " ";
		}
		
		String toString = spaces + "Disc " + name + ", weight: " + weight + ", stack weight: " + sumStack() + System.lineSeparator();
		
		for (RecursiveDisc upperDisc : upperDiscs) {
			toString += upperDisc.recursiveToString(level + 3);
		}
		return toString;
	}
}
