package aoc2018.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import aoc2018.AAoCA;

public class CartCourse extends AAoCA {

	private char[][] course;
	
	private String answer;
	
	private List<CartMetaData> carts;

	private int tickNumber;
	
	public CartCourse(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	@Override
	public boolean initialize(boolean isTest) {
		if (!super.initialize(2018, 13, isTest)) {
			return false;
		}
		List<String> inputLines = fetcher.getInputLines(); 
		int yCapacity = inputLines.size();
		int xCapacity = inputLines.get(0).length();
		course = new char[yCapacity][xCapacity];
		
		for (int i=0; i<inputLines.size(); i++) {
			course[i] = inputLines.get(i).toCharArray();
		}
		
		// Determine all carts and their meta data.
		carts = new ArrayList<>();
		
		for (int i=0; i<course.length; i++) {
			for (int j=0; j<course[i].length; j++) {
				
				char currentChar = getCharAt(j, i);
				if (isCartCharacter(currentChar)) {					
					CartMetaData cart = new CartMetaData(currentChar, new int[] { j, i });
					carts.add(cart);
				}
			}
		}
		
		return true;
	}
		
	@Override
	public boolean run() {
		tickNumber = 1;
		while (tick()) {
			tickNumber++;
		}
		return true;
	}
	
	/**
	 * Runs one loop, or 'tick' of the program. All cars are moved one space. 
	 */
	private boolean tick() {
		// Order the carts - top before bottom, left before right.
		Collections.sort(carts);
		List<CartMetaData> crashingCarts = new ArrayList<>();

		for (CartMetaData cart : carts) {
			if (crashingCarts.contains(cart)) {
				// This cart has been crashed into - it's already gone. 
				continue;
			}
			// Set the track character on the current position.
			int[] cartPosition = cart.getPosition();
			setCharAt(cart.getTrack(), cartPosition);
			// Move the cart one space in its current direction.
			cart.move();
			// Read the character at the new position.
			cartPosition = cart.getPosition();
			char nextTrack = getCharAt(cartPosition);
			if (isCartCharacter(nextTrack)) {
				// This character represents a cart, so we have a collision on our hands!
				logger.log("Collision at tick " + tickNumber + "! Crash position: ");
				logger.log(cartSurroundings(cartPosition));
				cart.setIsCrashingCar(true);
				addCartsByPosition(crashingCarts, cartPosition);
				
				if (isChallengeA) {
					answer = "First crash happened at position: " 
							+ cartPosition[0] + "," + cartPosition[1];
					return false;
				}
			} else {
				// No collision. Pass the next track character to the cart, so it can adjust its
				// direction if necessary.
				cart.readNextTrack(nextTrack);
				// Set the cart representation on its new position on the course.
				setCharAt(cart.getCart(), cartPosition);
			}
		}
		// Remove any colliding carts from the list.
		carts.removeAll(crashingCarts);
		
		if (carts.size() == 1) {
			// There is only one cart remaining. Its location is the answer to challenge B.
			answer = "Location of the last cart at moment of last collision: " 
					+ carts.get(0).getPosition()[0] + "," + carts.get(0).getPosition()[1];
			return false;
		}
		
		return true;
	}
	
	private void addCartsByPosition(List<CartMetaData> list, int[] position) {
		for (CartMetaData cart : carts) {
			if (Arrays.equals(position, cart.getPosition())) {
				list.add(cart);
				// If this is the car that was crashed into, set its underlying track on the course
				// in the position of the crash.
				if (!cart.getIsCrashingCar()) {
					setCharAt(cart.getTrack(), cart.getPosition());
				}
			}
		}
	}
	
	private String cartSurroundings(int[] position) {
		return sectionToString(position[0] - 4, position[1] - 4, 10);
	}
	
	@Override
	public String toString() {
		return sectionToString(0, 0, course.length);
	}
	
	/**
	 * Returns a String representation of a square portion of the course, stretching {@code size}
	 * square spaces counted right and down from the position specified by {@code x}, {@code y}.
	 */
	private String sectionToString(int x, int y, int size) {
		// First write the tens.
		String toString = "[     ";
		for (int i=0; i<size; i++) {
			int absoluteIndexX = x + i;
			if (absoluteIndexX % 10 == 0)
				toString += absoluteIndexX / 10;
			else
				toString += " ";
		}
		toString += " ]" + System.lineSeparator();

		toString += "[     ";
		for (int i=0; i<size; i++) {
			String fullAbsoluteIndexX = i + x + "";
			// Select the last digit of the resulting absolute index.
			toString += fullAbsoluteIndexX.substring(fullAbsoluteIndexX.length() - 1);
		}
		toString += " ]" + System.lineSeparator();

		for (int i=0; i<size; i++) {
			// Write the row number.
			int absoluteIndexY = i + y;
			toString += String.format("[%4s ", absoluteIndexY);
			
			for (int j=0; j<size; j++) {
				int absoluteIndexX = j + x;
				toString += safeCharAt(absoluteIndexX, absoluteIndexY);
			}
			toString += " ]" + System.lineSeparator();
		}
		return toString;
	}
	
	@Override
	public String getPrintableAnswer() {
		return answer;
	}

	private boolean isCartCharacter(char c) {
		return c == '^' || c == '>' || c == '<' || c == 'v';
	}
	
	private char safeCharAt(int x, int y) {
		if (y >= course.length || y < 0
				|| x >= course[y].length || x < 0) {
			// Out of bounds. Return empty track to indicate this.
			return ' ';
		}
		return course[y][x];
	}
		
	private char getCharAt(int x, int y) {
		return course[y][x];
	}
	
	private void setCharAt(char c, int[] position) {
		course[position[1]][position[0]] = c;
	}
	
	private char getCharAt(int[] position) {
		return course[position[1]][position[0]];
	}
}
