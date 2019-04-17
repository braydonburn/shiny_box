package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.OutputToFile;

/**
 * Reads and outputs the RCV1v2 collection, itemId and the number of words in the file
 */
public class Week2Task1 {
    
    public static void main(String[] args) throws FileNotFoundException {		
    	boolean STEMMING = false;
    	boolean STOPWORD = false;
    	
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk2.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
    	
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
		for (BowDocument doc : bowCollection.values()) {
			System.out.format("Document with itemid = %d contains %d words.\n",
					doc.getItemId(), doc.getWordCount());
		}
    }
      
}
