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

public class searcher {
	private ArrayList<String> title;
	private ArrayList<String> id;
	private String collectionFileName;
	
	public searcher() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		title = new ArrayList<String>();
		id = new ArrayList<String>();
		collectionFileName = "./collection.xml"; //collection.xml�� ���� ���ϰ� ���� ������ �ִٰ� ����
		getTitle();
	};
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
			id.add(((Node)nlist.item(i)).getAttributes().getNamedItem("id").getTextContent()); //doc id �Ӽ��� ��������
			title.add(getTagValue("title",(Element)nlist.item(i)));
		}
	}

	private String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	public ArrayList<Double> InnerProduct(String query, String indexPostFileName) throws ClassNotFoundException, IOException {
		HashMap<String,ArrayList> indexPost = getIndexPost(indexPostFileName);
		KeywordList kl = morphemeAnalyze(query);
		ArrayList<Double> score = new ArrayList<Double>();
		for(int i=0; i<id.size();i++) score.add(0d);
		for(int i=0; i<id.size(); i++) {
			String sid = id.get(i);
			for(int j=0; j<kl.size(); j++) {
				Keyword kwrd = kl.get(j);
				String str = kwrd.getString();
				if(indexPost.containsKey(str)) {
					if(indexPost.get(str).contains(sid)) {
						   score.set(i,score.get(i) + (double)indexPost.get(str).get(indexPost.get(str).indexOf(sid)+1));
					}
				}
			}
		}
		for(int i=0; i<score.size(); i++) score.set(i, Math.round(score.get(i)*100)/100.0);
		return score;
	}
	public ArrayList<Double> CalcSim(String query, String indexPostFileName) throws ClassNotFoundException, IOException {
		HashMap<String,ArrayList> indexPost = getIndexPost(indexPostFileName);
		KeywordList kl = morphemeAnalyze(query);
		ArrayList<Double> score1 = InnerProduct(query, indexPostFileName);
		ArrayList<Double> score2 = new ArrayList<Double>();
		
		double sqrt_a = 0.0d;
		for(int i=0; i<kl.size(); i++) {
			sqrt_a += Math.pow((double)kl.get(i).getCnt(),2);
		}
		sqrt_a = Math.sqrt(sqrt_a);
		
		for(int i=0; i<id.size();i++) score2.add(0d);
		for(int i=0; i<id.size(); i++) {
			String sid = id.get(i);
			double b = 0d;
			for(int j=0; j<kl.size(); j++) {
				Keyword kwrd = kl.get(j);
				String str = kwrd.getString();
				if(indexPost.containsKey(str)) {
					if(indexPost.get(str).contains(sid)) {
						b += Math.pow((double)indexPost.get(str).get(indexPost.get(str).indexOf(sid)+1), 2);
					}
				}
			}
			score2.set(i, score1.get(i) / (sqrt_a * Math.sqrt(b)));
			score2.set(i, Math.round(score2.get(i)*100)/100.0);
		}
//		System.out.println(score2);
		return score2;
	}
	
	private HashMap<String,ArrayList> getIndexPost(String indexPostFileName) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		HashMap<String,ArrayList> indexPost;
		FileInputStream fileStream = new FileInputStream(indexPostFileName);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		Object object = objectInputStream.readObject();
		
		indexPost = (HashMap)object;
		objectInputStream.close();
		return indexPost;
	}
	private KeywordList morphemeAnalyze(String query) {
		KeywordList kl;
		String output ="";
		KeywordExtractor ke = new KeywordExtractor();
		kl = ke.extractKeyword(query, true);
		for (int i=0; i<kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			output += kwrd.getString() + ":" + kwrd.getCnt() + "# ";
		}
		//System.out.println(output);
		return kl;
	}
	public void printTop3Title(ArrayList<Double> score) {
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
		//System.out.println("Title: " + title);
		//System.out.println("Score:" + score);
		//System.out.println("Rank: " + rank);
		//System.out.println("output: " + output);
		System.out.println(output.get(0) + ", " + output.get(1) + ", " + output.get(2));
	}
}
