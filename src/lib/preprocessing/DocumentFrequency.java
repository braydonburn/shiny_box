package lib.preprocessing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * Used to count the document frequency of all documents within the given data set.
 */
public class DocumentFrequency {

	private HashMap<String, Integer> df;
	private HashMap<String, Integer> relevantDoc;
	private int noOfDoc;
	
	/**
	 * Simple constructor to initialize the variables.
	 */
	public DocumentFrequency() {
		df = new LinkedHashMap<String, Integer>();
		relevantDoc = new LinkedHashMap<String, Integer>();
		noOfDoc = 0;
	}
	
	/**
	 * Calculate the document frequency of each term in the collection.
	 *  
	 * @param docCollection collection of BowDocuments
	 *  
	 * @return HashMap: string of term, integer amount term is seen
	 */
	public HashMap<String, Integer> calculateDF(BowCollection docCollection) {
		BowDocument scanningDoc;
		HashMap<String, Integer> docTermFreq;
		int originalFreq, newFreq;
		
		// Record the total number of documents inspected.
		noOfDoc = docCollection.size();
		
		for (Entry<Integer, BowDocument> doc : docCollection.entrySet()) {
			scanningDoc = doc.getValue();
			docTermFreq = scanningDoc.getTermFreqMap();
			for (String docTerm : docTermFreq.keySet()) {
				// Get to term frequency of the specified term in the scanning document
				originalFreq = docTermFreq.get(docTerm);
				
				// Update the term frequency in DF for all documents
				if (df.containsKey(docTerm)) {
					newFreq = originalFreq + df.get(docTerm);
					df.replace(docTerm, newFreq);
					relevantDoc.replace(docTerm, relevantDoc.get(docTerm)+1);
				} else {
					df.put(docTerm, originalFreq);
					relevantDoc.put(docTerm, 1);
				}
			}
		}
		
		sortingDF();
		return df;
	}
	
	/**
	 * Sort document frequency map by terms in ascending 
	 * alphabetic order and by frequency in descending order.
	 */
	private void sortingDF() {
		Stream<Map.Entry<String,Integer>> mapWithSortedKey =
				df.entrySet().stream().sorted(
					Map.Entry.comparingByKey()
				 );
		Stream<Map.Entry<String,Integer>> mapWithSortedValue =
				mapWithSortedKey.sorted(
					Collections.reverseOrder(Map.Entry.comparingByValue())
				 );
		Iterator<Entry<String, Integer>> sortedListIterator = mapWithSortedValue.iterator();
		LinkedHashMap<String, Integer> mapSequence = new LinkedHashMap<String, Integer>();
		while(sortedListIterator.hasNext()) {
			Entry<String, Integer> newItem = sortedListIterator.next();
			mapSequence.put(newItem.getKey(), newItem.getValue());
		}
		df = mapSequence;
	}
	
	/** 
	 * @return HashMap of information about the document frequency.
	 */
	public HashMap<String, Integer> getDFMap() {
		return df;
	}
	
	/** 
	 * @param term name being queried. 
	 * @return document frequency of given term.
	 */
	public int getDfByTerm(String term) {
		if (df.containsKey(term)) {
			return df.get(term);
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the number of relevant documents for the specified term. 
	 *
	 * @param term name being queried
	 * @return number of relevant documents.
	 */
	public int getRelevantDocByTerm(String term) {
		if (relevantDoc.containsKey(term)) {
			return relevantDoc.get(term);
		} else {
			return 0;
		}
	}
	
	/**
	 * Logs all frequencies to the console window
	 * 
	 * @return text description of the data stored in the object. 
	 */
	public String toString() {
		String strBuffer = "";
		strBuffer = strBuffer.concat(String.format(
						"There are %d documents in this data set and contains %d terms.\n",
						noOfDoc,
						df.size()));
		for (String term : df.keySet()) {
			strBuffer = strBuffer.concat(String.format(
							"%s: %d\n",
							String.format("%1$-12s", term),
							df.get(term)));
		}
		return strBuffer;
	}

}
