import java.util.ArrayList;

/**
 * This class is dictionary class actually to store given queries as terms in TermList and use them to find needed terms
 * 
 * @author Berk Gulay
 * 
 * @since 2017.04.06
 */
public class Dict implements Completable {
	
	private int termNum;
	private String fileName;
	private Term[] termList;
	
	//gets number of total terms in this dict
	public int getTermNum() {
		return termNum;
	}
	
	//gets FileName which comes from source file of this dict.
	public String getFileName() {
		return fileName;
	}
	
	//returns an array which keeps all terms in this dictionary
	public Term[] getTermList() {
		return termList;
	}
	
	//adds new terms(queries) to dict 
	public void addNewTerm(Term term,int index){
		this.termList[index] = term;
	}
	
	//Constructor of dict class. Starts this class with a file name and total term num. Also starts dict's List that keeps all terms.
	public Dict(int termNum, String fileName) {
		super();
		this.termNum = termNum;
		this.fileName = fileName;
		this.termList = new Term[termNum];
	}
	
	/**
	 * QUICK SORT Alg. is used for word sort(lexicographic sort A to Z).
	 * This method sorts Term List array by it's term's word. So lexicographicly sorts terms in Term List array.
	 * <p>
	 * (lexicographic sort will be done) , QUICK SORT algorithm is used for word sort. Instead of less() method compareTo() method is
	 * used to compare two Strings by their chars and sort have been done by char's ascii value so.
	 * 
	 * @since 2017.04.06
	 * 
	 * @param lo beginning index of Term List of this class which will be sorted by words
	 * @param hi end index of Term List of this class which will be sorted by words
	 * 
	 */
	@Override 
	public void wordSort(int lo, int hi) {
		
		if (hi<=lo) return;
		
		int j=wordSortPartition(lo,hi);
		wordSort(lo,j-1);
		wordSort(j+1,hi);
	}
	
	/**
	 * QUICK SORT Alg. is used for integer sort. Descending order will be done for weights in Sub Term List
	 * This method sorts Sub Term List array(found terms) by it's term's weight.
	 * <p>
	 * (integer sort will be done in descending order) QUICK SORT algorithm is used for word sort. Integer values are sorted with 
	 * classic Quick Sort algorithm but adapt it to Term usage to reach weights.
	 * @
	 * @since 2017.04.06
	 * 
	 * @param subTermList Array that will be sorted by weights. This array keeps the terms which are found in this dict by Search Key.
	 * @param lo beginning index of Sub Term List(found terms) which will be sorted by weights.
	 * @param hi end index of Sub Term List(found terms) which will be sorted by weights.
	 * 
	 */
	@Override // subTermList will be sorted by weights
	public void weightSort(Term[] subTermList,int lo,int hi) {
		
		if (hi<=lo) return;
		
		int j=weightSortPartition(subTermList,lo,hi);
		weightSort(subTermList,lo,j-1);
		weightSort(subTermList,j+1,hi);
		
	}
	
	/**
	 * BINARY SEARCH is used to find first matching term.
	 * This method finds needed terms by Search Key and returns them in Term List
	 * <p>
	 * Find operation will be done. Search Key will be used for finding terms(Binary Search alg. used to find first matching term)
	 * from this dictionaries terms. All matching terms will be found and returned in Term array.
	 * 
	 * @since 2017.04.06
	 * 
	 * @param subTermList Array that will be sorted by weights. This array keeps the terms which are found in this dict by Search Key.
	 * @param lo beginning index of Sub Term List(found terms) which will be sorted by weights.
	 * @param hi end index of Sub Term List(found terms) which will be sorted by weights.
	 * 
	 * @return Term[] Term array that keeps found terms among this dict's all terms,if returns null no such elements in TermList so subTermList is empty
	 */
	@Override 
	public Term[] foundTerms(String searchKey){
		
		ArrayList<Term> tempRes = new ArrayList<Term>();
		
		int fp=dictBinarySearch(searchKey); //place of first found term in dict's Term List. If -1 no item found
		if(fp==-1) return null; //no term can found
		tempRes.add(this.termList[fp]);
		
		int i=fp; int j=fp; //to find all matching terms we will traverse array from first found item's place to upward and downward
		
		while(true){ 
			--i;   //checks found terms at downside of first found term
			if(searchKey.length()>this.termList[i].getWord().length()){ //if search key is longer than this bottom term.
				break; //Break, because at bottom no other longer term which is matching with search key can be found. Because they are sorted by word.
			}
			else{
				if(searchKey.compareTo(this.termList[i].getWord().substring(0, searchKey.length()))==0){
					tempRes.add(this.termList[i]); // if terms are matching add the term in found TermList(Sub Term List)
					}
				else{break;} //if terms are not matching no other matching term can be found because they are sorted by words.
			}
		}	
		while(true){
			++j;  //checks found terms at upside of first found term
			if(searchKey.length()>this.termList[j].getWord().length()){ //if search key is longer than this upside term.
				break; //Break, because at upside there cant be any term which is matching. They are sorted by word.
			}
			else{
				if(searchKey.compareTo(this.termList[j].getWord().substring(0, searchKey.length()))==0){
					tempRes.add(this.termList[j]); // if terms are matching add the term in found TermList(Sub Term List)
					}
				else{break;} //if terms are not matching no other matching term can be found because they are sorted by words.
			}
		}
		
		int sizeOfSubList=tempRes.size();
		Term[] subTermList = new Term[sizeOfSubList];
		for(int t=0;t<sizeOfSubList;t++){
			subTermList[t]=tempRes.get(t); //put all matching terms from array list to normal array.
		}
		
		return subTermList;
	}
	
