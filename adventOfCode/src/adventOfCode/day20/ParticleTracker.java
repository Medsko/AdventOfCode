package adventOfCode.day20;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adventOfCode.AbstractAlgorithm;

/**
 * Keeps track of all particles in the swarm.
 */
public class ParticleTracker extends AbstractAlgorithm {

	private List<Particle> particleSwarm;

	private boolean isChallengeB;
	
	/** The particle that will stay closest to 0,0,0 in the long term. */
	private Particle theOne;
		
	public ParticleTracker(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}
	
	public ParticleTracker(boolean isCommandLineFine, boolean isChallengeB) {
		this(isCommandLineFine);
		this.isChallengeB = isChallengeB;
	}
	
	// Challenge A right on first try (again)! Answer: 161
	
	// Challenge B, after running until true direction and trajectory: 448
	// Challenge B, after going through 10000 iterations: 448
	// Challenge B, after going through 100000 iterations: 448
	// Challenge B, after 1_700_000 iterations: still 448...but AdventOfCode calls me a cheater...
	
	public boolean initialize(String inputFile) {
		
		ParticleBuilder builder = new ParticleBuilder();
		particleSwarm = builder.buildFromFile(inputFile);
		
		return !builder.getHasErrors();
	}
	
	/**
	 * Performs the calculations necessary to determine which particle will be closest to ground
	 * zero (position 0,0,0) in the long term.
	 */
	public void projectTrajectories() {

		boolean allParticlesReachedTrueDirectionAndTrajectory = false;
		
		timer.startAlgorithm("ParticleTracker.projectTrajectories()");
		
		int counter = 1;
		
		// Update all particles until they have all reached their 'true' direction and trajectory. 
		while (!allParticlesReachedTrueDirectionAndTrajectory) {
			
			allParticlesReachedTrueDirectionAndTrajectory = true;			
			
			for (Particle particle : particleSwarm) {
				// Update the velocity and position of the particle.
				particle.update();
				// Check if the particle has reached it's definitive direction.
				if (!particle.isTrueDirectionReached()) {
					allParticlesReachedTrueDirectionAndTrajectory = false;
				}
				// Check if the particle has reached it's definitive trajectory.
				if (!particle.isTrueTrajectoryReached()) {
					allParticlesReachedTrueDirectionAndTrajectory = false;
				}
			}
			
			if (isChallengeB) {
				removeCollidingParticles();
			}
			
			if (counter % 1000 == 0)
				System.out.println("Now ending iteration " + counter + "!");
			counter++;
		}
		
		if (isChallengeB) {
			System.out.println("Number of particles remaining after running until true direction"
					+ " and trajectory: " + particleSwarm.size());
		}
		
		theOne = Collections.min(particleSwarm, determineParticleComparator());
		
		timer.stopAlgorithm();
	}
	
	/**
	 * Checks if any particles are colliding at the current moment. Colliding particles are removed
	 * from the swarm.
	 */
	private void removeCollidingParticles() {
		Set<Particle> collidingParticles = new HashSet<>();
		for (int i=0; i<particleSwarm.size(); i++) {
			for (int j=i+1; j <particleSwarm.size(); j++) {
				
				Particle one = particleSwarm.get(i);
				Particle two = particleSwarm.get(j);

				if (collide(one, two)) {
					// Add the colliding particles to the set.
					collidingParticles.add(one);
					collidingParticles.add(two);
					// One of the colliding particles is newly added to the set. Log collision.
					System.out.println("Particles " + one.getId() + " and " + two.getId() 
						+ " collided!");
					break;
				}
			}
		}
		particleSwarm.removeAll(collidingParticles);
	}
	
	private boolean collide(Particle one, Particle other) {
		return Arrays.equals(one.getPosition(), other.getPosition());
	}
	
	private Comparator<Particle> determineParticleComparator() {
		return new Comparator<Particle>() {
			@Override
			public int compare(Particle one, Particle other) {
				if (one.getTotalAbsoluteAcceleration() > other.getTotalAbsoluteAcceleration())
					return 1;
				else if (one.getTotalAbsoluteAcceleration() < other.getTotalAbsoluteAcceleration())
					return -1;
				else if (one.getTotalAbsoluteVelocity() > other.getTotalAbsoluteVelocity())
					return 1;
				else if (one.getTotalAbsoluteVelocity() < other.getTotalAbsoluteVelocity())
					return -1;
				else if (one.getManhattanDistanceToZero() > other.getManhattanDistanceToZero())
					return 1;
				else if (one.getManhattanDistanceToZero() < other.getManhattanDistanceToZero())
					return -1;
				return 0;
			}
		};
	}

	public Particle getTheOne() {
		return theOne;
	}	
}
