package lib.preprocessing;

/**
 * Data structure to store relevant information for XML file.
 */
public class XmlStructure {
	private String fileName;
	private int itemId;
	private String content;
	
	public XmlStructure() {
		fileName = "";
		itemId = 0;
		content = "";
	}
	
	
	// Getter/setter
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemNo) {
		this.itemId = itemNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
