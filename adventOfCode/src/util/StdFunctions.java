package util;

public class StdFunctions {
	
	/** Calculates the absolute difference between two given integers. */
	public static int absoluteDifference(int x, int y) {
		
		int answer = 0;
		
		if (x < 0 && y < 0) {
			if (y < x)
				answer = Math.abs(y) - Math.abs(x);
			else
				answer = Math.abs(x) - Math.abs(y);
		} else {
			if (x > y)
				answer = Math.abs(x - y);
			else
				answer = Math.abs(y - x);
		}
		
		return answer;
	}
	
	public static double absoluteDifference(double x, double y) {
		
		double answer = 0;
		
		if (x < 0 && y < 0) {
			if (y < x)
				answer = Math.abs(y) - Math.abs(x);
			else
				answer = Math.abs(x) - Math.abs(y);
		} else {
			if (x > y)
				answer = Math.abs(x - y);
			else
				answer = Math.abs(y - x);
		}
		
		return answer;
	}
	
	public static double absoluteDifferenceFromZero(double x) {
		return absoluteDifference(0.0, x);
	}
	
	/**
	 * Calculates the Manhattan distance from starting to target position.
	 * Supports up to 256 dimensions.
	 * 
	 * @param start - the starting position.
	 * @param target - the target position.
	 * @return the Manhattan distance from starting to target position. 
	 */
	public static int manhattanDistance(int[] start, int[] target) {
		if (start.length != target.length) 
			throw new IllegalArgumentException("Dimensions of given positions are not equal!");
		int manhattanDistance = 0;
		for (int i=0; i<start.length; i++) {
			manhattanDistance += absoluteDifference(start[i], target[i]);
		}
		return manhattanDistance;
	}
	
	public static int sumAbsolute(int... values) {
		int answer = 0;
		for (int i : values)
			answer += Math.abs(i);
		return answer;
	}
}
