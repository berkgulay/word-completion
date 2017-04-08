import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextFileReader {
	
	/**
	 * 
	 * This method gives us an string array that includes the lines of a file which we give as a parameter.
	 * <p>
	 * This is method for reading a file and return its lines in an array.It returns a String array and this array includes
	 * these lines in String format.So we can use a text file and its lines.Method gives us a chance for reading lines and keep them
	 * in memory for manupulating and using them in some way.
	 * 
	 * @since 2017.04.06
	 * 
	 * @param path This method takes a file path as parameter
	 * 
	 * @return It returns a String array which has got lines of a file
	 * 
	 * @throws IOException If the file doesn't exist or it can't be founded method throws/prints a file exception to warn us	
	 */	
	public String[] readFile(String path) throws IOException{
		try{
			int i=0;
			
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String len = bf.readLine();
			bf.close();
			String[] results = new String[Integer.parseInt(len)+1];
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	int t=0;
					while(line.charAt(t)==' '){t++;}
					line = line.substring(t, line.length()); //take out the spaces from line's beginning
					
					results[i++]=line; //take that line into array
			    }
			    br.close();
			}
			
		return results;
		}
		catch (IOException e){
			System.out.println("! Invalid/Non-existing File, change it and try again");
			System.out.println(e.getMessage());
			throw new IOException();
		}
		
	}

}
