import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


public class Basic {
	public static void main(String[] args) throws Exception{
		String data = openFile(args[0]);
		data += "<EOF>";
		ArrayList<String> t = Lexer.lex(data);
		Parser.parse(t);
		
	}
	
	static String openFile(String s) throws Exception{
		File file = new File(s);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}
	
}
