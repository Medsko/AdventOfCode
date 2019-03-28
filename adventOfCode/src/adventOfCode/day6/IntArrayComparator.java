package adventOfCode.day6;

import java.util.Comparator;

public class IntArrayComparator implements Comparator<int[]> {

	@Override
	public int compare(int[] arrOne, int[] arrTwo) {
		if (arrOne.length != arrTwo.length)
			// The longer array is considered higher.
			return arrTwo.length - arrOne.length;
		for (int i=0; i<arrOne.length; i++) {
			if (arrOne[i] > arrTwo[i])
				return 1;
			if (arrOne[i] < arrTwo[i])
				return -1;
		}
		return 0;
	}
}
