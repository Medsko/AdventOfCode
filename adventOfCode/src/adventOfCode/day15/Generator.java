package adventOfCode.day15;

public class Generator {

	private long previousValue;
	
	private int factor;
	
	final private static int DIVIDER = 2147483647;
	
	private int nrOfTimesGenerated;
	
	private Integer criterion;
	
	public Generator(int startingValue, int factor) {
		previousValue = startingValue;
		this.factor = factor;
		nrOfTimesGenerated = 0;
	}
	
	public Generator(int startingValue, int factor, int criterium) {
		this(startingValue, factor);
		this.criterion = criterium;
	}
	
	public String nextValueAsBinary() {
		
		do {
			previousValue = generateNextValue();
			// If a criterion has been set, keep generating values until it has been met.
		} while (criterion != null && previousValue % criterion != 0);
		
		// With this zero-padding operation, forty million cycles take 50846 milliseconds.
//		String binary = String.format("%32s", Long.toBinaryString(previousValue)).replace(' ', '0');
		// This way, forty million cycles take 5083 milliseconds (so, 10 times faster...):
		String binary = Long.toBinaryString(previousValue);
		
		if (nrOfTimesGenerated++ < 5) {
			System.out.println("Generated value: " + previousValue);
			System.out.println("Binary: " + binary);
		}
		
		return binary;
	}
	
	private long generateNextValue() {
		return (previousValue * factor) % DIVIDER;
	}
}
