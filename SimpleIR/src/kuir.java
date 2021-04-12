import java.util.ArrayList;

public class kuir {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dir = "src\\data";
		String collection = "bin\\collection.xml";
		String indexxml = "bin\\index.xml";
		String query = "라면에는 면, 분말 스프가 있다.";
		String indexPost = "bin\\index.post";
		if(args.length >0)
		{
			//week2////////////////////////////////////
			if(args[0].equals("-c"))
				week2(args[1]);
			
			//week3/////////////////////////////////////
			else if(args[0].equals("-k"))
				week3(args[1]);
			//week4////////////////////////////////////
			else if(args[0].contentEquals("-i"))
				week4(args[1]);
			//week5///////////////////////////////////
			else if(args[0].contentEquals("-s") && args[2].contentEquals("-q"))
				week5(args[1],args[3]);
			/*
			 * Collection.xml은 class파일과 같은 경로에 있다고 가정했습니다!
			 * */
		}
		else {
		//	week2(dir);
		//	week3(collection);
		//	week4(indexxml);
			week5(indexPost,query);
		}
	}
	public static void week2(String dir) throws Exception{
		makeCollection a = new makeCollection();
		a.makeXML();
		a.getHTML(dir);
		a.XMLWrite();
	}
	public static void week3(String collection)throws Exception  {
		makeKeyword ma = new makeKeyword();
		ma.openXml(collection);
		ma.parseDoc();
		ma.set_doc();
		ma.xmlWrite();
	}
	public static void week4(String indexxml) throws Exception {
		indexer mp = new indexer();
		mp.openXml(indexxml);
		mp.parseDoc();
		mp.fileOutPut();
	}
	public static void week5(String indexPost,String query) throws Exception{
		searcher ft = new searcher();
		ArrayList<Double> score;
		score = ft.CalcSim(query,indexPost);
	//	System.out.println("Query: " + query);
		ft.printTop3Title(score);
	}

}
