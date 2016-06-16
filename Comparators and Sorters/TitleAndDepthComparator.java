/**
 * Ttile and Depth Comparator, implementing the comparator interface, compares two quake entry based on 
 * their titles aphabetically and breaks the ties by depth from the smallest to the greatest.
 *
 * @author Frank
 * @version May 24, 2016
 */

import java.util.*;

public class TitleAndDepthComparator implements Comparator<QuakeEntry> {
	public int compare(QuakeEntry q1, QuakeEntry q2) {
		// compare the title by alphabetical order first
		String title1 = q1.getInfo();
		String title2 = q2.getInfo();
		if (title1.compareTo(title2) < 0) {
			return -1;
		}

		else if (title1.compareTo(title2) > 0) {
			return 1;
		}
		// break ties by comparing depth
		else {
			return Double.compare(q1.getDepth(), q2.getDepth());
		}
	}
}