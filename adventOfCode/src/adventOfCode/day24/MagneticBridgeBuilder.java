package adventOfCode.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adventOfCode.AbstractAlgorithm;
import util.ArrayUtil;

public class MagneticBridgeBuilder extends AbstractAlgorithm {
	
	private boolean isDebug;
	
	private List<MagneticComponent> availableComponents;
		
	private List<MagneticComponent> currentBridge;
	
	private List<Integer[]> pastBridges;
	
	private int maxStrength;
	
	private Integer[] longestBridge;
	
	public MagneticBridgeBuilder(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public MagneticBridgeBuilder(boolean isCommandLineFine, boolean isDebug) {
		super(isCommandLineFine);
		this.isDebug = isDebug;
	}
	
	
	// First try: 1537 (too low).
	// Second try: 1906 (correct!!)
	
	// Challenge b, first try: 1824 (right again!!!)
	
	public void initialize(String inputFile) {
		availableComponents = new ArrayList<>();
		currentBridge = new ArrayList<>();
		pastBridges = new ArrayList<>();
		maxStrength = 0;
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] unparsedPorts = line.trim().split("/");
				int[] ports = ArrayUtil.stringToInt(unparsedPorts);
				MagneticComponent component = new MagneticComponent(ports);
				availableComponents.add(component);
			}			
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} 
	}
	
	public void buildBridges() {
		
		// TODO: to make this work, the outer algorithm (in this method) should eventually 'walk
		// back' through selected components: when a certain bridge has been finished, the most
		// recently added component should be removed from the available components, then the 
		// findNextLink() should be run again.
		List<MagneticComponent> firstTier = getValidLinks(0);
		
		buildNextBridge(firstTier, 0);
	}
	
	private void buildNextBridge(List<MagneticComponent> components, int currentPins) {
		
		for (MagneticComponent current : components) {
			
			current.link(currentPins);
			availableComponents.remove(current);
			currentBridge.add(current);
			saveCurrentBridge();
			
			List<MagneticComponent> nextLinks = getValidLinks(current.getRightPort());
			buildNextBridge(nextLinks, current.getRightPort());
			
			currentBridge.remove(current);
			availableComponents.add(current);
		}
	}
	
	private List<MagneticComponent> getValidLinks(int pins) {
		List<MagneticComponent> validLinks = new ArrayList<>();
		for (MagneticComponent component : availableComponents) {
			if (component.isValidLink(pins))
				validLinks.add(component);
		}
		return validLinks;
	}
	
	
	private void saveCurrentBridge() {
		List<Integer> bridgeList = new ArrayList<>();
		for (MagneticComponent component : currentBridge) {
			component.addToList(bridgeList);
		}
		Integer[] bridgeArray = bridgeList.toArray(new Integer[0]);
		int bridgeStrength = ArrayUtil.sum(bridgeArray);
		
		String message = "";
		
		if (longestBridge == null || bridgeArray.length > longestBridge.length) {
			// This is the first bridge that has been built, or it is longer than the longest
			// bridge so far. Assign it to the longest bridge variable.
			longestBridge = bridgeArray;
			message += "New longest bridge constructed!" + System.lineSeparator();
			
		} else if (bridgeArray.length == longestBridge.length) {
			// The bridges are equal in length. Determine which one is the strongest and set it as
			// the longest.
			int longestBridgeStrength = ArrayUtil.sum(longestBridge);
			if (bridgeStrength > longestBridgeStrength) {
				longestBridge = bridgeArray;
				message += "New longest bridge constructed!" + System.lineSeparator();
			}
		}
		
		if (bridgeStrength > maxStrength) {
			maxStrength = bridgeStrength;
			message += "New strongest bridge constructed!" + System.lineSeparator();
		}
		
		if (isDebug && !message.equals("")) {
			// This bridge was either longer or stronger than any other, or both. Log a message.
			message +=  bridgeToString(bridgeArray);
			logger.log(message);
		}

		pastBridges.add(bridgeArray);
	}
		
	public int getMaxStrength() {
		return maxStrength;
	}

	public int getNumberOfBridges() {
		return pastBridges.size();
	}
	
	public Integer[] getLongestBridge() {
		return longestBridge;
	}

	public String bridgeToString(Integer[] bridge) {
		String toString = "";
		
		for (int i=0; i<bridge.length; i++) {
			if (i % 2 == 0 && i > 0) {
				toString += "--";
			}
			toString += bridge[i];			
			if (i % 2 == 0) {
				toString += "/";
			}
		}
		toString += " strength: " + ArrayUtil.sum(bridge);
		return toString;
	}
}
