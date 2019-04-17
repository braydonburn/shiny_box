package week5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.preprocessing.BowCollection;
import lib.preprocessing.DocumentFrequency;
import lib.preprocessing.OutputToFile;

/**
 * Calculate Document-Frequency of each term and store them in a term:df
 * dictionary (in Python, C#) / HashMap (in Java). Call the created method then display a
 * list of TERM: DF for whole RCV1v2 document collection, and save the output into a text
 * file (file name is “your full name_wk5_t1.txt”). 
 */
public class Week5Task1 {
	
	public static void main(String[] args) throws FileNotFoundException {
		BowCollection bowCollection = new BowCollection(AppConfig.DEFAULT_DATASET_DIR);
		DocumentFrequency df = new DocumentFrequency();
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk5.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
		df.calculateDF(bowCollection);

		System.out.println(df.toString());
	}
}
