package adventOfCode.day25;

import java.util.Arrays;

public class InfiniteTape {

	private int[] tape;
	
	private int cursor;
	
	public InfiniteTape() {
		tape = new int[1];
		cursor = 0;
	}
	
	/**
	 * Moves the cursor the given distance along the tape. If the value is negative, the cursor
	 * moves left. If the value is positive, the cursor moves right.
	 * If the move places the cursor outside the bounds of the tape array, the capacity is enlarged. 
	 */
	public void move(int distance) {
		cursor += distance;
		if (cursor < 0 || cursor >= tape.length) {
			// The cursor was moved out of array bounds.
			int absDistance = Math.abs(distance);
			int start = distance < 0 ? absDistance : 0;
			int[] temp = tape;
			tape = new int[tape.length + absDistance];
			
			System.arraycopy(temp, 0, tape, start, temp.length);
			// If the cursor moved to the left, it should be reset to 0 (the new left-most index).
			cursor = distance < 0 ? 0 : cursor;
		}
	}
	
	public void write(int value) {
		tape[cursor] = value;
	}
	
	public int read() {
		return tape[cursor];
	}
	
	public int getChecksum() {
		int checksum = 0;
		for (int i=0; i< tape.length; i++)
			checksum += tape[i];
		return checksum;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(tape);
	}
}
