package aoc2018.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.MatrixUtil;

public class PathSection {

	private int[] position;
	
	private List<PathSection> nextSections;
	
	public PathSection(int[] position) {
		this.position = position;
		nextSections = new ArrayList<>();
	}
	
	/**
	 * Tests if the given section has been linked to this section or one of its children.
	 * @return {@code true} if the given section has been linked to this path.
	 */
	public boolean isSectionLinked(PathSection section) {
		if (Arrays.equals(position, section.position)) {
			return true;
		}
		// Check if a child section is linked.
		for (PathSection child : nextSections) {
			if (child.isSectionLinked(section)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Attempts to link the given PathSection to this one.
	 * @return {@code true} if section could be linked to this section, or to one of its children. 
	 */
	public boolean addLink(PathSection section) {
		// Check if the section is already linked to another section in this path.
		if (isSectionLinked(section)) {
			return true;
		}
		
		boolean isLinked = false;
		// Check if one or more of the next sections could be linked to the given section.
		for (PathSection child : nextSections) {
			if (child.addLink(section)) {
				isLinked = true;
			}
		}
		
		if (isAdjacent(section)) {
			// The given section can be linked to this one.
			nextSections.add(section);
			isLinked = true;
		}
		
		return isLinked;
	}
	
	public int pathLength() {
		int pathLength = 0;
		// Find the child with the longest path length.
		for (PathSection child : nextSections) {
			if (child.pathLength() > pathLength) {
				pathLength = child.pathLength();
			}
		}
		// Add this section to the total path length and return it.
		return pathLength + 1;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	/**
	 * Determines whether this PathSection is adjacent to the given PathSection.
	 * @return {@code true} if this section is adjacent to {@code other}. 
	 */
	public boolean isAdjacent(PathSection other) {
		return MatrixUtil.isAdjacent(position, other.position);
	}
	
	public boolean constructPathTo(int[] goal, List<int[]> squares) {
		if (!isSectionLinked(new PathSection(goal))) {
			// A route to the goal cannot be construed from the sections in this path.
			return false;
		}
		// We can construct the path. 
		// Add this section first, then determine what sections to use next.
		squares.add(position);
		
		for (PathSection child : nextSections) {
			if (child.constructPathTo(goal, squares)) {
				// One path - the first one according to reading order - is enough. 
				break;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
	
	private String toString(int level) {
		String toString = "";
		for (int i=0; i<level; i++) {
			toString += "    ";
		}
		toString += Arrays.toString(position);
		for (PathSection child : nextSections) {
			toString += System.lineSeparator();
			toString += child.toString(level + 1);
		}
		
		return toString;
	}
}
