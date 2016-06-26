package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.GeoUtils;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import parsing.ParseFeed;
import processing.core.PApplet;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * @author Frank Chen
 * Date: June 26, 2016
 * */
public class EarthquakeCityMap extends PApplet {
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {		
		// Initializing canvas and map tiles
		size(900, 700, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());
		earthquakesURL = "test2.atom";
		
		MapUtils.createDefaultEventDispatcher(this, map);
		
		
		// Reading in earthquake data and geometric properties
	    // load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		// read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		// read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    // for debugging
	    //printQuakes();
	 		
	    // Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	    // print a specific number of sorted earthquakes
	    sortAndPrint(20);
	    
	}  // End setup
	
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
		if (lastClicked != null && lastClicked.getClicked()) {
			drawCircle(lastClicked);
		}
		
	}
	
	private void sortAndPrint(int numToPrint) {
		// turn the list of quakeMarkers into an array
		Object[] array = quakeMarkers.toArray();
		Arrays.sort(array);
		if (numToPrint > array.length) {
			for (Object marker : array) {
				System.out.println(((EarthquakeMarker) marker).getTitle());
			}
		}
		else {
			for (int i = 0; i < numToPrint; i++) {
				System.out.println(((EarthquakeMarker) array[i]).getTitle());
			}
		}
	}
	
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(quakeMarkers);
		selectMarkerIfHover(cityMarkers);
	}
	
	
	// If there is a marker under the cursor, and lastSelected is null 
	// set the lastSelected to be the first marker found under the cursor
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// TODO: Implement this method
		for (Marker marker : markers){
			if (lastSelected == null && marker.isInside(map, mouseX, mouseY)){
				lastSelected = (CommonMarker) marker;
				marker.setSelected(true);;
				break;
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked()
	{
		if (lastClicked == null) {
			// loop through both quakeMarkers and cityMarkers to see which one got clicked
			for (Marker marker : map.getMarkers()) {
				if (marker.isInside(map, mouseX, mouseY)) {
					// if a city got clicked, keep all quakes and the city itself unhidden
					if (marker instanceof CityMarker) {
						hideMarkers(true);
						marker.setHidden(false);
						// display all quakes within the threat circle of the city
						for (Marker quake : quakeMarkers) {
							if (((EarthquakeMarker) quake).isInThreat(marker)) {
								quake.setHidden(false);
							}
						}
						lastClicked = (CommonMarker) marker;
					}
					// if a earthquake got clicked, keep all cities and the quake itself unhidden
					else if (marker instanceof EarthquakeMarker) {
						hideMarkers(true);
						marker.setHidden(false);
						for (Marker city : cityMarkers) {
							if (((EarthquakeMarker) marker).isInThreat(city)) {
								city.setHidden(false);
							}
						}
						lastClicked = (CommonMarker) marker;
						lastClicked.setClicked(true);
					}
					break;
				}
			}
		}
		else {
			lastClicked.setClicked(false);
			lastClicked = null;
			hideMarkers(false);
		}
	}
	
	/** Helper method: determine whether a city is in the threatCircle of a quake.
	 * Return true if yes, false otherwise.
	 */
	
	// hide or unhide markers depends on the boolean value
	private void hideMarkers(boolean bool) {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(bool);
		}
			
		for(Marker marker : cityMarkers) {
			marker.setHidden(bool);
		}
	}
	
	/** Helper method: draw the threat circle of a quake
	 */
	public void drawCircle(CommonMarker marker) {
		float distanceInKm = (float) ((EarthquakeMarker) marker).threatCircle();
		// temperary location that is at the edge of the threat circle of the marker
		Location temp = GeoUtils.getDestinationLocation(marker.getLocation(), 90, distanceInKm);
		ScreenPosition pos1 = map.getScreenPosition(temp);
		ScreenPosition pos2 = map.getScreenPosition(marker.getLocation());
		// threat circle radius in pixel
		float pix = pos1.dist(pos2);
		noFill();
		// draw the threat circle
		ellipse(pos2.x, pos2.y, pix, pix);
	}
	
	// helper method to draw key in GUI
	private void addKey() {	
		fill(255, 250, 240);
		rect(25, 50, 150, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", 50, 70);
		
		fill(color(102, 0, 0));
		triangle(60, 90, 55, 100, 65, 100);
		fill(255, 255, 255);
		ellipse(60, 115, 12, 12);
		fill(255, 255, 255);
		rect(54, 130, 12, 12);
		fill(color(0, 0, 255));
		ellipse(60, 195, 12, 12);
		fill(color(255, 255, 0));
		ellipse(60, 215, 12, 12);
		fill(color(255, 0, 0));
		ellipse(60, 235, 12, 12);
		fill(255);
		ellipse(60, 255, 12, 12);
		line(55, 250, 65, 260);
		line(65, 250, 55, 260);
		
		fill(0, 0, 0);
		text("City Marker", 75, 95);
		text("Land Quake", 75, 115);
		text("Ocean Quake", 75, 135);
		text("Size ~ Magnitude", 50, 155);
		text("Shallow", 75, 195);
		text("Intermediate", 75, 215);
		text("Deep", 75, 235);
		text("Past Day", 75, 255);
	}

	
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.	
	private boolean isLand(PointFeature earthquake) {
		
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		
		// not inside any country
		return false;
	}
	
	// prints countries with number of earthquakes
	private void printQuakes() {
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
		earthquakesURL = "test2.atom";
		int numOceanQuakes = earthquakes.size();
		for(Marker country : countryMarkers){
			int numQuakes = 0;
			for(PointFeature quake : earthquakes) {
				if (isInCountry(quake, country)){
					numQuakes += 1;
				}
			}
			if (numQuakes > 0) {
			    System.out.println(country.getProperty("name")+": " + numQuakes);
			    numOceanQuakes -= numQuakes;
			}
		}
		System.out.println("The number of quakes that were detected in the ocean: " + 
		numOceanQuakes);
	}
	
	
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake feature if 
	// it's in one of the countries.
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

}
