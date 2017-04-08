
public class Completer {
	private Dict dict;
	
	/**
	 * This method starts dict item of this completer class with it's initial values. 
	 * <p>
	 * Term List will be filled by other method, also total term num and file name will be assigned after this constructor call by DictCreator() method.
	 * 
	 * 
	 * @since 2017.04.06
	 * 
	 * @param dict item of dict class which we will fill it to use as source.
	 * @param readFileLines read queries from given input text file in an array. Array will be used to fill dict item in DictCreator method.
	 * 
	 * @throws IllegalArgumentException If weight of query is less than 0
	 * @throws NullPointerException If word of query is null
	 * 
	 */
	public Completer(Dict dict,String[] readFileLines) throws IllegalArgumentException,NullPointerException{
		super();
		this.dict = dict;
		if(readFileLines==null){return;} //If there is no such file lines in array, do not try to fill this dict with it.
		try{
			int ret=dictCreater(readFileLines);
			if(ret==-1){throw new IllegalArgumentException();} //If read query weight is smaller than 0, IllegalArgumentException will be thrown.
			else if(ret==-2){throw new NullPointerException();} //If read query word is null, NullPointerException will be thrown.
		}
		catch(IllegalArgumentException e){  //catch IllegalArgumentException above, print warn message and throw it again to catch it while calling of this constructor.
			System.out.println("! One of the term's weight is less than zero in input file. Change it and try again.");
			System.out.println(e.getMessage());
			throw new IllegalArgumentException();
		}
		catch(NullPointerException ex){ //catch NullPointerException above, print warn message and throw it again to catch it while calling of this constructor.
			System.out.println("! One of the term's word does not exist in input file. Change it and try again.");
			System.out.println(ex.getMessage());
			throw new NullPointerException();
		}
		
	}
	
	/**
	 * This method fills dict item of this completer class.
	 * <p>
	 * Term List will be filled, also total term num and file name will be assigned. Constructor of this class calls this method to
	 * fill and assign needed values in dict item.
	 * 
	 * @since 2017.04.06
	 * 
	 * @param readFileLines read queries from given input text file in an array. Array will be used to fill dict item.
	 * 
	 * @return 0 if filling is successful and no unexpected error/situation occurs.
	 * @return -1 if query weight that is read from text input is smaller than 0
	 * @return -2 if query word that is read from text input is null
	 * 
	 */
	public int dictCreater(String[] readFileLines){
		
		for(int i=1;i<readFileLines.length;i++){
			String[] splittedLine = readFileLines[i].split("\t");
			if(splittedLine.length<2){return -2;}
			
			long queryWeight = Long.parseLong(splittedLine[0]);
			String queryWord = splittedLine[1];
			if(queryWeight<0){return -1;}
			else if(queryWord.equals(null)){return -2;}
			
			Term nTerm = new Term(queryWeight, queryWord);
			this.dict.addNewTerm(nTerm, i-1);
		}
		return 0;
	}
	
	/**
	 * This method completes the words and prints the results. Uses dict's terms as source to complete words.
	 * <p>
	 * 
	 * Method search k number of value to complete if possible by search key. Finds terms from dict classes term list.
	 * Search all values in this list and if there are matching terms with search key prints them in descending order of weight
	 * as result. If no item is found warns user. If there is no enough value in matching terms according to k, also warns user and 
	 * prints all found terms as well.
	 * 
	 * <p>
	 * 
	 * Method uses Binary Search alg. to find matching terms. Sorts by weight and word with Quick Sort Algorithm that is adopted 
	 * to Term class usage.
	 * 
	 * @since 2017.04.06
	 * 
	 * @param k number of total output values that should be given. If bigger than found terms so all found terms will be given as result.
	 * @param searchKey Search Key is key to find matching terms from dict. So it is part of some words and we use it to search words.
	 * @param sortFlag If sort flag is 1 new file has entered, so dict should be sorted by words again. Else(sort flag is 0) do not sort it again same(prev.) file has entered as input.
	 * 
	 */
	public void complete(int k,String searchKey,int sortFlag){
		
		if(sortFlag==1){this.dict.wordSort(0, this.dict.getTermNum()-1);} //if sortflag is 1 sort the dict's term list. else do not sort again.
		
		Term[] foundTerms = this.dict.foundTerms(searchKey);
		if(foundTerms == null){ 							//if found terms array is null no matching terms.
			System.out.println("! No item can be found with your SearchKey, please change it and try again");
			return;
		}
		this.dict.weightSort(foundTerms, 0, foundTerms.length-1);
		
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\n% "+this.dict.getFileName()+" "+this.dict.getTermNum()+"\n"+searchKey);
		if(k<=foundTerms.length){
			for(int i=0;i<k;i++){
				System.out.println((i+1)+") "+foundTerms[i].getWeight()+"\t"+foundTerms[i].getWord());
			}
		}
		else{
			for(int j=0;j<foundTerms.length;j++){
				System.out.println((j+1)+") "+foundTerms[j].getWeight()+"\t"+foundTerms[j].getWord());
			}
			System.out.println("\n! Just "+foundTerms.length+" item can found with your Search Key");
		}
		System.out.println("-------------------------------------------------------------------------");
	}
	
	
	

}
