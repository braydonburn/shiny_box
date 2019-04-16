package week5;

import java.util.HashMap;
import java.util.Map.Entry;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.TfIdf;

/**
 * Calculate TF*IDF value of every term in a BowDocument in a given
 * BowDocument collection. Create a method in your processor class, and call
 * calculateTfIdf method to generate a term:tfidf HashMap for each document, then fill a
 * HashMap of docId: tfidfHashMap, print out top 20 terms (with its value of tfidf) for
 * each document if it has more than 20 terms, and save the output into a text file (file name
 * is “your full name_wk5_t2.txt”). 
 */
public class Week5Task2 {

	public static void main(String[] args) {
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		
		TfIdf result = new TfIdf(bowCollection);
		for(Entry<Integer, BowDocument> doc : bowCollection.entrySet()) {
			HashMap<String, Double> resultMap = result.calculateTfIdf(doc.getValue(), bowCollection.size());
			System.out.println("Document " + doc.getKey() + " contains " + resultMap.size() + " terms.");
			int printCounter = 0;
			for (Entry<String, Double> posting : resultMap.entrySet()) {
				System.out.format("%s: %.6f\n",
						String.format("%1$-12s", posting.getKey()),
						posting.getValue());
				
				// only print the top 20 terms
				if (++printCounter >= 20) {
					break;
				}
			}
			System.out.println();
		}
	}

}
