package adventOfCode.day12;

import java.util.Arrays;

import adventOfCode.InputDataObject;

public class Pipe implements InputDataObject {

	private int id;
	
	private int[] connectedPipes;
	
	private boolean connectedToZero;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getConnectedPipes() {
		return connectedPipes;
	}

	public void setConnectedPipes(int[] connectedPipes) {
		this.connectedPipes = connectedPipes;
	}

	public boolean isConnectedToZero() {
		return connectedToZero;
	}

	public void setConnectedToZero(boolean connectedToZero) {
		this.connectedToZero = connectedToZero;
	}
	
	@Override
	public String toString() {
		return "Id: " + id + ". Connected pipes: " + Arrays.toString(connectedPipes);
	}
}
