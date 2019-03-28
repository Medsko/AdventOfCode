package aoc2018.day08;

import java.util.Arrays;

import util.ArrayUtil;

/**
 * A node in a {@link LicenceTree}. 
 */
public class LicenceNode {

	private LicenceNode[] children;
	
	private int[] metadataEntries;
	
	private static int idCounter = 1;
	
	private int id;
	
	public LicenceNode(int nrOfChildren, int nrOfMetadataEntries) {
		children = new LicenceNode[nrOfChildren];
		metadataEntries = new int[nrOfMetadataEntries];
		id = idCounter++;
	}
	
	public void addChild(LicenceNode child, int index) {
		children[index] = child;
	}

	/**
	 * Gets the value of this node. The following rules apply: 
	 * <ul>
	 * 	<li> If a node has no child nodes, its value is the sum of its metadata entries. </li> 
	 * 	<li> If it <strong>does</strong> have child nodes, the metadata entries become (one-based) 
	 * 	indexes which refer to those child nodes. If a referenced child does not exist, that 
	 *  reference is skipped. </li>
	 * </ul>
	 * 
	 * @return the value of this node.
	 */
	public int getValue() {
		if (children.length == 0) {
			// For a node with no child nodes, the value is the sum of its metadata entries.
			return ArrayUtil.sum(metadataEntries);
		}
		
		int value = 0;
		for (int metadataEntry : metadataEntries) {
			// Check whether the index points to an existing child entry.
			if (metadataEntry <= children.length) {
				// Get the child at the given index and add its value to this node's value.
				LicenceNode child = children[metadataEntry - 1];
				value += child.getValue();
			}
		}
		return value;
	}
	
	public void addMetadataEntry(int entry, int index) {
		metadataEntries[index] = entry;
	}

	public LicenceNode[] getChildren() {
		return children;
	}
	
	public int getNrOfChildren() {
		return children.length;
	}
	
	public int getNrOfMetadataEntries() {
		return metadataEntries.length;
	}
	
	public int getSumMetadataEntries() {
		int sum = 0;
		for (int metadataEntry : metadataEntries) {
			sum += metadataEntry;
		}
		for (LicenceNode child : children) {
			sum += child.getSumMetadataEntries();
		}
		return sum;
	}

	public int[] getMetadataEntries() {
		return metadataEntries;
	}
	
	@Override
	public String toString() {
		String toString = "id: " + id + ", header: [" + children.length + ", " 
				+ metadataEntries.length + "], ";
		toString += "metadata: " + Arrays.toString(metadataEntries);
		return toString;
	}
}
