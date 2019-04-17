package week3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.BowDocument;
import lib.preprocessing.OutputToFile;

/**
 * Parsing - read files from RCV1v2, find the documentID and record it to a collection of BowDocument Objects
 */
public class Week3Task1 {

	public static void main(String[] args) throws FileNotFoundException {
		final boolean STOPWORD = false;
		final boolean STEMMING = false;
	
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk3.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
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
