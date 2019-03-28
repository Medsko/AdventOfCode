package aoc2018.day03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a claim that an elf has on a piece of the fabric. 
 */
public class FabricClaim {

	private int id;
	
	private int[] leftUpperCorner;
	
	private int[] size;
	
	private boolean doesNotOverlap;
	
	public FabricClaim(String input) {
		String[] fields = input.split(" ");
		id = Integer.parseInt(fields[0].substring(1));
		
		leftUpperCorner = new int[2];
		Matcher matcher = Pattern.compile("\\d+").matcher(fields[2]);
		matcher.find();
		leftUpperCorner[0] = Integer.parseInt(matcher.group());
		matcher.find();
		leftUpperCorner[1] = Integer.parseInt(matcher.group());
		
		size = new int[2];
		matcher = Pattern.compile("\\d+").matcher(fields[3]);
		matcher.find();
		size[0] = Integer.parseInt(matcher.group());
		matcher.find();
		size[1] = Integer.parseInt(matcher.group());
		doesNotOverlap = true;
	}

	public int getId() {
		return id;
	}

	public int[] getLeftUpperCorner() {
		return leftUpperCorner;
	}

	public int[] getSize() {
		return size;
	}

	public boolean doesNotOverlap() {
		return doesNotOverlap;
	}

	public void setDoesNotOverlap(boolean doesNotOverlap) {
		this.doesNotOverlap = doesNotOverlap;
	}
}
