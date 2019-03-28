package adventOfCode.day8;

import adventOfCode.InputDataObject;

public class JumpInstruction implements InputDataObject {

	private String registerName;
	
	/** 
	 * Indicates whether this instruction holds that the register should be incremented. 
	 * If false, the register should be decremented. 
	 */
	private boolean shouldIncrement;
	
	/** The amount to increment the register by. */
	private int value;

	/**
	 * The name of the register of which the value should be retrieved to evaluate the equality
	 * operation. 
	 */
	private String firstOperand;
	
	private int secondOperand;
	
	/** String representation of the boolean operator. */
	private EqualityOperator operator;

	
	public JumpInstruction(String registerName) {
		this.registerName = registerName;
	}
	
	
	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public boolean getShouldIncrement() {
		return shouldIncrement;
	}

	public void setShouldIncrement(boolean shouldIncrement) {
		this.shouldIncrement = shouldIncrement;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public EqualityOperator getOperator() {
		return operator;
	}
	
	public void setOperator(EqualityOperator operator) {
		this.operator = operator;
	}

	public String getRegisterNameFirstOperand() {
		return firstOperand;
	}

	public void setFirstOperand(String firstOperand) {
		this.firstOperand = firstOperand;
	}
	
	public int getSecondOperand() {
		return secondOperand;
	}

	public void setSecondOperand(int secondOperand) {
		this.secondOperand = secondOperand;
	}	
}
