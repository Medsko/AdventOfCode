package adventOfCode.day23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.InputFileReader;
import adventOfCode.day18.AbstractInstructionProcessor;
import adventOfCode.day18.RegisterInstruction;
import adventOfCode.day18.RegisterInstructionConverter;
import util.IOUtils;

public class ExperimentalCoprocessor extends AbstractInstructionProcessor {
	
	/** Keeps track of the number of times the 'multiply' instruction has been executed. */
	private int multiplyInvoked;
	
	public ExperimentalCoprocessor(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public ExperimentalCoprocessor(boolean isCommandLineFine, boolean isDebug) {
		super(isCommandLineFine, isDebug);
	}
	
	@Override
	protected boolean executeInstruction(RegisterInstruction instruction) {
		
		switch(instruction.getType()) {
			case SET:
				return set(instruction);
			case SUBTRACT:
				return subtract(instruction);
			case MULTIPLY:
				multiplyInvoked++;
				return multiply(instruction);
			case JUMP:
				return jump(instruction);
			default: return false;
		}
	}
	
	
	private boolean jump(RegisterInstruction instruction) {
		if (instruction.getRegister() == '1' 
				|| getValueForRegister(instruction.getRegister()) != 0) {
			long jumpAmount = getOperandFromInstruction(instruction);
			logger.log("About to jump! Amount: " + jumpAmount);
			// Now update it according to the instruction.
			instructionIndex += jumpAmount;
		} else {
			instructionIndex++;
		}
		return true;
	}

	private boolean subtract(RegisterInstruction instruction) {
		// Get the value, or a fresh zero, from the register.
		Long value = getValueForRegister(instruction.getRegister());
		// Subtract the operand from it, and set it on the register.
		value -= getOperandFromInstruction(instruction);
		registers.put(instruction.getRegister(), value);
		return true;
	}


	public boolean initialize() {
		RegisterInstructionConverter converter = new RegisterInstructionConverter();
		InputFileReader reader = new InputFileReader(converter);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay23.txt";
		
		if (!reader.readInput(inputFile)) {
			return false;
		}
		instructions = (List<RegisterInstruction>) converter.getConvertedInput();
		
		multiplyInvoked = 0;
		
		return true;
	}
	
	public boolean initializeB() {
		
		registers = new HashMap<>();
		
		registers.put('a', 1L);
		
		return initialize();
	}

	public int getMultiplyInvoked() {
		return multiplyInvoked;
	}
}
