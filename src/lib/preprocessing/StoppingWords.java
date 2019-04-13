package lib.preprocessing;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to define a list of stopping words from given file.
 */
public class StoppingWords {
	private String stopwordfile;
	private boolean enabled;
	private ArrayList<String> stopwords;
	private int validLength;
	
	/**
	 * @param file the stopping words file.
	 */
	public StoppingWords(String file) {		
		stopwordfile = file;
		stopwords = new ArrayList<String>();
		enabled = false;
		validLength = 0;
		loadStopWord();
	}
	

	public void enable() {
		this.enabled = true;
	}
	 
	public void disable() {
		this.enabled = false;
	}
	
	/**
	 * Set the valid length of a word
	 * @param length number to represent the length of a valid word.
	 */
	public void setValidLength(int length) {
		this.validLength = length;
	}
	
	/**
	 * Loads in stopping words file.
	 */
	private void loadStopWord() {
		Scanner in;
		try {
			in = new Scanner(new FileReader(stopwordfile));
	        in = in.useDelimiter(",");
	        while (in.hasNext()) {
	        	stopwords.add(in.next());
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Checks if word is in stopping words file.
	 * @param thisWord the word to be checked.
	 * @return true if it is a stop word and is not in valid word length;
	 * 		   otherwise, return false.
	 */
	public boolean isStopWord(String thisWord) {
		return (
					(
						this.enabled &&
						stopwords.contains(thisWord)
					)
					||
					(
						thisWord.length() < validLength
					)
				);
	}

}
