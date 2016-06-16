/**
 * MatchAllFilter is able to store and apply many filters
 * @author Frank
 * @version May 23 2016
 */

import java.util.*;

public class MatchAllFilter implements Filter {
	private ArrayList<Filter> list;

	public MatchAllFilter() {
		list = new ArrayList<Filter>();
	}

	public void addFilter(Filter f) {
		list.add(f);
	}

	// return true is the QuakeEntry satisfies all the filters in list; false otherwise
	public boolean satisfies(QuakeEntry qe) {
		for (Filter f : list) {
			if (! f.satisfies(qe)){
				return false;
			}
		}
		return true;
	}

	// get the names of all the filters used
	public String getName() {
		String filters = "";
		for (Filter f : list) {
			filters = filters + f.getName() + " ";
		}
		return filters;
	}
}