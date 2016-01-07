/**
 * https://www.topcoder.com/community/data-science/data-science-tutorials/dynamic-programming-from-novice-to-advanced/
 * (Section: Elementary)
 * Also, here: http://informatics.mccme.ru/mod/book/view.php?id=488
 * 
 * Longest Increasing Subsequence problem:
 * Given a sequence of N numbers – A[1] , A[2] , …, A[N] . Find the length of the longest non-decreasing sequence.
 * 
 * A subsolution state is defined as being the longest non-decreasing sequence which has its last number input[i].
 * To define subsolution[i], need to look at all previous subsolutions j=0..i-1,
 * and find an input[j] that is less than current number input[i] (we're looking for increasing sequence),
 * such that it's subsolution[j] is maximal (meaning the LIS that ends on this input[j] is maximal).
 * We then compute subsolution[i] as this maximal subsolution[j]+1.
 */
public class LongestIncreasingSubsequence {
	public int findLIS(int[] sequence){
		int[] subsolutions = new int[sequence.length];
		int[] preLastMembers = new int[sequence.length];
		
		subsolutions[0]=1;
		for(int i=1;i<sequence.length;i++){
			int preLastSequenceMemberIndex=-1, preLastSequenceMemberLIS=-1;
			for(int j=0; j<i; j++){
				if(sequence[j]<sequence[i] &&
					(subsolutions[j]>preLastSequenceMemberLIS || 
					(subsolutions[j]==preLastSequenceMemberLIS && sequence[preLastSequenceMemberIndex]>sequence[j]))){
						preLastSequenceMemberIndex = j;
						preLastSequenceMemberLIS = subsolutions[j];
				}
			}
			if(preLastSequenceMemberLIS!=-1){
				subsolutions[i]= preLastSequenceMemberLIS+1;
				preLastMembers[i] = preLastSequenceMemberIndex;
			}else{
				subsolutions[i]=1;
			}
		}
		
		int lisLength = 0;
		//find largest number out of subsolutions (isn't necessarily the last value in array)
		for(int i=0;i<sequence.length;i++){
			if(subsolutions[i]>=lisLength) lisLength = subsolutions[i];
		}

		
		return lisLength;
	}

	public static void main(String[] args) {
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();

		//------------------------------------------------------
		//Test 1
		int[] sequence = new int[]{1, 4, 5, 2, 3, 6};
		int answer = lis.findLIS(sequence);
		System.out.println("Answer: " + answer + ". Correct answer: 4;");
		
		//------------------------------------------------------
		//Test 2
		sequence = new int[]{5, 3, 4, 8, 6, 7};
		answer = lis.findLIS(sequence);
		System.out.println("Answer: " + answer + ". Correct answer: 4;");
		
		//------------------------------------------------------
		//Test 3
		sequence = new int[]{1,4,2,3};
		answer = lis.findLIS(sequence);
		System.out.println("Answer: " + answer + ". Correct answer: 3;");
		
		//------------------------------------------------------
		//Test 4
		sequence = new int[]{1,2,4,7,5,3};
		answer = lis.findLIS(sequence);
		System.out.println("Answer: " + answer + ". Correct answer: 4;");
		
		//------------------------------------------------------
		//Test 5
		sequence = new int[]{10,22,9,33,21,50,41,60,80};
		answer = lis.findLIS(sequence);
		System.out.println("Answer: " + answer + ". Correct answer: 6;");
	}

}