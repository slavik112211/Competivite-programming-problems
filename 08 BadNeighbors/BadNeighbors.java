/******************************************************************************
https://community.topcoder.com/tc?module=ProblemDetail&rd=5009&pm=2402
https://community.topcoder.com/stat?c=problem_statement&pm=2402&rd=5009
*******************************************************************************/

import java.util.Arrays;

public class BadNeighbors {
	public int maxDonations(int[] donations){
		return maxDonationsDynamicProgramming(donations);
	}

	/*
	 * Greedy programming method (simple intuitive approach):
	 * 1. Search over all of the donations and pick the max donation.
	 * 2. Mark this donation, and both it's neighbors as not-available for further consideration.
	 * 3. Keep picking until there are no unmarked donations left to choose.
	 * 
	 * Results:
	 *
	 * Test 0 answer: 19. Correct answer: 19; 
     * Test 1 answer: 15. Correct answer: 15; 
     * Test 2 answer: 21. Correct answer: 21; 
     * Test 3 answer: 16. Correct answer: 16; 
     * Test 4 answer: 2878. Correct answer: 2926;
     * 
     * Greedy algorithm fails to find the optimal solution for Test 4.
	 */
	private int maxDonationsGreedyRunner(int[] donations){
		int[] donationsAccepted = new int[donations.length];
		Arrays.fill(donationsAccepted, 0);
		int totalDonationsAccepted=0;
		
		return maxDonationsGreedy(donations, donationsAccepted, totalDonationsAccepted);
	}
	
	private int maxDonationsGreedy(int[] donations, int[] donationsAccepted, int totalDonationsAccepted){
		int maxDonation, maxDonationNeighbor;
		do{
			maxDonation=-1; maxDonationNeighbor=-1;
			for(int i=0; i<donations.length; i++){
				if(donations[i]>maxDonation && donationsAccepted[i] != 1){
					maxDonation = donations[i];
					maxDonationNeighbor = i;
				}
			}
			if(maxDonationNeighbor != -1){
				totalDonationsAccepted+=maxDonation;
				donationsAccepted[maxDonationNeighbor] = 1;
				donationsAccepted[leftNeighbor(maxDonationNeighbor, donations)] = 1;
				donationsAccepted[rightNeighbor(maxDonationNeighbor, donations)] = 1;
			}
		}while(maxDonationNeighbor != -1);
		
		return totalDonationsAccepted;
	}
	
	private int leftNeighbor(int maxDonationNeighbor, int[] array){
		return (maxDonationNeighbor == 0) ? array.length-1 : maxDonationNeighbor-1;
	}
	
	private int rightNeighbor(int maxDonationNeighbor, int[] array){
		return (maxDonationNeighbor == array.length-1) ? 0 : maxDonationNeighbor+1;
	}
	
	/*
	 * Dynamic programming (step-by-step optimization) method:
	 * 1. Consider finding the optimal arrangement for I-neighbors (I beginning with 1 to complete neighbors set N).
	 * 2. Imagine the optimal arrangement was found for K neighbors, and we're considering adding K+1 neighbor.
	 * 3. The optimal for K+1 neighbors will be the maxDonations value of:
	 * 		1. When K+1 neighbor's donation is not included
	 *         a. Mark K+1 as not-available initially, and proceed with Greedy);
	 *         b. Optimal value with K only elements;
	 * 		2. When K+1 neighbor's donation is included 
	 *         c. Including element K+1 initially, and calculate using greedy approach;
	 * 4. Pick max(a, b, c) and proceed to increase neighbors amount I until the case with all neighbors I=N is considered.
	 *
	 */
	private int maxDonationsDynamicProgrammingFail(int[] donations){
		
		int[] maximalDonationsWithINeighbors = new int[donations.length];
		
		//1. Initial setup. Maximal donations with only the first neighbor - donations from this first neighbor.
		maximalDonationsWithINeighbors[0] = donations[0];
		int[] donationsAccepted = new int[donations.length];
		int maximalDonationWithoutINeighbor, maximalDonationWithINeighbor;
		int[] currentDonationsAvailable;
		
		for(int i=1; i<donations.length; i++){
			//Scenario a. When K+1 (=i) neighbor's donation is not included
			//Mark i donation as not-available initially, and proceed with Greedy
			currentDonationsAvailable = Arrays.copyOf(donations, i+1);
			donationsAccepted = new int[currentDonationsAvailable.length];
			Arrays.fill(donationsAccepted, 0);
//			donationsAccepted[i]=1;
			maximalDonationWithoutINeighbor = maxDonationsGreedy(currentDonationsAvailable, donationsAccepted, 0);
			
			//Scenario c. When K+1 (=i) neighbor's donation is included
			//Including element K+1 initially (and the value of their donation), and calculate using greedy approach;
			donationsAccepted = new int[currentDonationsAvailable.length];
			Arrays.fill(donationsAccepted, 0);
			donationsAccepted[i]=1;
			donationsAccepted[leftNeighbor(i, currentDonationsAvailable)] = 1;
			donationsAccepted[rightNeighbor(i, currentDonationsAvailable)] = 1;
			maximalDonationWithINeighbor = maxDonationsGreedy(currentDonationsAvailable, donationsAccepted, donations[i]);
			
			maximalDonationsWithINeighbors[i] = Math.max(maximalDonationWithoutINeighbor, maximalDonationWithINeighbor);
//			maximalDonationsWithINeighbors[i] = maximum(maximalDonationWithoutINeighbor, maximalDonationsWithINeighbors[i-1], maximalDonationWithINeighbor);
			
			/*System.out.println("i=" + i + "; currentDonationsAvailable: " + Arrays.toString(currentDonationsAvailable) + 
					   ";\nmaximalDonationWithoutINeighbor: " + maximalDonationWithoutINeighbor + 
			           "; maximalDonationsWithoutINeighbors[i-1]: " + maximalDonationsWithINeighbors[i-1] + 
			           "; maximalDonationWithINeighbor: " + maximalDonationWithINeighbor +
			           "; maximalDonationsWithINeighbors: " + Arrays.toString(maximalDonationsWithINeighbors));*/
		}
		return maximalDonationsWithINeighbors[donations.length-1];
	}
	
