package adventOfCode.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode.InputFileReader;

/**
 * 'Cause Java. Uses an {@link InputFileReader} to read and process the input. The resulting list
 * of {@link JumpInstruction}s are then evaluated to build up a map of registers. Finally, the
 * highest value among the registers is determined and returned.
 */
public class RegisterRegistrator {

	/** Holds all register name-value pairs. */
	private Map<String, Integer> registers;
	
	private boolean isForB;
	
	public RegisterRegistrator() {
		registers = new HashMap<>();
	}
	
	public RegisterRegistrator(boolean isForB) {
		this();
		this.isForB = isForB;
	}
	
	/**
	 * 'Cause Java.
	 */
	public int register(String inputFile) {
		
		JumpInstructionInputConverter converter = new JumpInstructionInputConverter();
		InputFileReader inputReader = new InputFileReader(InputFileReader.Day.EIGHT);
		inputReader.setInputConverter(converter);
		inputReader.readInput(inputFile);
		
		List<JumpInstruction> instructions = converter.getConvertedInput();
		
		JumpInstructionProcessor processor = new JumpInstructionProcessor();
		
		for (JumpInstruction instruction : instructions) {
			processor.process(instruction, registers);
		}
		
		if (isForB)
			return processor.getHighestValueHeldDuringProcess();
		else
			return determineHighestRegister();
	}
	
	
	private int determineHighestRegister() {
		
		int highestRegisterValue = 0;
		
		for (Map.Entry<String, Integer> register : registers.entrySet()) {
			System.out.println("Register " + register.getKey() 
					+ " has value: " + register.getValue());
		}
		
		for (Integer value : registers.values()) {
			if (value > highestRegisterValue)
				highestRegisterValue = value;
		}
		
		return highestRegisterValue;
	}
}
