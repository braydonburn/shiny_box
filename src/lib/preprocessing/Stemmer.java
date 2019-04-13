package lib.preprocessing;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * Interfaces the snowball stemming library used for stemming functions.
 */
public class Stemmer extends englishStemmer {
	
	private SnowballStemmer stemmer;
	private boolean stemmingEnabled;

	/**
	 * @param stemmingEnabled indicates if stemming is used.
	 */
	public Stemmer(boolean stemmingEnabled) {
		super();
		this.stemmer = new englishStemmer();
		this.stemmingEnabled = stemmingEnabled;
	}
	
	/**
	 * Use the snowball algorithm to stem the given word.
	 * 
	 * @param string to be stemmed
	 * @return string which has been stemmed
	 */
	public String stemming(String word) {
		if (stemmingEnabled) {
			stemmer.setCurrent(word);
			stemmer.stem();
			return stemmer.getCurrent();
		}
		return word;
	}

}
