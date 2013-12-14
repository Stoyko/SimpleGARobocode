package id.ac.its.SimpleGARobocode;


public class Row {
	public enum Operator {
		greatherThan,
		lessThan,
		equal,
        addition,
		substraction,
		multiplication,
		division,
		absoluteValue,
		and,
		or,
		not,
		generateConstant,
		modulo,
		randomFloat,
		normalizeRelativeAngle,
		controlActuator
	};
	
	private Operator operator;
	
    private Input input1, input2;
	
	private float output;

	public float getResult() {
		return output;
	}

	public void setResult(float output) {
		this.output = output;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Input getInput1() {
		return input1;
	}

	public void setInput1(Input input1) {
		this.input1 = input1;
	}

	public Input getInput2() {
		return input2;
	}

	public void setInput2(Input input2) {
		this.input2 = input2;
	}
	
	public Row(Operator operator, Input input1, Input input2) {
		this.operator = operator;
		this.input1 = input1;
		this.input2 = input2;
	}
}
