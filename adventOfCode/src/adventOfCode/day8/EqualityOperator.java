package adventOfCode.day8;

/**
 * An equality operator that can be constructed from a {@link String} representation and can be
 * used to evaluate two operands.
 */
public enum EqualityOperator {
	
	GREATER_THAN(">"),
	GREATER_THAN_OR_EQUAL(">="),
	LESSER_THAN("<"),
	LESSER_THAN_OR_EQUAL("<="),
	EQUAL("=="),
	NOT_EQUAL("!=");
	
	public String representation;
	
	EqualityOperator(String representation) {
		this.representation = representation;
	}

	/**
	 * Returns an {@link EqualityOperator} based on the provided {@link String} representation.
	 * 
	 * @return - a {@link EqualityOperator} of the type implied by the {@link String} parameter.
	 * @throws IllegalArgumentException - when the provided {@link String} is not a valid 
	 * representation of any of the {@link EqualityOperator} values.
	 */
	public static EqualityOperator ofString(String representation) throws IllegalArgumentException {
		// Check whether the provided String is a valid representation of an equality operator.
		for (EqualityOperator operator : values()) {
			if (operator.representation.equals(representation)) {
				return operator;
			}
		}
		throw new IllegalArgumentException("Invalid String representation for equality operator!");
	}
	
	/**
	 * Evaluates the provided operands by applying this operator.
	 * 
	 * @param firstOperand - the left hand operand.
	 * @param secondOperand - the right side operand.
	 * @return boolean result of the equality operation.
	 */
	public boolean evaluate(int firstOperand, int secondOperand) {
		
		switch (this) {
			case GREATER_THAN:
				return firstOperand > secondOperand;
			case GREATER_THAN_OR_EQUAL:
				return firstOperand >= secondOperand;
			case LESSER_THAN:
				return firstOperand < secondOperand;
			case LESSER_THAN_OR_EQUAL:
				return firstOperand <= secondOperand;
			case EQUAL:
				return firstOperand == secondOperand;
			case NOT_EQUAL:
				return firstOperand != secondOperand;
			default:
				return false;
		}
	}
}
