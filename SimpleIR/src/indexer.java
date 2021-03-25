import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	private Document doc;
	private int docSize;
	private HashMap<String,Integer> map1; //�ܾ ��� ������ �ִ��� ��´�
	private HashMap<String,HashMap<String,Double>> map2; //����� ��´�
	private ArrayList<String> docName;
	private ArrayList<HashMap<String,Integer>> arr;
	public indexer() {
		map1 = new HashMap<String,Integer>();
		map2 = new HashMap<String,HashMap<String,Double>>();
		arr = new ArrayList<HashMap<String,Integer>>();
		docName = new ArrayList<String>();
		docSize = 0;
	}
	public void openXml(String fn) throws ParserConfigurationException, SAXException, IOException{
		File file = new File(fn);
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
	}
	public void parseDoc() {
		NodeList nlist = doc.getElementsByTagName("doc");
		docSize = nlist.getLength();
		String bodyParse = "";
		for (int i=0; i<docSize; i++) docName.add(((Node)nlist.item(i)).getAttributes().getNamedItem("id").getTextContent());
		for (int i=0; i<docSize; i++)
		{
			bodyParse= getTagValue("body",(Element)nlist.item(i));
			stringParsing(i,bodyParse); //�� doc�� body���� ���ڿ� �󵵸� map1, arr�� ����
		}
		
		for (int i=0; i<docSize; i++)
		{
			calculateTF_IDF(i);
		}
	//	printmap1();
	//	printmap2();
		
		//for(String key : map1.keySet())
			//System.out.println(key + ":" + map1.get(key));
		//int abc = 3;
		//for(String key : arr.get(abc).keySet())
			//System.out.println(key + ":" + arr.get(abc).get(key));

	}
	private void printmap1() {
		for(String key: map1.keySet())
			System.out.println(key + " " + map1.get(key));
	}
	private void printmap2() {
		for(String key: map2.keySet()) {
			System.out.printf(key + " -> ");
			for(String key2: map2.get(key).keySet())
					System.out.print(key2 + " " + map2.get(key).get(key2) + " ");
			System.out.println("");
		}
	}
	private void calculateTF_IDF(int index) {
		HashMap<String,Integer> hm = arr.get(index);
		for(String key : hm.keySet())
		{
			double tf_idf = hm.get(key) * Math.log(docSize / (double) map1.get(key));
			map2.get(key).replace(docName.get(index), tf_idf);
			if("��".contentEquals(key))
				System.out.println(index + " -> " + tf_idf);
		}
	
		
	}
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	private void stringParsing(int index, String s) {
		s = s.replaceAll("\t", "");
		s = s.replaceAll("\n", ""); //���๮��, Tab ����
		//System.out.println(s);
		String[] sa; // "#"�� �������� ������
		sa = s.split("#");
		HashMap<String,Integer> indexArr = new HashMap<String,Integer>(); //index���� �ܾ �󵵼� ����
		for(int i=0; i<sa.length; i++) {
			String[] data = sa[i].split(":"); // ":"�� �������� ������
			if(data[0].contentEquals("��"))
				System.out.println("��" + index);
			if(map1.containsKey(data[0]))
				map1.replace(data[0], map1.get(data[0]) + 1); //�̹� map1�� �����Ͱ� ������ value �� 1 �����ֱ�
			else {
				map1.put(data[0], 1); //�ƴϸ� map1�� ������ �߰��ϱ�
				HashMap<String,Double> nhm = new HashMap<String,Double>();
				for(int j=0; j<docSize; j++) {
					nhm.put(docName.get(j),(double)0);
				}
				map2.put(data[0],nhm);
			}
			indexArr.put(data[0], Integer.parseInt(data[1]));
		}
		arr.add(index,indexArr);
	}
}
