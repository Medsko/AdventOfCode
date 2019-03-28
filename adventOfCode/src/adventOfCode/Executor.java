package adventOfCode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import adventOfCode.day01.NeighboringNumberMatcher;
import adventOfCode.day10.HashKnot;
import adventOfCode.day11.HexEdClass;
import adventOfCode.day12.DigitalPlumber;
import adventOfCode.day13.HackRun;
import adventOfCode.day14.DiskDefragmenter;
import adventOfCode.day15.Judge;
import adventOfCode.day16.Choreographer;
import adventOfCode.day17.Spinlock;
import adventOfCode.day18.Duet;
import adventOfCode.day18.DuetProgram;
import adventOfCode.day18.DuetThread;
import adventOfCode.day19.TubeDiagram;
import adventOfCode.day19.TubeTraverser;
import adventOfCode.day2.CorruptionChecksumCalculator;
import adventOfCode.day20.ParticleTracker;
import adventOfCode.day21.FractalPainter;
import adventOfCode.day22.Sporifica;
import adventOfCode.day23.ExperimentalCoprocessor;
import adventOfCode.day24.MagneticBridgeBuilder;
import adventOfCode.day25.TuringMachine;
import adventOfCode.day4.AnagramPassphraseParser;
import adventOfCode.day4.PassphraseParser;
import adventOfCode.day5.TwistyTrampolineEscaper;
import adventOfCode.day6.MemoryReallocator;
import adventOfCode.day7.RecursiveCircus;
import adventOfCode.day8.RegisterRegistrator;
import adventOfCode.day9.Day9;
import log.CrappyLogger;
import util.ArrayUtil;
import util.IOUtils;
import util.Util;

public class Executor {
	
	private static AlgorithmTimer timer = new AlgorithmTimer(false);
	
	private static CrappyLogger logger = new CrappyLogger(false);
	
	private void p(String answer) {
		System.out.println(answer);
	}
	
	private String day1() {
		NeighboringNumberMatcher matcher = new NeighboringNumberMatcher();
		return "Thanks for all the yummy numbers! The solution to the captcha is: " + matcher.calculateSumOfMatchingNeighbors();
	}
	
	private static void day1b() {
		NeighboringNumberMatcher matcher = new NeighboringNumberMatcher();
		matcher.initialize(NeighboringNumberMatcher.INPUT);
		int matches = matcher.calculateSumMatchingHalfwayRound();
		System.out.println("Number of matches found: " + matches);
	}
	
	private String day2() {
		
		String fileName = "C:/Users/Medsko/Documents/ExcelInputForAOC.txt";
		
		CorruptionChecksumCalculator checksummer = new CorruptionChecksumCalculator();
		checksummer.setCalculateSumOfEvenDividers(true);
		return "Hope the fight against corruption is going well. The sum of all checksums is: " + checksummer.calculateCheckSum(fileName);
	}
	
	private String day3a() {
		
		return "Insert result from ManhattanTaxicab here";
	}
	
	private String day3b() {
		
		return "Insert result from ManhattanTaxicab2 here";
	}
	
	private String day4a() throws IOException {
		
		Path path = Paths.get("C:\\Temp\\AdventOfCode\\inputDay4a.txt");
		
		PassphraseParser parser = new PassphraseParser(path);
		
		return "Number of valid passphrases in input: " + parser.countValidPassphrases();
	}
	
	private String day4b() throws IOException {

		Path path = Paths.get("C:\\Temp\\AdventOfCode\\inputDay4a.txt");

		AnagramPassphraseParser parser = new AnagramPassphraseParser(path);
		
		return "Number of valid, non-anagram passphrases in input: " + parser.countValidPassphrases();
	}
	
	private String day5a() {
		
		TwistyTrampolineEscaper escaper = new TwistyTrampolineEscaper("C:/Temp/AdventOfCode/inputDay5a.txt");
		
		int nrOfSteps = escaper.calculateStepsToEscape();
		
		return "Number of steps to exit maze: " + nrOfSteps + ".";
	}
	
	private String day5b() {

		TwistyTrampolineEscaper escaper = new TwistyTrampolineEscaper("C:/Temp/AdventOfCode/inputDay5a.txt");
		escaper.setIsDay5b(true);
		int nrOfSteps = escaper.calculateStepsToEscape();
		
		return "Number of steps to exit maze: " + nrOfSteps + ".";
	}
	
