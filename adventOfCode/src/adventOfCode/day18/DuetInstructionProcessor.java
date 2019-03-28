package adventOfCode.day18;

import java.util.ArrayDeque;
import java.util.Queue;

import adventOfCode.day8.JumpInstruction;

public class DuetInstructionProcessor extends AbstractInstructionProcessor {

	private Queue<Long> queue;
	
	private int nrOfSends;
	
	public DuetInstructionProcessor(boolean isCommandLineFine, boolean isDebug) {
		super(isCommandLineFine, isDebug);
		queue = new ArrayDeque<>();
	}

	@Override
	protected boolean executeInstruction(RegisterInstruction instruction) {
		switch (instruction.getType()) {
			case RECOVER:
				return recover(instruction);
			case SOUND:
				// Send a value to the other processor.
				return send(instruction);
			case SET:
				// Set the specified register to the specified value.
				return set(instruction);
			case ADD:
				return add(instruction);
			case MULTIPLY:
				return multiply(instruction);
			case MODULO:
				return modulo(instruction);			
			case JUMP:
				return jump(instruction);
			default:
				return true;
		}
	}
	
	/**
	 * Executed when a sound/send instruction is encountered. In this challenge B implementation,
	 * the value of the specified register is added to the queue of the other program.
	 */
	protected boolean send(RegisterInstruction instruction) {
//		Long valueToAdd = getValueForRegister(instruction.getRegister());
//		other.addValueToQueue(valueToAdd);
		nrOfSends++;
		
		return true;
	}
	
	/**
	 * Executed when a recover/receive instruction is encountered. In the multi-threaded/B 
	 * implementation, it retrieves the next value from the queue, blocking if the queue is empty.
	 * If both programs are blocked simultaneously, both should terminate.
	 */
	private boolean recover(RegisterInstruction instruction) {
		Long valueFromQueue = queue.poll();
		if (valueFromQueue != null) {
			registers.put(instruction.getRegister(), valueFromQueue);
			return true;
		} 
		return false;
	}
	
	private boolean add(RegisterInstruction instruction) {
		long value = getValueForRegister(instruction.getRegister())
			+ getOperandFromInstruction(instruction);
		registers.put(instruction.getRegister(), value);
		return true;
	}
	
	private boolean modulo(RegisterInstruction instruction) {
		long value = getValueForRegister(instruction.getRegister())
				+ getOperandFromInstruction(instruction);
		registers.put(instruction.getRegister(), value);
		return true;
	}
	
	private boolean jump(RegisterInstruction instruction) {
		if (getValueForRegister(instruction.getRegister()) > 0) {
			// The index of the next instruction was already incremented. Revert this action.
			long jumpAmount = getOperandFromInstruction(instruction);
			logger.log("About to jump! Amount: " + jumpAmount);
			// Now update it according to the instruction.
			instructionIndex += jumpAmount;
		}
		return true;
	}
}
