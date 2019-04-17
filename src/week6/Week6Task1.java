package week6;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Calculate and store the average document length in given documents set.
 */
public class Week6Task1 {

	public static void main(String[] args) {
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		
		for (BowDocument thisDoc : bowCollection.values()) {
			System.out.printf("Document %s has a document length of %d.\n",
							  thisDoc.getDocId(),
							  thisDoc.getWordCount());
		}
		System.out.printf("\nThese %d documents have an average document length of %d.\n",
						  bowCollection.size(),
						  bowCollection.getAverageDocLength());
	}
	
}