	private String day6a() {
		
		MemoryReallocator reallocator = new MemoryReallocator(false);
		Integer[] classyInput = IOUtils.readIntegerInputToArray(IOUtils.PATH_TO_INPUT + "inputDay6a.txt");
		int [] input = IOUtils.convertIntegerArrayToIntArray(classyInput);
		int answer = reallocator.reallocate(input);
		
		return "Number of cycles before encountering endless loop: " + answer;
	}
	
	private String day6b() {
		
		MemoryReallocator reallocator = new MemoryReallocator(true);
		Integer[] classyInput = IOUtils.readIntegerInputToArray(IOUtils.PATH_TO_INPUT + "inputDay6a.txt");
		int [] input = IOUtils.convertIntegerArrayToIntArray(classyInput);
		int answer = reallocator.reallocate(input);
		
		return "Number of cycles between first and second encounter memory bank state: " + answer;
	}
	
	private String day7a() {
		RecursiveCircus circus = new RecursiveCircus();
		String name = circus.findNameOfLowestDisc();
		
		return "The name of the lowest disc is: " + name;
	}
	
	private String day7b() {
		RecursiveCircus circus = new RecursiveCircus();
		circus.setForB(true);
		
		return circus.findNameOfLowestDisc(); 
	}
	
	private String day8a() {
		RegisterRegistrator registrator = new RegisterRegistrator();
		int answer = registrator.register(IOUtils.PATH_TO_INPUT + "inputDay8.txt");
		return "The highest value in all registers is: " + answer;
	}
	
	private String day8b() {
		RegisterRegistrator registrator = new RegisterRegistrator(true);
		int answer = registrator.register(IOUtils.PATH_TO_INPUT + "inputDay8.txt");
		return "The highest value held in a register during the process is: " + answer;
	}
	
	private String day9a() {
		int answer = Day9.calculateTotalScore(false);
		return "The total score for all the groups in the input is: " + answer;
	}

	private String day9b() {
		int answer = Day9.calculateTotalScore(true);
		return "The total number of non-canceled characters in the garbage is: " + answer;
	}
	
	private String day10a() {
		HashKnot knotter = new HashKnot();
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay10.txt";
		int answer = knotter.knotIt(inputFile);
		return "After executing, the product of the first and second position is: " + answer;
	}
	
	private String day10b() {
		HashKnot knotter = new HashKnot();
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay10Test.txt";
		String answer = knotter.fullKnotIt(inputFile);
		return "Hexadecimal string representation of hash knot: " + answer;
	}
	
	private static void day11a() {
		HexEdClass hexEdClass = new HexEdClass();
		hexEdClass.retraceChild();
		int answer = hexEdClass.getDistanceToStart();
		System.out.println("Number of steps to get child back to start: " + answer);
	}
	
	private static void day11b() {
		HexEdClass hexEdClass = new HexEdClass(true);
		hexEdClass.retraceChild();
		int answer = hexEdClass.getDistanceToStart();
		System.out.println("Number of steps to get child back to start from furthest position: " 
				+ answer);
	}
	
	private static void day12a() {
		DigitalPlumber plumber = new DigitalPlumber();
		plumber.processPipes();
		int answer = plumber.getNrOfPipesConnectedToZero();
		System.out.println("Number of pipes connected to pipe zero: " + answer);
	}
	
	private static void day12b() {
		DigitalPlumber plumber = new DigitalPlumber();
		plumber.processPipes();
		int answer = plumber.getNrOfGroups();
		plumber.printGroupsSuccint();
		System.out.println("Number of groups: " + answer);
	}
	
	private static void day13a() {
		HackRun hackRun = new HackRun();
		hackRun.jamItIn();
		Util.underline(75);
		System.out.println("Total severity of run: " + hackRun.getTotalSeverity());
	}
	
	private static void day13b() {
		HackRun hackRun = new HackRun();
		int answer = hackRun.penetrate();
		Util.underline(75);
		System.out.println("Number of picoseconds of delay to avoid all scanners: " + answer);
	}
	
	private static void day14a() {
		DiskDefragmenter defragmenter = new DiskDefragmenter();
		defragmenter.defragment();
		System.out.println("Number of used squares: " + defragmenter.getTotalCountUsedSquares());
	}
	
	private static void day14b() {
		DiskDefragmenter defragmenter = new DiskDefragmenter();
		defragmenter.defragment();
		defragmenter.registerRegions();
		System.out.println("Number of regions: " + defragmenter.getNrOfRegions());
	}
	
