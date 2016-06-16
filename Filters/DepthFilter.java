/**
 * Filter the earthquakes so that all results have depth greater than a minimum and smaller than a
 * maximum magnitude
 * @author Frank
 * @version May 22 2016
 */
public class DepthFilter implements Filter{
	private double depMin;
	private double depMax;
	// the name the user wants to call the filter
	private String filterName;

	// a constructor to initialize the three variables
	public DepthFilter(double min, double max, String name){
		depMin = min;
		depMax = max;
		filterName = name;
	}

	public boolean satisfies(QuakeEntry qe){
		return (qe.getDepth() >= depMin && qe.getDepth() <= depMax);
		}

	public String getName() {
		return filterName;
	}
}
