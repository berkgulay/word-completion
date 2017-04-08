
/**
	 * @author Berk Gulay
	 * 
	 * Interface of "completable" dict classes. If dict class can complate some words with it's data, it is completable.
	 * If so dict class implements this interface.
	 * 
	 * @since 2017.04.06
	 * 	
	 */	
public interface Completable {
	
	//TermList can be sorted by it's word value
	public void wordSort(int lo, int hi);
	
	//SubTermList(found terms in dict's TermList) can be sorted by their weights in descending order.
	public void weightSort(Term[] subTermList,int lo,int hi);
	
	//From dict's TermList needed Terms can be found with search keys. And this method returns found terms with that search key.
	public Term[] foundTerms(String searchKey);	

}