	//Hint taken from http://acmph.blogspot.ca/2009/09/badneighbors-2004-tccc-round-4-topcoder.html
	//Doesn't honor the invariants: last element is taken together with the first element, 
	//which can't be done since they are considered neighbors (all neighbors sit in a circle)
	private int[] maxDonationsDynamicProgrammingNotCircular(int[] donations){
		
		int[] maximalDonationsWithINeighbors = new int[donations.length];
		
		//1. Initial setup. Maximal donations with only the first neighbor - donations from this first neighbor.
		maximalDonationsWithINeighbors[0] = donations[0];
		int maximalDonationWithoutINeighbor, maximalDonationWithINeighbor;
		
		for(int i=1; i<donations.length; i++){
			//Scenario a. current i-th neighbor's donation included: element[i]+dp[i-2]
			maximalDonationWithINeighbor = donations[i] + getArrayElement(maximalDonationsWithINeighbors, i-2);
			
			//Scenario b. When i-th neighbor's donation is not included: element[i-1]+dp[i-3]
			maximalDonationWithoutINeighbor = donations[i-1] + getArrayElement(maximalDonationsWithINeighbors, i-3);
			
			maximalDonationsWithINeighbors[i] = Math.max(maximalDonationWithoutINeighbor, maximalDonationWithINeighbor);
			/*System.out.println("i=" + i + "; currentDonationsAvailable: " + Arrays.toString(currentDonationsAvailable) + 
					   ";\nmaximalDonationWithoutINeighbor: " + maximalDonationWithoutINeighbor + 
			           "; maximalDonationsWithoutINeighbors[i-1]: " + maximalDonationsWithINeighbors[i-1] + 
			           "; maximalDonationWithINeighbor: " + maximalDonationWithINeighbor +
			           "; maximalDonationsWithINeighbors: " + Arrays.toString(maximalDonationsWithINeighbors));*/
		}
		return maximalDonationsWithINeighbors;
	}
	
