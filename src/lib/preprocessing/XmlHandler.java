package lib.preprocessing;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Extends DefaultHandler to decode the XML file into self-defined
 * XmlStructure. 
 */
public class XmlHandler extends DefaultHandler {
	// Indicates if search text is inside <text> tag
	boolean inTextTag; 
    
    String textContent;
	private final XmlStructure xml;

	/**
	 * @param filename indicates which file to be handled.
	 */
	public XmlHandler(String filename) {
		xml = new XmlStructure();
		xml.setFileName(filename);
		inTextTag = false;
		textContent = "";
	}
	
    @Override
    /**
     * Runs while the handler found a XML opening tag.
     * If tag is newsitem, then set the item id of this XML
     *    structure to the value of 'itemid' in this tag; or
     * If tag is text, then set inTextTag to true.
     */
    public void startElement(String uri, String localName,String qName,
    						 Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("newsitem")) {
            xml.setItemId(Integer.parseInt(attributes.getValue("itemid")));
        }

        if (qName.equalsIgnoreCase("text")) {
        	// Search is inside <text> tag so set bool to true
        	inTextTag = true;
        }                    
    }

    @Override
    /**
     * Runs while the handler found a XML closing tag. If found, then the inTextTag
     * status is set to false and the content read so far will be stored inside the
     * XML content.
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("text")) {
        	inTextTag = false;
            xml.setContent(textContent);
        }    
    }

    @Override
    /**
     * Runs while the handler found the content between the XML opening & closing
     * tags. Appends the context found to the textContent variable.
     */
    public void characters(char ch[], int start, int length) throws SAXException {
        if (inTextTag) {
        	textContent = textContent.concat(new String(ch, start, length));
        }

    }
    
    /**
     * @return XmlStructure that stored the context of the corresponding file.
     */
    public XmlStructure getXmlStructure() {
    	return xml;
    }
}
