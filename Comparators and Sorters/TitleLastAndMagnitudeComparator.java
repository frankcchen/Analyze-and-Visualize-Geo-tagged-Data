/**
 * sort earthquakes by the last word in their title first and break ties by magnitude.
 *
 * @author Frank
 * @version May 24, 2016
 */

import java.util.*;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
	public int compare(QuakeEntry q1, QuakeEntry q2) {
		String [] list1 = q1.getInfo().split(" ");
		String [] list2 = q2.getInfo().split(" ");
		String word1 = list1[list1.length-1];
		String word2 = list2[list2.length-1];
		// compare the last word in the title by alphabetical order first
		if (word1.compareTo(word2) < 0) {
			return -1;
		}

		else if (word1.compareTo(word2) > 0) {
			return 1;
		}
		// break ties by comparing depth
		else {
			return Double.compare(q1.getMagnitude(), q2.getMagnitude());
		}
	}
}