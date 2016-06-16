/**
 * Filter the earthquakes so that all results have titles which contain a particular phrase at
 * a specified location:"start", "end" or "any".
 * @author Frank
 * @version May 22 2016
 */
public class PhraseFilter implements Filter{
	// where to search the phrase: "start", "end" or "any"
	private String where;
	// the phrase to search in the title of the quake
	private String phrase;
	private String filterName;

	// a constructor to initialize the two variables
	public PhraseFilter(String str, String phr, String name){
		where = str;
		phrase = phr;
		filterName = name;
	}

	public boolean satisfies(QuakeEntry qe){
		String title = qe.getInfo();
		boolean bool = true;
        if (where.equals("start")) {
            bool = title.startsWith(phrase);
        }
        else if (where.equals("end")) {
            bool = title.endsWith(phrase);
        }
        else if (where.equals("any")) {
            bool = title.indexOf(phrase) != -1;
		}
		return bool;
	}

	public String getName() {
		return filterName;
	}
}
