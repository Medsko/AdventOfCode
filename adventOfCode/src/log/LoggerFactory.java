package log;

public class LoggerFactory {
	
	private static LoggerFactory instance;
	
	private LoggerFactory() {}
	
	public static LoggerFactory getInstance() {
		if (instance == null) {
			instance = new LoggerFactory();
		}
		return instance;
	}
	
	public Logger getLogger(boolean isCommandLineFine) {
		return new CrappyLogger(isCommandLineFine);
	}
}
