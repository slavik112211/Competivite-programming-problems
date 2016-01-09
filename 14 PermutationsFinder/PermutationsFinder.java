import java.util.ArrayList;
import java.util.Arrays;

public class PermutationsFinder<T> {
	
	private ArrayList<ArrayList<T>> permutations = new ArrayList<ArrayList<T>>();
	private ArrayList<ArrayList<T>> input;
	private int permutationsIterator=0;
	
	PermutationsFinder(T[][] inputArray) {
		input = new ArrayList<ArrayList<T>>();
		for(T[] subList: inputArray){
			ArrayList<T> inputSublist = new ArrayList<T>();
			inputSublist.addAll(Arrays.asList(subList));
			input.add(inputSublist);
		}
		findPermutations(new ArrayList<T>(), 0);
	}
	
	public void findPermutations(ArrayList<T> currentPermutation, int currentSublist){
		if(currentSublist>=input.size()){
			ArrayList<T> newPermutation = new ArrayList<T>();
			newPermutation.addAll(currentPermutation);
			permutations.add(newPermutation);
			return;
		}

		for(int i=0; i<input.get(currentSublist).size(); i++){
			currentPermutation.add(input.get(currentSublist).get(i));
			findPermutations(currentPermutation, currentSublist+1);
			currentPermutation.remove(currentPermutation.size()-1);
		}
	}
	
	public ArrayList<T> next(){
		return permutations.get(permutationsIterator++);
	}
	
	public boolean hasNext(){
		return permutations.size()>permutationsIterator;
	}
	
	public static void main(String[] args) {
		Integer[][] input = new Integer[][] {{1,2}, {3,4,5}, {6,7}};
		PermutationsFinder<Integer> permutationsFinder = new PermutationsFinder<Integer>(input);
		while(permutationsFinder.hasNext()) 
			System.out.println(permutationsFinder.next());
		
		Character[][] input2 = new Character[][] {{'a','b'}, {'c','d','e'}, {'f','g'}};
		PermutationsFinder<Character> permutationsFinder2 = new PermutationsFinder<Character>(input2);
		while(permutationsFinder2.hasNext()) 
			System.out.println(permutationsFinder2.next());
	}
}
