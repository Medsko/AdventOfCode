package adventOfCode.day16;

public class DanceInstruction {

	private int nrOfProgramsToSpin;
	
	private String programA;
	
	private String programB;
	
	private Move move;
	
	public enum Move {
		SPIN,
		EXCHANGE,
		PARTNER
	}
	
	public DanceInstruction(String instruction) {
		switch (instruction.charAt(0)) {
			case 's':
				move = Move.SPIN;
				nrOfProgramsToSpin = Integer.parseInt(instruction.substring(1));
				break;
			case 'x':
				move = Move.EXCHANGE;
				programA = instruction.substring(1, instruction.indexOf("/"));
				programB = instruction.substring(instruction.indexOf("/") + 1, instruction.length());
				break;
			case 'p':
				move = Move.PARTNER;
				programA = instruction.substring(1, 2);
				programB = instruction.substring(3, 4);
		}
	}

	public int getNrOfProgramsToSpin() {
		return nrOfProgramsToSpin;
	}

	public String getProgramA() {
		return programA;
	}

	public String getProgramB() {
		return programB;
	}

	public Move getMove() {
		return move;
	}
}
