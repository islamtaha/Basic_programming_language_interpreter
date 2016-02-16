
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
 
  public class Evaluator {
	  static Object eval(String expr){
		  String s = "";
		  int l = 0;
		  String varname = "";
		  boolean isVar = false;
		  int i = 0;
		  for(; i < expr.length(); i++){
			  if(expr.charAt(i) == '$'){
				  isVar = true;
			  }
			  if(isVar && expr.charAt(i) != ' '){
				  varname += expr.charAt(i);
			  }else if(isVar && expr.charAt(i) == ' '){
				  if( ((String)Parser.getVar("VAR:"+varname)).substring(0, 3).equals("NUM")){
					  s += ((String)Parser.getVar("VAR:"+varname)).substring(4); 
				  }else if(((String)Parser.getVar("VAR:"+varname)).substring(0, 4).equals("EXPR")){
					  s += ((String)Parser.getVar("VAR:"+varname)).substring(5);
				  }
				  l = i;
				  isVar = false;
			  }else if(expr.charAt(i) == ' '){
				  s += expr.substring(l, i);
				  l = i;
			  }
		  }
		  s += expr.substring(l, i);
		  expr = s;
		  ScriptEngineManager manager = new ScriptEngineManager();
		  ScriptEngine engine = manager.getEngineByName("JavaScript");
		  Object res = 0;
		try {
			Object t = engine.eval(expr);
			if(t instanceof Integer){
				res = (Integer) t;
			}else if(t instanceof Double){
				res = (Double) t;
			}else if(t instanceof Boolean){
				res = (Boolean) t;
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		  return res;
	  }
 }
