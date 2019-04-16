package lib.preprocessing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * Calculates the TF*IDF value of each term in the data set.
 */
public class TfIdf {
	
	private HashMap<Integer,BowDocument> bowCollection;

	/**
	 * @param collection the BowCollection.
	 */
	public TfIdf(HashMap<Integer,BowDocument> collection) {
		bowCollection = collection;
	}

	/**
	 * Calculates the TFIDF value of each term
	 * 
	 * @param aDoc current document being processed
	 * @param noDocs total number of documents in the data set
	 * 
	 * @return hash map for all TFIDF values
	 */
	public HashMap<String, Double> calculateTfIdf(
				BowDocument aDoc, int noDocs) {
		
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		HashMap<String, Integer> freqMap = aDoc.getTermFreqMap();

		/* Calculate the denominator of the TF*IDF formula */
		double denominator = 0;
		for (String term: freqMap.keySet()) {
			denominator += Math.pow(
					calculateSingleTfIdf(term, freqMap.get(term), noDocs),
					2);
		}
		denominator = Math.sqrt(denominator);
		
		/* Calculate the numerator of the TF*IDF formula */
		for (Entry<String, Integer> post: freqMap.entrySet()) {
			String term = post.getKey();
			Integer frequency = post.getValue();
			double tfidfResult = calculateSingleTfIdf(term, frequency, noDocs)
								 / denominator;
			resultMap.put(term, tfidfResult);
		}

		return sortingTfIdf(resultMap);
	}
	
	/**
	 * Given a single document and the term frequency, this method will calculate
	 * the relevant TF*IDF information.
	 * 
	 * @param term the specific term name to be calculated
	 * @param termFreqInDoc the term frequency within the current document
	 * @param noDocs the total number of documents in the data set
	 * 
	 * @return the TFIDF calculation result
	 */
	private double calculateSingleTfIdf(String term, int termFreqInDoc, int noDocs) {
		int docFreqForThisTerm;
		double termFrequency,
			   inverseDocumentFrequency;
					
		/* Go through each document and count how many document contains the term */
		docFreqForThisTerm = 0;
		for(int id : bowCollection.keySet()) {
			BowDocument scanningDoc = bowCollection.get(id);
			if (scanningDoc.getTermFreqMap().keySet().contains(term)) {
				++docFreqForThisTerm;
			}
		}

		termFrequency = 1.0 + Math.log10(termFreqInDoc);
		inverseDocumentFrequency = Math.log10(noDocs / docFreqForThisTerm);
		
		return termFrequency * inverseDocumentFrequency;
		
	}
	
	/**
	 * Sorts the entire TF*IDF map by term name in ascending order and by the
	 * TF*IDF value in descending order
	 * 
	 * @param originalMap is the original unsorted map
	 * 
	 * @return sorted TF*IDF map
	 */
	private HashMap<String, Double> sortingTfIdf(HashMap<String, Double> originalMap) {
		// sort by the term names
		Stream<Map.Entry<String,Double>> mapWithSortedKey =
				originalMap.entrySet().stream().sorted(
					Map.Entry.comparingByKey()
				 );
		// sort by the TF*IDF values
		Stream<Map.Entry<String,Double>> mapWithSortedValue =
				mapWithSortedKey.sorted(
					Collections.reverseOrder(Map.Entry.comparingByValue())
				 );
		HashMap<String, Double> sortedTfIdf = new LinkedHashMap<String, Double>();
		Iterator<Entry<String, Double>> sortedListIterator = mapWithSortedValue.iterator();
		
		while (sortedListIterator.hasNext()) {
			Entry<String, Double> item = sortedListIterator.next();
			sortedTfIdf.put(item.getKey(), item.getValue());
		}
		return sortedTfIdf;
	}
}
