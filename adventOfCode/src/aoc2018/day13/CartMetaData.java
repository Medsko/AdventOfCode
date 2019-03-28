package aoc2018.day13;

import adventOfCode.Direction;
import adventOfCode.Direction.TwoDimGrid;

public class CartMetaData implements Comparable<CartMetaData> {

	private static int nextId = 1;
	
	private int id;
	
	private int[] position;
	
	/**
	 * The type of track piece the cart is currently on.  
	 */
	private char track;
		
	private Direction.TwoDimGrid direction;
	
	private boolean isCrashingCar;
	
	/**
	 * The direction that the next turn will be. Alternates between left (0), straight (1) and 
	 * right (2).
	 */
	private int nextTurn;
	
	
	public CartMetaData(char cartChar, int[] position) {
		this.position = position;
		direction = TwoDimGrid.fromRepresentation(cartChar);
		nextTurn = 0;
		// First underlying track is a straight track matching the direction the cart is facing.
		track = direction.isVertical() ? '|' : '-';
		id = nextId++;
	}
		
	/**
	 * Moves this cart one place in its current direction. 
	 */
	public void move() {
		direction.apply(position);
	}
	
	public void readNextTrack(char nextTrack) {
		if (nextTrack == '\\' || nextTrack == '/') {
			curve(nextTrack);
		} else if (nextTrack == '+') {
			// Intersection. Turn in the next direction.
			turn();
		}
		// In all other cases, we should just follow the track in the current direction.
		track = nextTrack;
	}
	
	private void curve(char curveTrack) {
		if (curveTrack == '\\' && direction.isVertical())
			// Turn left.
			direction = direction.rotateCounterClockwise();
		else if (curveTrack == '\\')
			// Turn right.
			direction = direction.rotateClockwise();
		else if (curveTrack == '/' && direction.isVertical())
			// Turn right.
			direction = direction.rotateClockwise();
		else
			direction = direction.rotateCounterClockwise();
	}
	
	private void turn() {
		if (nextTurn == 0) {
			direction = direction.rotateCounterClockwise();
			nextTurn++;
		} else if (nextTurn == 1) {
			// Keep moving straight ahead. 
			nextTurn++;
		} else {
			direction = direction.rotateClockwise();
			nextTurn = 0;
		}
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public char getTrack() {
		return track;
	}
	
	public char getCart() {
		return direction.representation;
	}
	
	public int getId() {
		return id;
	}

	public boolean getIsCrashingCar() {
		return isCrashingCar;
	}

	public void setIsCrashingCar(boolean isCrashingCar) {
		this.isCrashingCar = isCrashingCar;
	}

	@Override
	public int compareTo(CartMetaData other) {
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
}
