package adventOfCode.day13;

/**
 * Represents a security scanner on a layer.   
 */
public class PacketScanner {

	private int range;
	
	private int positionScanner;
	
	private boolean isMovingBack;
	
	public PacketScanner(int range) {
		this.range = range;
		positionScanner = 0;
		isMovingBack = false;
	}

	/**
	 * Repositions this scanner's position on its layer. 
	 */
	public void updatePosition() {
		if (range == 0)
			// This is a dud scanner, representing a layer without a scanner.
			return;
		if (isMovingBack) {
			if (positionScanner - 1 < 0) {
				// Moving further would be out of range. Reverse direction.
				isMovingBack = false;
				positionScanner++;
			} else {
				positionScanner--;
			}
		} else {
			if (positionScanner + 1 >= range) {
				// Moving further would be out of range. Reverse direction.
				isMovingBack = true;
				positionScanner--;
			} else {
				positionScanner++;
			}
		}
	}
	
	/** Resets the scanner to its original position and moving direction. */
	public void reset() {
		positionScanner = 0;
		isMovingBack = false;
	}
	
	/** Resets the scanner to the given number of steps back. */
	public void reset(int stepsBack) {		
		if (range == 0)
			// This is a dud scanner, so nothing to reset.
			return;

		// Reverse the direction.
		isMovingBack = !isMovingBack;
		// Move back for the given number of steps.
		for (int i=0; i<stepsBack; i++) {
			updatePosition();
		}
		// Set the direction back.
		isMovingBack = !isMovingBack;
	}
	
	/**
	 * Checks if there is an intercept and if so, returns the range of this scanner, which will
	 * factor into the ultimate severity.
	 * @return the range of this scanner, or 0 if this scanner is a dud or no intercept occurred.
	 */
	public int interceptSeverity() {
		if (range == 0)
			// This is a dud scanner, representing a layer without a scanner.
			return 0;
		else if (positionScanner == 0)
			// The scanner is at the location we are entering. This is an intercept. 
			return range;
		else
			return 0;
	}
}
