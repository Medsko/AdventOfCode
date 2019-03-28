package adventOfCode.day18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.AlgorithmTimer;
import adventOfCode.InputFileReader;
import log.CrappyLogger;
import util.IOUtils;

public class DuetProgram implements Runnable {

	protected AlgorithmTimer timer;
	
	protected Map<Character, Long> registers;
	
	protected List<RegisterInstruction> instructions;
	
	private int currentInstructionIndex;
	
	/** The frequency that was most recently played. */
	private long lastPlayedSound;
		
	protected CrappyLogger logger;
	
	protected boolean isDebug;
	
	protected long iterations;
	
	/**
	 * Continuing (or jumping) off either end of the program terminates it: if this happens,
	 * this flag is set to true. 
	 */
	protected boolean endOfProgram;
	
	public DuetProgram() {
		registers = new HashMap<>();
		logger = new CrappyLogger(false);
		
		if (!initialize()) {
			logger.log("Error while initializing!");
		}
	}
	
	// First try: -6669 (incorrect).
	
	public void run() {
		
		currentInstructionIndex = 0;

		RegisterInstruction instruction;
		
		do {
			
			if (currentInstructionIndex >= instructions.size() || currentInstructionIndex < 0) {
				endOfProgram = true;
				break;
			}
			
			instruction = instructions.get(currentInstructionIndex++);
			
			iterations++;
			
			if (isDebug && iterations % 1000 == 0) {
				System.out.println("At " + iterations + " iterations!");
				printRegisters();
			}
		} while (executeInstruction(instruction));
	}
	
	private void printRegisters() {
		StringBuilder total = new StringBuilder();
		for (Map.Entry<Character, Long> register : registers.entrySet()) {
			if (total.length() > 0)
				total.append(", ");
			total.append("register " + register.getKey() + ": " + register.getValue());
		}
		logger.log(total.toString());
	}
	
	/**
	 * 
	 * @return {@code true} if execution should continue, {@false} if it completed.
	 */
	protected boolean executeInstruction(RegisterInstruction instruction) {
		
		Long value;
		
		if (isDebug)
			logger.log("Now executing instruction: " + instruction);
		
		switch (instruction.getType()) {
			case RECOVER:
				// Return the last sound played as answer to the problem - but only if the value
				// of the specified register is not zero. 
				return recover(instruction);
			case SOUND:
				// Play a sound with a frequency equal to the value of the specified register.
				return sound(instruction);
			case SET:
				// Set the specified register to the specified value.
				registers.put(instruction.getRegister(), getOperandFromInstruction(instruction));
				return true;
			case ADD:
				value = getValueForRegister(instruction.getRegister())
					+ getOperandFromInstruction(instruction);
				registers.put(instruction.getRegister(), value);
				return true;
			case MULTIPLY:
				value = getValueForRegister(instruction.getRegister())
					* getOperandFromInstruction(instruction);
				registers.put(instruction.getRegister(), value);
				return true;
			case MODULO:
				value = getValueForRegister(instruction.getRegister())
					% getOperandFromInstruction(instruction);
				registers.put(instruction.getRegister(), value);
				return true;
			case JUMP:
				if (getValueForRegister(instruction.getRegister()) > 0) {
					// The index of the next instruction was already incremented. Revert this action.
					currentInstructionIndex--;
					long jumpAmount = getOperandFromInstruction(instruction);
					logger.log("About to jump! Amount: " + jumpAmount);
					// Now update it according to the instruction.
					currentInstructionIndex += jumpAmount;
				}
				return true;
			default:
				return true;
		}
	}
	
	/**
	 * Executed when a recover/receive instruction is encountered. 
	 */
	protected boolean recover(RegisterInstruction instruction) {
		return registers.get(instruction.getRegister()) == 0;
	}
	
	/**
	 * Executed when a sound/send instruction is encountered. 
	 */
	protected boolean sound(RegisterInstruction instruction) {
		lastPlayedSound = registers.get(instruction.getRegister());
		logger.log("Playing frequency: " + lastPlayedSound);
		return true;
	}
	
	/**
	 * Gets the value for the given register from the map. If the register was not yet present,
	 * zero is returned.
	 */
	protected Long getValueForRegister(Character register) {
		Long value = registers.get(register);
		if (value != null)
			return value;
		else
			return 0L;
	}

	protected Long getOperandFromInstruction(RegisterInstruction instruction) {
		if (instruction.getOperand() != null) {
			return instruction.getOperand();
		} else {
			return registers.get(instruction.getRegisterValueForOperator());
		}
	}
	
	/** Reads the instructions from input file. */
	protected boolean initialize() {
		RegisterInstructionConverter converter = new RegisterInstructionConverter();
		InputFileReader reader = new InputFileReader(converter);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay18.txt";
		
		if (!reader.readInput(inputFile)) {
			return false;
		}
		instructions = (List<RegisterInstruction>) converter.getConvertedInput();
		
		return true;
	}

	public boolean isEnded() {
		return endOfProgram;
	}
	
	public long getLastPlayedSound() {
		return lastPlayedSound;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
}
