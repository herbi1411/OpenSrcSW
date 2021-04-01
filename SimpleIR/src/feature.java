import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.nodes.Document;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class feature {
	private String query;
	private ArrayList<String> title;
	private ArrayList<String> id;
	private ArrayList<Double> score;
	private String collectionFileName;
	private String postFileName;
	private HashMap<String,ArrayList> indexPost;
	private KeywordList kl;
	
	public feature() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		query = "";
		title = new ArrayList<String>();
		score = new ArrayList<Double>();
		id = new ArrayList<String>();
		collectionFileName = "./bin/collection.xml";
		postFileName = "./bin/index.post";
		
		getTitle();
		getIndexPost();
	};
	public feature(String n) throws ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
		this();
		setQuery(n);
	}
	public void setQuery(String n){
		this.query = n;
		morphemeAnalyze();
	}
	private void getTitle() throws ParserConfigurationException, SAXException, IOException {
		File file = new File(collectionFileName);
		org.w3c.dom.Document doc;
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nlist = doc.getElementsByTagName("doc");
		for (int i=0; i<nlist.getLength(); i++)
		{
			id.add(((Node)nlist.item(i)).getAttributes().getNamedItem("id").getTextContent()); //doc id 속성값 가져오기
			title.add(getTagValue("title",(Element)nlist.item(i)));
			score.add(0d);
		}
	}
	private void getIndexPost() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileInputStream fileStream = new FileInputStream(postFileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		Object object = objectInputStream.readObject();
		
		indexPost = (HashMap)object;
		Iterator<String> it = indexPost.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			ArrayList value = (ArrayList) indexPost.get(key);
			System.out.println(key + " -> " + value);
		}
	}
	private String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	private void morphemeAnalyze() {
		String output ="";
		KeywordExtractor ke = new KeywordExtractor();
		kl = ke.extractKeyword(query, true);
		for (int i=0; i<kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			output += kwrd.getString() + ":" + kwrd.getCnt() + "#";
		}
		System.out.println(output);
	}
	public void CalcSim() {
		for(int i=0; i<id.size(); i++) {
			String sid = id.get(i);
			for(int j=0; j<kl.size(); j++) {
				Keyword kwrd = kl.get(j);
				String str = kwrd.getString();
				//int val = kwrd.getCnt();
				if(indexPost.containsKey(str)) {
					if(indexPost.get(str).contains(sid)) {
						  // score.set(i,score.get(i) + (double) val * (double)indexPost.get(str).get(indexPost.get(str).indexOf(sid)+1));
						   score.set(i,score.get(i) + (double)indexPost.get(str).get(indexPost.get(str).indexOf(sid)+1));
						 //  System.out.println("kwrdString :" + str + " kwrdVal :" + val + " idNum: " + sid + "idKwrdVal: " + (double)indexPost.get(str).get(indexPost.get(str).indexOf(sid)+1));
					}
				}
			}
		}
		System.out.println(score);
	}
	public void printTop3Title() {
		ArrayList<Integer> rank = new ArrayList<Integer>();
		ArrayList<String> output = new ArrayList<String>();
		for(int i=0; i<id.size(); i++) {rank.add(1);}
		
		for(int i=0; i<rank.size(); i++)
			for(int j=0; j<rank.size(); j++) {
				if(score.get(i) < score.get(j))
					rank.set(i, rank.get(i)+1);
			}
		
		for(int i=1; i<=3; i++) {
			for(int j=0; j<rank.size(); j++) {
				if(rank.get(j) == i) {
					output.add(title.get(j));
				}
			}
		}
		System.out.println(rank);
		System.out.println(output);
		
		System.out.println(output.get(0) + ", " + output.get(1) + ", " + output.get(2));
	}
}
