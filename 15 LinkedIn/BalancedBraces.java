import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class BalancedBraces {

	/*
	 * Complete the function below.
	 */
	    static String yes="YES";
	    static String no="NO";
	    
	    static String[] Braces(String[] values) {
	        char currentChar;
	        String[] answers = new String[values.length];
	        Stack<Character> bracesStack = new Stack<Character>();
	        for(int i=0;i<values.length;i++){
	            for(int j=0; j<values[i].length(); j++){
	                currentChar = values[i].charAt(j);
	                if(!isBrace(currentChar)) continue;
	                if(isOpeningBrace(currentChar)){
	                    bracesStack.push(new Character(currentChar));
	                } else if(isClosingBrace(currentChar)){
	                    if(bracesStack.empty()){
	                        answers[i]=no;
	                        break;
	                    }
	                    Character lastOpeningBrace = bracesStack.pop();
	                    if(!bracesMatch(lastOpeningBrace.charValue(), currentChar)){
	                        answers[i]=no;
	                        break;
	                    }
	                    
	                }
	            }
	            if(answers[i] == null && bracesStack.empty()) answers[i]=yes;
	        }
	        return answers;
	    }

	    static boolean isBrace(char input){
	        return (isOpeningBrace(input) || isClosingBrace(input)) ? true : false;
	    }

	    static boolean isOpeningBrace(char input){
	        return (input=='(' || input=='[' || input=='{') ? true : false;
	    }

	    static boolean isClosingBrace(char input){
	        return (input==')' || input==']' || input=='}') ? true : false;
	    }

	    static boolean bracesMatch(char braceA, char braceB){
	        return ((braceA=='(' && braceB==')') || 
	                (braceA=='[' && braceB==']') || 
	                (braceA=='{' && braceB=='}')) ? true : false;
	    }
	
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
//        final String fileName = System.getenv("OUTPUT_PATH");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        String[] res;
        
        int _values_size = 0;
        _values_size = Integer.parseInt(in.nextLine());
        String[] _values = new String[_values_size];
        String _values_item;
        for(int _values_i = 0; _values_i < _values_size; _values_i++) {
            try {
                _values_item = in.nextLine();
            } catch (Exception e) {
                _values_item = null;
            }
            _values[_values_i] = _values_item;
        }
        
        res = Braces(_values);
        for(int res_i=0; res_i < res.length; res_i++) {
//            bw.write(String.valueOf(res[res_i]));
//            bw.newLine();
        	System.out.print(String.valueOf(res[res_i]));
        	System.out.print("\n");
        }
        
//        bw.close();
    }
}

