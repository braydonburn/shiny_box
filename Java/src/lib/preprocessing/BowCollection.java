package lib.preprocessing;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import config.AppConfig;

/**
 * Manages the bag of words collection.
 */
public class BowCollection extends LinkedHashMap<Integer,BowDocument> {
	private static final long serialVersionUID = 1L;

	private int totalDocLength;

	/**
	 * Loads all documents in the given path then retrieves doc info for later use.
	 * @param dir is the folder where the data set is being stored.
	 */
	public BowCollection(String dir) {
		super();
		totalDocLength = 0;
		loadCollectionByFolderPath(dir);
		parseCollection();
	}
	

	/**
	 * Loads the files that stored the related documents into the bag of words collection. 
	 * @param dir is the directory where the data set is stored.
	 */
	private void loadCollectionByFolderPath(String dir) {
		File folder = new File(dir);
		File[] datalist = folder.listFiles();
		
		/* Search for all files that contain an ID and treat it as
		 * a data set.
		 */
		for (File file: datalist) {
			if (file.isFile()) {
				String fileid = file.getName().replaceAll("\\D", "");
				if (fileid.length() > 0) {
					int fileno = Integer.parseInt(fileid);
					this.put(fileno, new BowDocument(Integer.toString(fileno)));
				}
			}
		}
	}
	
	/**
	 * Reads all files in given data set then stores the related information into
	 * the BowDocument. 
	 */
	public void parseCollection() {
		XmlReader fileReader;
		XmlStructure fileStructure;
		
		for(BowDocument thisDoc : this.values()) {
			fileReader = new XmlReader(AppConfig.DEFAULT_DATASET_DIR);
			fileReader.setFileNo(thisDoc.getFileNo());
			fileStructure = fileReader.ReadXml();
			thisDoc.setitemId(fileStructure.getItemId());
			String content = fileStructure.getContent();
			// Remove words that contain non word characters
			content = content.replaceAll("[\\W]", " "); 
			
			StringTokenizer st = new StringTokenizer(content);
	        while (st.hasMoreTokens()) {
	            String scanner = st.nextToken();
	            if (scanner != null) {
	            	scanner = scanner.toLowerCase();
		            if (scanner.length() > 0 && scanner.matches("^[a-zA-Z]*$")) {
		            	thisDoc.addWordCount();
		            }
	            }
	        }
	        accumulateDocLength(thisDoc.getWordCount());
		}
	}
	
	/**
	 * Adds the given value to the existing document length.
	 * @param addupValue number of words to add to length.
	 */
	private void accumulateDocLength(int increaseValueInt) {
		totalDocLength += increaseValueInt;
	}
	
	/**
	 * Calculates the average document length. 
	 * @return the rounded up average document length.
	 */
	public int getAverageDocLength() {
		return totalDocLength / this.size();
	}
}
