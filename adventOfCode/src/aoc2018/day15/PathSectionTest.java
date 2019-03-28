package aoc2018.day15;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PathSectionTest {

	private PathSection sectionOne;
	
	private void constructSection() {
		sectionOne = new PathSection(new int[] { 3, 3 });
		sectionOne.addLink(new PathSection(new int[] { 4, 3 }));
		sectionOne.addLink(new PathSection(new int[] { 3, 4 }));
		sectionOne.addLink(new PathSection(new int[] { 4, 4 }));
	}
	
	@Test
	public void testLinking() {
		
		sectionOne = new PathSection(new int[] { 3, 3 });
		int[] next = new int[] { 3, 4 };
		int[] otherNext = new int[] { 4, 3 };
		int[] afterNext = new int[] { 4, 4 };
		
		if (sectionOne.addLink(new PathSection(afterNext))) {
			System.out.println("Linked section that is not adjacent to any squares in path!");
		}
		assertTrue("Failed to link a section that was totally linkable!", 
				sectionOne.addLink(new PathSection(next)));
		
		sectionOne.addLink(new PathSection(otherNext));
		sectionOne.addLink(new PathSection(afterNext));
		
		String toStringBefore = sectionOne.toString();
		
		// Adding the same square a second time should return true (because the path can be linked
		// to the given square)...
		assertTrue("Square already in path not deemed linkable!", 
				sectionOne.addLink(new PathSection(afterNext)));
		// ...but should not be added to the path, i.e. the output of toString() should be the same.
		assertTrue("Square was added to path twice!", toStringBefore.equals(sectionOne.toString()));
		
		System.out.println(sectionOne);
	}
	
	@Test
	public void testPathConstruction() {
		constructSection();
		// Pick a goal that is not yet in the path, but could be linked.
		int[] goal = new int[] { 5, 4 };
		List<int[]> squares = new ArrayList<>();
		assertFalse("Path to unreachable goal was constructed!", 
				sectionOne.constructPathTo(goal, squares));
		// Add the goal square to the path.
		sectionOne.addLink(new PathSection(goal));
		assertTrue("Could not construct path to goal that is most def reachable!", 
				sectionOne.constructPathTo(goal, squares));
		
		String pathString = "Cohesive path: ";
		for (int[] square : squares) {
			pathString += Arrays.toString(square) + " ";
		}
		System.out.println(pathString);
	}
}
