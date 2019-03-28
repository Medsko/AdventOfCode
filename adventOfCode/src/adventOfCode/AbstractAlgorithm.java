package adventOfCode;

import java.time.temporal.ChronoUnit;

import log.CrappyLogger;

public abstract class AbstractAlgorithm {

	protected AlgorithmTimer timer;
	
	protected CrappyLogger logger;
	
	protected boolean isCommandLineFine;
	
	public AbstractAlgorithm(boolean isCommandLineFine) {
		timer = new AlgorithmTimer(isCommandLineFine);
		logger = new CrappyLogger(isCommandLineFine);
		this.isCommandLineFine = isCommandLineFine;
	}

	public void setPrecision(ChronoUnit precision) {
		timer.setPrecision(precision);
	}
}
