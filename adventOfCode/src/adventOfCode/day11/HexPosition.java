package adventOfCode.day11;

import java.util.HashMap;
import java.util.Map;

import util.StdFunctions;
import util.Util;

/**
 * Represents a position on the hex grid.
 * 'North' directions are considered 'up', i.e. an increase in axis value, while 'South' is 'down',
 * i.e. a decrease in axis value.
 */
public class HexPosition {

	private int nrOfRounds = 0;
	
	private Map<String, Integer> nrOfStepsPerDirection;
	
	private double xPos;
	
	private double yPos;
	
	private double maxPosX;
	
	private double maxPosY;
	
	private boolean isChallengeB;
	
	private Direction currentDirection;
	
	private enum Direction {
		NORTH_EAST("ne"),
		NORTH_WEST("nw"),
		SOUTH_EAST("se"),
		SOUTH_WEST("sw"),
		NORTH("n"),
		SOUTH("s"),
		EAST("e"),
		WEST("w"),
		HOME("home");
		
		private String representation;
		
		Direction(String representation) {
			this.representation = representation;
		}
		
		static Direction fromRepresentation(String representation) {
			// Check whether the provided String is a valid representation of an equality operator.
			for (Direction direction : values()) {
				if (direction.representation.equals(representation)) {
					return direction;
				}
			}
			throw new IllegalArgumentException("Invalid String representation for equality operator!");
		}
		
		static Direction fromPosition(double xPos, double yPos) {
			String representation = "";
			// Determine whether the zero point is north or south of the given position.
			if (yPos < 0)
				representation += "n";
			if (yPos > 0)
				representation += "s";
			// Determine whether the zero point is east or west of the given position.
			if (xPos < 0)
				representation += "e";
			if (xPos > 0)
				representation += "w";
			if (representation.length() == 0)
				representation = "home";
			return fromRepresentation(representation);
		}
	}

	
	public HexPosition() {
		// Initialize the map with steps taken per direction.
		nrOfStepsPerDirection = new HashMap<>();
		for (Direction dir : Direction.values()) {
			nrOfStepsPerDirection.put(dir.representation, 0);
		}
		
		xPos = 0;
		yPos = 0;
	}
	
	public HexPosition(boolean isChallengeB) {
		this();
		this.isChallengeB = isChallengeB;
	} 
	
	/**
	 * Moves one square into the given direction. 
	 */
	public void move(String direction) {
		
		// Increment the number of steps taken in this direction.
		increaseNrOfStepsTakenInDirection(direction);
		// Set the current direction, based on the provided representation.
		currentDirection = Direction.fromRepresentation(direction);
		
		takeStepInCurrentDirection();
		
		// If the new position is further away, update the maximum position.
		if ( StdFunctions.absoluteDifferenceFromZero(xPos) + StdFunctions.absoluteDifferenceFromZero(yPos)
				> StdFunctions.absoluteDifferenceFromZero(maxPosX) + StdFunctions.absoluteDifferenceFromZero(maxPosY)) {
			maxPosX = xPos;
			maxPosY = yPos;
		}
		
		nrOfRounds++;
		// Log the current position.
		logPosition();
	}
	
	private void takeStepInCurrentDirection() {
		switch(currentDirection) {
			case NORTH:
				yPos += 1;
				break;
			case SOUTH:
				yPos -= 1;
				break;
			case NORTH_EAST:
				xPos += 0.5;
				yPos += 0.5;
				break;
			case NORTH_WEST:
				xPos -= 0.5;
				yPos += 0.5;
				break;
			case SOUTH_WEST:
				xPos -= 0.5;
				yPos -= 0.5;
				break;
			case SOUTH_EAST:
				xPos += 0.5;
				yPos -= 0.5;
				break;
			default:
				throw new UnsupportedOperationException("Direction not supported!");
		}
	}
	
	private void increaseNrOfStepsTakenInDirection(String direction) {
		nrOfStepsPerDirection.put(direction, nrOfStepsPerDirection.get(direction) + 1);
	}
	
	
	public int distanceFromStart() {
		int distance = 0;
		
		if (isChallengeB) {
			xPos = maxPosX;
			yPos = maxPosY;
		}
		// Determine the general direction we will be moving in.
		currentDirection = Direction.fromPosition(xPos, yPos);
		
		while (xPos != 0 || yPos != 0) {
			takeStepInCurrentDirection();
			distance++;
			System.out.println("Took one step back to start! Direction: " + currentDirection
					+ ".  Current position: x = " + xPos + ", y = " + yPos);
			// If we moved to a position that is directly above or beneath the start position,
			// adjust the direction accordingly.
			if (xPos == 0)
				if (yPos < 0 )
					currentDirection = Direction.NORTH;
				else if (yPos > 0)
					currentDirection = Direction.SOUTH;
		}
		return distance;
	}
	
	public void logPosition() {
		System.out.println("Current position: x = " + xPos + ", y = " + yPos);
		if (nrOfRounds % 100 == 0) {
			// At every hundred turn mark, also log the number of steps taken in each direction.
			logNrOfStepsTaken();
			Util.underline();
		}
	}
	
	private void logNrOfStepsTaken() {
		String totalNrOfSteps = "Number of steps taken per direction: ";
		for (String s : nrOfStepsPerDirection.keySet()) {
			totalNrOfSteps += s + " = " + nrOfStepsPerDirection.get(s) + ", ";
		}
		System.out.println(totalNrOfSteps);
	}
}
