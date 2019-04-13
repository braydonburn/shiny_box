package config;

/**
 * Static values used throughout the program
 */
public class AppConfig {

	public final static String DEFAULT_RESOURCE_DIR = ".//src//resources//";
	public final static String DEFAULT_DATASET_DIR = DEFAULT_RESOURCE_DIR + "RCV1v2//";
	public final static String DEFAULT_STOPWORD_FILE = DEFAULT_RESOURCE_DIR + "stopword//common-english-words.txt";
	public final static int VALID_WORD_LENGTH = 3;
	
	public AppConfig() {
		super();
	}

}
