import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class gensnippet {
	private ArrayList<Integer> arr;
	public gensnippet() {
		arr = new ArrayList<Integer>();
	}
	public void analyze(String fn, String st) throws FileNotFoundException {
		FileInputStream fi = new FileInputStream(new File(fn));;
		//���� �о���� ���� �𸣰ڽ��ϴ�.
		//  for (fi.hasNextLine()) �о�� ������ �ִٸ�
		String[] sp = st.split(" ");
		String s;
		for(int i = 0; i<sp.length; i++) {
			s = sp[i];
		}
	}
}
