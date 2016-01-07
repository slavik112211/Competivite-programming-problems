/******************************************************************************
https://www.topcoder.com/community/data-science/data-science-tutorials/dynamic-programming-from-novice-to-advanced/

Given a list of N coins, their values (V1, V2, … , VN), and the total sum S. 
Find the minimum number of coins the sum of which is S 
(we can use as many coins of one type as we want), 
or report that it’s not possible to select coins in such a way that they sum up to S.

Subsolutions are built as follows:
i: outer loop adds available coins to the set;
j: inner loop considers a total sum of value j+1;

Each new value subsolutions[i][j] is calculated as a minimum of:
1. Subsolution of this total sum, without this type of coin (that allows for total sum j) = subsolutions[i][j]
2. Subsolution of this total sum with this type of coin
   (but missing one last coin of this type):
	subsolutions[i, j - coinTypes(i)]+1
	One is added to denote this one last added coin.

See subsolutionsMatrixMinCoins.jpg for details.

*******************************************************************************/

public class MinCoins {

	private static final int INF = 100;
	private int[][] subsolutions;
	private int sum;
	private int[] coinTypes;

	private void initSubsolutionsMatrix() {
		subsolutions = new int[coinTypes.length][sum];
		for (int i = 0; i < coinTypes.length; i++)
			for (int j = 0; j < sum; j++)
				subsolutions[i][j] = INF;
	}

	private int getSubsolution(int i, int j) {
		return (i < 0 || j < 0) ? INF : subsolutions[i][j];
	}

	public int findMinCoinsForSum(int[] coinTypes, int sum) {
		this.coinTypes = coinTypes;
		this.sum = sum;
		initSubsolutionsMatrix();

		for (int i = 0; i < coinTypes.length; i++) {
			int minCoinsWithoutThisType, minCoinsWithThisType;
			for (int j = 0; j < sum; j++) {
				minCoinsWithoutThisType = getSubsolution(i - 1, j);
				minCoinsWithThisType = getSubsolution(i, j - coinTypes[i]);
				if(minCoinsWithThisType!=INF) minCoinsWithThisType++;
				if(coinTypes[i] == j+1) {
					subsolutions[i][j]=1;
					continue;
				}
				if (minCoinsWithoutThisType < minCoinsWithThisType) {
					subsolutions[i][j] = minCoinsWithoutThisType;
				} else {
					subsolutions[i][j] = minCoinsWithThisType;
				}

			}
		}

		return subsolutions[coinTypes.length - 1][sum - 1];

	}

	public static void main(String[] args) {
		MinCoins mc = new MinCoins();
		int[] coinTypes = new int[] {10, 5, 2, 3};
		int minCoins = mc.findMinCoinsForSum(coinTypes, 16);
		System.out.println("Solution: " + minCoins);
	}
}