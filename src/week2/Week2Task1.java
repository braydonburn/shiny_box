package week2;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;

/**
 * Reads and outputs the RCV1v2 collection, itemId and the number of words in the file
 */
public class Week2Task1 {
    
    public static void main(String[] args) {		
    	boolean STEMMING = false;
    	boolean STOPWORD = false;
    	
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
		for (BowDocument doc : bowCollection.values()) {
			System.out.format("Document with itemid = %d contains %d words.\n",
					doc.getItemId(), doc.getWordCount());
		}
    }
      
}
