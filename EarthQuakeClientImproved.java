import java.util.*;
import edu.duke.*;

public class EarthQuakeClientImproved {
    public EarthQuakeClientImproved() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        // use the magnitude filter first
        // Filter f1 = new MagnitudeFilter(4.0, 5.0);
        Location tokyo = new Location(35.42, 139.43);
        Filter f1 = new DistanceFilter(10000.0, tokyo, "DistFilter"); 
        ArrayList<QuakeEntry> first  = filter(list, f1);
        System.out.println(first.size());
        Filter f2 = new PhraseFilter("end", "Japan", "PhraseFilter");
        // apply the second filter to the result from the first filter
        ArrayList<QuakeEntry> second = filter(first, f2);
        System.out.println(second.size());
        for (QuakeEntry qe: second) { 
            System.out.println(qe);
        } 
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
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

    public void testMatchAllFilter() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        // initialize the constructor to store all filters
        MatchAllFilter maf = new MatchAllFilter();
        Filter f1 = new MagnitudeFilter(0.0, 2.0, "MagFilter");
        Filter f2 = new DepthFilter(-100000.0, -10000.0, "DepFilter");
        Filter f3 = new PhraseFilter("any", "a", "PhraseFilter");
        maf.addFilter(f1);
        maf.addFilter(f2);
        maf.addFilter(f3);
        ArrayList<QuakeEntry> result = filter(list, maf);
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        System.out.println("Filters used are: " + maf.getName());
    }

    public void testMatchAllFilter2() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        // initialize the constructor to store all filters
        MatchAllFilter maf = new MatchAllFilter();
        Filter f1 = new MagnitudeFilter(0.0, 3.0, "MagFilter");
        Location tulsa = new Location(36.1314, -95.9372);
        Filter f2 = new DistanceFilter(10000.0, tulsa, "DistFilter");
        Filter f3 = new PhraseFilter("any", "Ca", "PhraseFilter");
        maf.addFilter(f1);
        maf.addFilter(f2);
        maf.addFilter(f3);
        ArrayList<QuakeEntry> result = filter(list, maf);
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        System.out.println("Filters used are: " + maf.getName());
    }
}
