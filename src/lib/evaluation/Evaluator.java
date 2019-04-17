package lib.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Calculates various evaluation measures of relevance from a query.
 */
public class Evaluator {
	private HashMap<Integer, RelevanceJudgment>	benchmarkRec,
												irRec;
	private int numOfRelevantDocuments,
				numOfRetrievedDocuments,
				numOfRelevantAndRetrievedDoc;

	/**
	 * Reads the given benchmark file and the given output of an IR
	 * model. 
	 * @param topic the topic that related to the query.
	 * @param benchmark the benchmark file.
	 * @param irOutput the output of an IR model.
	 * @throws IOException from {@link #scanForRecords}, or if any given file is not
	 * 			a file in the system.
	 * @throws NumberFormatException from {@link #scanForRecords}
	 * @throws RelevanceJudgmentException from {@link #scanForRecords}
	 */
	public Evaluator(String topic, File benchmark, File irOutput)
			throws IOException, NumberFormatException, RelevanceJudgmentException {
		/* Process only if both files are accessible */
		if (benchmark.isFile() && irOutput.isFile()) {
			benchmarkRec = scanForRecords(topic, benchmark);
			irRec = scanForRecords(topic, irOutput);
		} else {
			throw new IOException("Cannot open the evaluating file(s).");
		}
		
		// prepare the relevance information for future use.
		countRelevance();
	}
	
	/**
	 * Read given file and transform every single line into a HashMap
	 * of records containing the document ID and the relevance of that document.
	 * 
	 * @param topic related to the current relevance situation.
	 * @param recordFile the given file for retrieving records.
	 * @return HashMap of records
	 * @throws FileNotFoundException if Scanner() method call fails.
	 * @throws NumberFormatException from {@link RelevanceJudgment#setJudgmentByString}.
	 * @throws RelevanceJudgmentException from {@link RelevanceJudgment#setJudgmentByString}.
	 */
	private HashMap<Integer, RelevanceJudgment> scanForRecords(String topic,
					File recordFile)
				throws FileNotFoundException, NumberFormatException,
						   RelevanceJudgmentException {

		HashMap<Integer, RelevanceJudgment> records
			= new HashMap<Integer, RelevanceJudgment>();
		Scanner fileScanner;
		String recordScanner;
		
		fileScanner = new Scanner(recordFile);
		
		// Read every line in the file and retrieve the related information.
		while(fileScanner.hasNextLine()) {
			recordScanner = fileScanner.nextLine();
			RelevanceJudgment judgmentRecord = new RelevanceJudgment(topic);
			
			// Convert the line of record into a RelevanceJudgment object.
			// Will print warning if it fails
			try {
				judgmentRecord.setJudgmentByString(recordScanner);
			} catch (RelevanceJudgmentException e) {
				System.out.println(e.getMessage()+" Skipped one record.");
			}
			records.put(judgmentRecord.getDocId(), judgmentRecord);
		}

		fileScanner.close();
		
		return records;
	}
	

	/**
	 * Count the number of relevant documents and the number of retrieved documents.
	 */
	private void countRelevance() {
		numOfRelevantDocuments = 0;
		numOfRetrievedDocuments = 0;
		numOfRelevantAndRetrievedDoc = 0;
		RelevanceJudgment inspectingItem,
						  inspectingIrOutput;

		// Lookup every records in the benchmark file and check the relevance.
		for (int recId : benchmarkRec.keySet()) {
			inspectingItem = benchmarkRec.get(recId);
			inspectingIrOutput = irRec.get(recId);

			if (inspectingItem.isRelevant()) {
				// If the record is relevant,
				// then add the counter 'totalNumOfRelevantDoc' by 1.
				++numOfRelevantDocuments;
				
				if (inspectingIrOutput.isRetrieved()) {
					// If the record is also retrieved,
					// then add the counter 'totalNumOfRetrievedDoc' by 1,
					// and add the counter 'numOfRelevantAndRetrievedDoc' by 1.
					++numOfRetrievedDocuments;
					++numOfRelevantAndRetrievedDoc;
				}
			} else if (inspectingIrOutput.isRetrieved()) {
				// If the record not relevant but is retrieved,
				// then add the counter 'totalNumOfRetrievedDoc' by 1.
				++numOfRetrievedDocuments;
			}
		}
	}
	
	/**
	 * Get the number of relevant documents in the benchmark data set.
	 * 
	 * @return the total number of relevant documents.
	 */
	public int getNumOfRelevantDocuments() {
		return numOfRelevantDocuments;
	}
	
	/**
	 * Get the number of retrieved documents in the output of IR model data set.
	 * 
	 * @return total number of retrieved documents.
	 */
	public int getNumOfRetrievedDocuments() {
		return numOfRetrievedDocuments;
	}
	
	/**
	 * Get the number of documents that is both retrieved and relevant.
	 * 
	 * @return number of documents in both sets.
	 */
	public int getNumOfRetrievedAndRelevantDocuments() {
		return numOfRelevantAndRetrievedDoc;
	}
	
	/**
	 * Calculate the recall value of this model.
	 * 
	 * @return recall value.
	 * @throws ArithmeticException if no relevant document found.
	 */
	public double calculateRecall() throws ArithmeticException {
		/* detect possible errors in the calculation */
		if (numOfRelevantDocuments == 0) {
			throw new ArithmeticException(
					"Cannot calculate the recall as there is no"
					+ " relevant document found.");
		}
				
		return (double)numOfRelevantAndRetrievedDoc / numOfRelevantDocuments;
	}

	/**
	 * Calculate the precision value of this model.
	 * 
	 * @return precision value.
	 * @throws ArithmeticException if no retrieved document found.
	 */
	public double calculatePrecision() throws ArithmeticException {
		/* detect any possible error in the calculation */
		if (numOfRetrievedDocuments == 0) {
			throw new ArithmeticException(
					"Cannot calculate the precision as there is no"
					+ " retrieved document found.");
		}

		return (double)numOfRelevantAndRetrievedDoc / numOfRetrievedDocuments;
	}

	/**
	 * Calculate F-Measure value of this model.
	 * 
	 * @return F-Measure value.
	 * @throws ArithmeticException if no document found.
	 */
	public double calculateFMeasure() throws ArithmeticException{
		double recall = calculateRecall();
		double precision = calculatePrecision();
		
		if (recall == 0 && precision == 0) {
			throw new ArithmeticException(
					"Cannot calculate the F-Measure value as there is no"
					+ "relevant document nor retrieved document found.");
		}
		
		// return the result of the F-Measure formula in the task sheet.
		return (2 * recall * precision) / (recall + precision);
	}
}
