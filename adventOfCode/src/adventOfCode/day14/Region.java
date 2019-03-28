package adventOfCode.day14;

import java.util.ArrayList;
import java.util.List;

public class Region {

	private List<int[]> squares;
	
	/** The distinct integer marking this region (its 'identifier', if you will).*/
	private int mark;
	
	/** The mark of a region that was merged into this region. */
	private int obsoleteRegionMark;
	
	public Region(int mark) {
		this.mark = mark;
		squares = new ArrayList<>();
	}
	
	public void addSquare(int[] square) {
		squares.add(square);
	}
	
	public void addSquares(List<int[]> squares) {
		this.squares.addAll(squares);
	}
	
	public int getMark() {
		return mark;
	}
	
	public void decrementMark() {
		mark--;
	}
	
	public List<int[]> getSquares() {
		return squares;
	}
	
	public int getObsoleteRegionMark() {
		return obsoleteRegionMark;
	}

	public void setObsoleteRegionMark(int obsoleteRegionMark) {
		this.obsoleteRegionMark = obsoleteRegionMark;
	}
}
