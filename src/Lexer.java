import java.util.ArrayList;


public class Lexer {
	static ArrayList<String> envir = new ArrayList<String>();
	public static ArrayList<String> op = new ArrayList<String>();
	static ArrayList<String> lex(String data){
	
		String token = "";
		String expr = "";
		String var = "";
		String oper = "";
		boolean isExpr = false;
		boolean isStr = false;
		boolean isVar = false;
		boolean isOp = false;
		String str = "";
		char[] fileContent = data.toCharArray();
		for(char c: fileContent){
			token += c;
			
			
			if(isOp && token.equals("t")){
				op.add(oper);
				oper = "";
				isOp = false;
			}
			if(isOp){
				oper += token;
			}
			
			
			if(token.equals(" ")){
				if(isStr){
					str += token;
					token = "";
				}else if(isVar){
					envir.add("VAR:" + var);
					isVar = false;
					var = "";
					token = "";
				}else {
					token = "";
				}
			}else if(token.equals("\r\n") || token.equals("<EOF>")){
				if(!expr.equals("") && isExpr){
					envir.add("EXPR:"+expr);
					expr = "";
				}else if(!expr.equals("") && !isExpr){
					envir.add("NUM:"+expr);
					expr = "";
				}else if(!var.equals("")){
					envir.add("VAR:" + var);
					isVar = false;
					var = "";
				}
				token = "";
			}else if(token.equals("=") && !isStr){
				if(!expr.equals("") && !isExpr){
					envir.add("NUM:"+expr);
					expr = "";
				}
				if(!var.equals("")){
					envir.add("VAR:" + var);
					token = "";
					isVar = false;
				}
				if(envir.get(envir.size()-1).equals("EQUALS")){
					envir.remove(envir.size()-1);
					envir.add("EQEQ");
					var = "";
					token = "";
				}else{
					envir.add("EQUALS");
				}
				token = "";
			}else if(token.equals("!") && !isStr){
				token = "";
			}else if(token.equals(">") && !isStr){
				token = "";
			}else if(token.equals("<") && !isStr){
				token = "";
			}else if(token.equals("$")&& !isStr){
				isVar = true;
				var += token;
				token = "";
			}else if(isVar && token.equals("\r")){
				
			}else if(isVar){
				if(token.equals("<") || token.equals(">")){
					if(!token.equals("")){
						envir.add("VAR:"+var);
						var = "";
						isVar = false;
					}
				}
				var += token;
				token = "";
			}else if(token.equals("PRINT") || token.equals("print")) {
				envir.add("PRINT");
				token = "";
			}else if(token.equals("INPUT") || token.equals("input")) {
				envir.add("INPUT");
				token = "";
			}else if(token.equals("IF") || token.equals("if")) {
				envir.add("IF");
				isOp = true;
				token = "";
			}else if(token.equals("ENDIF") || token.equals("endif")) {
				envir.add("ENDIF");
				token = "";
			}else if(token.equals("THEN") || token.equals("then")) {
				if(!expr.equals("") && !isExpr){
					envir.add("NUM:"+expr);
					expr = "";
				}
				envir.add("THEN");
				token = "";
			}else if(token.equals("0") || token.equals("1") || token.equals("2") || token.equals("3") || token.equals("4") || token.equals("5") || token.equals("6") || token.equals("7") || token.equals("8") || token.equals("9") ){
				expr += token; 
				token = "";
			}else if(token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*") || token.equals("(") || token.equals(")") || token.equals("%")){
				expr += token;
				isExpr = true;
				token = "";
			}else if(token.equals("\"")){
				if(isStr){
					envir.add("String:" + str + "\"");
					str = "";
					isStr = false;
					token ="";
				}else{
					isStr = true;
				}
			}else if(token.equals("\t")){
				token = "";
			}else if(isStr){
				str += token;
				token = "";
			}
			
		}
		
		
		
//		System.out.println(expr);
		System.out.println(envir);
		return envir;
	}
	
	
	
}
