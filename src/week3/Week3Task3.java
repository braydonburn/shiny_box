package week3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.OutputToFile;

/**
 * Stopping words – use given stopping words list to ignore/remove all stopping words from the term list of documents.
 */
public class Week3Task3 extends Week3Task2 {
	
	public static void main(String[] args) throws FileNotFoundException {
		final boolean STOPWORD = true;
		final boolean STEMMING = false;

		bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR, STOPWORD, STEMMING);
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk3_3.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
		for (BowDocument thisDoc: bowCollection.values()) {
			displayDocInfo(thisDoc.getDocId());
		}
	}
	
}