	private int maxDonationsDynamicProgramming(int[] donations){
		int[] maximalDonationsWithINeighborsWithoutFirst = maxDonationsDynamicProgrammingNotCircular(Arrays.copyOfRange(donations, 1, donations.length));
		
		int[] maximalDonationsWithINeighbors = new int[donations.length];
		
		//1. Initial setup. Maximal donations with only the first neighbor - donations from this first neighbor.
		maximalDonationsWithINeighbors[0] = donations[0];
		int maximalDonationWithoutINeighbor, maximalDonationWithINeighbor;
		
		for(int i=1; i<donations.length; i++){
			//Scenario a. current i-th neighbor's donation included: element[i]+dp[i-2]
			maximalDonationWithINeighbor = donations[i] + getArrayElement(maximalDonationsWithINeighborsWithoutFirst, i-3);  //has to be i-2, but is i-1, since this array doesn't include 1st element
			
			//Scenario b. When i-th neighbor's donation is not included: element[i-1]+dp[i-3]
			maximalDonationWithoutINeighbor = donations[i-1] + getArrayElement(maximalDonationsWithINeighbors, i-3);
//			maximalDonationWithoutINeighbor = getArrayElement(maximalDonationsWithINeighbors, i-1);
			
			maximalDonationsWithINeighbors[i] = Math.max(maximalDonationWithoutINeighbor, maximalDonationWithINeighbor);
			/*System.out.println("i=" + i + "; currentDonationsAvailable: " + Arrays.toString(currentDonationsAvailable) + 
					   ";\nmaximalDonationWithoutINeighbor: " + maximalDonationWithoutINeighbor + 
			           "; maximalDonationsWithoutINeighbors[i-1]: " + maximalDonationsWithINeighbors[i-1] + 
			           "; maximalDonationWithINeighbor: " + maximalDonationWithINeighbor +
			           "; maximalDonationsWithINeighbors: " + Arrays.toString(maximalDonationsWithINeighbors));*/
		}
		return maximalDonationsWithINeighbors[donations.length-1];
	}
	
	private int getArrayElement(int[] array, int index){
		return (index<0 || index>array.length-1) ? 0 : array[index];
	}
	
	private static int maximum(int a, int b, int c){
		int maximum;
		if     (a>=b && a>=c) maximum=a;
		else if(b>=a && b>=c) maximum=b;
		else if(c>=a && c>=b) maximum=c;
		else                  maximum=a;
		return maximum;
	}

	public static void main(String[] args){
		BadNeighbors badNeighbors = new BadNeighbors();

		//------------------------------------------------------
		//Test 0
		int[] donations = new int[]{ 10, 3, 2, 5, 7, 8 };
		int answer = badNeighbors.maxDonations(donations);
		int correctAnswer = 19;
		System.out.println("Test 0 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 1
		donations = new int[]{ 11, 15 };
		answer = badNeighbors.maxDonations(donations);
		correctAnswer = 15;
		System.out.println("Test 1 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 2
		donations = new int[]{ 7, 7, 7, 7, 7, 7, 7 };
		answer = badNeighbors.maxDonations(donations);
		correctAnswer = 21;
		System.out.println("Test 2 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 3
		donations = new int[]{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };
		answer = badNeighbors.maxDonations(donations);
		correctAnswer = 16;
		System.out.println("Test 3 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 4
		donations = new int[]{94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61, 6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397, 52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72};
		answer = badNeighbors.maxDonations(donations);
		correctAnswer = 2926;
		System.out.println("Test 4 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 5
		donations = new int[]{10};
		int[] donationsAccepted = new int[]{0};
		int totalDonationsAccepted=0;
		
		answer = badNeighbors.maxDonationsGreedy(donations, donationsAccepted, totalDonationsAccepted);
		correctAnswer = 10;
		System.out.println("Test 5 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");

		//------------------------------------------------------
		//Test 6
		donations = new int[]{10, 15};
		donationsAccepted = new int[]{0, 1};
		totalDonationsAccepted=0;
		
		answer = badNeighbors.maxDonationsGreedy(donations, donationsAccepted, totalDonationsAccepted);
		correctAnswer = 10;
		System.out.println("Test 6 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");
		
		//------------------------------------------------------
		//Test 7
		donations = new int[]{10};
		donationsAccepted = new int[]{1};
		totalDonationsAccepted=10;
		
		answer = badNeighbors.maxDonationsGreedy(donations, donationsAccepted, totalDonationsAccepted);
		correctAnswer = 10;
		System.out.println("Test 7 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");
		
		//------------------------------------------------------
		//Test 8
		donations = new int[]{10};
		donationsAccepted = new int[]{1};
		totalDonationsAccepted=0;
		
		answer = badNeighbors.maxDonationsGreedy(donations, donationsAccepted, totalDonationsAccepted);
		correctAnswer = 0;
		System.out.println("Test 8 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");
		
		//------------------------------------------------------
		//Test 9
		answer = maximum(3,2,4);
		System.out.println("Test 9 answer: " + answer + ". Correct answer: 4;");
		
		//------------------------------------------------------
		//Test 10
		donations = new int[]{347, 49, 608, 460, 67, 856, 21, 526, 552, 412, 761, 286, 481, 441, 598, 933, 462, 328, 92};
		answer = badNeighbors.maxDonations(donations);
		correctAnswer = 4866;
		System.out.println("Test 10 answer: " + answer + ". Correct answer: " + correctAnswer + "; ");
	}
}
