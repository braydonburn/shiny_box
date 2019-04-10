package lib.preprocessing;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Used to read a target XML file and return the content in XMLStructure.
 */
public class XmlReader {
	private static final String FILENAME_SUFFIX = "newsML.xml";

	private String datasetDirectory;
	private String fileNo;
	private XmlStructure xml;
	
	/**
	 * @param dir the directory path of the data source.
	 */
	public XmlReader(String dir) {
		datasetDirectory = dir;
		fileNo = "";
		xml = new XmlStructure();
	}
	
	/**
	 * @param fileNo to indicate the file id of the XML file
	 */
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	/**
	 * Reads the XML file and stores the relevant information into XmlStructure
	 * @return XmlStruture object containing information of the XML file.
	 */
	public XmlStructure ReadXml() {
		XmlHandler handler = new XmlHandler(fileNo + FILENAME_SUFFIX);
		xml = handler.getXmlStructure();
		
		try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();         
            saxParser.parse(datasetDirectory + xml.getFileName(), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return xml;
	}

}
