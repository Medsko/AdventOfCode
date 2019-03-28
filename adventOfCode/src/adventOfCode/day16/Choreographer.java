package adventOfCode.day16;

import java.util.Arrays;
import java.util.List;

import adventOfCode.AlgorithmTimer;

public class Choreographer {

	private char[] programs;
	
	private char[] startingPositions;
	
	private boolean isDebug;
	
	private List<DanceInstruction> instructions;
	
	private AlgorithmTimer timer;
	
	// First try: o, f, n, e, h, p, b, c, a, m, i, j, l, g, d, k 
	
	// First try part 2: idopkafbcehjglnm - second try: abdjimglkpfhceon - third try: jfpckgeiamdbhonl
	
	public Choreographer() {
		isDebug = false;
		initialize();
	}
	
	public Choreographer(boolean isDebug) {
		this();
		this.isDebug = isDebug;
	}
	
	/**
	 * Determines the positions the programs are in after performing their dance 10000 times. 
	 */
	public void tour() {
		timer.startAlgorithm("Choreographer.tour()");
		// Copy the starting postions of the programs to another array.
		startingPositions = new char[16];
		System.arraycopy(programs, 0, startingPositions, 0, 16);
		
		int nrOfCycles = 0;
		
		for (int i=1; i<=1_000_000_000; i++) {
			
			perform();
						
			if (Arrays.equals(programs, startingPositions)) {
				System.out.println("After " + i + " cycles, the programs are once again in their "
						+ "starting positions!");
				// The programs are back in their starting positions. We've found the size of the
				// loop! Determine how many extra cycles should be performed after running the
				// loop that ends on the starting position n number of times.
				// Don't forget to incorporate the current cycle!
				i = 1_000_000_000 - (1_000_000_000 % i);
				nrOfCycles = 0;
			}
			
			nrOfCycles++;
		}
		
		System.out.println("Number of extra cycles ran: " + nrOfCycles);
		timer.stopAlgorithm();
	}
	
	public void perform() {
		
		for (DanceInstruction instruction : instructions) {
			switch (instruction.getMove()) {
			case SPIN:
				performSpin(instruction);
				break;
			case EXCHANGE:
				performExchange(instruction);
				break;
			case PARTNER:
				performPartner(instruction);
			}
		}
	}
	
	/**
	 * Shifts a number of programs from the outer right of the array to the outer left of the array,
	 * otherwise keeping the order of the programs intact. The number of programs shifted is
	 * specified in the instruction.
	 */
	private void performSpin(DanceInstruction spin) {
		int nrOfProgramsToSpin = spin.getNrOfProgramsToSpin();
		
		if (nrOfProgramsToSpin == 0) {
			// If no programs should spin, we are done.
			return;
		}
		// Determine the starting index of the sub-group that will be moved.
		int indexFrom = programs.length - nrOfProgramsToSpin;
		
		char[] nonSpinners = new char[indexFrom];
		
		if (isDebug) {
			System.out.println("Going to spin " + nrOfProgramsToSpin + " programs. ");
		}
		// Copy the non-spinning programs to a temporary array.
		System.arraycopy(programs, 0, nonSpinners, 0, indexFrom);
		// Copy the spinning programs from the end to the begin of the programs array.
		System.arraycopy(programs, indexFrom, programs, 0, nrOfProgramsToSpin);
		// Copy the non-spinning programs from the temporary array to the end of the array.
		System.arraycopy(nonSpinners, 0, programs, nrOfProgramsToSpin, nonSpinners.length);
		
		if (isDebug) {
			System.out.println("Order after spin: " + Arrays.toString(programs));
		}
	}
	
	/**
	 * Performs the 'exchange' move, in which two programs, located at the indices specified in the
	 * instruction, are switched. 
	 */
	private void performExchange(DanceInstruction exchange) {
		// The characters that were read as instructions signify a position in the array, so they
		// should be interpreted as integers to accurately execute the instruction (and avoid
		// AIOOB-exceptions).
		if (isDebug) {
			System.out.println("Going to exchange programs at position " + exchange.getProgramA() 
				+ " and " + exchange.getProgramB());
		}
		int posA = Integer.parseInt(exchange.getProgramA());
		int posB = Integer.parseInt(exchange.getProgramB());
		// Exchange the programs.
		switchPositions(posA, posB);
		
		if (isDebug) {
			System.out.println("Order after exchange: " + Arrays.toString(programs));
		}
	}
	
	/**
	 * Performs the 'partner' move, in which two programs, whose names are specified in the 
	 * instruction, switch positions.
	 */
	private void performPartner(DanceInstruction partner) {
		String finder = new String(programs);
		
		if (isDebug) {
			System.out.println("Going to partner programs: " + partner.getProgramA() + " and " 
					+ partner.getProgramB());
		}
		
		int posA = finder.indexOf(partner.getProgramA());
		int posB = finder.indexOf(partner.getProgramB());
		
		// Exchange the programs.
		switchPositions(posA, posB);
		
		if (isDebug) {
			System.out.println("Order after partner: " + Arrays.toString(programs));
		}
	}
	
	/**
	 * Moves the program at position A to position B, and vice versa.
	 */
	private void switchPositions(int posA, int posB) {
		char temp = programs[posA];
		programs[posA] = programs[posB];
		programs[posB] = temp;
	}
	
	private void initialize() {
		initializePrograms();
		instructions = new Interpreter().interpretInstructions();
		timer = new AlgorithmTimer(false);
	}
	
	/**
	 * Provides a way to not have to hard-code a character array holding the first 16 letters
	 * of the alphabet.
	 */
	private void initializePrograms() {
		// There are 16 programs, named 'a' through 'p'.
		programs = new char[16];
		for (int i=0; i<100; i++) {
			if (i == 'a') {
				for (int j=0; j<programs.length; j++) {
					programs[j] = (char) (i + j);
				}
				break;
			}
		}
	}

	public char[] getPrograms() {
		return programs;
	}
	
	public String getProgramsAsString() {
		return Arrays.toString(programs);
	}
	
	public int getNrOfInstructions() {
		return instructions.size();
	}
}
