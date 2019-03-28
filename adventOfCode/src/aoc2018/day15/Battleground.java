package aoc2018.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import aoc2018.AAoCA;
import aoc2018.CharMatrix;
import util.MatrixUtil;

/**
 * The stage for the epic battle between the elves and goblins... 
 */
public class Battleground extends AAoCA {

	/** The stage for a grim battle to the death. */	
	private CharMatrix cave;
	
	private List<Unit> units;
	
	// PROCESSING	
	private CaveScanner scanner;
	
	private int elvesAttackPower;
	
	private boolean elfDied;
	
	// OUTPUT
	private int rounds;
	
	
	// Challenge B first try: 40 * 1502 = 60080 (too low).
	// Second try: 60200 (still too low).
	
	public Battleground(boolean isCommandLineFine) {
		super(isCommandLineFine);
		elvesAttackPower = 2;
	}
	
	@Override
	public boolean initialize(boolean isTest) {
		return initialize(isTest, null);
	}
	
	public boolean initialize(boolean isTest, String inputFileName) {
		elfDied = false;
		// Increase the elf attack power in case another battle will be fought.
		elvesAttackPower++;
		
		if (inputLines == null) {
			if (!super.initialize(2018, 15, isTest, inputFileName)) {
				return false;
			}
		}
		
		cave = new CharMatrix(inputLines.size());
		for (int i=0; i<inputLines.size(); i++) {
			cave.setRow(i, inputLines.get(i).toCharArray());
		}
		
		scanner = new CaveScanner(cave);
		
		units = new ArrayList<>();
		List<int[]> elfPositions = cave.getOccurrences('E');
		List<int[]> goblinPositions = cave.getOccurrences('G');
		
		for (int[] elfPosition : elfPositions) {
			if (isChallengeA) {
				// In challenge A, elves have 3 attack power.
				units.add(new Unit('E', elfPosition));
			} else {
				// For challenge B, the attack power of the elves will vary.
				units.add(new Unit('E', elfPosition, elvesAttackPower, 200));
			}
		}
		
		for (int[] goblinPosition : goblinPositions) {
			// Goblins always have an attack power of 3.
			units.add(new Unit('G', goblinPosition));
		}
				
		return true;
	}


	@Override
	public boolean run() {

		rounds = 0;
		timer.startSubAlgorithm();
		
		while (round()) {
			if (isTest) {
				logger.log(MatrixUtil.toString(cave.getMatrix()));
			}
		}
		
		logger.log("Combat finished at round " + rounds + " Elf attack power: " + elvesAttackPower 
				+ ". Hp left per unit: ");
		
		for (Unit unit : units) {
			logger.log(unit);
		}
		timer.stopSubAlgorithm();
		return true;
	}
	
	public boolean runB() {

		timer.startAlgorithm("Battleground.runB()");
		
		do {
			initialize(isTest);
			run();			
		} while (elfDied);
		
		timer.stopAlgorithm();
		
		// If the loop was broken, that means all elves lived through a battle!
		printableAnswer = "Elf attack power necessary to prevent any elf's death: " 
				+ elvesAttackPower + System.lineSeparator();
		printableAnswer += getOutcome();
		return true;
	}
		
