 /**
 * Filter the earthquakes so that all results are within a specified maximum distance from the
 * given location.
 * @author Frank
 * @version May 22 2016
 */
public class DistanceFilter implements Filter{
	// private instance variables
	private double distMax;
	private Location location;
	private String filterName;

	// a constructor to initialize the 3 variables
	public DistanceFilter(double max, Location loc, String name){
		distMax = max;
		location = loc;
		filterName = name;
	}

	public boolean satisfies(QuakeEntry qe){
		return (qe.getLocation().distanceTo(location)/1000 < distMax);
		}

	public String getName() {
		return filterName;
	}
}