	/**
	 * 
	 * Quick Sort's partition segment. Returns place of true placed term by word like normal Quick Sort alg. does.
	 * <p>
	 * Quick Sort is used to sort String values also by their chars ascii value with compareTo method. So Quick Sort's partition segment
	 * is just adopted to String sorting by ascii values and compareTo() method usage.
	 * @
	 * @since 2017.04.06
	 * 
	 * @param lo beginning index of Term List of this dict which will be sorted by words.
	 * @param hi end index of Term List of this dict which will be sorted by words.
	 * 
	 * @return int returns index of true placed term for this partition. (by word of term)
	 */
	public int wordSortPartition(int lo, int hi){
		
		int i=lo; int j=hi+1;
		
		while(true){
			while(this.termList[lo].getWord().compareTo(this.termList[++i].getWord())>0){
				if(i==hi) break;
			}
			while(this.termList[lo].getWord().compareTo(this.termList[--j].getWord())<0){}
			
			if(i>=j) break;
			swap(this.termList,i,j);
		}
		swap(this.termList,lo,j);
		
		return j;
		
	}
	
	/**
	 * 
	 * Quick Sort's partition segment. Returns place of true placed term by weight like normal Quick Sort alg. does.
	 * <p>
	 * Quick Sort is used to sort integer values. Descending order sort will be done so algorithm is adopted to it.
	 * @
	 * @since 2017.04.06
	 * 
	 * @param subTermList array of found terms from this dict according to Search Key.
	 * @param lo beginning index of Sub Term List(found terms) which will be sorted by weights.
	 * @param hi end index of Sub Term List(found terms) which will be sorted by weights.
	 * 
	 * @return int returns index of true placed term for this partition. (by weight of term) 
	 */
	public int weightSortPartition(Term[] subTermList,int lo,int hi){
		
		int i=lo; int j=hi+1;
		
		while(true){
			while(subTermList[lo].getWeight()<subTermList[++i].getWeight()){
				if(i==hi) break;
			}
			while(subTermList[lo].getWeight()>subTermList[--j].getWeight()){}
			
			if(i>=j) break;
			swap(subTermList,i,j);
		}
		swap(subTermList,lo,j);
		
		return j;
	}
	
	/**
	 * 
	 * BINARY SEARCH algorithm is used to find matching term according to Search Key.
	 * <p>
	 * Finds matching term according to Search Key with Binary Search usage. Returns its place in Term List array.
	 * @
	 * @since 2017.04.06
	 * 
	 * @param subTermList array of found terms from this dict according to Search Key.
	 * @param lo beginning index of Sub Term List(found terms) which will be sorted by weights.
	 * @param hi end index of Sub Term List(found terms) which will be sorted by weights.
	 * 
	 * @return int place of found term in Term List of this dict. If no item found returns -1.
	 */
	public int dictBinarySearch(String searchKey) {
		
		int lo=0; int hi=this.termNum-1;
		
		while(lo<=hi){
			
			int mid=(lo+hi)/2;
			int cmp; //shows our place to continue to search
			if(searchKey.length()<=this.termList[mid].getWord().length()){ 
				cmp=searchKey.compareTo(this.termList[mid].getWord().substring(0, searchKey.length())); //check if they are equal
			}	
			else{ // if search key is longer than our term's word, take all of our term. But they cant be equal just check what's our place to continue.
				cmp=searchKey.compareTo(this.termList[mid].getWord());
			}
			
			if(cmp>0){
				lo=mid+1;
			}
			else if(cmp<0){
				hi=mid-1;
			}
			else{
				return mid;
			}
		}
		
		return -1; //not found in List
	}
	
	/**
	 * Swaps to given elements by their place in given Term List.(Change places by indexes of two item in array that is Term List)
	*/
	public void swap(Term[] termList,int i,int j){
		Term temp=termList[i];
		termList[i]=termList[j];
		termList[j]=temp;
	}
	
	
	
	
	

}