	private static void day15a() {
		// Judge instance constructed with test values.
//		Judge judy = new Judge(65, 8921);
		
		Judge judy = new Judge(591, 393);
		judy.judge();
		System.out.println("Number of matching pairs: " + judy.getNrOfMatchingPairs());
	}
	
	private static void day15b() {
		// Judge instance constructed with test values.
//		Judge judy = new Judge(65, 4, 8921, 8);

		Judge judy = new Judge(591, 4, 393, 8);
		judy.judge();
		System.out.println("Number of (highly scrutinized) matching pairs: " 
				+ judy.getNrOfMatchingPairs());
	}
	
	private static void day16a() {
		// Pass 'true' for debug mode.
		Choreographer choreographer = new Choreographer(true);
		choreographer.perform();
		System.out.println("Number of instructions executed: " + choreographer.getNrOfInstructions());
		System.out.println("Final order of programs: " + 
				choreographer.getProgramsAsString().replace(", ", ""));
	}
	
	private static void day16b() {
		Choreographer choreographer = new Choreographer();
		choreographer.tour();
		System.out.println("Final order of programs after a billion cycles: " + 
				choreographer.getProgramsAsString().replace(", ", ""));
	}
	
	private static void day17a() {
		// Testing value: 3, expected outcome: 638.
//		Spinlock spinlock = new Spinlock(3);
		Spinlock spinlock = new Spinlock(354);
		spinlock.run();
		System.out.println("Value after last insertion: " + spinlock.getNextValue());
	}
	
	private static void day17b() {
//		Spinlock spinlock = new Spinlock(3, 2018);
		Spinlock spinlock = new Spinlock(354, 50_000_000);
		spinlock.run();
		System.out.println("Value after zero: " + spinlock.getValueAfterZero());
	}
	
	private static void day18a() {
		DuetProgram duet = new DuetProgram();
		duet.run();
		System.out.println("Value of recovered frequency: " + duet.getLastPlayedSound());
	}
	
	private static void day18b() {

		Duet duet = new Duet();
		int nrOfTimesOneSent = duet.runUntilDeadlock();
		System.out.println("Number of times program one sent a value: " + nrOfTimesOneSent);
	}
	
	private static void day19a() {
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay19.txt";
		TubeDiagram diagram = new TubeDiagram(inputFile);
		TubeTraverser traverser = new TubeTraverser(diagram);
		traverser.initialize();
		
		int counter = 1;
		timer.startAlgorithm("TubeTraverser main algorithm");
		
		while (traverser.processCharacter(traverser.readNextCharacter())) {
			counter++;
			if (counter % 10 == 0) {
				System.out.println("Iteration " + counter + ", " + traverser.toString());
			}
		}
		timer.stopAlgorithm();
		System.out.println("Letters encountered on the way: " 
				+ traverser.getEncounteredLetterString());
		
		System.out.println("Number of steps for challenge b: " + counter);
	}
	