	public boolean round() {
		// For all units, in order of reading position...
		Collections.sort(units);
		
		for (Unit unit : units) {
			// Skip dead units.
			if (unit.isDead()) {
				continue;
			}
			// Identify all possible targets.
			char enemy = unit.getEnemySymbol();
			List<int[]> possibleTargets = cave.getOccurrences(enemy);
			
			if (possibleTargets.size() == 0) {
				// No more targets available. This unit's race won the battle!
				printableAnswer = unit.getRace().getPluralName() + " won the battle!";
				printableAnswer += System.lineSeparator();
				printableAnswer += getOutcome();
				return false;
			}
			
			// Check whether the unit is already adjacent to a target. If so, attack. 
			if (attack(unit, possibleTargets)) {
				// Attack was performed. Continue on to the next unit.
				continue;
			}
			
			// No unit in range yet. Find the shortest path to any square adjacent to an enemy.
			List<int[]> inRangeSquares = new ArrayList<>();
			for (int[] possibleTarget : possibleTargets) {
				inRangeSquares.addAll(scanner.determineAdjacentOpenSquares(possibleTarget));
			}
			// Determine the shortest path to a position in range of an enemy.
			PathSection path = scanner.determinePathToClosestReachableSquare(
					inRangeSquares, unit.getPosition());
			if (path == null) {
				// No enemies are reachable.
				continue;
			}
			// Move one square in that direction.
			cave.setCharAt(unit.getPosition(), '.');
			unit.move(path.getPosition());
			cave.setCharAt(unit.getPosition(), unit.getRace().symbol);
			
			// Check whether we are now in range of an enemy, and attack if so.
			attack(unit, possibleTargets);
			
			// If an elf has died and this is challenge B, the battle is lost.
			if (!isChallengeA && elfDied) {
				return false;
			}
		}
		// Remove any units that died this round from the list.
		units = units.stream()
				.filter((unit)-> !unit.isDead())
				.collect(Collectors.toList());

		rounds++;
		
		return true;
	}

	/**
	 * Determines whether the given unit is in range of any enemies and if so, attacks.
	 * If more than one possible target is determined this way, the enemy with the lowest hp is
	 * selected. If that still results in more than one option, the first enemy according to
	 * reading order is selected.
	 * @return {@code true} if a valid target could be attacked. 
	 */
	private boolean attack(Unit unit, List<int[]> possibleTargets) {
		List<int[]> adjacentSquares = scanner.determineAdjacentSquares(unit.getPosition());
		
		List<Unit> targets = new ArrayList<>(); 		
		for (int[] adjacentSquare : adjacentSquares) {
			if (scanner.containsPosition(possibleTargets, adjacentSquare)) {
				Optional<Unit> optionalTarget = getByPosition(adjacentSquare);
				if (!optionalTarget.isPresent()) {
					logger.log("Error while determining a target for attack!");
					return false;
				}
				targets.add(optionalTarget.get());
			}
		}
		
		if (targets.size() == 0) {
			// No valid targets found.
			return false;
		}
		// Determine which target should be attacked.
		Unit unitToAttack = null;
		for (Unit target : targets) {
			// The unit with the least hit points left should be attacked.
			// If two units have equally low HP, the first one according to reading order should
			// be picked. As the potential targets are already in reading order, the first unit
			// encountered with the lowest HP should be attacked.
			if (unitToAttack == null || target.getHp() < unitToAttack.getHp()) {
				unitToAttack = target;
			}
		}
		// Apply one hit to the target.
		if (unitToAttack.takeHit(unit)) {
			// The unit died. Remove the representation of the unit on the battleground.
			cave.setCharAt(unitToAttack.getPosition(), '.');
			// If this is challenge b and an elf has died, the battle is already lost.
			if (!isChallengeA && unitToAttack.getRace() == Unit.Race.ELF) {
				logForTest("Battle with elf attack power = " + elvesAttackPower + ": " 
						+ "an elf died in round " + rounds);
				elfDied = true;
			}
		}

		return true;
	}
	
	private String getOutcome() {
		// Remove any dead units from the list.
		units = units.stream()
				.filter((u)-> !u.isDead())
				.collect(Collectors.toList());
		// Outcome is product of number of rounds played times total hit points left.
		int totalHp = calculateTotalRemainingHp();
		int outcome = rounds * totalHp;
		return "Outcome: " + rounds + " * " + totalHp + " = " + outcome;
	}
	
	public int calculateTotalRemainingHp() {
		int total = 0;
		for (Unit unit : units) {
			total += unit.getHp();
		}
		return total;
	}
	
	private Optional<Unit> getByPosition(int[] position) {
		return units.stream()
			.filter((unit)-> Arrays.equals(position, unit.getPosition()))
			.findFirst();
	}
	
	public CharMatrix getCave() {
		return cave;
	}	
}
