public class kuir {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String dir = "src\\data";
		String collection = "bin\\collection.xml";
		String indexxml = "bin\\index.xml";
		String query = "��鿡�� ��, �и������� �ִ�. ������ �������̴�. ���� ������ ��ġ���̴�. ��ġ�򿡴� ���԰�ʹ�.";
		/*String query = "���Ϻ��� �� ���� �ʴ� �ƹ����� ���� �Ŵ�\r\n" + 
				"�ȳ��̶� ���θ� �� ���� ��\r\n" + 
				"���� ���� ��Ҹ� ���� �������� �����\r\n" + 
				"�� ȥ�ڼ� �غ��ϰ� �׷� ��� ������\r\n" + 
				"ȭ�� ���� ������ ���� �� ���Ƽ�\r\n" + 
				"�ֽ� ���� ������ ������ ��\r\n" + 
				"����ó�� �³��� �ƹ� �ϵ� ���� ��ó��\r\n" + 
				"�̺���� �³��� ���� �� �� �ٲ��� ����\r\n" + 
				"�Ϸ�㸸 �ȳ� ������ �� ������ �ž�\r\n" + 
				"�ٽ� �� �� ����� �ϴ� �� �ȳ��� �ƴ� ������ ����\r\n" + 
				"���� ���� ��� �� ���� �� ���Ƽ� �ٸ� ���� �غ��� ������ �귯\r\n" + 
				"����ó�� �³��� �ƹ� �ϵ� ���� ��ó��\r\n" + 
				"�̺���� �³��� ���� �� �� �ٲ��� ����\r\n" + 
				"�Ϸ�㸸 �ȳ� ������ �� ������ �ž�\r\n" + 
				"�ٽ� �� �� ����� �ϴ� �� �ȳ��� �ƴ� ������ ����\r\n" + 
				"������� ���� �Ժη� �ڲ� ������ �� �Ǵ� �ž�\r\n" + 
				"���� �ϰ� �ߴ� ��\r\n" + 
				"�������� �ܿ� �� �ʸ� �� �� ������\r\n" + 
				"����� �� ������\r\n" + 
				"�̾��ϴ� �� �� ���� ���� ���� �ҷ�\r\n" + 
				"�Ϸ�㸸 �ȳ� ������ �� ������ �ž�\r\n" + 
				"�ٽ� �� �� ����� �ϴ� �� �ȳ��� �ƴ� ������ ����\r\n" + 
				"���� ���� �¹��� �׷� ���� ���� �󱼷�\r\n" + 
				"���� �Ⱦ� ���� �׷� �� �� �ٲ��� ����\r\n" + 
				"�ϰ� �����ߴ� ǥ���� �� �������\r\n" + 
				"�ڲ� ���� �󱼷� ������ �귯��\r\n" + 
				"�ʸ� �� �� ���ݾ�";*/
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
