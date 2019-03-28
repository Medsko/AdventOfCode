package aoc2018;

import java.time.temporal.ChronoUnit;
import java.util.List;

import adventOfCode.AlgorithmTimer;
import log.CrappyLogger;

/**
 * Abstract Advent of Code Algorithm 2.0 - new and improved!
 */
public abstract class AAoCA {
	
	protected AlgorithmTimer timer;
	
	protected CrappyLogger logger;

	protected AocInputFetcher fetcher;
	
	protected boolean isTest;
	
	protected boolean isCommandLineFine;
	
	protected boolean isChallengeA;
	
	protected String printableAnswer;
	
	protected List<String> inputLines;
	
	public AAoCA(boolean isCommandLineFine) {
		timer = new AlgorithmTimer(isCommandLineFine);
		logger = new CrappyLogger(isCommandLineFine);
		this.isCommandLineFine = isCommandLineFine;
	}

	public void setPrecision(ChronoUnit precision) {
		timer.setPrecision(precision);
	}
	
	/**
	 * Initializes the {@link AAoCA}. Implementing classes should define all actions necessary
	 * before {@link #run()} can be called.
	 * <p>
	 * If input from a file is required, it is advisable to call 
	 * {@link #initialize(int, int, boolean)} with the correct year and number from this method.
	 * </p>
	 * @param isTest - flag indicating whether the algorithm should be run in test form.
	 * @return {@code boolean} value indicating success.   
	 */
	public abstract boolean initialize(boolean isTest);

	/**
	 * Main method. All steps of the algorithm that are necessary to get the answer to a challenge 
	 * are aggregated in this method.
	 */
	public abstract boolean run();
	
	/**
	 * After running {@link #initialize(boolean)} and {@link #run()}, call this to get a 
	 * user-friendly message specifying the answer to the challenge. 
	 */
	public String getPrintableAnswer() {
		return printableAnswer;
	}
	
	protected boolean initialize(int year, int day, boolean isTest) {
		this.isTest = isTest;

		fetcher = new AocInputFetcher(year, isTest);
		if (!fetcher.fetchInput(day)) {
			return false;
		}
		inputLines = fetcher.getInputLines();
		return true;
	}
	
	protected boolean initialize(int year, int day, boolean isTest, String altFileName) {
		this.isTest = isTest;
		fetcher = new AocInputFetcher(year, isTest);
		fetcher.setInputFileName(altFileName);
		if (!fetcher.fetchInput(day)) {
			return false;
		}
		inputLines = fetcher.getInputLines();
		return true;
	}
	
	protected void logForTest(Object toLog) {
		if (isTest)
			logger.log(toLog);
	}

	public void setIsChallengeA(boolean isChallengeA) {
		this.isChallengeA = isChallengeA;
	}
}
