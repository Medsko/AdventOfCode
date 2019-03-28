package aoc2018;

import java.util.ArrayList;
import java.util.List;

/**
 *  
 */
public class AlgorithmRunner {

	/**
	 * The list of algorithms to run. 
	 */
	List<AAoCA> algorithms;
	
	public AlgorithmRunner() {
		algorithms = new ArrayList<>();
	}
	
	public void runAll() {
		for (AAoCA algorithm : algorithms) {
			algorithm.initialize(false);
			algorithm.run();
			System.out.println(algorithm.getPrintableAnswer());
		}
	}
	
	public void testAll() {
		for (AAoCA algorithm : algorithms) {
			algorithm.initialize(true);
			algorithm.run();
			System.out.println(algorithm.getPrintableAnswer());
		}
	}
	
	public void addAlgorithm(AAoCA algorithm) {
		algorithms.add(algorithm);
	}
}
