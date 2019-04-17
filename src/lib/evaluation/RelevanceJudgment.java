package lib.evaluation;

/**
 * RelevanceJudgment stores the relevance judgment record from a file.
 */
public class RelevanceJudgment {
	private String topic;
	private int docId;
	Integer relevanceJudgment;

	/**
	 * @param topic indicates which topic is these data sets.
	 */
	public RelevanceJudgment(String topic) {
		this.topic = topic;
		docId = 0;
		relevanceJudgment = 0;
	}

	/**
	 * @return the string value of topic.
	 */
	public String getTopic() {
		return topic;
	}
	
	/**
	 * @return the integer value of docId.
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId is the document id of this relevance document.
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * @return the integer value of relevanceJudgment.
	 */
	public int getRelevanceJudgment() {
		return relevanceJudgment;
	}

	/**
	 * @param relevanceJudgment is the relevance judgment of this document.
	 * 		  0 = not relevant, 1 = relevant
	 */
	public void setRelevanceJudgment(Integer relevanceJudgment) {
		this.relevanceJudgment = relevanceJudgment;
	}
	
	/**
	 * Interprets the given string and stores the relevant information. 
	 * @param text is the string that stored the relevant information.
	 * @throws NumberFormatException when the token cannot be parsed.
	 * @throws RelevanceJudgmentException when the data provided is not usable.
	 */
	public void setJudgmentByString(String text)
			throws NumberFormatException, RelevanceJudgmentException {
		final String DELIMITER = " ";
		String[] recordTokens;
		String topic;
		int docId,
		    relevance;
		
		// splits the given text into tokens, and stores the information into class
		// attributes.
		recordTokens = text.split(DELIMITER);
		topic = recordTokens[0];
		docId = Integer.parseInt(recordTokens[1]);
		if (recordTokens[2].matches("[01]")) {
			relevance = Integer.parseInt(recordTokens[2]);
		} else {
			throw new RelevanceJudgmentException("Relevance Judgment not recognized.");
		}
		
		// validate the topic before storing the data.
		if (topic.equals(this.topic)) {
			setDocId(docId);
			setRelevanceJudgment(relevance);
		} else {
			throw new RelevanceJudgmentException("Topic not match.");
		}
	}
	
	/**
	 * A predicate to return true/false value of the relevanceJudgment.
	 * @return true if the value of relevanceJudgment = 1,
	 * 		   false if the value is not equal to 1, i.e. 0
	 */
	public boolean isRelevant() {
		return relevanceJudgment == 1;
	}
	
	/**
	 * @return true if the value of relevanceJudgment = 1,
	 * 		   false if the value is not equal to 1, i.e. 0
	 */
	public boolean isRetrieved() {
		return isRelevant();
	}
}
