package net.glease.chem.simple.calculation;

/**
 * Describe a function that a {@link CalculationProvider} support.
 *
 * @author glease
 * @since 0.1
 */
public final class FunctionDescriptor {
	/**
	 * Create a {@link FunctionDescriptor} with given name and operand count.
	 * @param name name
	 * @param operandCount operand count
	 * @return the {@link FunctionDescriptor}
	 */
	public static FunctionDescriptor of(final String name, final int operandCount) {
		return of(name, name, operandCount);
	}
	/**
	 * Create a {@link FunctionDescriptor} with given name, description and operand count.
	 * @param name name
	 * @param operandCount operand count
	 * @return the {@link FunctionDescriptor}
	 */
	public static FunctionDescriptor of(final String name, final String description, final int operandCount) {
		return new FunctionDescriptor(name, description, operandCount);
	}

	private final String name;
	private final String description;
	private final int operandCount;

	private FunctionDescriptor(final String name, final String description, final int operandCount) {
		super();
		this.name = name;
		this.description= description;
		this.operandCount = operandCount;
	}

	/**
	 * Get the name of function.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the count of operands.
	 *
	 * @return count of operands
	 */
	public int getOperandCount() {
		return operandCount;
	}

	/**
	 * Get the description.
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
}