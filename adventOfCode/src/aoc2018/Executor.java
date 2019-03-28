package aoc2018;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import aoc2018.day01.ChronoCalibrator;
import aoc2018.day02.BoxSorter;
import aoc2018.day03.FabricDivider;
import aoc2018.day04.GuardWatcher;
import aoc2018.day05.Polymer;
import aoc2018.day06.Coordinator;
import aoc2018.day07.AssemblyLine;
import aoc2018.day08.LicenceTree;
import aoc2018.day09.MarbleGame;
import aoc2018.day10.StarWatcher;
import aoc2018.day11.ChronalCharger;
import aoc2018.day12.SubterraneanPotter;
import aoc2018.day13.CartCourse;
import aoc2018.day14.ChocolateRecipeCreator;
import aoc2018.day15.Battleground;

public class Executor {

	private static void p(Object toPrint) {
		System.out.println(toPrint);
	}
	
	private static void day1a() {
		ChronoCalibrator calibrator = new ChronoCalibrator();
		calibrator.initialize();
		calibrator.calibrate();
		System.out.println("Final frequency: " + calibrator.getFrequency());
	}
	
	private static void day1b() {
		ChronoCalibrator calibrator = new ChronoCalibrator();
		calibrator.initialize();
		calibrator.findDoubleFrequency();
		System.out.println("First frequency encountered twice: " + calibrator.getFrequency());
	}
	
	private static void day2a() {
		BoxSorter sorter = new BoxSorter(true);
		sorter.initialize();
		sorter.calculateChecksum();
		System.out.println("Checksum for box ids: " + sorter.getChecksum());
	}
	
	private static void day2b() {
		BoxSorter sorter = new BoxSorter(true);
		sorter.initialize();
		sorter.findCommonality();
		System.out.println("Common letters between correct boxes: " + sorter.getCommonLetters());
	}
	
	private static void day3() {
		FabricDivider divider = new FabricDivider(false);
		divider.initialize();
		divider.applyAllClaims();
		System.out.println("Number of squares with overlapping claims: " 
				+ divider.getOverlappingClaimedSquares());
		System.out.println("Id of claim that does not overlap: " + divider.getOverlapFreeClaimId());
	}
	
	private static void day4() {
		GuardWatcher watcher = new GuardWatcher(true);
		watcher.initialize();
		watcher.watch();
		System.out.println("Guard id of sleepiest guard times the minute he was most often asleep: "
				+ watcher.determineSleepiestGuard(false));
		System.out.println("Guard id of the guard who slept most often at one minute: "
				+ watcher.determineSleepiestGuard(true));
	}

	private static void day5() {
		Polymer polymer = new Polymer(true);
		polymer.initialize();
		System.out.println("Units remaining after reduction: " + polymer.reduce());
		System.out.println("Smallest polymer size after removing most blocking unit: " 
				+ polymer.findShortestPolymerAfterRemovingOneType());
	}
	
	private static void day6() {
		Coordinator coordinator = new Coordinator(false);
		coordinator.initialize(false);
//		coordinator.marco();
//		System.out.println("Biggest area: " + coordinator.polo());
		
		// Challenge B:
		int safeSpaceSize = coordinator.determineSafeSpaceSize();
		System.out.println("Size of safe space: " + safeSpaceSize);
	}
	
	private static void day7() {
		AssemblyLine assembly = new AssemblyLine(true);
		assembly.initialize(false);
		// Challenge A:
//		assembly.lineUpSteps();
//		System.out.println("Order of execution: " + assembly.getOrderedSteps());
		
		// Challenge B:
		System.out.println("Total seconds needed for execution: " + assembly.constructSleigh());
	}
	
	private static void day8() {
		LicenceTree tree = new LicenceTree(true);
		tree.initialize(false);
		tree.grow();
		System.out.println("Sum of all metadata entries: " + tree.calculateSumMetadata());
		System.out.println("Value of the root node: " + tree.calculateRootValue());
	}
	
	private static void day9() {
		
		MarbleGame game = new MarbleGame(true, false);
		// Test cases.
//		game.initialize(9, 25); // Expected high score: 32
//		game.initialize(10, 1618); // Expected high score: 8317
		
		// Actual input.
//		game.initialize(446, 71522);
		
		// Challenge B input.
		game.initialize(446, 71522 * 100);
		
		game.play();
		System.out.println("High score: " + game.getHighScore());
	}
	
	private static void day10() {
		StarWatcher watcher = new StarWatcher(true);
		watcher.initialize(false);
		watcher.runUntilCoherent();
		System.out.println("Coherent message displayed after " + watcher.getTurn() + " seconds.");
	}
	
