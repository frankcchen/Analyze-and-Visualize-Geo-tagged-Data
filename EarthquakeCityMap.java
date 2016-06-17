
//Java utilities libraries
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * @author FRANK CHEN
 * Date: June 16, 2016
 * */
public class EarthquakeCityMap extends PApplet {

	// to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
    
    // an int that represents the color yellow.  
    private int yellow = color(255, 255, 0);
    private int red = color(255, 0, 0);
    private int blue = color(0, 0, 255);
	
	public void setup() {
		size(950, 600, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Create a SimplePointMarker for every PointFeature in the list earthquakes
	    for (PointFeature f : earthquakes){
	    	SimplePointMarker marker = createMarker(f);
	    	float mag = Float.parseFloat(f.getProperty("magnitude").toString());
	    	// Set the color and size of the marker based on the magnitude of
	    	// its corresponding earthquake.
	    	if (mag < THRESHOLD_LIGHT) {
	    		marker.setColor(blue);
	    		marker.setRadius(5);
	    	}else if (THRESHOLD_LIGHT <= mag && mag < THRESHOLD_MODERATE){
	    		marker.setColor(yellow);
	    		marker.setRadius(10);
	    	}else{
	    		marker.setColor(red);
	    		marker.setRadius(15);
	    	}
	    	markers.add(marker);
	    }
	    // Add those markers created to the map.
	    map.addMarkers(markers);
	}
		
	// A helper method: takes in an earthquake feature and returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature)
	{
		return new SimplePointMarker(feature.getLocation(), feature.getProperties());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// Implement this method to draw the map key
	private void addKey() 
	{	
		// Processing's graphics methods: https://www.processing.org/reference/
		fill(252, 245, 200);
		rect(25, 50, 152, 250, 7);
		textSize(12);
		fill(0, 0, 102);
		text("Earthquake Key", 58, 80);
		fill(red);
		ellipse(50, 115, 15, 15);
		fill(0);
		text("5.0+ Magnitude", 75, 120);
		fill(yellow);
		ellipse(50, 165, 10, 10);
		fill(0);
		text("4.0+ Magnitude", 75, 170);
		fill(blue);
		ellipse(50, 215, 5, 5);
		fill(0);
		text("Below 4.0", 75, 220);
	
	}
}
