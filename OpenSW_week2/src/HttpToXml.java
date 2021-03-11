import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

public class HttpToXml {
	private Document doc;
	private Elements contents;
	private org.w3c.dom.Document xml;
	private Element docs;
	public void makeXML() throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		xml = docBuilder.newDocument();
		docs = xml.createElement("docs");
	}
	public void set_doc(int num, String tt, String bd) {
		Element doc_id = xml.createElement("doc");
		Element title = xml.createElement("title");
		Element body = xml.createElement("body");
		
		doc_id.setAttribute("id", Integer.toString(num));
		
		title.appendChild(xml.createTextNode(tt));
		doc_id.appendChild(title);
		
		body.appendChild(xml.createTextNode(bd));
		doc_id.appendChild(body);
		
		docs.appendChild(doc_id);
	}
	public void XMLWrite() throws Exception {
		xml.appendChild(docs);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(new FileOutputStream(new File("src/collection.xml")));
		transformer.transform(source,result);
	}
	public void getHTML() throws IOException{
		
		int num = 0;
		String[] st = {"떡","라면","아이스크림","초밥","파스타"};
		for(String s : st) {
		File file = new File("./src/" + s + ".html");	
        doc = Jsoup.parse(file, "UTF-8");
        HTMLParsing(num++);
		}
	}
	public void HTMLParsing(int num) {
		String tt;
		String bd = "";
		doc.outputSettings().prettyPrint(false);
		contents = doc.select("title");
		tt = contents.text();
		contents = doc.select("p");
		for(int i=0; i<contents.size()-1;i++) {
			bd += contents.get(i).text()+"\n\t\t";
		}
		bd+=contents.get(contents.size()-1).text();
		set_doc(num,tt,bd);
	}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpToXml a = new HttpToXml();
		a.makeXML();
		a.getHTML();
		a.XMLWrite();
	}

}
