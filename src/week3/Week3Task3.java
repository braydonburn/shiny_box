package week3;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Stopping words – use given stopping words list to ignore/remove all stopping words from the term list of documents.
 */
public class Week3Task3 extends Week3Task2 {
	
	public static void main(String[] args) {
		final boolean STOPWORD = true;
		final boolean STEMMING = false;

		bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
		for (BowDocument thisDoc: bowCollection.values()) {
			displayDocInfo(thisDoc.getDocId());
		}
	}
	
}
