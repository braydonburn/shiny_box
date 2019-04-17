package week6;

import config.AppConfig;
import lib.preprocessing.BM25;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Create a method to calculate a given document's BM25 score for a given query.
 * Please note you should parse query using same stemming method as parsing document. Print
 * out the BM25 scores for every document in collection for query "stock market"
 */
public class Week6Task2 {
	
	private static final String DEFAULT_QUERY = "stock market";
	
	private static String query;

	public static void main(String[] args) {
		if (args.length > 0) {
			query = args[1];
		} else {
			query = DEFAULT_QUERY;
		}
		
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		BM25 bm25Calculator = new BM25(bowCollection);

		System.out.printf("Average document length %d for query: %s\n\n",
				  		  bowCollection.getAverageDocLength(),
				  		  query);

		for (BowDocument thisDoc : bowCollection.values()) {
			System.out.printf(
					"Document: %s, Length: %d and BM25 score: %.6f\n",
			  		thisDoc.getDocId(),
			  		thisDoc.getWordCount(),
			  		bm25Calculator.calculateBM25(
			  				thisDoc, query,
			  				bowCollection.getAverageDocLength(),
			  				bowCollection.size())
				);
		}
	}
	
}
