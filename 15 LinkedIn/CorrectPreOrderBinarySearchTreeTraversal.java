import java.io.*;
import java.util.*;
/*
Take 3 hour test on HackerRank (3 problems):
https://www.hackerrank.com/tests/ao69klqqgcm/191db3aefe0b0b607c806f2ef1f1b27c

    1         2          3
     \       / \        / \
      3     1   3      2   5
     /                /   / \
    2                1   4   6
   (a)       (b)        (c)


For the three trees above, the pre-order traversal list will be:
(a) 1 3 2
(b) 2 1 3
(c) 3 2 1 5 4 6

Given a list of numbers, determine whether they&nbsp;can represent the pre-order traversal list of a binary search tree (BST).
 */
public class CorrectPreOrderBinarySearchTreeTraversal {
    
    static String[] answers;
    static String yes="YES", no="NO";
    static ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();
    
    static void determineBSTValidity(){
    	ArrayList<Integer> testCase;
       for(int i=0;i<input.size();i++){
    	   testCase = input.get(i);
    	   boolean validPreOrderBSTTraversal = determineBSTValidityForTree(testCase, 0, 1, testCase.size()-1);
    	   answers[i] = (validPreOrderBSTTraversal) ? yes : no;
       }
    }
    
    static boolean determineBSTValidityForTree(ArrayList<Integer> testCase, Integer rootIndex, 
    		Integer childrenStartIndex, Integer childrenEndIndex){
    	int rightHandChildIndex=-1;
    	boolean preOrderBSTTraversalValid=true;
    	if(childrenStartIndex>childrenEndIndex){
    		return true;
    	}
    	// Determine the right-hand child of the root: a number that is larger than the root
    	// All the numbers smaller than the root can be fit in left-hand child's tree
    	for(int i=childrenStartIndex;i<=childrenEndIndex; i++){
    		if(testCase.get(i)>testCase.get(rootIndex)){
    			rightHandChildIndex=i;
    			break;
    		}
    	}

    	if(rightHandChildIndex==-1){ //right-hand child not found, recurse to the left child
    		preOrderBSTTraversalValid = determineBSTValidityForTree(
    				testCase, rootIndex+1, rootIndex+2, childrenEndIndex);
    		if(!preOrderBSTTraversalValid) return false;
    	} else{ //right hand child found, recurse both to left and right
        	preOrderBSTTraversalValid = determineBSTValidityForTree(  //left child tree
    				testCase, rootIndex+1, rootIndex+2, rightHandChildIndex-1);
    		if(!preOrderBSTTraversalValid) return false;
        	
        	// All the numbers to the right of the newly found right-hand-child
        	// have to be larger than current root
        	// (Binary search tree property - all to the right are larger than the root)
        	for(int i=rightHandChildIndex+1;i<=childrenEndIndex; i++){
        		if(testCase.get(i)<testCase.get(rootIndex)) return false;
        	}
    		preOrderBSTTraversalValid = determineBSTValidityForTree( //right child tree
    				testCase, rightHandChildIndex, rightHandChildIndex+1, childrenEndIndex);
    		if(!preOrderBSTTraversalValid) return false;
    	}
    	
    	return preOrderBSTTraversalValid;
    }
    
    static void printInputValues(){
        for(int i=0; i<input.size(); i++){
            for(Integer value : input.get(i)){
                System.out.print(value.toString() + " ");
            }
            System.out.print("\n");
        }
    }
    
    static void printAnswers(){
        for(int i=0; i<answers.length; i++)
        	System.out.println(answers[i]);
    }
    
    public static void main(String args[] ) throws Exception {
        Scanner std_input = new Scanner(System.in);
        int testcasesAmount = Integer.parseInt(std_input.nextLine());
        answers = new String[testcasesAmount];
        int testcaseSize, i, j;
        String testcaseInput;
        String[] testcaseInputArray;
        ArrayList<Integer> testcaseArray;
        for(i=0; i<testcasesAmount; i++){
            testcaseSize = Integer.parseInt(std_input.nextLine());
            testcaseArray = new ArrayList<Integer>(testcaseSize); 
            testcaseInput = std_input.nextLine();
            testcaseInputArray = testcaseInput.split(" ");
            for(j=0; j<testcaseSize; j++)
                testcaseArray.add(Integer.parseInt(testcaseInputArray[j]));
            input.add(testcaseArray);
        }

        determineBSTValidity();
        //printInputValues();
        printAnswers();
    }
}


/*
Input:
6     -> number of testCases
3     -> length of 1st testcase
1 2 3 -> 1st testcase
3     -> length of 2nd testcase
2 1 3 -> 2nd testcase
6
3 2 1 5 4 6
4
1 3 4 2
5
3 4 5 1 2
5
5 3 2 4 1

Output:
YES
YES
YES
NO
NO
NO

For explanation of results see CorrectPreOrderBinarySearchTreeTraversal_examples.jpg

*/