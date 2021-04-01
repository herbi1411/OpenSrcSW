public class kuir {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dir = "src\\data";
		String collection = "bin\\collection.xml";
		String indexxml = "bin\\index.xml";
		String query = "라면에는 면, 분말스프가 있다. 내일은 수요일이다. 오늘 저녁은 김치찜이다. 김치찜에는 떡먹고싶다.";
		/*String query = "내일부터 못 보면 너는 아무렇지 않을 거니\r\n" + 
				"안녕이란 말로만 참 쉬운 것\r\n" + 
				"매일 너의 목소린 내게 습관보다 무서운데\r\n" + 
				"너 혼자서 준비하고 그런 얘기 하지마\r\n" + 
				"화를 내면 정말로 끝일 것 같아서\r\n" + 
				"애써 나는 웃지만 눈물이 나\r\n" + 
				"어제처럼 굿나잇 아무 일도 없던 것처럼\r\n" + 
				"이별대신 굿나잇 내일 니 맘 바뀔지 몰라\r\n" + 
				"하룻밤만 안녕 내일은 다 괜찮을 거야\r\n" + 
				"다신 안 볼 사람들 하는 그 안녕이 아닌 걸지도 몰라\r\n" + 
				"지금 내가 울면 다 끝날 것 같아서 다른 생각 해봐도 눈물이 흘러\r\n" + 
				"어제처럼 굿나잇 아무 일도 없던 것처럼\r\n" + 
				"이별대신 굿나잇 내일 니 맘 바뀔지 몰라\r\n" + 
				"하룻밤만 안녕 내일은 다 괜찮을 거야\r\n" + 
				"다신 안 볼 사람들 하는 그 안녕이 아닌 걸지도 몰라\r\n" + 
				"헤어지잔 말은 함부로 자꾸 꺼내면 안 되는 거야\r\n" + 
				"전부 니가 했던 말\r\n" + 
				"이제서야 겨우 난 너를 알 것 같은데\r\n" + 
				"사랑인 것 같은데\r\n" + 
				"미안하단 그 말 듣지 않은 얘기로 할래\r\n" + 
				"하룻밤만 안녕 내일은 다 괜찮을 거야\r\n" + 
				"다신 안 볼 사람들 하는 그 안녕이 아닌 걸지도 몰라\r\n" + 
				"하지 말아 굿바이 그런 슬픈 낯선 얼굴로\r\n" + 
				"나를 안아 봐봐 그럼 니 맘 바뀔지 몰라\r\n" + 
				"니가 좋아했던 표정을 나 지어볼래도\r\n" + 
				"자꾸 못난 얼굴로 눈물이 흘러서\r\n" + 
				"너를 볼 수 없잖아";*/
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
			else if(args[0].contentEquals("-??"))
				week5(args[1]);
		}
		else {
		//	week2(dir);
		//	week3(collection);
		//	week4(indexxml);
			week5(query);
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
	public static void week5(String query) throws Exception {
		feature ft = new feature();
		ft.setQuery(query);
		ft.CalcSim();
		ft.printTop3Title();
	}

}
