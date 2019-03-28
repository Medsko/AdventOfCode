package aoc2018.day15;

import java.util.Arrays;

import util.MatrixUtil;

/**
 * A soldier in the battle for hot chocolate that took place on day 15.
 * A collection of {@link Unit}s can be ordered by 'reading order', i.e. from top to bottom and
 * from left to right. 
 */
public class Unit implements Comparable<Unit> {

	/**
	 * Sure, the same could be accomplished with a simple boolean. But it would either have to be
	 * named isElf or isGoblin, and I would argue that would smack of razy lacism. Moreover,
	 * what if this battle should be extended to comprise more races...five, for instance?
	 */
	public enum Race {
		ELF('E'),
		GOBLIN('G');
		
		public char symbol;
		
		Race(char symbol) {
			this.symbol = symbol;
		}
		
		public static Race fromSymbol(char symbol) {
			switch (symbol) {
				case 'E' : return ELF;
				case 'G' : return GOBLIN;
				default : return null;
			}
		} 
		
		public String getPluralName() {
			return name() + "S";
		}
	}
	
	private Race race;
	
	private int[] position;
	
	private int attackPower;
	
	private int hp;
	
	public Unit(char symbol, int[] position) {
		// All units have 3 attack power and start with 200 hit points (in challenge A at least).
		this(symbol, position, 3, 200);
	}
	
	public Unit(char symbol, int[] position, int attackPower, int hp) {
		race = Race.fromSymbol(symbol);
		this.position = position;
		this.attackPower = attackPower;
		this.hp = hp;
	}
	
	public void move(int[] newPosition) {
		if (!MatrixUtil.isAdjacent(position, newPosition)) {
			throw new IllegalArgumentException("The new position is not adjacent to the "
					+ "current position!");
		}
		position = newPosition;
	}
	
	/**
	 * Takes a hit from the given unit.
	 * @return {@true} if the hit was fatal.
	 */
	public boolean takeHit(Unit other) {
		hp -= other.getAttackPower();
		return isDead();
	}
	
	public boolean isDead() {
		return hp <= 0;
	}
	
	/**
	 * Determines whether the given {@link Unit} is an enemy of this one.
	 * @return {@code true} if the other unit is of a different race. A sad definition of enmity,
	 * but one that adheres to the rules of this day's challenge.
	 */
	public boolean isEnemy(Unit other) {
		return other.race != race;
	}
	
	@Override
	public String toString() {
		return race.toString() + " at " + Arrays.toString(position) + "(" + hp + ")";
	}
	
	@Override
	public int compareTo(Unit other) {
		// First, compare by y position.
		if (position[1] < other.position[1])
			return -1;
		else if (position[1] > other.position[1])
			return 1;
		else if (position[0] < other.position[0])
			return -1;
		else if (position[0] > other.position[0])
			return 1;
		return 0;
	}

	public int getAttackPower() {
		return attackPower;
	}
	
	public int getHp() {
		return hp;
	}

	public Race getRace() {
		return race;
	}
	
	public char getEnemySymbol() {
		return race == Race.ELF ? 'G' : 'E';
	}

	public int[] getPosition() {
		return position;
	}	
}
