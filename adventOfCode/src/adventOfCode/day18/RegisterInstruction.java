package adventOfCode.day18;

public class RegisterInstruction {

	public enum Type {
		
		SOUND("snd"),
		SET("set"),
		ADD("add"),
		SUBTRACT("sub"),
		MULTIPLY("mul"),
		MODULO("mod"),
		RECOVER("rcv"),
		JUMP("jgz");
		
		public String representation;
		
		Type(String representation) {
			this.representation = representation;
		}
		
		public static Type fromRepresentation(String representation) {
			if ("jnz".equals(representation)) {
				return JUMP;
			}
			
			for (Type type : Type.values()) {
				if (type.representation.equals(representation)) {
					return type;
				}
			}
			return null;
		}
	}
	
	private Type type;
	
	private Character register;
	
	private Long operand;
	
	public String toString() {
		
		String toString = "type: " + type.name();
		toString += ", register: " + register;
		
		if (operand != null)
			toString += ", operand: " + operand;
		if (registerValueForOperator != null)
			toString += ", register holding operand: " + registerValueForOperator;
		
		return toString;
	}
	
	/** 
	 * The name of the register from which the value should be retrieved to use as operator. 
	 */
	private Character registerValueForOperator;
	
	public RegisterInstruction() {}
	
	public RegisterInstruction(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public Long getOperand() {
		return operand;
	}

	public void setOperand(Long operand) {
		this.operand = operand;
	}

	public Character getRegisterValueForOperator() {
		return registerValueForOperator;
	}

	public void setRegisterValueForOperator(Character registerValueForOperator) {
		this.registerValueForOperator = registerValueForOperator;
	}

	public void setRegister(Character register) {
		this.register = register;
	}

	public Character getRegister() {
		return register;
	}
}
