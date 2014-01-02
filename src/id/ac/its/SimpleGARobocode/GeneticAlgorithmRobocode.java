package id.ac.its.SimpleGARobocode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmRobocode {

	int genomeNumber;
	List <String>  genomeStringRun = null, 
				   genomeStringOnScanned = null, 
				   genomeStringOnHitWall = null, 
				   genomeStringOnHitBullet = null, 
				   genomeStringOnHitRobot = null;
	List <Integer> fitnessValue = null;
	Random random = new Random();

	public GeneticAlgorithmRobocode(int genomeNumber, List<String> genomeStringRun, List<String> genomeStringOnScanned, List<String> genomeStringOnHitWall, List<String> genomeStringOnHitBullet, List<String> genomeStringOnHitRobot, List<Integer> fitnessValue) {
		this.genomeNumber = genomeNumber;
		this.genomeStringRun = genomeStringRun;
		this.genomeStringOnScanned = genomeStringOnScanned;
		this.genomeStringOnHitWall = genomeStringOnHitWall;
		this.genomeStringOnHitBullet = genomeStringOnHitBullet;
		this.genomeStringOnHitRobot = genomeStringOnHitRobot;
		this.fitnessValue = fitnessValue;
	}
	
	public void doCrossingMutating() {
		List <String> newGenomeStringRun = new ArrayList<String>(),
					  newGenomeStringOnScanned = new ArrayList<String>(),
					  newGenomeStringOnHitWall = new ArrayList<String>(),
					  newGenomeStringOnHitBullet = new ArrayList<String>(),
					  newGenomeStringOnHitRobot = new ArrayList<String>();
		String p1, p2;
		String child;
		
		for (int i = 0; i < this.genomeNumber; i++) {
			// Crossing and Mutating for Run
			p1 = tournamentSelection(genomeStringRun);
			p2 = tournamentSelection(genomeStringRun);
			
			child = getChild(p1, p2);
			child = mutate(child);
			
			newGenomeStringRun.add(child);
			
			// Crossing and Mutating for OnScanned
			p1 = tournamentSelection(genomeStringOnScanned);
			p2 = tournamentSelection(genomeStringOnScanned);
			
			child = getChild(p1, p2);
			child = mutate(child);
			
			newGenomeStringOnScanned.add(child);
			
			// Crossing and Mutating for OnHitWall
			p1 = tournamentSelection(genomeStringOnHitWall);
			p2 = tournamentSelection(genomeStringOnHitWall);
			
			child = getChild(p1, p2);
			child = mutate(child);
			
			newGenomeStringOnHitWall.add(child);
			
			// Crossing and Mutating for OnHitBullet
			p1 = tournamentSelection(genomeStringOnHitBullet);
			p2 = tournamentSelection(genomeStringOnHitBullet);
			
			child = getChild(p1, p2);
			child = mutate(child);
			
			newGenomeStringOnHitBullet.add(child);
			
			// Crossing and Mutating for OnHitRobot
			p1 = tournamentSelection(genomeStringOnHitRobot);
			p2 = tournamentSelection(genomeStringOnHitRobot);
			
			child = getChild(p1, p2);
			child = mutate(child);
			
			newGenomeStringOnHitRobot.add(child);
			
		}
		
		// Change all genome with new genome
		genomeStringRun.clear();
		genomeStringRun.addAll(newGenomeStringRun);
		
		genomeStringOnScanned.clear();
		genomeStringOnScanned.addAll(newGenomeStringOnScanned);
		
		genomeStringOnHitWall.clear();
		genomeStringOnHitWall.addAll(newGenomeStringOnHitWall);
		
		genomeStringOnHitBullet.clear();
		genomeStringOnHitBullet.addAll(newGenomeStringOnHitBullet);
		
		genomeStringOnHitRobot.clear();
		genomeStringOnHitRobot.addAll(newGenomeStringOnHitRobot);
		
		fitnessValue.clear();
		for(int i = 0; i < genomeNumber; i++){
			fitnessValue.add(-100);
		}
	}

	private String mutate(String p) {
		StringBuilder sp = new StringBuilder(p);
		for (int i = 0; i < 704; i++) {
            if (Math.random() <= 0.015) {
                // Create random gene
                int gene = random.nextInt(1);
                if (gene == 0) {
                    sp.setCharAt(i, '0');
                } else {
                	sp.setCharAt(i, '1');
                }
            }
        }
		
		return sp.toString();
	}

	private String getChild(String p1, String p2) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 704; i++) {
			if(Math.random() <= 0.5) {
				sb.append(p1.charAt(i));
			} else {
				sb.append(p2.charAt(i));
			}
		}
		
		return sb.toString();
	}

	private String tournamentSelection(List<String> p) {
		// Get only 5 of the individual
		List<String> selectedIndividual = new ArrayList<String>();
		
		for(int i = 0; i < 5; i++) {
			int randomValue = (int) Math.random() * this.genomeNumber;
			selectedIndividual.add(p.get(randomValue));
		}
		
		// Get best and second best fittest value
		int maxFitness = 0;
		int indexOfMax = 0;
		for(int i = 0; i < 5; i++) {
			if(maxFitness <= this.fitnessValue.get(p.indexOf(selectedIndividual.get(i)))) {
				maxFitness = this.fitnessValue.get(p.indexOf(selectedIndividual.get(i)));
				indexOfMax = i;
			}
		}
		
		return p.get(indexOfMax);
	}

	
}
