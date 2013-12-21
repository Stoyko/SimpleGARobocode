package id.ac.its.SimpleGARobocode;

public class Input {
	public enum InputType {
		velocity, 
		heading, 
		gunHeading,
		gunHeat,
		distanceToWestWall,
		distanceToNorthWall,
		distanceToEastWall,
		distanceToSOuthWall,
		constant1,
		constant2,
		constant10,
		constant90,
		enemyVelocity,
		enemyEnergy,
		enemyHeading,
		enemyBearing,
		enemyDistance,
		previousLine
	}
	
	public enum DoActuatorType {
		turnLeft,
		turnRight		
	}
	
	private int inputType;
	
	public InputType getInputTypeEnum() {
		return InputType.values()[this.inputType];
	}
	
	
	
	public DoActuatorType getDoActuatorTypeEnum() {
		return DoActuatorType.values()[this.inputType];
	}
	
	public int getInputType() {
		return inputType;
	}
	public void setInputType(int inputType) {
		this.inputType = inputType;
	}
	
	public int getPreviousLine() {
		return previousLine;
	}
	public void setPreviousLine(int previousLine) {
		this.previousLine = previousLine;
	}
	
	public Input(int inputType) {
		this.inputType = inputType;
	}
	
	public Input(int inputType, int previousLine) {
		this.inputType = inputType;
		this.previousLine = previousLine;
	}
	
}
