package adventOfCode.day13;

import java.util.ArrayList;
import java.util.List;

public class Firewall {

	private List<PacketScanner> firewall;
	
	private int currentLayer;
	
	public Firewall() {
		firewall = new ArrayList<>();
		// Initialize the cursor to point to the index before the zero-based first.
		currentLayer = -1;
	}

	public void addScanner(int layer, int range) {
		while (++currentLayer < layer) {
			// Add dud scanners for empty layers.
			firewall.add(new PacketScanner(0));
		}
		firewall.add(new PacketScanner(range));
	}
	
	public void updateScannerPositions() {
		for (PacketScanner scanner : firewall)
			scanner.updatePosition();
	}
	
	public boolean isIntercept(int position) {
		
		PacketScanner scannerAtLayer = firewall.get(position);
		if (scannerAtLayer.interceptSeverity() != 0) {
			System.out.println("Intercepted at position " + position + "!");
			return true;
		}
		return false;
	}
	
	public int interceptSeverity(int position) {
		PacketScanner scannerAtLayer = firewall.get(position);
		// The intercept severity is the product of the range of the current scanner 
		// and the depth of the current layer.
		int severity = scannerAtLayer.interceptSeverity() * position;
		
		if (severity > 0)
			System.out.println("Severity at position " + position + ": " + severity);
		
		return severity;
	}
	
	/**
	 * Resets all scanners to their initial position: zero. 
	 */
	public void resetScanners() {
		for (PacketScanner scanner : firewall)
			scanner.reset();
	}
	
	/**
	 * Resets all scanners to their state the given number of steps ago.
	 * @param stepsBack - the number of steps that all scanners should be reverted. 
	 */
	public void resetScanners(int stepsBack) {
		for (PacketScanner scanner : firewall)
			scanner.reset(stepsBack);
	}
	
	public int size() {
		return firewall.size();
	}
	
	/**
	 * Returns a String representation of the portion of this firewall between the start and 
	 * end layers.
	 * 
	 * @param start - the start of the portion that will be represented, inclusive.
	 * @param end - the end of the portion that will be represented, exclusive.
	 */
	public String toString(int start, int end) {
		return "";
	}
	
	public String toString() {
		return toString(0, firewall.size());
	}
}
