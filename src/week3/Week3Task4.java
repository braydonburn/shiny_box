package week3;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Stopping words – use given stopping words list to ignore/remove all stopping words from the term list of documents.
 */
public class Week3Task4 extends Week3Task2 {
	
	public static void main(String[] args) {
		final boolean STOPWORD = true;
		final boolean STEMMING = false;

		bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
		for (BowDocument thisDoc: bowCollection.values()) {
			displayDocInfo(thisDoc.getDocId());
		}
	}
	/**
	 * Sorts and prints the data of given doc ID to console.
	 * @param aDocId the doc ID we want to print.
	 */
	public static void displayDocInfo(int aDocId) {
		final int ONE_TAP_SPACES = 7;
		
		HashMap<String, Integer> termFreqMap = bowCollection.get(aDocId).getTermFreqMap();		
		Map<String, Integer> sorted = termFreqMap
			        .entrySet()
			        .stream()
			        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			        .collect(
			            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
			                LinkedHashMap::new));
		
		System.out.format("Document %d has %d different terms:\n",
				aDocId, sorted.size());
		for (String name : sorted.keySet()) {
			System.out.format("%s,%s%d\n",
					name,
					(name.length() < ONE_TAP_SPACES)? "\t\t":"\t",
							sorted.get(name));
		}
		System.out.println("\n\n");
	}	
}
