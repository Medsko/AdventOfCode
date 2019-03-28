package adventOfCode.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import adventOfCode.AlgorithmTimer;
import util.IOUtils;

public class HackRun {

	/** The total severity of intercepts the heroic malware packet has accumulated.*/
	private int totalSeverity;
	
	private Firewall firewall;
	
	private AlgorithmTimer timer; 
	
	// TODO: make it work.
	// First run total: 1132 (too low).
		
	/**
	 * Kicks off the journey through the security system.
	 */
	public void jamItIn() {
		// Initialize the instance variables to their start value.
		readInput();
		totalSeverity = 0;
		
		for (int i=0; i<firewall.size(); i++) {
			// Get the intercept value of the malware packet at the current position and add it to
			// the total.
			totalSeverity += firewall.interceptSeverity(i);
			// Update the position of the scanners.
			firewall.updateScannerPositions();
		}
	}
	
	/**
	 * The more subtle sibling to {@link #jamItIn()}, this process runs through hack scenarios that
	 * differ in number of picoSeconds delay that precede the run. Once a scenario is found that
	 * yields no intercept severity, the number of picoSeconds delay that was used is returned.
	 * 
	 * @return the number of picoSeconds of delay necessary to avoid all scanners.
	 */
	public int penetrate() {
		readInput();
		int nrOfPicoSecondsDelay = 0;
		
		timer = new AlgorithmTimer(false);
		timer.startAlgorithm("HackRun.penetrate()");
		// Kick off endless loop.
		outer:
		for (;;) {
			
			// Run the scenario. 
			for (int i=0; i<firewall.size(); i++) {

				if (firewall.isIntercept(i)) {
					// Intercepted! Walk back all scanners to their position before this run.
					firewall.resetScanners(i);
					// Increase the delay, and update the scanner positions accordingly.
					nrOfPicoSecondsDelay++;
					firewall.updateScannerPositions();
					continue outer;
				}
				// Update the position of the scanners and continue this run.
				firewall.updateScannerPositions();
			}
			// An entire run was finished without any intercepts. We found the correct delay!
			break;
		}
		timer.stopAlgorithm();
		
		return nrOfPicoSecondsDelay;
	}
	
	/**
	 * Reads the input from the input file and converts it into scanners that are placed into
	 * the {@link #firewall}.
	 */
	private void readInput() {
		
		Path inputFile = Paths.get(IOUtils.PATH_TO_INPUT, "inputDay13.txt");
		firewall = new Firewall();
		
		try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
			String line;
			while ((line = reader.readLine()) != null) {
				
				String layerString = line.substring(0, line.indexOf(":"));
				int layer = Integer.parseInt(layerString);
				
				String rangeString = line.substring(line.indexOf(":") + 1).trim();
				int range = Integer.parseInt(rangeString);
				
				firewall.addScanner(layer, range);
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
			return;
		}
	}

	public int getTotalSeverity() {
		return totalSeverity;
	}
}
