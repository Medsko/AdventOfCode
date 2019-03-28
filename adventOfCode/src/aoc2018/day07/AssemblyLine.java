package aoc2018.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import aoc2018.AbstractAocAlgorithm;

public class AssemblyLine extends AbstractAocAlgorithm {
	
	private List<AssemblyStep> inputSteps;
	
	private String processedSteps;

	/** The amount of seconds remaining to finish a given step, per worker. */
	private int[] workers;
	
	private Character[] executingSteps;
	
	private int baseStepExecutionTime;
	
	// Answer challenge A: OCPUEFIXHRGWDZABTQJYMNKVSL
	
	// Execution order B:  OPCUXEHFIRWZGDABTQYJMNKVSL
	
	// First try challenge B: 1004 (too high)
	// Second try challenge B: 992 (still too high)...
	
	
	public AssemblyLine(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public boolean initialize(boolean isTest) {
		if (!initialize(2018, 7, isTest)) {
			return false;
		}
		
		if (isTest) {
			// In the test scenario, only 2 workers are available, but the steps only take as long
			// as the integer equivalent of their name (A = 1 etc; subtract 64 from char value).
			baseStepExecutionTime = 0;
			workers = new int[2];
			executingSteps = new Character[2];
		} else {
			baseStepExecutionTime = 60;
			workers = new int[5];
			executingSteps = new Character[5];
		}

		Map<Character, AssemblyStep> steps = new HashMap<>();

		for (String line : fetcher.getInputLines()) {
			String[] splitLine = line.split(" ");
			Character stepName = splitLine[7].toCharArray()[0];
			Character prereqName = splitLine[1].toCharArray()[0];
			AssemblyStep step = steps.get(stepName);
			AssemblyStep prereq = steps.get(prereqName);
			
			// If no step of this name has been encountered yet, instantiate one now.
			if (step == null) {
				step = new AssemblyStep(stepName);
				steps.put(stepName, step);
			}
			// Same for prerequisite step.
			if (prereq == null) {
				prereq = new AssemblyStep(prereqName);
				steps.put(prereqName, prereq);
			}
			// Add the link.
			step.addPrerequisite(prereqName);
			prereq.addDependant(stepName);
		}
		// Log the names of the input steps.
		String allStepNames = "";
		for (Character name : steps.keySet()) {
			allStepNames += name;
		}
		System.out.println("All steps: " + allStepNames);
		
		// Now add the finalized input steps to the list.
		inputSteps = new ArrayList<>();
		inputSteps.addAll(steps.values());
		// Initialize the processed steps variable.
		processedSteps = "";

		return true;
	}
	
	public int constructSleigh() {
		LinkedList<AssemblyStep> availableSteps = new LinkedList<>();
		int totalExecutionTime = -1;
		// Keep executing turns until all steps have been executed.
		while (!allStepsDone(availableSteps)) {
			
			// First advance all workers one turn, as this might make more steps available.
			for (int i=0; i<workers.length; i++) {
				
				if (workers[i] != 0) {
					// Advance the work on this step by one second.
					workers[i]--;
					
					if (workers[i] == 0) {
						// This worker has finished its work on the current step.
						logger.log("Worker " + i + " has finished working on step " 
								+ executingSteps[i] + " !");
						processedSteps += executingSteps[i];
						executingSteps[i] = null;
					}
				}
			}

			// Determine all available steps.
			getAvailableSteps(availableSteps);
			sortStepsAlphabetically(availableSteps);

			// Now assign new available steps to idle workers.
			for (int i=0; i<workers.length; i++) {
			
				if (workers[i] == 0) {
					// This worker is not working on any steps yet. Select the next step to execute
					// and assign it to this worker.
					AssemblyStep nextStep = availableSteps.poll();
					
					if (nextStep == null) {
						// No next step is available yet. Sadly, this worker will remain unemployed.
						continue;
					}
					logger.log("Worker " + i + " is now starting work on step " + nextStep.getName());
					// Determine the execution time of the next step.
					int executionTime = nextStep.getExecutionTime();
					executionTime += baseStepExecutionTime;
					// Put the worker to work on the step. 
					workers[i] = executionTime;
					executingSteps[i] = nextStep.getName();
				}

			}
			
			logger.log("Finishing up second " + totalExecutionTime + " now. Steps finished so far: "
					+ processedSteps);
			totalExecutionTime++;
		}
		
		return totalExecutionTime;
	}
	
	private boolean allStepsDone(List<AssemblyStep> availableSteps) {
		boolean done = true;
		for (Character step : executingSteps) {
			if (step != null) {
				// This worker is still working on a step...
				done = false;
			}
		}
		return done && inputSteps.isEmpty() && availableSteps.isEmpty();
	}
	
	private List<AssemblyStep> getAvailableSteps(List<AssemblyStep> availableSteps) {
		List<AssemblyStep> removedSteps = new ArrayList<>();
		for (AssemblyStep step : inputSteps) {
			if (isAvailable(step)) {
				removedSteps.add(step);
				availableSteps.add(step);
			}
		}
		inputSteps.removeAll(removedSteps);

		return availableSteps;
	}
	
	public void lineUpSteps() {
		lineUpSelectedSteps(inputSteps);
	}
	
	private void lineUpSelectedSteps(List<AssemblyStep> selectedSteps) {
		// Sort the steps alphabetically.
		sortStepsAlphabetically(selectedSteps);
		// Find the first available step and append it.
		for (AssemblyStep step : selectedSteps) {
			if (isAvailable(step)) {
				selectedSteps.remove(step);
				processedSteps += step.getName();
				// Recursively add all steps that depend on this step.
				lineUpSelectedSteps(selectedSteps);
				break;
			}
		}
	}
	
	private void sortStepsAlphabetically(List<AssemblyStep> selectedSteps) {
		// Sort the steps alphabetically.
		Collections.sort(selectedSteps, new Comparator<AssemblyStep>() {
			@Override
			public int compare(AssemblyStep step, AssemblyStep other) {
				if (step.getName() > other.getName())
					return 1;
				if (step.getName() < other.getName())
					return -1;
				return 0;
			}
		});
	} 

	/**
	 * Determines whether the given step is available. This is the case if all it's prerequisite
	 * steps have already been lined up.
	 */
	private boolean isAvailable(AssemblyStep step) {
		for (Character prerequisite : step.getPrerequisites()) {
			if (processedSteps.indexOf(prerequisite) < 0) {
				return false;
			}
		}
		return true;
	}

	public String getOrderedSteps() {
		return processedSteps;
	}
}
