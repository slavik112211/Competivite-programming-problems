import java.util.ArrayList;
import java.util.Arrays;

/**
 *  PermutationsFinder is storing all possible permutations,
 *  and next() calls return the pre-stored permutations.
 *  
 *  PermutationsFinderUsingPointers implements another possible solution:
 *  storing pointers for each sublist,
 *  and on each next() call create a permutation object based on sublist pointers.
 */
public class PermutationsFinderUsingPointers<T> {
	
	private ArrayList<ArrayList<T>> input;
	private int[] sublistPointers;
	
	PermutationsFinderUsingPointers(T[][] inputArray) {
		input = new ArrayList<ArrayList<T>>();
		for(T[] subList: inputArray){
			ArrayList<T> inputSublist = new ArrayList<T>();
			inputSublist.addAll(Arrays.asList(subList));
			input.add(inputSublist);
		}
		sublistPointers = new int[input.size()];
		//On next() call we increment last sublist pointer (from -1 to 0)
		sublistPointers[sublistPointers.length-1] = -1;
	}
	
	public ArrayList<T> next(){
		ArrayList<T> permutation = new ArrayList<T>();
		boolean pointerIncremented = incrementPointer(sublistPointers.length-1);
//		boolean pointerIncremented = incrementPointerIterative();
		if(!pointerIncremented) return permutation;
		
		for(int i=0; i<input.size(); i++)
			permutation.add(input.get(i).get(sublistPointers[i]));
		return permutation;
	}
	
	private boolean incrementPointer(int sublistIndex){
		boolean pointerIncremented = false;
		if(sublistIndex<0) return pointerIncremented;
		
		if(sublistPointers[sublistIndex]+1>input.get(sublistIndex).size()-1){
			sublistPointers[sublistIndex] = 0;
			pointerIncremented = incrementPointer(sublistIndex-1);
		} else {
			sublistPointers[sublistIndex] += 1;
			pointerIncremented=true;
		}
		return pointerIncremented;
	}
	
	/**
	 *  Same as incrementPointer(), but implemented using
	 *  an iterative approach (while loop) instead of recursion
	 */
	private boolean incrementPointerIterative(){
		boolean pointerIncremented = false;
		int sublistIndex = sublistPointers.length-1;
		while(sublistIndex>=0){
			if(sublistPointers[sublistIndex]+1>input.get(sublistIndex).size()-1){
				sublistPointers[sublistIndex] = 0;
				sublistIndex-=1;
			} else {
				sublistPointers[sublistIndex] += 1;
				pointerIncremented=true;
				break;
			}
		}
		return pointerIncremented;
	}
	
	public static void main(String[] args) {
		Integer[][] input = new Integer[][] {{1,2}, {3,4,5}, {6,7}};
		PermutationsFinderUsingPointers<Integer> permutationsFinder = new PermutationsFinderUsingPointers<Integer>(input);
		ArrayList<Integer> permutation;
		do{
			permutation = permutationsFinder.next();
			System.out.println(permutation);
		} while(permutation.size()!=0);
		
		Character[][] input2 = new Character[][] {{'a','b'}, {'c','d','e'}, {'f','g'}};
		PermutationsFinderUsingPointers<Character> permutationsFinder2 = new PermutationsFinderUsingPointers<Character>(input2);
		
		ArrayList<Character> charPermutation;
		do{
			charPermutation = permutationsFinder2.next();
			System.out.println(charPermutation);
		} while(charPermutation.size()!=0);
	}
}