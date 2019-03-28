package aoc2018.day07;

import java.util.ArrayList;
import java.util.List;

public class AssemblyStep {

	private Character name;
	
	private List<Character> prerequisites;
	
	private List<Character> dependants;
	
	public AssemblyStep(Character name) {
		this.name = name;
		prerequisites = new ArrayList<>();
		dependants = new ArrayList<>();
	}
	
	public void addPrerequisite(Character prerequisite) {
		prerequisites.add(prerequisite);
	}
	
	public void addDependant(Character dependant) {
		dependants.add(dependant);
	}

	public int getExecutionTime() {
		// Integer values of upper case characters starts at 65 for 'A'.
		return (int) name - 64;
	}
	
	public Character getName() {
		return name;
	}
	
	public List<Character> getPrerequisites() {
		return prerequisites;
	}

	public List<Character> getDependants() {
		return dependants;
	}
}
