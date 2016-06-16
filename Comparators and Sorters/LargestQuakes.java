/**
 * Determine the N biggest earthquakes in a given list of earthquake data
 * 
 * @author  Frank
 */

import java.util.*;

public class LargestQuakes{
	public void findLargestQuakes(){
		EarthQuakeParser parser = new EarthQuakeParser();
		String source = "data/nov20quakedatasmall.atom";
		ArrayList<QuakeEntry> list = parser.read(source);
		System.out.println("read data for "+list.size());
		// for (QuakeEntry qe: list){
		// 	System.out.println(qe);
		// }
		// test indexOfLargest:
		int idx = indexOfLargest(list);
		System.out.println(idx + " magnitude: "+list.get(idx).getMagnitude());
		// print the N largest earthquakes
		ArrayList<QuakeEntry> largest = getLargest(list, 5);
		for (QuakeEntry qe : largest){
			System.out.println(qe);
		}
	}

	// get the index of the quake with the largest magnitude in a given array of quake data
	public int indexOfLargest(ArrayList<QuakeEntry> data){
		int idx = 0;
		for (int k=0; k < data.size(); k++){
			double magnitude = data.get(k).getMagnitude();
			if (magnitude > data.get(idx).getMagnitude()){
				idx = k;
			}
		}
		return idx;
	}

	// get an array of N largest earthquakes
	public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, Integer howMany){
		// make a copy of the input array of quake data
		ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
		// initialize an empty array to store the top N largest quakes
		ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();
		for (int k=0; k < howMany; k++){
			int idx = indexOfLargest(copy);
			result.add(copy.get(idx));
			// remember to remove the quake already found
			copy.remove(idx);
		}
		return result;
	}
}


