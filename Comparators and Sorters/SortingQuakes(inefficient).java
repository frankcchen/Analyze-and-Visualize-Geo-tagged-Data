
/**
 * Several sorting methods for the QuakeEntry class.
 * 
 * @author Frank
 * @version May 23, 2016
 */

import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }

    public int getLargestDepth(ArrayList<QuakeEntry> quakes, int from) {
        int maxIdx = from;
        for (int i = from + 1; i < quakes.size(); i++) {
            if (quakes.get(i).getDepth() > quakes.get(maxIdx).getDepth()) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    public void sortByLargestDepth(ArrayList<QuakeEntry> in) {
        for (int i=0; i< in.size(); i++) {
            int maxIdx = getLargestDepth(in, i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmax = in.get(maxIdx);
            in.set(i, qmax);
            in.set(maxIdx, qi);
        }
    }

    // Bubble Sort
    // numSorted is the number of times this method has already been called on the array list
    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted) {
        for (int i=0; i < quakeData.size() - numSorted - 1; i++) {
            QuakeEntry firstQuake = quakeData.get(i);
            QuakeEntry secondQuake = quakeData.get(i+1);
            if (firstQuake.getMagnitude() > secondQuake.getMagnitude()) {
                quakeData.set(i, secondQuake);
                quakeData.set(i+1, firstQuake);
            }
        }

    }

    // If the ArrayList in has N elements in it, this method calls onePassBubbleSort N – 1 times 
    // to sort the elements in in
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in) {
        for (int i=0; i < in.size()-1; i++) {
            onePassBubbleSort(in, i);
        }
    }

    // check if an ArrayList is already sorted by magnitude in ascending order
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes) {
        for (int i=0; i<quakes.size()-1; i++) {
            if (quakes.get(i).getMagnitude() > quakes.get(i+1).getMagnitude()) {
                return false;
            }
        }
        return true;
    }

    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in) {
        for (int i=0; i < in.size()-1; i++) {
            // stop the sorting if the list is already sorted
            if (checkInSortedOrder(in)) {
                System.out.println("Passes needed: " + i);
                break;
            }
            onePassBubbleSort(in, i);
        }
    }


    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        String source2 = "data/earthquakeDataSampleSix2.atom";
        String source3 = "data/earthquakeDataSampleSix1.atom";
        //String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source3);  
       
        System.out.println("read data for "+list.size()+" quakes");    
        //sortByLargestDepth(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list) {
		System.out.println("Latitude,Longitude,Magnitude,Info");
		for(QuakeEntry qe : list){
			System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
			                  qe.getLocation().getLatitude(),
			                  qe.getLocation().getLongitude(),
			                  qe.getMagnitude(),
			                  qe.getInfo());
	    }
		
	}
}
