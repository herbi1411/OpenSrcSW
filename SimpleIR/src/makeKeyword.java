import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class makeKeyword {
	private org.w3c.dom.Document newDoc;
	private org.w3c.dom.Document doc;
	private ArrayList<String> titleList;
	private ArrayList<String> bodyList;
	private Element docs; //new Xml doc
	public makeKeyword() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		newDoc = docBuilder.newDocument();
		docs = newDoc.createElement("docs");
		
		titleList = new ArrayList<String>();
		bodyList = new ArrayList<String>();
	}
	public void openXml(String fn) throws IOException, ParserConfigurationException, SAXException {
		File file = new File(fn);
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
	}
	
	public void parseDoc() {
		NodeList nlist = doc.getElementsByTagName("doc");
		for (int i=0; i<nlist.getLength(); i++)
		{
			String titleParse = getTagValue("title",(Element)nlist.item(i));
			String bodyParse = getTagValue("body",(Element)nlist.item(i));
			String output = morphemeAnalyze(bodyParse);
			this.titleList.add(titleParse);
			this.bodyList.add(output);
		}
	}
	private String morphemeAnalyze(String n) {
		String output ="";
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(n, true);
		for (int i=0; i<kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			output += kwrd.getString() + ":" + kwrd.getCnt() + "#";
		}
		output = output.substring(0,output.length()-1); // 마지막 #제거
		int size = output.length();
		int qutient = size / 60;
		if(size % 60 == 0) qutient -=1;
		String rs = "";
		int st = 0;
		int end = 60;
		for(int i=0; i<qutient; i++){
			rs+=output.substring(st, end) + "\n\t\t";
			st = end;
			end += 60;
		}
		rs += output.substring(st,size);
		output = rs;
		return output;
	}
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	public void set_doc() {
		for(int i=0; i<this.titleList.size(); i++) {
			Element doc_id = newDoc.createElement("doc");
			Element title = newDoc.createElement("title");
			Element body = newDoc.createElement("body");
			
			doc_id.setAttribute("id", Integer.toString(i));
			
			title.appendChild(newDoc.createTextNode(this.titleList.get(i)));
			doc_id.appendChild(title);
			
			body.appendChild(newDoc.createTextNode(this.bodyList.get(i)));
			doc_id.appendChild(body);
			
			docs.appendChild(doc_id);
		}
	}
	public void xmlWrite() throws Exception {
		newDoc.appendChild(docs);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(newDoc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("index.xml")));
		transformer.transform(source,result);
	}

}
