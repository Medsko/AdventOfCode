package aoc2018.day15;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BattlegroundTest {

	@Test
	public void testMovement() {
		Battleground battle = new Battleground(true);
		battle.initialize(true, "inputDay15MovementTest.txt");

		// Log starting position.
		assertTrue(battle.round());
		assertTrue(battle.round());
		assertTrue(battle.round());
	}
	
	@Test
	public void testCombat() {
		Battleground battle = new Battleground(true);
		battle.initialize(true, "inputDay15CombatTest.txt");
		int expectedTotalHp = battle.calculateTotalRemainingHp();
		// Initially, elves are adjacent to one goblin. So, first round, 12 hp is lost in total.
//		battle.round();
		expectedTotalHp -= 12;
		int totalRemainingHp = battle.calculateTotalRemainingHp();
//		assertEquals("Unexpected amount of hp lost! Total hp: " + totalRemainingHp,
//				expectedTotalHp, totalRemainingHp);
		
		battle.run();
		System.out.println(battle.getPrintableAnswer());
	}
	
}
