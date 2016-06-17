package demos;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;


import de.fhpotsdam.unfolding.marker.Marker;

/**
 * Visualizes life expectancy in different countries. 
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file (provided by the World Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 */
public class LifeExpectancy extends PApplet {

	UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	List<Feature> countries;
	List<Marker> countryMarkers;

	public void setup() {
		size(950, 600, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		lifeExpMap = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		println("Loaded " + lifeExpMap.size() + " data entries");
		

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();
		addKey();

	}
	
	private void addKey() 
	{	
		// Processing's graphics methods: https://www.processing.org/reference/
		fill(252, 245, 200);
		rect(25, 50, 152, 250, 7);
		textSize(12);
		fill(0);
		text("Life Expectancy", 50, 90);
		fill(0, 0, 255);
		rect(50, 115, 20, 20);
		fill(0);
		text("80-90", 75, 130);
		fill(0, 102, 204);
		rect(50, 135, 20, 20);
		fill(0);
		text("70-80", 75, 150);
		fill(103, 26, 235);
		rect(50, 155, 20, 20);
		fill(0);
		text("60-70", 75, 170);
		fill(99, 14, 141);
		rect(50, 175, 20, 20);
		fill(0);
		text("50-60", 75, 190);
		fill(247, 129, 25);
		rect(50, 195, 20, 20);
		fill(0);
		text("40-50", 75, 210);
		fill(128, 128, 128);
		rect(50, 235, 20, 20);
		fill(0);
		text("No data", 75, 250);
	
	}

	//Helper method to color each country based on life expectancy
	//Red-orange indicates low (near 40)
	//Blue indicates high (near 100)
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			if (lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				// Encode value as brightness (values range: 40-90)
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}

	//Helper method to load life expectancy data from file
	private HashMap<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		HashMap<String, Float> lifeExpMap = new HashMap<String, Float>();

		String[] rows = loadStrings(fileName);
		for (String row : rows) {
			// Reads country name and population density value from CSV row
			// NOTE: Splitting on just a comma is not a great idea here, because
			// the csv file might have commas in their entries, as this one does.  
			// We do a smarter thing in ParseFeed, but for simplicity, 
			// we just use a comma here, and ignore the fact that the first field is split.
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				lifeExpMap.put(columns[4], Float.parseFloat(columns[5]));
			}
		}

		return lifeExpMap;
	}

}
