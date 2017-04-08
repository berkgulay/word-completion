
/**
	 * @author Berk Gulay
	 * 
	 * Terms class to store query values of input file in it with their weight and word. 
	 * 
	 * @since 2017.04.06
	 */	
public class Term {
	
	private final long weight;
	private final String word;
	
	//gets weight of query value in it.
	public long getWeight() {
		return weight;
	}
	//gets word of query value in it.
	public String getWord() {
		return word;
	}
	
	//Constructor of this term class. Starts this class with its final field values from input file.
	public Term(long weight, String word) {
		super();
		this.weight = weight;
		this.word = word;
	}
	
}
