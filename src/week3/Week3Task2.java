package week3;

import java.util.HashMap;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 *  Tokenizing – update Task 1 program to fill term:freq map for every document.
 */
public class Week3Task2 {
	static protected BowCollection bowCollection;

	public static void main(String[] args) {
		final boolean STOPWORD = false;
		final boolean STEMMING = false;
		
		bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
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
		
		HashMap<String, Integer> termFreqMap = bowCollection.get(aDocId).getTermFreqMap();
		System.out.format("Document %d has %d different terms:\n",
				aDocId, termFreqMap.size());
		for (String name : termFreqMap.keySet()) {
			System.out.format("%s,%s%d\n",
					name,
					(name.length() < ONE_TAP_SPACES)? "\t\t":"\t",
					termFreqMap.get(name));
		}
		System.out.println("\n\n");
	}

}
