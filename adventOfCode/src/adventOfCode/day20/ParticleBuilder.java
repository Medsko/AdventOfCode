package adventOfCode.day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import util.ArrayUtil;

/**
 * Builds {@link Particle} objects from a line in the input file.
 */
public class ParticleBuilder {

	/** Let's start out with an optimistic mindset. */
	private boolean hasErrors = false;
	
	private int currentId;
	
	/**
	 * Builds a particle from a line of input. 
	 */
	public Particle build(String originalInput, int id) {
		Particle particle = new Particle(originalInput, id);
		
		String[] attributes = originalInput.split(">");
		
		particle.setPosition(convertToAttribute(attributes[0]));
		particle.setVelocity(convertToAttribute(attributes[1]));
		particle.setAcceleration(convertToAttribute(attributes[2]));
		
		return particle;
	}
	
	/**
	 * Converts a section of the input for a particle to an attribute (e.g. velocity). 
	 */
	private int[] convertToAttribute(String attributeString) {
		// Select only the comma-separated values of the attribute.
		attributeString = attributeString.substring(attributeString.indexOf("<") + 1);
		// Split at the comma's, and return the result of parsing each value.
		return ArrayUtil.stringToInt(attributeString.split(","));
	}
	
	/**
	 * Builds a bunch of particles and returns them as a list.
	 * @param inputFile - the file with the input from which the particles will be constructed.
	 * @return the list of particles. 
	 */
	public List<Particle> buildFromFile(String inputFile) {
		
		List<Particle> particles = new ArrayList<>();
		currentId = 0;
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				// Build the particle using this line's input and increment the line number.
				particles.add(build(line.trim(), currentId++));
			}
		} catch (IOException ioex) {
			hasErrors = true;
			ioex.printStackTrace();
		}
		
		return particles;
	}

	public boolean getHasErrors() {
		return hasErrors;
	}
}
