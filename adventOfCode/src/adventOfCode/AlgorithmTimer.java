package adventOfCode;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import log.CrappyLogger;

/**
 * Offers different ways to time (parts of) an algorithm. 
 */
public class AlgorithmTimer {

	private Instant algorithmStart;
	
	private String algorithmName;
	
	private Instant subAlgorithmStart;
	
	private CrappyLogger logger;
	
	private ChronoUnit precision;
	
	public AlgorithmTimer(boolean isCommandLineFine) {
		logger = new CrappyLogger(isCommandLineFine);
		// Default precision: milliseconds.
		precision = ChronoUnit.MILLIS;
	}
	
	/**
	 * If the algorithm that is being timed contains a loop, it might be interesting to measure
	 * the amount of time it takes to complete one, or a number of loops. 
	 */
	public void startSubAlgorithm() {
		subAlgorithmStart = Instant.now();
		logger.log("Starting subsection algorithm " + algorithmName);
	}
	
	public void stopSubAlgorithm() {
		if (subAlgorithmStart == null)
			throw new IllegalStateException("startSubAlgorithm() was not called!");
		
		Instant now = Instant.now();
		long specifiedPrecisionDiff = subAlgorithmStart.until(now, precision);
		// Compose the message.
		String message = "Subsection of " + algorithmName + " took " + specifiedPrecisionDiff 
				+ precision.toString() + " to complete.";
		logger.log(message);
		subAlgorithmStart = null;
	}
	
	/**
	 * Call at the start of an algorithm to start the timer.
	 * 
	 * @param algorithmName - the name the caller provided for the currently measured algorithm.
	 * This could be either a method name, or a term to describe an algorithm with greater span.  
	 */
	public void startAlgorithm(String algorithmName) {
		this.algorithmName = algorithmName;
		algorithmStart = Instant.now();
		logger.log("Starting algorithm " + algorithmName);
	}

	public void logTime() {
		// Compose the message.
		String message = composeLogMessage();		
		logger.log(message);
	}
	
	private String composeLogMessage() {
		if (algorithmStart == null)
			throw new IllegalStateException("startAlgorithm() was not called!");
		Instant now = Instant.now();
		long specifiedPrecisionDiff = algorithmStart.until(now, precision);
		long millisDiff = algorithmStart.until(now, ChronoUnit.MILLIS);
		Duration formattedDiff = Duration.ofMillis(millisDiff);
		// Compose the message.
		String message = "Time passed since " + algorithmName + " started: " 
			+ specifiedPrecisionDiff + " " + precision.toString() + System.lineSeparator() 
			+ "Attempt at prettified time measurement: " + formattedDiff.toString();
		return message;
	}
	
	/**
	 * Call at the end of an algorithm to stop the timer and write the timed period to file. 
	 */
	public void stopAlgorithm() {
		// Compose the message.
		String message = "Stopping execution of " + algorithmName + "." + System.lineSeparator();
		message += composeLogMessage();
		logger.log(message);
		// Nullify the starting instant to prevent misreads (caused by reusing the timer without  
		// calling startAlgorithm() first).
		algorithmStart = null;
	}
	
	public ChronoUnit getPrecision() {
		return precision;
	}

	public void setPrecision(ChronoUnit precision) {
		this.precision = precision;
	}

	public static void main(String[] args) {
		// Open the input folder.
		Desktop desktop = Desktop.getDesktop();
		File inputDir = new File(CrappyLogger.PATH_TO_LOG_DIRECTORY);
		try {
			desktop.open(inputDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
