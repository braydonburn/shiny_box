package lib.preprocessing;

import java.util.StringTokenizer;

public class BM25 {
	private BowCollection bowCollection;
	private Stemmer snowballStemmer;
	
	/**
	 * @param collection bag of words collection
	 */
	public BM25(BowCollection collection) {		
		// Stem words to match bow
		snowballStemmer = new Stemmer(collection.isStemmingEnabled());

		bowCollection = collection;
	}
	
	/**
	 * Calculates the BM25 value of the given document and query.
	 * 
	 * @param aDoc BowDocument
	 * @param aQuery String of query
	 * @param avgDocLen integer of the average document length
	 * @param docNo integer of total number of document in a collection
	 * @return double value of given document's BM25 score.
	 */
	public double calculateBM25(BowDocument aDoc, String aQuery, int avgDocLen, int docNo) {
		int totalNoOfDocInCollection = docNo; // total number of documents in given documents collection
		int docFreqOfTerm = 0; // document frequency of term i
		double noOfRelevantDoc = 0; // number of relevant documents which contain term i, assume it is zero
		double totalNoOfRelevanDocInCollection = 0; // total number of relevant documents in given document collection, assume it is zero
		int termFreInDoc = 0; // term i frequency in given document D
		double k1 = 1.2; // parameters given by the equation
		double b = 0.75; // parameters given by the equation
		double k2 = 100; // parameters given by the equation
		double K = 0;
		double termFreqInQuery = 1; // the term i frequency in given query Q
		
		double result = 0;
		
		DocumentFrequency df = new DocumentFrequency();
		df.calculateDF(bowCollection);
		
		// tokenize query and calculate the score of  each single term
		StringTokenizer st = new StringTokenizer(aQuery);
		String querykey;
		while (st.hasMoreTokens()) {
			querykey = snowballStemmer.stemming(st.nextToken().toLowerCase());
			docFreqOfTerm = df.getRelevantDocByTerm(querykey);
			termFreInDoc = aDoc.getTermFreq(querykey);
			double lengthRatio = (double)aDoc.getWordCount()/avgDocLen;
			K = k1*( (1-b)+b*lengthRatio );
			result += calculateScoreForSingleQueryTerm(
					noOfRelevantDoc,
					totalNoOfRelevanDocInCollection,
					docFreqOfTerm,
					totalNoOfDocInCollection,
					termFreInDoc,
					termFreqInQuery,
					K,k1,k2
					);
		}
		return result;
	}
	
	/**
	 * BM25 equation used to calculate the score of one query term. 
	 *
	 * @param r the number of relevant documents
	 * @param R total number of relevant documents in the collection
	 * @param n document frequency of this term
	 * @param N the total number of documents in the collection
	 * @param f the term frequency in this document
	 * @param qf the term frequency in the query
	 * @param K parameter given by equation
	 * @param k1 parameter given by equation
	 * @param k2 parameter given by equation
	 * @return the score of this single query term
	 */
	private double calculateScoreForSingleQueryTerm(
			double r,  
			double R,  
			double n, 
			double N,  
			double f,  
			double qf,
			double K,
			double k1,
			double k2) {
		double result;
		result =
			(
				Math.log(
						
						(
							(r + 0.5) / (R - r + 0.5)
						)
						/
						(
							(n-r+0.5)
							/
							(N-n-R+r+0.5)
						)
					)
					*
					(
						((k1 + 1) * f)
						/
						(K+f)
					)
					*
					(
						((k2+1)*qf)
						/
						(k2+qf)
					)
				);

		return result;
		
	}

}
