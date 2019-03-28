package adventOfCode.day18;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adventOfCode.InputConverter;
import adventOfCode.day18.RegisterInstruction.Type;

public class RegisterInstructionConverter implements InputConverter<RegisterInstruction> {

	private List<RegisterInstruction> instructions;
	
	public RegisterInstructionConverter() {
		instructions = new ArrayList<>();
	}
	
	@Override
	public boolean convertLine(String line) {
		
		String[] input = line.split(" ");
		
		RegisterInstruction instruction = new RegisterInstruction(Type.fromRepresentation(input[0]));
		instruction.setRegister(input[1].charAt(0));
		
		if (input.length == 3) {
			try {
				long operator = Integer.parseInt(input[2]);
				instruction.setOperand(operator);
			} catch (NumberFormatException nfex) {
				instruction.setRegisterValueForOperator(input[2].charAt(0));
			}
		}
		
		instructions.add(instruction);
		
		return true;
	}

	@Override
	public Collection<RegisterInstruction> getConvertedInput() {
		return instructions;
	}
}
