package adventOfCode;

public class Direction {

	public enum FourWay {
		UP(0, 1, '^'), 
		RIGHT(1, 0, '>'), 
		DOWN(0, -1, 'v'), 
		LEFT(-1, 0, '<');
		
		public int xOperand;
		
		public int yOperand;
		
		public char representation;
		
		FourWay(int xOperand, int yOperand, char representation) {
			this.xOperand = xOperand;
			this.yOperand = yOperand;
			this.representation = representation;
		}
		
		/**
		 * Applies the operands that are typical for this four way direction to the given position,
		 * i.e. moves the position one square in this direction.
		 * 
		 * @param position - position to modify, in the form of an array holding two integer values,
		 * the first being the x position, the second being the y.
		 */
		public void apply(int[] position) {
			position[0] += xOperand;
			position[1] += yOperand;
		}
		
		public FourWay opposite(FourWay direction) {
			switch(direction) {
				case UP: return DOWN;
				case RIGHT: return LEFT;
				case DOWN: return UP;
				case LEFT: return RIGHT;
				default: return null;
			}
		}
		
		public FourWay rotateClockwise() {
			switch(this) {
				case UP: return RIGHT;
				case RIGHT: return DOWN;
				case DOWN: return LEFT;
				case LEFT: return UP;
				default: return null;
			}
		}
		
		/**
		 * Returns the result of turning counter clockwise from the given direction.
		 */
		public FourWay rotateCounterClockwise() {
			return opposite(rotateClockwise());
		}
		
		public boolean isVertical() {
			return this == UP || this == DOWN;
		}
	}
	
	/**
	 * Same as {@link FourWay}, but with directions on y axis flipped, to allow for more intuitive
	 * use on a two-dimensional grid. Such a grid might be an array holding arrays. 
	 */
	public enum TwoDimGrid {
		UP(0, -1, '^'), 
		RIGHT(1, 0, '>'), 
		DOWN(0, 1, 'v'), 
		LEFT(-1, 0, '<');
		
		public int xOperand;
		
		public int yOperand;

		public char representation;

		
		TwoDimGrid(int xOperand, int yOperand, char representation) {
			this.xOperand = xOperand;
			this.yOperand = yOperand;
			this.representation = representation;
		}
		
		public static TwoDimGrid fromRepresentation(char representation) {
			switch (representation) {
				case '^' : return UP;
				case '>' : return RIGHT;
				case 'v' : return DOWN;
				case '<' : return LEFT;
				default : return null;
			}
		}
		
		/**
		 * Applies the operands that are typical for this four way direction to the given position,
		 * i.e. moves the position one square in this direction.
		 * 
		 * @param position - position to modify, in the form of an array holding two integer values,
		 * the first being the x position, the second being the y.
		 */
		public void apply(int[] position) {
			position[0] += xOperand;
			position[1] += yOperand;
		}
		
		public TwoDimGrid opposite() {
			switch(this) {
				case UP: return DOWN;
				case RIGHT: return LEFT;
				case DOWN: return UP;
				case LEFT: return RIGHT;
				default: return null;
			}
		}
		
		public TwoDimGrid rotateClockwise() {
			switch(this) {
				case UP: return RIGHT;
				case RIGHT: return DOWN;
				case DOWN: return LEFT;
				case LEFT: return UP;
				default: return null;
			}
		}
		
		/**
		 * Returns the result of turning counter clockwise from the given direction.
		 */
		public TwoDimGrid rotateCounterClockwise() {
			return rotateClockwise().opposite();
		}
		
		public boolean isVertical() {
			return this == UP || this == DOWN;
		}
	}
	
}
