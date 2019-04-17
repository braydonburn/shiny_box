package week4;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.OutputToFile;

/**
 *  Stemming – using porter2 stemming algorithm to update BowDocument’s term list (e.g., Dictionary or HashMap)
 */
public class Week4Task1 {
	
	static protected BowCollection bowCollection;
	
	public static void main(String[] args) throws FileNotFoundException {
		final boolean STOPWORD = true;
		final boolean STEMMING = true;

		bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk4.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
		for (BowDocument thisDoc: bowCollection.values()) {
			displayDocInfo(thisDoc.getDocId());
		}
	}
	
	/**
	 * Prints the data of given doc ID to console.
	 * @param aDocId the doc ID we want to print.
	 */
	public static void displayDocInfo(int aDocId) {
		final int ONE_TAP_SPACES = 7;
		
		HashMap<String, Integer> termFreqMap = bowCollection.get(aDocId).getSortedTermFreqMap();
		Map<String, Integer> sorted = termFreqMap
		        .entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		        .collect(
		            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                LinkedHashMap::new));
		System.out.format("Document %d contains %d terms and has total %d words.\n",
				aDocId, sorted.size(), bowCollection.get(aDocId).getWordCount());
		for (String name : sorted.keySet()) {
			System.out.format("%s,%s%d\n",
					name,
					(name.length() < ONE_TAP_SPACES)? "\t\t":"\t",
							sorted.get(name));
		}
		System.out.println("\n\n");
	}
}
