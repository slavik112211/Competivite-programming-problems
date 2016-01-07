package BinarySearch;


/*
 * Example: A sorted array has been rotated so that the elements might appear in the order 
 * 3456712. How would you find the minimum element? You may assume that the array has
 * all unique elements.
 * From: Cracking the Coding Interview, p. 52
 * 
 */
class BinarySearch {
	int determineMiddle(int indexStart, int indexEnd){
		return (int) (indexEnd-indexStart)/2+indexStart;
	}
	
	int findMin(int[] inputArray, int indexStart, int indexEnd){
		int indexMin = -1;
		if(indexEnd-indexStart<2){
			return (inputArray[indexStart]<inputArray[indexEnd]) ?  indexStart :  indexEnd;
		}
		int indexMiddle =  determineMiddle(indexStart, indexEnd);
		if(inputArray[indexMiddle]>inputArray[indexEnd]){
			indexMin = findMin(inputArray, indexMiddle, indexEnd);
		} else if(inputArray[indexMiddle]<inputArray[indexStart]){
			indexMin = findMin(inputArray, indexStart, indexMiddle);
		} else { // array is ordered and seems like it's not shifted, return first element
			return indexStart;
		}
		return indexMin;
	}

	public static void main(String[] args){
		BinarySearch sorter = new BinarySearch();
//		int[] input = new int[]{4, 5, 6, 7, 8, 9, 1, 2, 3};
		int[] input = new int[]{3, 4, 5, 6, 7, 8, 1, 2};
//		int[] input = new int[]{1, 2, 3, 4, 5, 6, 7};
//		int[] input = new int[]{8, 9, 1, 2, 3, 4, 5, 6, 7};
		int indexMin = sorter.findMin(input, 0, input.length-1);
		System.out.println(indexMin);
	}
	
}