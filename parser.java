import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class parser {

	public static void main(String[] args) throws IOException {

		
		BufferedReader br = null;
		String fname;
		BufferedReader fnameReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the absolute address of the file with production rules");
		fname = fnameReader.readLine();
		//FileReader fr = new FileReader("C:\\Users\\Sushma Alapati\\Desktop\\CDLab\\LR_1Parse\\src\\productions.txt");
	    FileReader fr = new FileReader(fname);
		String line = null, nonTerminal, rest;
		HashMap<Integer, HashMap<String,String>> parseTable = new HashMap<Integer,HashMap<String,String>>();
		ArrayList<String> rightMost= new ArrayList<String>();
		HashMap<Integer,production> prodRules = new HashMap<Integer,production>();
		try {
			br = new BufferedReader(fr);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return ;
		}
		int i =1 , totCount = 0;
		while((line =  br.readLine()) != null) {
			rightMost = new ArrayList<String>();
			totCount  =0;
			nonTerminal = line.substring(0, line.indexOf("->")-1);
			rest = line.substring(line.indexOf("->")+2, line.length());
			StringTokenizer str1 = new StringTokenizer(rest," ");
			while(str1.hasMoreTokens()) {
				rightMost.add(str1.nextToken());
				totCount ++;
			}
			prodRules.put(i, new production(nonTerminal,rightMost,totCount));
			i++;
		}
		fr.close();
		br.close();
		
		// PRINT PRODUCTION RULES
		//		System.out.println(prodRules.size());
		//		for(int i1=1; i1<= prodRules.size(); i1++) {
		//			System.out.println(i1+"."+prodRules.get(i1).start);
		//			System.out.println(prodRules.get(i1).prod);
		//		}
		//
		
		
		
		// ---------------------------PARSE TABLE STORING---------------------------
		//fr = new FileReader("C:\\Users\\Sushma Alapati\\Desktop\\CDLab\\LR_1Parse\\src\\ParseTable.txt");
		System.out.println("Absolute Path for file with ParseTable");
		fname = fnameReader.readLine();
		fr = new FileReader(fname);
		try {
			br = new BufferedReader(fr);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return ;
		}
		String [] symbols = { "=" , "*", "id","$", "S","L","R"}; // symbols order 
		line = null;
		int State, j =0;
		String remain =  null;
		String val = null;
		HashMap<String, String> temp = new HashMap<String,String>();
		while((line = br.readLine()) != null) {
			temp = new HashMap<String,String>();
			j =0 ;
			State = Integer.parseInt(line.substring(0, line.indexOf("\t")));
			remain =  line.substring(line.indexOf("\t")+1, line.length());
			StringTokenizer str = new StringTokenizer(remain , "\t");
			while(str.hasMoreTokens()) {
				val = str.nextToken();
				if(!val.equals("-")){
					temp.put(symbols[j], val);
				}
				j++;			
			}
		    parseTable.put(State,temp);
		}
		fr.close();
		br.close();
		
		// 	 PRINT PARSE TABLE
		//		HashMap<String,String> cur ;
		//		for(int i2 = 0 ; i2< parseTable.size();i2++) {
		//			//System.out.println(parseTable.get(0));
		//			cur = parseTable.get(0);
		//			System.out.println(cur+" "+i2);
		//			for( String s : symbols) {
		//				System.out.println(i2+" |"+s+"| res : |"+cur.get(s)+"|");
		//			}
		//		}
		
		

		//--------------------------- PARSING -----------------
		System.out.println("Absolute path for Input File");
		fname = fnameReader.readLine();
	//	fr = new FileReader("C:\\Users\\Sushma Alapati\\Desktop\\CDLab\\LR_1Parse\\src\\input1.txt");
		fr = new FileReader(fname);
		try {
			br = new BufferedReader(fr);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return ;
		}
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		String input = br.readLine();
		String curr;
		int newState;
		StringTokenizer str2 = new StringTokenizer(input," ");
		ArrayList<String> inputArr = new ArrayList<String>();
		
		while(str2.hasMoreTokens()) {
			inputArr.add(str2.nextToken());
		}
		int inputPtr  = 0;
		production rule;
		int numSymbols = 0;
		System.out.println("Stack top "+"\t\t\t"+ "Current input pointer"+"\t\t"+"ACTION / GOTO");
		
		while(inputPtr < inputArr.size()) {
			
			curr = parseTable.get(stack.peek()).get(inputArr.get(inputPtr));
			System.out.println(stack.peek()+"\t\t\t\t"+inputArr.get(inputPtr)+"\t\t\t\t"+curr);	
			if(curr == null) {
				System.out.println("Rejected !");
				break;
			}


			else if(curr.charAt(0)=='R') {
				rule = prodRules.get(Integer.parseInt(curr.substring(1, curr.length())));
				numSymbols = rule.count;
				while(numSymbols > 0 && !stack.isEmpty()) {
					stack.pop();
					numSymbols--;
				}
				System.out.println("Reduce with : "+rule.start+" -> "+rule.prod);
				stack.push(Integer.parseInt( parseTable.get(stack.peek()).get(rule.start)));

			}

			else if (curr.charAt(0)=='S'){
				stack.push(Integer.parseInt(curr.substring(1,curr.length())));
				inputPtr++;
			}

			else if(curr != null){
				System.out.println("Accepted !");
				break;
			}

						
		}
		fr.close();
		br.close();
		return;
	}

}
