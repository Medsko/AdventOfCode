package adventOfCode.day18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.AbstractAlgorithm;

public abstract class AbstractInstructionProcessor extends AbstractAlgorithm {
	
	protected Map<Character, Long> registers;
	
	protected List<RegisterInstruction> instructions;
	
	protected int instructionIndex;
	
	protected boolean isDebug;
	
	
	public AbstractInstructionProcessor(boolean isCommandLineFine) {
		super(isCommandLineFine);
		registers = new HashMap<>();
		instructions = new ArrayList<>();
	}
	
	public AbstractInstructionProcessor(boolean isCommandLineFine, boolean isDebug) {
		this(isCommandLineFine);
		this.isDebug = isDebug;
	}

	protected abstract boolean executeInstruction(RegisterInstruction instruction);
	
	public void process() {
		
		int iterations = 0;
		instructionIndex = 0;
		RegisterInstruction instruction;
		
		do {
			instruction = instructions.get(instructionIndex);
			
			if (instruction.getType() != RegisterInstruction.Type.JUMP)
				instructionIndex++;
			iterations++;
			
			if (isDebug && iterations % 1000 == 0) {
				System.out.println("At " + iterations + " iterations!");
				System.out.println(registersToString());
			}

		// Keep processing if executeInstruction returns true...
		} while (executeInstruction(instruction)
					// ...and there is an instruction at the position of the instruction index.
					&& !(instructionIndex >= instructions.size() 
						|| instructionIndex < 0));
	}
	
	protected boolean set(RegisterInstruction instruction) {
		// Set the specified register to the specified value.
		registers.put(instruction.getRegister(), getOperandFromInstruction(instruction));
		return true;
	}
	
	protected boolean multiply(RegisterInstruction instruction) {
		// Get the value, or a fresh zero, from the register.
		Long value = getValueForRegister(instruction.getRegister());
		// Multiply it by the operand, and set it on the register.
		value *= getOperandFromInstruction(instruction);
		registers.put(instruction.getRegister(), value);
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

	/**
	 * Gets the operand (the number will be added, subtracted etc. to/from the register) from the
	 * instruction. If the operand specified is a register name, the corresponding value is
	 * retrieved from the register map by means of {@link #getValueForRegister(Character)}.
	 * 
	 * @param instruction - the instruction that is being executed.
	 * @return the value for the operand as a Long.
	 */
	protected Long getOperandFromInstruction(RegisterInstruction instruction) {
		if (instruction.getOperand() != null) {
			return instruction.getOperand();
		} else {
			return registers.get(instruction.getRegisterValueForOperator());
		}
	}
	
	protected String registersToString() {
		StringBuilder total = new StringBuilder();
		for (Map.Entry<Character, Long> register : registers.entrySet()) {
			if (total.length() > 0)
				total.append(", ");
			total.append("register " + register.getKey() + ": " + register.getValue());
		}
		return total.toString();
	}
}
