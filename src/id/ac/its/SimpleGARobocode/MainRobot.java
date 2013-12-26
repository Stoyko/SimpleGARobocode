package id.ac.its.SimpleGARobocode;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.RobocodeFileWriter;
import robocode.ScannedRobotEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MainRobot extends AdvancedRobot {
	TableREX tableRun, tableOnScanned, tableOnHitWall, tableOnHitBullet, tableOnHitRobot = null;
	int state, genomeNumber;
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
	public void initialize() throws IOException{
		
		BufferedReader buf = null;
		try {
			// Text file reading part
			buf = new BufferedReader(new FileReader(getDataFile("ga.txt")));
			
		} catch (FileNotFoundException e) {
			// Generate the first randomize file
			int defaultGenomeNumber = 16;
			
			
			// Read buf file
			try {
				buf = new BufferedReader(new FileReader(getDataFile("ga.txt")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// Read the state
		try {
			state = Integer.parseInt(buf.readLine());
			genomeNumber = Integer.parseInt(buf.readLine());
			for(int i = 0; i < genomeNumber; i++) {
				genomeStringRun.add(buf.readLine());
				genomeStringOnScanned.add(buf.readLine());
				genomeStringOnHitWall.add(buf.readLine());
				genomeStringOnHitBullet.add(buf.readLine());
				genomeStringOnHitRobot.add(buf.readLine());
			}
			
			for(int i = 0; i < genomeNumber; i++) {
				fitnessValue.add(Integer.parseInt(buf.readLine()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Is this crossing and mutating state?
		if(state == 0) {
			// Crossing and mutating part
			GeneticAlgorithmRobocode ga = new GeneticAlgorithmRobocode(genomeNumber, genomeStringRun, genomeStringOnScanned, genomeStringOnHitWall, genomeStringOnHitBullet, genomeStringOnHitRobot, fitnessValue);
			ga.doCrossingMutating();
		
			// Write to a file
			RobocodeFileWriter fw = new RobocodeFileWriter(new File("ga.txt"));
            fw.write("0\n");
			for (int i = 0; i < genomeNumber; i++) {
				fw.write(genomeStringRun.get(i) + "\n");
				fw.write(genomeStringOnScanned.get(i) + "\n");
				fw.write(genomeStringOnHitWall.get(i) + "\n");
				fw.write(genomeStringOnHitBullet.get(i) + "\n");
				fw.write(genomeStringOnHitRobot.get(i) + "\n");
				
				
			}
			
			for (int i = 0; i < genomeNumber; i++) {
				fw.write("0\n");
			}
		}
		
        // Testing part
        try {
            tableRun = new TableREX(genomeStringRun.get(state - 1));
            tableOnScanned = new TableREX(genomeStringOnScanned.get(state - 1));
            tableOnHitWall = new TableREX(genomeStringOnHitWall.get(state - 1));
            tableOnHitRobot = new TableREX(genomeStringOnHitRobot.get(state - 1));
            tableOnHitBullet = new TableREX(genomeStringOnHitBullet.get(state - 1));
        } catch (Exception e) {
            
        }
	}
	
	public void run() {
		try {
			this.initialize();
		} catch (IOException e) {
			
		}
		
		initialized = true;
		while(true) {
			// Read run table
			for(int i = 0; i < tableRun.getTableRow().size(); i++) {
				// Calculate result, depends on operator
				Row.Operator operator = tableRun.getTableRow().get(i).getOperator();
				
				//double inputValue2 = 
				switch(tableRun.getTableRow().get(i).getOperator()) {
				case greatherThan:
					//tableRun.getTableRow().get(i).setResult((double) );
					break;
					
				}
			}
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {

	}

	@Override
	public void onHitRobot(HitRobotEvent e) {
		
	}
	
	
	

}
