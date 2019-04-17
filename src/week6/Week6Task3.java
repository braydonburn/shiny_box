package week6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import config.AppConfig;
import lib.preprocessing.BM25;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

public class Week6Task3 {
	
	private final static int NO_OF_TOP_RESULTS = 3;
	
	public static void main(String[] args) {
		String[] queries = {"British fashion",
						  "fashion awards",
						  "Stock market",
						  "British Fashion Awards",
						  "Car failure"
						  };
		
		
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		HashMap<Integer, Double> bm25scores;
		BM25 bm25Calculator = new BM25(bowCollection);
		for (int i = 0; i < queries.length; ++i) {
			bm25scores = new LinkedHashMap<Integer, Double>();
			System.out.printf("Average document length %d for query: %s\n\n",
					  		  bowCollection.getAverageDocLength(),
					  		  queries[i]);
	
			/* inspects each document in the collection and
			 *  find out their BM25 scores */
			for (BowDocument thisDoc : bowCollection.values()) {
				double weight = bm25Calculator.calculateBM25(
					  				thisDoc, queries[i],
					  				bowCollection.getAverageDocLength(),
					  				bowCollection.size());
				bm25scores.put(thisDoc.getDocId(), weight);
				System.out.printf(
						"Document: %s, Length: %d and BM25 score: %.6f\n",
				  		thisDoc.getDocId(),
				  		thisDoc.getWordCount(),
				  		weight
					);
			}
			
			/* Sort all documents' BM25 scores and display only the top 3 results */
			Stream<Map.Entry<Integer,Double>> sortedmap =
					bm25scores.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
			Iterator<Entry<Integer,Double>> lookup = sortedmap.iterator();
			System.out.printf("\nFor query \"%s\", three recommended relevant documents and their BM25 score:\n",
								queries[i]);
			int count = 0;
			while (lookup.hasNext() && ++count <= NO_OF_TOP_RESULTS) {
				Entry<Integer,Double> item = lookup.next();
				System.out.printf("\n%s : %f",
									item.getKey(),
									item.getValue());
			}
			System.out.printf("\n\n* * * * * * * *\n\n");
		}
	}
}
