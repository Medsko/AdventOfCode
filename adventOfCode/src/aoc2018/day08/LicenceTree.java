package aoc2018.day08;

import aoc2018.AbstractAocAlgorithm;

public class LicenceTree extends AbstractAocAlgorithm {

	private int[] input;
	
	private int readIndex = 0;
	
	private LicenceNode root;
	
	public LicenceTree(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public boolean initialize(boolean isTest) {
		if (!super.initialize(2018, 8, isTest)) {
			return false;
		}
		String[] rawInput = fetcher.getInputLines().get(0).split(" ");
		input = new int[rawInput.length];
		// Parse each input integer.
		for (int i=0; i<rawInput.length; i++) {
			input[i] = Integer.parseInt(rawInput[i]);
		}
		
		return true;
	}
	
	public void grow() {
		readIndex = 0;	
		root = constructLicenceNode();
		constructChildNodes(root);
	}
	
	public int calculateRootValue() {		
		return root.getValue();
	}
	
	/**
	 * After calling {@link #initialize(boolean)} and {@link #grow()}, calling this method will
	 * provide the answer to challenge A.
	 * @return the sum of all metadata entries.  
	 */
	public int calculateSumMetadata() {	
		return root.getSumMetadataEntries();
	}
	
	
	private void constructChildNodes(LicenceNode parent) {
		// Check if this parent node has child nodes that have not been built yet. 
		for (int i=0; i<parent.getChildren().length; i++) {
			if (parent.getChildren()[i] == null) {
				// Construct the child node.
				LicenceNode child = constructLicenceNode();
				parent.addChild(child, i);
				// Construct the child nodes of this new child node.
				constructChildNodes(child);
			}
		}
		// This node has no children, or all its children have already been constructed.
		// Read in the specified number of metadata entries.
		for (int i=0; i<parent.getNrOfMetadataEntries(); i++) {
			parent.addMetadataEntry(input[readIndex], i);
			readIndex++;
		}
		logger.log("Finished building node: " + parent);
	}
	
	private LicenceNode constructLicenceNode() {
		// Read the next two entries in the input (i.e. the header of the next node), and use them
		// to construct the new node.
		return new LicenceNode(input[readIndex++], input[readIndex++]);
	}
		
	// Traverse the input data in a left-first manner.
	// Each encountered set of two integers represents a header - create the number of nodes
	// specified in the header.
	// For the first of these nodes, repeat this procedure.
	
	// When a node is encountered whose header specifies no child nodes, walk back through the
	// tree structure that was created so far. For each 'left' node, set the specified number of
	// meta-data entries. If, on this walk back, a node is encountered that specifies more than
	// one child nodes, reverse the direction - fill the header of the next child node, check if it
	// specifies one or more child nodes etc.
	
	// When this zig-zag is complete, the tree is built.
}
