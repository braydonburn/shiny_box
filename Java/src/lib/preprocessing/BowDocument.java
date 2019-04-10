package lib.preprocessing;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * BowDocument is used for storing bag of words of a document. It contains a map of each term in a document
 * and the frequency of each term.
 */
public class BowDocument {
	private final int INITIAL_WORDCOUNT = 0;
	private final int INITIAL_TERM_FREQ = 1;
	private final int TERM_FREQ_INCREMENT = 1;
	
	private String fileNo;
	private int docID;
	private int itemID;
	private HashMap<String, Integer> termFreqMap;
	private int docLength;
	
	/**
	 * Initializes variables.
	 * @param fileId file ID of current document.
	 */
	public BowDocument(String fileId) {
		fileNo = fileId;
		termFreqMap = new HashMap<String, Integer>();
		docLength = INITIAL_WORDCOUNT;
	}

	/**
	 * @return the file no. of the current document.
	 */
	public String getFileNo() {
		return fileNo;
	}

	/**
	 * @return the current document ID.
	 */
	public int getDocId() {
		return docID;
	}
	
	/**
	 * Sets itemID and docID at the same time.
	 * @param id indicate which file is being used.
	 */
	public void setitemId(int id) {
		itemID = id;
		docID = itemID;
	}

	/**
	 * @return the document's item ID.
	 */
	public int getItemId() {
		return itemID;
	}
	
	/**
	 * If the term is already in the bag add its frequency by 1
	 * else add the new term into the bag. 
	 * @param name the term name
	 */
	public void addTerm(String name) {
		if (termFreqMap.keySet().contains(name)) {
			int frequency = termFreqMap.get(name) + TERM_FREQ_INCREMENT;
			termFreqMap.replace(name, frequency);
		} else {
			termFreqMap.put(name, INITIAL_TERM_FREQ);
		}
	}
	
	/**
	 * Gets the target term's frequency.
	 * @param name term name.
	 * @return frequency of the target term.
	 */
	public int getTermFreq(String name) {
		if (termFreqMap.keySet().contains(name)) {
			return termFreqMap.get(name);
		} else {
			return 0;
		}
	}

	/**
	 * @return the term frequency map stored in this instance.
	 */
	public HashMap<String, Integer> getTermFreqMap() {
		return termFreqMap;
	}
	
	/**
	 * @return sorted term frequency map as an iterator object.
	 */
	public HashMap<String, Integer> getSortedTermFreqMap() {
		// sort by names
		Stream<Map.Entry<String,Integer>> mapWithSortedKey =
				termFreqMap.entrySet().stream().sorted(
					Map.Entry.comparingByKey()
				 );
		// sort by values.
		Stream<Map.Entry<String,Integer>> mapWithSortedValue =
				mapWithSortedKey.sorted(
					Collections.reverseOrder(Map.Entry.comparingByValue())
				 );
		
		// The following lines store the sorted list into a LinkedHashMap  
		Iterator<Entry<String, Integer>> sortedListIterator = mapWithSortedValue.iterator();
		HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		while (sortedListIterator.hasNext()) {
			Entry<String, Integer> item = sortedListIterator.next();
			sortedMap.put(item.getKey(), item.getValue());
		}
		
		return sortedMap;
	}
	
	/**
	 * @return size of bag of words.
	 */
	public int getTermCount() {
		return termFreqMap.size();
	}

	/**
	 * Increment docLength by 1.
	 */
	public void addWordCount() {
		docLength += 1;
	}
	
	/**
	 * @return the value of wordCount.
	 */
	public int getWordCount() {
		return docLength;
	}
	
}
