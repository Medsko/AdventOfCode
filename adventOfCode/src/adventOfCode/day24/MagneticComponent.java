package adventOfCode.day24;

import java.util.List;

public class MagneticComponent {

	/** The value on the left of this component. Gets set after successful link. */
	private Integer leftPort;
	/** The value on the right of this component. Gets set after successful link. */
	private Integer rightPort;
	
	private int[] availablePorts;
	
	public MagneticComponent(int[] ports) {
		availablePorts = ports;
	}
	
	/** 
	 * Attempts to link this {@link MagneticComponent} to the calling component.
	 * 
	 * @param pins - the number of pins the right port of the calling component has.
	 * @return flag indicating whether components were successfully linked.
	 */
	public boolean link(int pins) {
		if (pins == availablePorts[0]) {
			leftPort = availablePorts[0];
			rightPort = availablePorts[1];
			return true;
		} else if (pins == availablePorts[1]) {
			leftPort = availablePorts[1];
			rightPort = availablePorts[0];
			return true;
		}
		return false;
	}
	
	public boolean isValidLink(int pins) {
		return pins == availablePorts[0] || pins == availablePorts[1];
	}
	
	public void addToList(List<Integer> list) {
		list.add(leftPort);
		list.add(rightPort);
	}
	
	/**
	 * Return the port of this component that has not yet been linked.
	 */
	public Integer getRightPort() {
		return rightPort;
	}
}
