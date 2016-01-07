/******************************************************************************
https://community.topcoder.com/tc?module=ProblemDetail&rd=5006&pm=1918
https://community.topcoder.com/stat?c=problem_statement&pm=1918&rd=5006
*******************************************************************************/

import java.util.Arrays;

public class FlowerGarden {
	public int[] getOrdering(int[] height, int[] bloom, int[] wilt){
		int[] ordering = new int[]{3, 4, 5, 1, 2};
		return ordering;
	}

	private void findTimeOverlaps(){

	}

	// Answer taken from 
	// http://stackoverflow.com/questions/11248764/how-is-the-flowergarden-pr0blem-on-topcoder-a-dp-one/22707350#22707350
    public int[] getOrdering2(int[] height, int[] bloom, int[] wilt) {
        int[] optimal = new int[height.length];
        int[] optimalBloom = new int[bloom.length];
        int[] optimalWilt = new int[wilt.length];

        // init state
        optimal[0] = height[0];
        optimalBloom[0] = bloom[0];
        optimalWilt[0] = wilt[0];

        // run dynamic programming
        for(int i = 1; i < height.length; i ++) {
            int currHeight = height[i];
            int currBloom = bloom[i];
            int currWilt = wilt[i];

            int offset = 0; // by default, type i is to be put to 1st row
            for(int j = 0; j < i; j ++) {
                if(currWilt >= optimalBloom[j] && currWilt <= optimalWilt[j] ||
                        currBloom >= optimalBloom[j] && currBloom <= optimalWilt[j] ||
                        currWilt >= optimalWilt[j] && currBloom <= optimalBloom[j]) {  // life period overlap
                    if(currHeight < optimal[j]) {  // life overlap, and type i is shorter than type j
                        offset = j;
                        break;
                    } else {
                        offset = j + 1; // type i overlap with type j, and i is taller than j. Put i after j
                    }
                } else {  // not overlap with current
                    if(currHeight < optimal[j]) {
                        offset = j + 1; // type i not overlap with j, i is shorter than j, put i after j
                    }
                    // else keep offset as is considering offset is smaller than j
                }
            }

            // shift the types after offset
            for(int k = i - 1; k >= offset; k -- ) {
                optimal[k+1] = optimal[k];
                optimalBloom[k+1] = optimalBloom[k];
                optimalWilt[k+1] = optimalWilt[k];
            }
            // update optimal
            optimal[offset] = currHeight;
            optimalBloom[offset] = currBloom;
            optimalWilt[offset] = currWilt;
        }
        return optimal;
    }

	public static void main(String[] args){
		FlowerGarden flowerGarden = new FlowerGarden();

		//------------------------------------------------------
		//Test 0
		int[] height = new int[]{5,4,3,2,1};
		int[] bloom  = new int[]{1,1,1,1,1};
		int[] wilt   = new int[]{365,365,365,365,365};
		int[] ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		int[] correctAnswer = new int[]{ 1,  2,  3,  4,  5 };
		
		String correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 0 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));

		//------------------------------------------------------
		//Test 1
		height = new int[]{5,4,3,2,1};
		bloom  = new int[]{1,5,10,15,20};
		wilt   = new int[]{4,9,14,19,24};
		ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		correctAnswer = new int[]{ 5,  4,  3,  2,  1 };
		
		correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 1 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));

		//------------------------------------------------------
		//Test 2
		height = new int[]{5,4,3,2,1};
		bloom  = new int[]{1,5,10,15,20};
		wilt   = new int[]{5,10,15,20,25};
		ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		correctAnswer = new int[]{ 1,  2,  3,  4,  5 };
		
		correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 2 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));

		//------------------------------------------------------
		//Test 3
		height = new int[]{5,4,3,2,1};
		bloom  = new int[]{1,5,10,15,20};
		wilt   = new int[]{5,10,14,20,25};
		ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		correctAnswer = new int[]{ 3,  4,  5,  1,  2 };
		
		correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 3 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));

		//------------------------------------------------------
		//Test 4
		height = new int[]{1,2,3,4,5,6};
		bloom  = new int[]{1,3,1,3,1,3};
		wilt   = new int[]{2,4,2,4,2,4};
		ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		correctAnswer = new int[]{ 2,  4,  6,  1,  3,  5 };
		
		correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 4 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));

		//------------------------------------------------------
		//Test 5
		height = new int[]{3,2,5,4};
		bloom  = new int[]{1,2,11,10};
		wilt   = new int[]{4,3,12,13};
		ordering = flowerGarden.getOrdering2(height, bloom, wilt);
		correctAnswer = new int[]{ 4,  5,  2,  3 };
		
		correctness = (Arrays.equals(ordering, correctAnswer)) ? "correct" : "not correct";
		System.out.print("Test 5 answer is " + correctness + "; ");
		System.out.println(Arrays.toString(ordering));
	}
}
