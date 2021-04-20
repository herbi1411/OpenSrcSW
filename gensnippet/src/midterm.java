import java.io.FileNotFoundException;

public class midterm {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if(args[0].contentEquals("-f")) {
			String fn = args[1];
			String st = args[3];
			gensnippet gsn = new gensnippet();
			gsn.analyze(fn, st);
		}
		
	
	}

}
