package adventOfCode.day20;

import util.StdFunctions;

/**
 * Represents a particle, i.e. an actor in the day 20 challenge. 
 */
public class Particle {

	/** The definition of this particle as it was read from the input file. */
	private String originalInput;
	
	/** The line number of this particle. */
	private int id;
	
	/** The position of this particle on the x, y and z axis. */
	private int[] position;

	/** The velocity of this particle on the x, y and z axis. */
	private int[] velocity;
	
	/** The acceleration of this particle on the x, y and z axis. */
	private int[] acceleration;
	
	/**
	 * Indicates whether this particle has reached its long term ('true') direction, for all three
	 * dimensions. This is the case if the acceleration and velocity both 'point' in the same
	 * direction, i.e. if both are negative, or both are positive.
	 */
	private boolean trueDirectionReached;
	
	private boolean trueTrajectoryReached;
	
	public Particle(String originalInput, int id) {
		this.originalInput = originalInput;
		this.id = id;
		trueDirectionReached = false;
		trueTrajectoryReached = false;
	}
	
	/**
	 * Updates this particle's velocity by the acceleration for all dimensions, and checks whether
	 * the 'true' trajectory has been reached for all dimensions.
	 * Then updates the position by the resulting velocity (once again, for all dimensions). 
	 */
	public void update() {
		// Update the velocity of the particle. Only check whether the true trajectory is reached
		// if it has not been reached already.
		trueDirectionReached = updateAttribute(velocity, acceleration, !trueDirectionReached);
		
		trueTrajectoryReached = updateAttribute(position, velocity, !trueTrajectoryReached);
	}
	
	/**
	 * Updates the velocity by the acceleration for all three dimensions.
	 * @param attribute - the attribute to update.
	 * @param operand - the values used to update the attribute.
	 * @param checkTrueDirOrAcc - determines whether it should be checked if this particle has 
	 * reached its true direction or acceleration.
	 */
	private boolean updateAttribute(int[] attribute, int[] operand, boolean checkTrueDirOrAcc) {
		
		boolean trueDirOrAccReached = true;
		
		for (int i=0; i<attribute.length; i++) {
			// Update the velocity for this dimension by the value of the acceleration.
			attribute[i] += operand[i];
			
			if (checkTrueDirOrAcc &&
					// We should check whether this particle has reached its true trajectory.
					// If the velocity is positive or zero, but the acceleration is negative...
					((attribute[i] >= 0 && operand[i] < 0))
					// ...or the velocity is negative or zero, but the acceleration is positive...
					|| attribute[i] <= 0 && operand[i] > 0){
				// ...this particle has not yet reached its true trajectory.
				trueDirOrAccReached = false;
			}
		}
		return trueDirOrAccReached;
	}
	
	/**
	 * Determines the absolute distance from this particle's position to ground zero 
	 * (position 0,0,0).
	 */
	public int getManhattanDistanceToZero() {
		return StdFunctions.sumAbsolute(position);
	}
	
	/**
	 * Determines the total absolute acceleration of this particle, by adding the accelerations
	 * in all dimensions together.
	 */
	public int getTotalAbsoluteAcceleration() {
		return StdFunctions.sumAbsolute(acceleration);
	}
	
	public int getTotalAbsoluteVelocity() {
		return StdFunctions.sumAbsolute(velocity);
	}

	public boolean isTrueDirectionReached() {
		return trueDirectionReached;
	}

	public boolean isTrueTrajectoryReached() {
		return trueTrajectoryReached;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		if (position.length != 3)
			throw new IllegalArgumentException("This playing field is three-dimensional!");
		this.position = position;
	}

	public int[] getVelocity() {
		return velocity;
	}

	public void setVelocity(int[] velocity) {
		if (velocity.length != 3)
			throw new IllegalArgumentException("This playing field is three-dimensional!");
		this.velocity = velocity;
	}

	public int[] getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int[] acceleration) {
		if (acceleration.length != 3)
			throw new IllegalArgumentException("This playing field is three-dimensional!");
		this.acceleration = acceleration;
	}

	public String getOriginalInput() {
		return originalInput;
	}
	
	public int getId() {
		return id;
	}
}