	private static void day11() {
		// Test chargers. 
//		ChronalCharger testChargerA = new ChronalCharger(true, 57);
//		ChronalCharger testChargerB = new ChronalCharger(true, 39);
//		testChargerA.fillGrid();
//		testChargerB.fillGrid();
//		System.out.println(testChargerA.getPowerLevelAt(122, 79)); // Expected: -5
//		System.out.println(testChargerB.getPowerLevelAt(217, 196)); // Expected: 0

//		ChronalCharger testChargerC = new ChronalCharger(true, 18);
//		testChargerC.fillGrid();
//		testChargerC.findHighestPowerUnit(3);
//		System.out.println(testChargerC.gridToString(testChargerC.getHighestPowerUnit()));
//		System.out.println(Arrays.toString(testChargerC.getHighestPowerUnitPosition()));
		
		// Actual input challenge A.
//		ChronalCharger charger = new ChronalCharger(true, 7803);
//		charger.fillGrid();
//		charger.findHighestPowerUnit(3);
//		System.out.println("Left upper corner of unit with the highest power level: " 
//				+ Arrays.toString(charger.getHighestPowerUnitPosition()));
		 
		// Challenge B - test input.
//		ChronalCharger bCharger = new ChronalCharger(true, 42);
//		bCharger.fillGrid();
//		bCharger.findHighestPowerUnitAnySize();
//		System.out.println("Total power of highest power unit: " + bCharger.getHighestPowerLevel());
//		System.out.println("Highest power unit: " 
//				+ bCharger.gridToString(bCharger.getHighestPowerUnit()));
//		System.out.println("Left upper corner of unit with the highest power level: " 
//				+ Arrays.toString(bCharger.getHighestPowerUnitPosition()));
		
		// Challenge B - challenge input.
		ChronalCharger ultimateCharger = new ChronalCharger(true, 7803);
		ultimateCharger.fillGrid();
		ultimateCharger.findHighestPowerUnitAnySize();
		System.out.println("Total power of highest power unit: " + ultimateCharger.getHighestPowerLevel());
		System.out.println("Highest power unit: " 
				+ ultimateCharger.gridToString(ultimateCharger.getHighestPowerUnit()));
		System.out.println("Left upper corner of unit with the highest power level: " 
				+ Arrays.toString(ultimateCharger.getHighestPowerUnitPosition()));
//		Left upper corner of unit with the highest power level: [230, 272, 17]

	}
	
	private static void day12() {
		SubterraneanPotter potter = new SubterraneanPotter(true);
		potter.initialize(false);
		// Challenge A.
		potter.runGenerations(20);
		p("Sum of the numbers of all pots which contain a plant: " 
				+ potter.sumOfPotNumbersContainingPlants());
		
		potter.initialize(false);
		potter.runGenerations(95);
		// After 95 generations, the amount added to the total each generation stabilizes to 91.
		long total = potter.sumOfPotNumbersContainingPlants();
		long generationsLeft = 50_000_000_000L - 95;

		total += generationsLeft * 91;
		p("Total after fifty billion generations: " + total);
//		long fifBil = 	 50_000_000_000L;
//		long answer = 4_550_000_002_111L;
	}
	
	private static void day13() {
		CartCourse course = new CartCourse(false);
		course.initialize(false);
		course.setIsChallengeA(false);
		course.run();
		p(course.getPrintableAnswer());
	}
	
	private static void day14() {
		ChocolateRecipeCreator chef = new ChocolateRecipeCreator(true);
		chef.initialize(false);
		chef.setIsChallengeA(false);
		chef.run();
		p(chef.getPrintableAnswer());
	}
	
	private static void day15() {
		Battleground battle = new Battleground(false);
		// Challenge A.
//		battle.setIsChallengeA(true);
//		battle.initialize(false);
//		battle.run();
//		p(battle.getPrintableAnswer());
		
		// Challenge B.
		battle = new Battleground(true);
		battle.setIsChallengeA(false);
		battle.initialize(false);
		battle.runB();
		p(battle.getPrintableAnswer());
	}
	
	public static void main(String[] args) {
//		Executor.day1a();
//		Executor.day1b();
//		Executor.day2a();
//		Executor.day2b();
//		Executor.day3();
//		Executor.day4();
//		Executor.day5();
//		Executor.day6();
//		Executor.day7();
//		Executor.day8();
//		Executor.day9();
//		Executor.day10();
//		Executor.day11();
//		Executor.day12();
//		Executor.day13();
//		Executor.day14();
		Executor.day15();

	}
	
}