	private static void day20a() {
		ParticleTracker tracker = new ParticleTracker(true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay20.txt";
		String testInputFile = IOUtils.PATH_TO_INPUT + "inputDay20Test.txt";
		tracker.initialize(inputFile);
		
		tracker.projectTrajectories();
		
		System.out.println("Particle that stays closest to zero in the long run: " 
				+ tracker.getTheOne().getId() + ", " + tracker.getTheOne().getOriginalInput());
	}
	
	private static void day20b() {
		ParticleTracker tracker = new ParticleTracker(true, true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay20.txt";
		String testInputFile = IOUtils.PATH_TO_INPUT + "inputDay20Test.txt";
		tracker.initialize(inputFile);
		
		tracker.projectTrajectories();
		
		System.out.println("Particle that stays closest to zero in the long run: " 
				+ tracker.getTheOne().getId() + ", " + tracker.getTheOne().getOriginalInput());
	}
	
	private static void day21a() {
		FractalPainter painter = new FractalPainter(true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay21.txt";
		painter.readRules(inputFile);
		painter.enhance(5);
		
//		String testInputFile = IOUtils.PATH_TO_INPUT + "inputDay21Test.txt";
//		painter.readRules(testInputFile);
//		painter.enhance(2);
		
		System.out.println("After the specified number of iterations, the fractal is: ");
		System.out.println(painter.toString());
		
		System.out.println("Number of pixels that are 'on': " + painter.getTotalPixelsOn());
	}
	
	private static void day21b() {
		FractalPainter painter = new FractalPainter(true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay21.txt";
		painter.setPrecision(ChronoUnit.MICROS);
		painter.readRules(inputFile);
		painter.enhance(18);
		// This might look like sh*t (for the record: it did/does).
		logger.log("After the specified number of iterations, the fractal is: ");
		logger.log(painter.toString());
		
		System.out.println("Number of pixels that are 'on': " + painter.getTotalPixelsOn());
	}
	
	private static void day22a() {
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay22.txt";
		String testInputFile = IOUtils.PATH_TO_INPUT + "inputDay22Test.txt";
		
		Sporifica spore = new Sporifica();
//		spore.initialize(testInputFile);
		spore.initialize(inputFile);
		System.out.println("Number of infections caused: " + spore.activate(10000, false));
	}
	
	private static void day22b() {
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay22.txt";
		String testInputFile = IOUtils.PATH_TO_INPUT + "inputDay22Test.txt";
		
		Sporifica spore = new Sporifica();
//		spore.initialize(testInputFile);
		spore.initialize(inputFile);

//		System.out.println("Number of infections caused: " + spore.activate(100, true));
		
		System.out.println("Number of infections caused: " + spore.activate(10_000_000, true));
	}
	
	private static void day23a() {
		ExperimentalCoprocessor processor = new ExperimentalCoprocessor(true, true);
		processor.initialize();
		processor.process();
		System.out.println("Number of times 'multiply' was invoked: " 
				+ processor.getMultiplyInvoked());
	}
	
	private static void day24a() {
		MagneticBridgeBuilder builder = new MagneticBridgeBuilder(false, true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay24.txt";
		String inputTestFile = IOUtils.PATH_TO_INPUT + "inputDay24Test.txt";
		
//		builder.initialize(inputTestFile);
		builder.initialize(inputFile);
		
		builder.buildBridges();
//		Integer[] strongestBridge = builder.findStrongestBridge();
		System.out.println("Total number of bridges found: " + builder.getNumberOfBridges());
		
		System.out.println("Strength of strongest bridge found: " 
				+ builder.getMaxStrength());
	}
	
	private static void day24b() {
		MagneticBridgeBuilder builder = new MagneticBridgeBuilder(false, true);
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay24.txt";
		String inputTestFile = IOUtils.PATH_TO_INPUT + "inputDay24Test.txt";
		
//		builder.initialize(inputTestFile);
		builder.initialize(inputFile);
		builder.buildBridges();

		Integer[] longestBridge = builder.getLongestBridge();
		
		System.out.println("Longest bridge found: " + builder.bridgeToString(longestBridge));
	}
	
	private static void day25a() {
		String inputFile = IOUtils.PATH_TO_INPUT + "inputDay25.txt";
		String inputTestFile = IOUtils.PATH_TO_INPUT + "inputDay25Test.txt";
		
		TuringMachine turing = new TuringMachine();
		turing.initialize(inputFile);
//		turing.initialize(inputTestFile);
		System.out.println("Diagnostic checksum: " + turing.performDiagnostics());
	}
	
	public static void main(String[] args) {

		Executor executor = new Executor();
//		System.out.println(executor.day1());
		Executor.day1b();
//		System.out.println(executor.day2());
//		System.out.println(executor.day3a());
		
//		try {
//			System.out.println(executor.day4a());
//			System.out.println(executor.day4b());
//		} catch (IOException ioex) {
//			System.out.println("I/O error occurred:");
//			ioex.printStackTrace();
//		}
		
//		System.out.println(executor.day5a());
//		System.out.println(executor.day5b());
//		System.out.println(executor.day6a());
//		System.out.println(executor.day6b());
//		System.out.println(executor.day7a());
//		executor.p(executor.day7b());
//		executor.p(executor.day8a());
//		executor.p(executor.day8b());
//		executor.p(executor.day9a());
//		executor.p(executor.day9b());
//		executor.p(executor.day10a());
//		executor.p(executor.day10b());
//		Executor.day11a();
//		Executor.day11b();
//		Executor.day12a();
//		Executor.day12b();
//		Executor.day13a();
//		Executor.day13b();
//		Executor.day14a();
//		Executor.day14b();
//		Executor.day15a();
//		Executor.day15b();
//		Executor.day16a();
//		Executor.day16b();
//		Executor.day17a();
//		Executor.day17b();
//		Executor.day18a();
//		Executor.day18b();
//		Executor.day19a();
//		Executor.day20a();
//		Executor.day20b();
//		Executor.day21a();
//		Executor.day21b();
//		Executor.day22a();
//		Executor.day22b();
//		Executor.day23a();
		
//		Executor.day24a();
//		Executor.day24b();
//		Executor.day25a();
	}
}




