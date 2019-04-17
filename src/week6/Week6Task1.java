package week6;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.OutputToFile;

/**
 * Calculate and store the average document length in given documents set.
 */
public class Week6Task1 {

	public static void main(String[] args) throws FileNotFoundException {
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk6.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
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
