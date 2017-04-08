import java.io.IOException;
import java.util.Scanner;

/**
 * This class is Main class of this program.
 * 
 * @author Berk Gulay
 * 
 * @since 2017.04.06
 */
public class Main {
	
	/**
	 * Main method asks user for inputs and assigns needed class items like dict or completer. According to search key matching terms
	 * will be printed as result. Text File input will be used as source in dict item. First k output will be printed in descending
	 * order as result
	 * 
	 * @author Berk Gulay
	 * 
	 * @since 2017.04.06
	 */
	public static void main(String[] args) {
		
		TextFileReader reader = new TextFileReader();
		Scanner sc = new Scanner(System.in);
		
		String prevFileName=" ";
		String[] readFileLines = null;
		int termNum = 1;
		Dict dict = new Dict(termNum, prevFileName); //dict item just for initialization, it wont be used in this form
		Completer comp = new Completer(dict, readFileLines);  //completer item just for initialization, it wont be used in this form also.
		while(true){
			
			System.out.println("Please enter a source Filename : ");
			String fileName=sc.next();
			
			System.out.println("Please enter a -k- int(must be bigger than 0) as number of output words : ");
			int k = sc.nextInt();
			if(k<=0){System.out.println("! Your -k- value is invalid please change it and try again"); continue;}
			
			System.out.println("Please enter a Search Key to complete words : ");
			String searchKey=sc.next();
			
			int sortFlag=1;
			if(fileName.equals(prevFileName)){sortFlag=0;} //if previous successful file is same with this input file do not sort again.
			
			if(sortFlag!=0){ //if previous successful file is not same with this one
				try{
					readFileLines = reader.readFile(fileName); //read given file
				}
				catch(IOException e){ //catch IO exceptions and warn user in readFile class. Start program execution from beginning.
					continue;
				}
				
				termNum=Integer.parseInt(readFileLines[0]);
				dict = new Dict(termNum, fileName); //new dict item is started with it's values from input text
				try{
					comp = new Completer(dict,readFileLines); //assign new completer with values.
				}
				catch(IllegalArgumentException e){ //catch exceptions for weight and warn user in Completer class. 
					continue; // Start program execution from beginning.
				}
				catch(NullPointerException ex){ //catch exceptions for word and warn user in Completer class. 
					continue; // Start program execution from beginning.
				}
				prevFileName = fileName; // Store this file name for next execution of program. If next file is same do not sort it again. Use this completer item.
			}
			
			comp.complete(k, searchKey, sortFlag); //Complete search key by dict source and print first k found term as result.
			
			System.out.println("\n**If you want to use previous(same file name as prev.) input text file just by changing it's lines(with new lines in it), do not rerun the program.\n  Quit and run it again from beginning with new input file to be able to take changes from new file.\n  Otherwise program will be run with your previous input file without your changes, because it keeps the file in memory, if the file is same.");
			System.out.println("\nIf you want to quit please enter q or Q , if you want to rerun the program enter any key value : ");
			String qs=sc.next();
			if(qs.equals("q") || qs.equals("Q")){break;}
		}
		
		sc.close();
	}
	

}
