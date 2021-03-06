package week7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import config.AppConfig;
import lib.evaluation.Evaluator;
import lib.preprocessing.OutputToFile;

/**
 * Read a topic-doc-assignments file (e.g., topicdocassign.txt, the benchmark) and
 * a retrieved topic-doc-assignments file (e.g., topicdocassigntest.txt, the output of an IR
 * model); calculate three evaluation measures of Recall, Precision, and F-Measure
 */
public class Week7Task1 {
	
	public static final String TOPIC = "R105";

	public static void main(String[] args) throws FileNotFoundException {
		File benchmark = new File(AppConfig.DEFAULT_RESOURCE_DIR + "evaluation//topicdocassign.txt");
		File irOutput = new File(AppConfig.DEFAULT_RESOURCE_DIR
								+ "evaluation//topicdocassigntest.txt");
		
    	// Outputs console information to specified file, if it doesn't exist create new file
    	FileOutputStream file = new FileOutputStream("src/results/BraydonBurn_wk7.txt", false);
    	OutputToFile tee = new OutputToFile(file, System.out);
    	System.setOut(tee);
		
		try {
			/**
			 * Evaluator may throw the following exception:
			 * IOException, NumberFormatException, RelevanceJudgmentException
			 */
			Evaluator evaluator = new Evaluator(TOPIC, benchmark, irOutput);
			int numOfRelevantDoc = evaluator.getNumOfRelevantDocuments();
			int numOfRetrievedAndRelevantDoc = evaluator.getNumOfRetrievedAndRelevantDocuments();
			double recall = evaluator.calculateRecall();
			double precision = evaluator.calculatePrecision();
			double fMeasure = evaluator.calculateFMeasure();
			
			System.out.format("The number of relevant documents = %d\n", numOfRelevantDoc);
			System.out.format("The number of retrieved relevant documents = %d\n", numOfRetrievedAndRelevantDoc);
			System.out.format("recall = %.6f\n", recall);
			System.out.format("precision = %.6f\n", precision);
			System.out.format("F-Measure = %.6f\n", fMeasure);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
