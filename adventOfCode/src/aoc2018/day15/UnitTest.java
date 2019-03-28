package aoc2018.day15;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * This might be totally redundant...but I just couldn't resist :). 
 */
public class UnitTest {

	private Unit testElf;
	
	private Unit testGoblin;
	
	private void prepare() {
		int[] positionElf = new int[]{ 2, 5 };
		int[] positionGoblin = new int[]{ 4, 3 };
		testElf = new Unit('E', positionElf, 20, 5);
		testGoblin = new Unit('G', positionGoblin, 4, 20);
	}
	
	@Test
	public void testAdversity() {
		prepare();
		assertTrue("We aren't in the market for interracial peace and love!", 
				testElf.isEnemy(testGoblin));
	}
	
	@Test
	public void testHit() {
		prepare();
		// The elf can kill the goblin in one hit, but not the other way around.
		assertFalse("Elf died from non-fatal hit!", testElf.takeHit(testGoblin));
		assertTrue("Goblin survived fatal hit!", testGoblin.takeHit(testElf));
	}
	
}
