package aoc2018;

import adventOfCode.AbstractAlgorithm;

public abstract class AbstractAocAlgorithm extends AbstractAlgorithm {

	protected AocInputFetcher fetcher;
	
	protected boolean isTest;
	
	public AbstractAocAlgorithm(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public boolean initialize(int year, int day) {
		return initialize(year, day, false);
	}
	
	public boolean initialize(int year, int day, boolean isTest) {
		this.isTest = isTest;
		fetcher = new AocInputFetcher(year, isTest);
		return fetcher.fetchInput(day);
	}
	
	protected void logForTest(Object toLog) {
		if (isTest) {
			logger.log(toLog);
		}
	}
}
