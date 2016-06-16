/**
 * Filter the earthquakes so that all results have magnitude greater than a minimum and smaller than a
 * maximum magnitude
 * @author Frank
 * @version May 22 2016
 */
public class MagnitudeFilter implements Filter{
	private double magMin;
	private double magMax;
	private String filterName;

	// a constructor to initialize the two variables
	public MagnitudeFilter(double min, double max, String name){
		magMin = min;
		magMax = max;
		filterName = name;
	}

	public boolean satisfies(QuakeEntry qe){
		return (qe.getMagnitude() >= magMin && qe.getMagnitude() <= magMax);
		}

	public String getName() {
		return filterName;
	}
}
