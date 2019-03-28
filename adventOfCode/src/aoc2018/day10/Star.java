package aoc2018.day10;

import java.util.Arrays;

import util.StdFunctions;

public class Star {

	private static int nextId;
	
	private int id;
	
	private int[] position;
	
	private int[] velocity;
	
	public Star(String input) {
		position = new int[] {
			Integer.parseInt(input.substring(input.indexOf('<') + 1, input.indexOf(',')).trim()),
			Integer.parseInt(input.substring(input.indexOf(',') + 1, input.indexOf('>')).trim())
		};
		
		velocity = new int[] {
			Integer.parseInt(input.substring(input.lastIndexOf('<') + 1, input.lastIndexOf(',')).trim()),
			Integer.parseInt(input.substring(input.lastIndexOf(',') + 1, input.lastIndexOf('>')).trim())
		};
		
		id = nextId++;
	}

	/**
	 * Tests whether this star is adjacent to the given star. This is the case if both stars's 
	 * positions differ one or less on both the x and y axis. 
	 */
	public boolean isAdjacent(Star other) {
		if (id == other.id)
			// The other star is the same as this star.
			return false;
		
		return StdFunctions.absoluteDifference(position[0], other.position[0]) <= 1
				&& StdFunctions.absoluteDifference(position[1], other.position[1]) <= 1;
	}
	
	/**
	 * Updates this star's position by its velocity. 
	 */
	public void updatePosition() {
		position[0] += velocity[0];
		position[1] += velocity[1];
	}
	
	public int[] getPosition() {
		return position;
	}

	public int[] getVelocity() {
		return velocity;
	}
	
	@Override
	public String toString() {
		String toString = "star at " + Arrays.toString(position) + " with velocity " 
				+ Arrays.toString(velocity);
		return toString;
	}
}
