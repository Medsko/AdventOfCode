package adventOfCode.day8;

import java.util.Map;

/**
 * Reads the mentioned registers from the provided {@link Map} and evaluates the equality operation.
 * If this evaluates to {@code true}, increases the register by the value specified in the 
 * instruction. If either or both register names are not yet present in the {@link Map}, adds 
 * it/them with a value of zero.
 */
public class JumpInstructionProcessor {

	private int highestValueHeldDuringProcess = 0;
	
	public void process(JumpInstruction instruction, Map<String, Integer> registers) {
				
		String nameOperandRegister = instruction.getRegisterNameFirstOperand();
		
		if (!registers.containsKey(nameOperandRegister)) {
			registers.put(nameOperandRegister, 0);
		}
		
		int firstOperand = registers.get(nameOperandRegister);
		int secondOperand = instruction.getSecondOperand();
		EqualityOperator operator = instruction.getOperator();
		
		String registerName = instruction.getRegisterName();
		
		if (operator.evaluate(firstOperand, secondOperand)) {
			// The operation evaluated to true. Update the value of the register.
			// Determine the amount to add to the value.
			int amountToAdd = instruction.getValue();
			if (!instruction.getShouldIncrement())
				// The value should be decremented by the specified amount.
				amountToAdd = -amountToAdd;
			
			// Add the register if it was not yet present in the map.
			if (!registers.containsKey(registerName)) {
				registers.put(registerName, 0);
			}
			// Update the register.
			Integer newValue = registers.get(registerName) + amountToAdd;
			registers.put(registerName, newValue);
			
			// Update the highest value held during process, if applicable.
			if (newValue > highestValueHeldDuringProcess)
				highestValueHeldDuringProcess = newValue;
			
			System.out.println("Operation " + firstOperand + " " + operator.representation + " " 
			+ secondOperand + " evaluated to true, so register " + registerName + " is incremented "
					+ " by " + amountToAdd);
		} else {
			System.out.println("Operation " + firstOperand + " " + operator.representation + " " 
			+ secondOperand + " evaluated to false, so register " + registerName + " is unchanged.");
		}
	}

	public int getHighestValueHeldDuringProcess() {
		return highestValueHeldDuringProcess;
	}
	
	public void resetHighestValueHeldDuringProcess() {
		highestValueHeldDuringProcess = 0;
	}
}
