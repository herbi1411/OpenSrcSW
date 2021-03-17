public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//week2////////////////////////////////////
		/*HtmlToXml a = new HtmlToXml();
		a.makeXML();
		a.getHTML();
		a.XMLWrite();*/
		//week3/////////////////////////////////////
		MorphemeAnalyzer ma = new MorphemeAnalyzer();
		String fn = "collection.xml";
		ma.openXml(fn);
		ma.parseDoc();
		ma.set_doc();
		ma.xmlWrite();
	}

}
