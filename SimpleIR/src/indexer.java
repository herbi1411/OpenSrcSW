import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
	private HashMap<String,HashMap<String,Double>> map2; //����� ��´� //���� ������ ��� ����!!
	private HashMap<String,ArrayList> result; //�������´� //�ܾ �������� ���� id�� ���� ����!
	private ArrayList<String> docName;
	private ArrayList<HashMap<String,Integer>> arr;
	public indexer() {
		map1 = new HashMap<String,Integer>();
		map2 = new HashMap<String,HashMap<String,Double>>();
		arr = new ArrayList<HashMap<String,Integer>>();
		docName = new ArrayList<String>();
		result = new HashMap<String,ArrayList>();
		docSize = 0;
	}
	public void openXml(String fn) throws ParserConfigurationException, SAXException, IOException{
		File file = new File(fn);
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
	}
	public void parseDoc() throws ClassNotFoundException, IOException {
		NodeList nlist = doc.getElementsByTagName("doc");
		docSize = nlist.getLength();
		String bodyParse = "";
		for (int i=0; i<docSize; i++) docName.add(((Node)nlist.item(i)).getAttributes().getNamedItem("id").getTextContent()); //doc id �Ӽ��� ��������
		for (int i=0; i<docSize; i++)
		{
			bodyParse= getTagValue("body",(Element)nlist.item(i));
			stringParsing(i,bodyParse); //�� doc�� body���� ���ڿ� �󵵸� map1, arr�� ����
		}
		
		for (int i=0; i<docSize; i++)
		{
			calculateTF_IDF(i); //����ġ ���
		}
		
		
	//	printmap1();
	//	printmap2();
		map2_To_result(); //HashMap<String,HashMap<String,Double>> -> HashMap<String,ArrayList> , �󵵼� 0�� �� ����
	//	printResult();
	}
	
	private void map2_To_result() {
		for(String key: map2.keySet()) {
			ArrayList marr = new ArrayList();
			HashMap<String,Double> map2data = map2.get(key);
			for(String key2: map2data.keySet()) {
				if(map2data.get(key2) != 0) {
					marr.add(key2);
					marr.add(map2data.get(key2));
				}
			}
			result.put(key,marr);
		}
	}
	private void printResult() {
		for(String key : result.keySet()) {
			System.out.print(key + " -> ");
			System.out.println(result.get(key));
		}
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
			map2.get(key).replace(docName.get(index), (double) Math.round(tf_idf*100)/100);
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
		String[] sa; // "#"�� �������� ������
		sa = s.split("#");
		HashMap<String,Integer> indexArr = new HashMap<String,Integer>(); //index���� �ܾ �󵵼� ����
		for(int i=0; i<sa.length; i++) {
			String[] data = sa[i].split(":"); // ":"�� �������� ������
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
	public void fileOutPut() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileOutputStream fileStream = new FileOutputStream("index.post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		objectOutputStream.writeObject(result);
		
		//file ����� ��� �ƴ��� �ٽ� �����ͼ� Ȯ��
		
		FileInputStream fileStream2 = new FileInputStream("index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream2);
		Object object = objectInputStream.readObject();
		
		HashMap hashMap = (HashMap)object;
		Iterator<String> it = hashMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			ArrayList value = (ArrayList) hashMap.get(key);
			System.out.println(key + " -> " + value);
		}
	}
	public HashMap<String,HashMap<String,Double>> getMap2() {return map2;}
	public HashMap<String,ArrayList> getResult(){return result;} 
}
