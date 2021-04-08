import java.util.ArrayList;

public class kuir {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dir = "src\\data";
		String collection = "bin\\collection.xml";
		String indexxml = "bin\\index.xml";
		//String query = "라면에는 면, 분말스프가 있다.";
		String query = "\r\n" + 
				"\r\n" + 
				"오늘 아침에도 내가 뭘 했는지 몰라 아니 내게 아침이란 게 있나\r\n" + 
				"\r\n" + 
				"한 아마 12시쯤에 인났었지\r\n" + 
				"\r\n" + 
				"달력을 보니 오늘은 고백데이래 창밖에 남자 여자에게 고백해\r\n" + 
				"\r\n" + 
				"난 친구에게 내 잘못을 고백해\r\n" + 
				"\r\n" + 
				"십이시간을 넘게 자도 일어나보면 졸려 매일 똑같은 하루 이런 날 보면 질려\r\n" + 
				"\r\n" + 
				"걷는 게 귀찮아서 배로 누운 그대로 여기저기 닦다보니 안 해도 돼 걸레로\r\n" + 
				"\r\n" + 
				"청소말이야 계란말이 하나 밥상에 올라가도 이게 웬 떡이야\r\n" + 
				"\r\n" + 
				"그림의 떡이야\r\n" + 
				"\r\n" + 
				"날마다 찬장을 열어보면 어제 먹고 남은 반 쪼가리\r\n" + 
				"\r\n" + 
				"라면인건가 라면인건가 라면인건가\r\n" + 
				"\r\n" + 
				"오늘도 내 점심은\r\n" + 
				"\r\n" + 
				"라면인건가 라면인건가 라면인건가\r\n" + 
				"\r\n" + 
				"오늘 아침에도 내가 뭘 했는지를 몰라 아니 내게 아침이란 게 있나\r\n" + 
				"\r\n" + 
				"한 아마 12시쯤에 인나\r\n" + 
				"\r\n" + 
				"커튼 사이로 해가 빛나면 나도 신나서\r\n" + 
				"\r\n" + 
				"양치도 안하고 놀다가 밤이 되서야 후회를 하지\r\n" + 
				"\r\n" + 
				"사실 내 맘은 이렇지 않은데 하고 싶은 거 많고\r\n" + 
				"\r\n" + 
				"그 곳에 몸을 담고 의미 있는 일분을 살고 싶어도 시간은 가는데\r\n" + 
				"\r\n" + 
				"하루 종일 티비가 켜져 있어 그 속엔 웃음이 가득하지만\r\n" + 
				"\r\n" + 
				"티비에 비추는 내 모습은 점점 비만이 돼 가\r\n" + 
				"\r\n" + 
				"나의 미래가 being like 띵띵 불어버린\r\n" + 
				"\r\n" + 
				"라면인건가 라면인건가 라면인건가\r\n" + 
				"\r\n" + 
				"라면인건가 라면인건가 라면인건가\r\n" + 
				"\r\n" + 
				"오늘도 내 점심은\r\n" + 
				"\r\n" + 
				"라면인건가 나만이런가 (꿈이)라면일어나\r\n" + 
				"\r\n" + 
				"Let's Go!\r\n";
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
		ft.printTop3Title(score);
	}

}
