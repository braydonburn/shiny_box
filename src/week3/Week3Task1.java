package week3;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Parsing - read files from RCV1v2, find the documentID and record it to a collection of BowDocument Objects
 */
public class Week3Task1 {

	public static void main(String[] args) {
		final boolean STOPWORD = false;
		final boolean STEMMING = false;
		
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR,
				STOPWORD, STEMMING);
		printDocumentIds(bowCollection);
	}
	
	/**
	 * Prints all the documentIDs of the BowDocument from the given BowCollection.
	 * @param bowCollection collection that stored all the bowDocument in the data set.
	 */
	public static void printDocumentIds(BowCollection bowCollection) {
		int counter = 1;
		System.out.format("There are total %d documents in the data set.\n",
				bowCollection.size());
		System.out.println("Doc\tdocumentID");
		System.out.println("---\t----------");
		for (BowDocument thisDoc : bowCollection.values()) {
			System.out.format("#%d\t%d\n", counter++, thisDoc.getDocId());
		}
	}

}
