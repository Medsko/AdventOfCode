package aoc2018.day09;

import java.util.Arrays;

import aoc2018.AbstractAocAlgorithm;
import util.ArrayUtil;

public class MarbleGame extends AbstractAocAlgorithm {
	// Input
	/** Represents the players. The integer value represents that player's score. */
	private long[] players;
	
	private int lastMarbleWorth;
	
	// Processing
	
	private RotatingList<Integer> circle;
	
	// Challenge A: 390592
	
	public MarbleGame(boolean isCommandLineFine, boolean isTest) {
		super(isCommandLineFine);
		this.isTest = isTest;
	}
	
	@Override
	public boolean initialize(int nrOfPlayers, int lastMarbleWorth) {
		players = new long[nrOfPlayers];
		this.lastMarbleWorth = lastMarbleWorth;
		circle = new RotatingList<>();
		// Add the first marble to the circle.
		circle.add(0);
		return true;
	}
	
	public void play() {		

		timer.startAlgorithm("MarbleGame.play()");

		for (int marbleWorth=1; marbleWorth<=lastMarbleWorth; marbleWorth++) {
			// If the marble has a number that is a multiple of 23, a different play is executed.
			if (marbleWorth % 23 == 0) {
				// The current player scores some points! The score is the sum of: 
				// 1) the current marble; 
				int score = marbleWorth;
				// 2) the worth of the marble 7 marbles back from the current index.
				circle.rotate(-7);
				int removedMarble = circle.removeAtCursor();
				score += removedMarble;
				
				// Update the player's score.
				int scoringPlayer = (marbleWorth - 1) % players.length;
				players[scoringPlayer] += score;
				
				// Set the cursor on the next marble.
				circle.rotate(1);
				
			} else {
				// A marble is inserted after the next marble from the current marble.
				circle.rotate(1);
				circle.add(marbleWorth);
			}
			
			if (isTest)
				logger.log(circle.toString());
		}
		timer.stopAlgorithm();
		logger.log("Player scores at end of game: " + Arrays.toString(players));
	}
	
	public long getHighScore() {
		return ArrayUtil.max(players);
	}
}
