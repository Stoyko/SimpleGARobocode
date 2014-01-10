package id.ac.its.SimpleGARobocode;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobocodeFileWriter;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.util.*;
import id.ac.its.SimpleGARobocode.Row.Operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MainRobot extends AdvancedRobot {
	TableREX tableRun, tableOnScanned, tableOnHitWall, tableOnHitBullet, tableOnHitRobot = null;
	int state = 0, genomeNumber = 16;
	List <String> genomeStringRun = new ArrayList<String>(), 
				  genomeStringOnScanned = new ArrayList<String>(), 
				  genomeStringOnHitWall = new ArrayList<String>(), 
				  genomeStringOnHitBullet = new ArrayList<String>(), 
				  genomeStringOnHitRobot = new ArrayList<String>();
	List <Integer> fitnessValue  = new ArrayList<Integer>();
	boolean initialized = false;
	
	/*
	 * This method purpose is to initialize GeneticAlgorithm Robocode Mechanism
	 * by generating new population (or use existing) from/to a text file
	 * and get/set the current testing state (e.g. crossing/mutating part or testing n-th genome part
	 *
	 * Data will be saved in ga.txt file with this format :
	 * 
	 * 0 	# Current state (0 for crossing/mutating, > 0for testing n-th part)
	 * 1 	# Total genome in population
	 * 10010010101010011001011001010101010101010111010101  	# tableRun Genome #1
	 * 10101010101001101010011001010101010101010101001100 	# tableOnScanned Genome #1
	 * 10111011011010110110111101010101010011001010101010	# tableOnHitWall Genome #1
	 * 11011110101010101001011001010101010101010101001010	# tableOnHitBullet	Genome #1
	 * 10010101010101010101010100101010101010111010101001 	# tableOnHitRobot Genome #1
	 * 2002		# Fitness value for Genome #1
	 */
	
	private void doWriteFile() throws IOException {
        RobocodeFileWriter fw = new RobocodeFileWriter(getDataFile("ga.txt"));
        fw.write(state + "\n");
        fw.write(genomeNumber + "\n");
        for (int i = 0; i < genomeNumber; i++) {
            fw.write(genomeStringRun.get(i) + "\n");
            fw.write(genomeStringOnScanned.get(i) + "\n");
            fw.write(genomeStringOnHitWall.get(i) + "\n");
            fw.write(genomeStringOnHitBullet.get(i) + "\n");
            fw.write(genomeStringOnHitRobot.get(i) + "\n");
            fw.write(fitnessValue.get(i) + "\n");
        }
        
        fw.close();
	}
	
	public void initialize() throws IOException{
		try {
            BufferedReader buf = null;
            // Text file reading part
            
            if(getDataFile("ga.txt").length() == 0) {
                // Generate the first randomize file
                
                // Generate Random String for all genome
                // FIRST CONCERN HERE
                StringBuilder sb = new StringBuilder();
                this.state = 1;
                for (int i = 0; i < this.genomeNumber; i++) {
                    // Table Run
                    sb.delete(0, sb.length());
                    for(int k = 0; k < 704; k++) {
                        sb.append((int) (Math.random() * 100) % 2);
                    }
                    genomeStringRun.add(sb.toString());
                    
                    // Table OnScanned
                    sb.delete(0, sb.length());
                    for(int k = 0; k < 704; k++) {
                        sb.append((int) (Math.random() * 100) % 2);
                    }
                    genomeStringOnScanned.add(sb.toString());
                    
                    
                    // Table OnHitWall
                    sb.delete(0, sb.length());
                    for(int k = 0; k < 704; k++) {
                        sb.append((int) (Math.random() * 100) % 2);
                    }
                    genomeStringOnHitWall.add(sb.toString());
                    

                    // Table OnHitRobot
                    sb.delete(0, sb.length());
                    for(int k = 0; k < 704; k++) {
                        sb.append((int) (Math.random() * 100) % 2);
                    }
                    genomeStringOnHitRobot.add(sb.toString());
                    
                    
                    // Table OnHitBullet
                    sb.delete(0, sb.length());
                    for(int k = 0; k < 704; k++) {
                        sb.append((int) (Math.random() * 100) % 2);
                    }
                    genomeStringOnHitBullet.add(sb.toString());
                    
                    fitnessValue.add(-100);
                    
                }
                this.doWriteFile();
                
            }
            
            // Read the state
            try {
                
                buf = new BufferedReader(new FileReader(getDataFile("ga.txt")));
                state = Integer.parseInt(buf.readLine());
                genomeNumber = Integer.parseInt(buf.readLine());
                for(int i = 0; i < genomeNumber; i++) {
                    genomeStringRun.add(buf.readLine());
                    genomeStringOnScanned.add(buf.readLine());
                    genomeStringOnHitWall.add(buf.readLine());
                    genomeStringOnHitBullet.add(buf.readLine());
                    genomeStringOnHitRobot.add(buf.readLine());
                    fitnessValue.add(Integer.parseInt(buf.readLine()));
                }
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            // Is this crossing and mutating state?
            if(state == this.genomeNumber) {
            	// Reset state
            	state = 0;
            }
            if(state == 0) {
                // Crossing and mutating part
                GeneticAlgorithmRobocode ga = new GeneticAlgorithmRobocode(genomeNumber, genomeStringRun, genomeStringOnScanned, genomeStringOnHitWall, genomeStringOnHitBullet, genomeStringOnHitRobot, fitnessValue);
                ga.doCrossingMutating();
                state = state + 1; 
                // Write to a file
                this.doWriteFile();
            }
            
            
            
            // Testing part
            tableRun = new TableREX(genomeStringRun.get(state - 1));
            tableOnScanned = new TableREX(genomeStringOnScanned.get(state - 1));
            tableOnHitWall = new TableREX(genomeStringOnHitWall.get(state - 1));
            tableOnHitRobot = new TableREX(genomeStringOnHitRobot.get(state - 1));
            tableOnHitBullet = new TableREX(genomeStringOnHitBullet.get(state - 1));
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchOfOperator(Row.Operator operator, Row aRow, double inputValue1, double inputValue2) {
		switch(operator) {
        case greatherThan:
            aRow.setResult(inputValue1 > inputValue2? 1.0 : 0.0);
            break;
        case absoluteValue:
            aRow.setResult(Math.abs(inputValue1));
            break;
        case addition:
            aRow.setResult(inputValue1 + inputValue2);
            break;
        case and:
            aRow.setResult((int) inputValue1 & (int) inputValue2);
            break;
        case controlActuator:
            // Unimplemented yet
            Input.DoActuatorType actuatorType = aRow.getInput1().getDoActuatorTypeEnum();
            switch(actuatorType) {
            case back:
                this.back(inputValue2);
                break;
            case fire:
                this.fire(inputValue2);
                break;
            case forward:
                this.ahead(inputValue2);
                break;
            case turnLeft:
                this.turnLeft(inputValue2);
                break;
            case turnRight:
                this.turnRight(inputValue2);
                
            }
        case division:
            if(inputValue2 == 0) {
            	inputValue2 = 1;
            }
        	aRow.setResult(inputValue1 / inputValue2);
            break;
        case equal:
            aRow.setResult(inputValue1 == inputValue2? 1.0 : 0.0);
            break;
        case generateConstant:
            aRow.setResult((int) (Math.random() * 100.0));
            break;
        case lessThan:
                aRow.setResult(inputValue1 < inputValue2? 1.0 : 0.0);
                break;
            case modulo:
                aRow.setResult(inputValue1 % inputValue2);
                break;
            case multiplication:
                aRow.setResult(inputValue1 * inputValue2);
                break;
            case normalizeRelativeAngle:
                aRow.setResult(Utils.normalRelativeAngle(inputValue1));
                break;
            case not:
                aRow.setResult(~((int) inputValue1));
                break;
            case or:
                aRow.setResult((int) inputValue1 & (int) inputValue2);
                break;
            case randomFloat:
                aRow.setResult(Math.random() * 100.0);
                break;
            case substraction:
                aRow.setResult(inputValue1 - inputValue2);
            }
	}
	
	private double getInputValue(Input.InputType inputType, ScannedRobotEvent sre, int previousLine, int thisLine) {
		switch(inputType) {
		case constant1:
			return 1.0;
		case constant10:
			return 10.0;
		case constant2:
			return 2.0;
		case constant90:
			return 90.0;
		case distanceToEastWall:
			// How?
			break;
		case distanceToNorthWall:
			// How?
			break;
		case distanceToSOuthWall:
			// How?
			break;
		case distanceToWestWall:
			// How?
			break;
		case enemyBearing:
			if(sre != null) {
				return sre.getBearing();
			} else {
				// Doesn't decided yet
			}
		case enemyDistance:
			if (sre != null) {
				return sre.getDistance();
			} else {
				// Doesn't decided yet
			}
		case enemyEnergy:
			if (sre != null) {
				return sre.getEnergy();
			} else {
				// Doesn't decided yet
			}
		case enemyHeading:
			if (sre != null) {
				return sre.getHeading();
			} else {
				// Doesn't decided yet
			}
		case enemyVelocity:
			if (sre != null) {
				return sre.getVelocity();
			} else {
				// Doesn't decided yet
			}
		case gunHeading:
			return this.getGunHeading();
		case gunHeat:
			return this.getGunHeat();
		case heading:
			return this.getHeading();
		case previousLine:
			if (thisLine == 0) {
				return tableRun.getTableRow().get(0).getResult();
			} else {
                return tableRun.getTableRow().get(thisLine - (previousLine % thisLine)).getResult();
			}
		}
		
		return 1.0;
	}
	
	public void run() {
		try {
			this.initialize();
		} catch (IOException e) {
			
		}
		
		try {
            initialized = true;
            while(true) {
                // Read run table
                for(int i = 0; i < tableRun.getTableRow().size(); i++) {
                    // Calculate result, depends on operator
                    Row.Operator operator = tableRun.getTableRow().get(i).getOperator();
                    Row aRow = tableRun.getTableRow().get(i);
                    double inputValue1 = getInputValue(aRow.getInput1().getInputTypeEnum(), null, aRow.getInput1().getPreviousLine(), i);
                    double inputValue2 = getInputValue(aRow.getInput2().getInputTypeEnum(), null, aRow.getInput2().getPreviousLine(), i);
                    if(i % 11 == 0) {
                    	this.switchOfOperator(Row.Operator.controlActuator, aRow, inputValue1, inputValue2);
                    } else {
                    	this.switchOfOperator(operator, aRow, inputValue1, inputValue2);
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		for(int i = 0; i < tableOnScanned.getTableRow().size(); i++) {
			Row.Operator operator = tableOnScanned.getTableRow().get(i).getOperator();
			Row aRow = tableOnScanned.getTableRow().get(i);
			double inputValue1 = getInputValue(aRow.getInput1().getInputTypeEnum(), e, aRow.getInput1().getPreviousLine(), i);
			double inputValue2 = getInputValue(aRow.getInput2().getInputTypeEnum(), e, aRow.getInput2().getPreviousLine(), i);
			if(i % 11 == 0) {
            	this.switchOfOperator(Row.Operator.controlActuator, aRow, inputValue1, inputValue2);
            } else {
            	this.switchOfOperator(operator, aRow, inputValue1, inputValue2);
            }
			
		}
		fire(10);
		
	}

	@Override
	public void onHitRobot(HitRobotEvent e) {
		for(int i = 0; i < tableOnHitRobot.getTableRow().size(); i++) {
			Row.Operator operator = tableOnHitRobot.getTableRow().get(i).getOperator();
			Row aRow = tableOnHitRobot.getTableRow().get(i);
			double inputValue1 = getInputValue(aRow.getInput1().getInputTypeEnum(), null, aRow.getInput1().getPreviousLine(), i);
			double inputValue2 = getInputValue(aRow.getInput2().getInputTypeEnum(), null, aRow.getInput2().getPreviousLine(), i);
			if(i % 11 == 0) {
            	this.switchOfOperator(Row.Operator.controlActuator, aRow, inputValue1, inputValue2);
            } else {
            	this.switchOfOperator(operator, aRow, inputValue1, inputValue2);
            }
			
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		for(int i = 0; i < tableOnHitWall.getTableRow().size(); i++) {
			Row.Operator operator = tableOnHitWall.getTableRow().get(i).getOperator();
			Row aRow = tableOnHitWall.getTableRow().get(i);
			double inputValue1 = getInputValue(aRow.getInput1().getInputTypeEnum(), null, aRow.getInput1().getPreviousLine(), i);
			double inputValue2 = getInputValue(aRow.getInput2().getInputTypeEnum(), null, aRow.getInput2().getPreviousLine(), i);
			if(i % 11 == 0) {
            	this.switchOfOperator(Row.Operator.controlActuator, aRow, inputValue1, inputValue2);
            } else {
            	this.switchOfOperator(operator, aRow, inputValue1, inputValue2);
            }
			
		}
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		for(int i = 0; i < tableOnHitBullet.getTableRow().size(); i++) {
			Row.Operator operator = tableOnHitBullet.getTableRow().get(i).getOperator();
			Row aRow = tableOnHitBullet.getTableRow().get(i);
			double inputValue1 = getInputValue(aRow.getInput1().getInputTypeEnum(), null, aRow.getInput1().getPreviousLine(), i);
			double inputValue2 = getInputValue(aRow.getInput2().getInputTypeEnum(), null, aRow.getInput2().getPreviousLine(), i);
			if(i % 11 == 0) {
            	this.switchOfOperator(Row.Operator.controlActuator, aRow, inputValue1, inputValue2);
            } else {
            	this.switchOfOperator(operator, aRow, inputValue1, inputValue2);
            }
			
		}
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		fitnessValue.set(state - 1, event.getResults().getScore());
        state++;
		try {
			this.doWriteFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
