package es.tekniker.eefrmwrk.prediction;

import java.util.StringTokenizer;

public class Parser {
	
	public void parse(String input) throws Exception{
		String modelName = null;
		String algorithm = null;
		String vars = null;
		String target = null;
		boolean clean = false;
		boolean missingValues = false;
		StringTokenizer tokenizer = new StringTokenizer(input);
		 while (tokenizer.hasMoreTokens()) {
			 switch(tokenizer.nextToken()){
			 	case "-N":
			 		modelName = tokenizer.nextToken();	
			 		break;
			 	case "-A":
			 		algorithm = tokenizer.nextToken();
			 		break;
			 	case "-V":
			 		vars = tokenizer.nextToken();
			 		break;
			 	case "-T":
			 		target = tokenizer.nextToken();
			 		break;
			 	case "-C":
			 		clean = true;
			 		break;
			 	case "-M":
			 		missingValues = true;
			 		break;
			 	default: 
			 		throw new Exception("invalid input");
			 }
	     }
//		 if(modelName == null || algorithm == null || vars == null || target == null)	throw new Exception("invalid input");
		 parseVars(vars);
		 System.out.println(vars);
	}
	
	
	public void parseVars(String input){
		StringTokenizer tokenizer = new StringTokenizer(input,",");
		String avgVars="";
		String medianVars="";
		String simpleVars="";
		String token="";
		String recursiveInput;
		String[] subStrings;
		boolean onAVG = false;
		boolean onMEDIAN = false;
		
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			System.out.println("tokenizer_0 : " + token);
			
			if(token.contains("avg(") || onAVG){
				onAVG = true;
				System.out.println("------------	onAVG_true_0 ------");
				subStrings = token.split("[(]");
//				if(subStrings[1].equals("avg") || subStrings[1].equals("median") ){ //The first variable is another function
				if(token.contains("avg(avg") || token.contains("avg(median") ){ //The first variable is another function
					recursiveInput = subStrings[1] + "(" + subStrings[2];
					while(tokenizer.hasMoreTokens() && !token.contains(")")){ //continue looking for the right parentheses
						token = tokenizer.nextToken();
						System.out.println("tokenizer_1 : " + token);
						recursiveInput +="," + token;
						if( token.contains(")") )
								break;
					}
					System.out.println("\n\n******	Recursive	*******\n\n");
					parseVars(recursiveInput);
					System.out.println("\n\n******	END Recursive	*******\n\n");
				}
//				else if(subStrings[1].contains(")")){ 
				else if(token.contains(")")){ 
					subStrings = token.split("avg[(]");
					subStrings = subStrings[subStrings.length-1].split("[)]");
					avgVars+=subStrings[0];
					onAVG = false;
					System.out.println("------------	onAVG_false_0 ------");

				}
				else{
					avgVars+=subStrings[subStrings.length-1];
					while(tokenizer.hasMoreTokens()){ //continue looking for the right parentheses
						token = tokenizer.nextToken();
						System.out.println("tokenizer_2 : " + token);
						if(token.contains("avg") || token.contains("median")){
							recursiveInput = token;
							while(tokenizer.hasMoreTokens() && !token.contains(")")){
								token = tokenizer.nextToken();
								System.out.println("tokenizer_3 : " + token);
								recursiveInput +="," + token;
								if(token.contains("))")){
									onAVG = false;
									System.out.println("------------	onAVG_false_1 ------");
									break;
								}
								if( token.contains(")") )
										break;
							}
							System.out.println("\n\n******	Recursive	*******\n\n");
							parseVars(recursiveInput);
							System.out.println("\n\n******	END Recursive	*******\n\n");
							System.out.println("******"+token+"*******");
							if(token.contains("))"))
									break; //Upper while (A function has been the last variable)
						}
						else if(token.contains(")")){
							System.out.println("\n\n******	JURRRRRRR	*******\n\n");

							subStrings = token.split("[)]");
							avgVars+=" "+subStrings[0];
							onAVG = false;
							System.out.println("------------	onAVG_false_2 ------");
							break;
						}
						else avgVars+=token;
					}
				}
			
			}
			else if(token.contains("median(") || onMEDIAN){
				onMEDIAN = true;
//				System.out.println("------------	onMEDIAN_true_0 ------");
				subStrings = token.split("[(]");
//				if(subStrings[1].equals("avg") || subStrings[1].equals("median") ){ //The first variable is another function
				if(token.contains("median(avg") || token.contains("median(median") ){ //The first variable is another function
					recursiveInput = subStrings[1] + "(" + subStrings[2];
					while(tokenizer.hasMoreTokens() && !token.contains(")")){ //continue looking for the right parentheses
						token = tokenizer.nextToken();
//						System.out.println("tokenizer_1 : " + token);
						recursiveInput +="," + token;
						if( token.contains(")") )
								break;
					}
//					System.out.println("\n\n******	Recursive	*******\n\n");
					parseVars(recursiveInput);
//					System.out.println("\n\n******	END Recursive	*******\n\n");
				}
//				else if(subStrings[1].contains(")")){ 
				else if(token.contains(")")){ 
					subStrings = token.split("median[(]");
					subStrings = subStrings[subStrings.length-1].split("[)]");
					medianVars+=subStrings[0];
					onMEDIAN = false;
//					System.out.println("------------	onMEDIAN_false_0 ------");

				}
				else{
					medianVars+=subStrings[subStrings.length-1];
					while(tokenizer.hasMoreTokens()){ //continue looking for the right parentheses
						token = tokenizer.nextToken();
//						System.out.println("tokenizer_2 : " + token);
						if(token.contains("avg") || token.contains("median")){
							recursiveInput = token;
							while(tokenizer.hasMoreTokens() && !token.contains(")")){
								token = tokenizer.nextToken();
//								System.out.println("tokenizer_3 : " + token);
								recursiveInput +="," + token;
								if(token.contains("))")){
									onMEDIAN = false;
//									System.out.println("------------	onAVG_false_1 ------");
									break;
								}
								if( token.contains(")") )
										break;
							}
//							System.out.println("\n\n******	Recursive	*******\n\n");
							parseVars(recursiveInput);
//							System.out.println("\n\n******	END Recursive	*******\n\n");
//							System.out.println("******"+token+"*******");
							if(token.contains("))"))
									break; //Upper while (A function has been the last variable)
						}
						else if(token.contains(")")){
//							System.out.println("\n\n******	JURRRRRRR	*******\n\n");

							subStrings = token.split("[)]");
							medianVars+=" "+subStrings[0];
							onMEDIAN = false;
//							System.out.println("------------	onAVG_false_2 ------");
							break;
						}
						else medianVars+=token;
					}
				}
			}
			else simpleVars += " " + token;

		}
		System.out.println("avg : " +avgVars );
		System.out.println("med : " +medianVars );
		System.out.println("sim : " +simpleVars );

	}
}
