import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


public class Parser {
	static TreeMap<String, Object> envVar = new TreeMap<String, Object>(); 
	
	
	static void parse(ArrayList<String> t) {
		int i = 0;
		int k = 0;
		boolean cond = false;
		int endifpos = 0; 
		boolean isIf = false;
		
		while(i < t.size()-1){
			if(!cond && isIf){
				isIf = false;
				cond = false;
				i = endifpos;
			}else if(t.get(i).equals("ENDIF")){
				i+= 1;
			}else if((t.get(i) + " " + (t.get(i+1)).substring(0, 3)).equals("PRINT VAR")){
				print(t.get(i+1));
				i += 2; 
			}else if((t.get(i) + " " + (t.get(i+1)).substring(0, 3)).equals("PRINT NUM")){
					print(t.get(i+1));
					i += 2; 
			}else if((t.get(i+1)).length() >= 4 && (t.get(i) + " " + (t.get(i+1)).substring(0, 4)).equals("PRINT EXPR")){
					print(t.get(i+1));
					i += 2; 
			}else if(t.get(i+1).length() >= 6 && (t.get(i) + " " + (t.get(i+1)).substring(0, 6)).equals("PRINT String")){
				print(t.get(i+1));
				i += 2;
			}else  if((t.get(i).length() >= 3 && (t.get(i).substring(0, 3) + " " + t.get(i+1) + " " + t.get(i+2).substring(0, 3)).equals("VAR EQUALS NUM"))){
				assign(t.get(i), t.get(i+2));
				i += 3; 
			}else  if(t.get(i).length() >= 3 && (t.get(i).length() >= 3 && (t.get(i).substring(0, 3) + " " + t.get(i+1) + " " + t.get(i+2).substring(0, 3)).equals("VAR EQUALS VAR"))){
				assign(t.get(i), getVar(t.get(i+2)));
				i += 3; 
			}else  if((t.get(i).length() >= 3 && (t.get(i).substring(0, 3) + " " + t.get(i+1) + " " + t.get(i+2).substring(0, 4)).equals("VAR EQUALS EXPR"))){
				assign(t.get(i), "NUM:"+evalExpr(t.get(i+2).substring(5)));
				i += 3; 
			}else  if(t.get(i).length() >= 3 && ((t.get(i).substring(0, 3) + " " + t.get(i+1) + " " + t.get(i+2).substring(0, 6)).equals("VAR EQUALS String"))){
				assign(t.get(i), t.get(i+2));
				i += 3; 
			}else  if(t.get(i+1).length() >= 6 && (t.get(i) + " " + t.get(i+1).substring(0, 6) + " " + t.get(i+2).substring(0, 3)).equals("INPUT String VAR")){
				input(t.get(i+1).substring(7), t.get(i+2).substring(4));
				i += 3; 
			}else  if(((t.get(i).equals("IF") && t.get(i+4).equals("THEN")))){
				Boolean b = (Boolean) evalExpr(Lexer.op.get(k));
				k++;
				isIf = true;
				if(b){
					cond = true;
				}
				for(int j = i; j < t.size(); j++){
					if(t.get(j).equals("ENDIF")){
						endifpos = j;
					}
				}
				i += 5;
			}
		 }
		 
		 System.out.println(envVar);
		
	}
	
	 
	
	
	
	static void input(String s, String v) {
		System.out.print(s);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String val = scan.next();
		envVar.put(v,"String:\"" + val +"\"");
		
	}





	static void assign(String v, Object o) {
		envVar.put(v.substring(4), o);
	}
	 
	 
	 

	static void print(String s){
					
		if(s.substring(0, 6).equals("String")){
			s = s.substring(8, s.length()-1);
			System.out.println(s);
			s = "";
		}else if(s.subSequence(0, 3).equals("NUM")){
			s = s.substring(4, s.length());
			System.out.println(s);
		}else if(s.subSequence(0, 4).equals("EXPR")){
			double	x =(double) evalExpr(s.substring(5, s.length()));
			System.out.println(x);
		}else if(s.subSequence(0, 3).equals("VAR")){
			s = (String) getVar(s);
			if(s.subSequence(0, 3).equals("NUM")){
				s = s.substring(4, s.length());
				System.out.println(s);
			}else if(s.subSequence(0, 4).equals("EXPR")){
				double x =(double) evalExpr(s.substring(5, s.length()));
				System.out.println(x);
			}else if(s.substring(0, 6).equals("String")){
				s = s.substring(8, s.length()-1);
				System.out.println(s);
				s = "";
			}
					
		}
		
	}

		
		
	static Object evalExpr(String expr) {
		return Evaluator.eval(expr);
	}
		
		
		
	static Object getVar(String var){
		var = var.substring(4);
		if(envVar.containsKey(var)){
			return envVar.get(var);
		}else{
			throw new IllegalArgumentException("VARIABLE ERROR: undefined variable");
		}
	}

	
	
	
}
