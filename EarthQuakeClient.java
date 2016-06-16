import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    /* Find earthquakes with a magnitude bigger than a specific value */
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }

        return answer;
    }

    /*
    * Find all earthquakes that are within a certain distance from a given location
    */
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }

        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        // this link gives live data
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        // the small test file below is used for testing and debugging (in all other functions as well)
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> result = filterByMagnitude(list, 5.0);
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria");

    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);

        // notice that the distance is in meters so we convert to kilometers
        ArrayList<QuakeEntry> result = filterByDistanceFrom(list, 1000*1000, city);
        for (QuakeEntry qe : result) {
            System.out.println(qe.getLocation().distanceTo(city) / 1000 + " " + qe.getInfo());
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }

    /* 
    * Find the earthquakes whose depths are between minDepth and maxDepth.
    */
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData){
            if (qe.getDepth() > minDepth && qe.getDepth() < maxDepth){
                answer.add(qe);
            }
        }
        return answer;
    }

    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        System.out.println("Find quakes with depth between -10000.0 and -5000.0");

        ArrayList<QuakeEntry> result = filterByDepth(list, -10000.0, -5000.0);
        for (QuakeEntry qe : result){
            System.out.println(qe);
        }
        System.out.println("Found "+result.size()+" quakes that match that criteria");
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData){
            String title = qe.getInfo();
            if (where.equals("start") && title.startsWith(phrase)) {
                answer.add(qe);
            }
            else if (where.equals("end") && title.endsWith(phrase)) {
                answer.add(qe);
            }
            else if (where.equals("any") && title.indexOf(phrase) != -1) {
                answer.add(qe);
            }
        }
        return answer;
    }

    /* 
    * Find the earthquakes whose titles contain certain phrases at start, end or anywhere.
    * This helps gain certain non-numeric info about the earthquakes.
    */
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        //String phrase = "California";
        //String where = "end";
        //String phrase = "Can";
        //String where = "any";
        String phrase = "Explosion";
        String where = "start";
        ArrayList<QuakeEntry> result = filterByPhrase(list, where, phrase);

        for (QuakeEntry qe : result){
            System.out.println(qe);
        }
        System.out.println("Found "+result.size()+" quakes that match "+phrase+" at "+where);
    }
    
}
