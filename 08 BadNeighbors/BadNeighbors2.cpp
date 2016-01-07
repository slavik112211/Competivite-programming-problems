// Solution taken from: https://learn.hackerearth.com/forum/35/find-the-max-donation-for-bad-neighbors/
// Solution is using the hint from: http://acmph.blogspot.ca/2009/09/badneighbors-2004-tccc-round-4-topcoder.html
// Problem definition: https://community.topcoder.com/stat?c=problem_statement&pm=2402&rd=5009
//

#include <iostream>
#include <vector>

#include <stdio.h> //printf
#include <cstring> //memset

using namespace std;

int badneighbor(int a[], int n);

main()
{
    // int a[] = { 10, 3, 2, 5, 7, 8 };
    // int a[] = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }; 
    //*
    int a[] = { 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
  52, 72, 37, 51, 1, 81, 435, 435, 7, 36, 57, 86, 81, 72 };
  //*/
    // int a[] = { 7, 7, 7, 7, 7, 7, 7 };
    // printf("max = %d \n", badneighbor(a, 6));
    // printf("max = %d \n", badneighbor(a, 10));
    printf("max = %d \n", badneighbor(a, 40));
    // printf("max = %d \n", badneighbor(a, 7));
}

int maxThree(int a, int b, int c){
	int max;
	if (a>=b) { max = (a>=c) ? a : c; }
	else { max = (b>=c) ? b : c; };
	return max;
}

int badneighbor(int a[], int n)
{
    vector<int> s;
    int contrib_0[n];
    int contrib_n[n];
    int max;

    if (n < 3) {
        /* corner case need to return the max of the 3 */
        int max = maxThree(a[0], a[1], a[2]);
        return max;
    }
    memset(contrib_0, 0, n * sizeof(int));
    memset(contrib_n, 0, n * sizeof(int));
    contrib_0[0] = a[0];
    contrib_0[1] = a[1];
    contrib_0[2] = a[0] + a[2];
    for (int i = 3; i <= n-2; i++) {
        if (contrib_0[i-1] > contrib_0[i-2] + a[i]) {
            contrib_0[i] = contrib_0[i-1];
        } else {
            contrib_0[i] = contrib_0[i-2] + a[i];
        }
    }

    contrib_n[1] = a[1];
    contrib_n[2] = a[2];
    contrib_n[3] = a[1] + a[3];

    for (int i = 4; i <= n-1; i++) {
        if (contrib_n[i-1] > contrib_n[i-2] + a[i]) {
            contrib_n[i] = contrib_n[i-1];
        } else {
            contrib_n[i] = contrib_n[i-2] + a[i];
        }
    }

    return contrib_0[n-2]>contrib_n[n-1]?contrib_0[n-2]:contrib_n[n-1];
}

// a         = {1, 2, 3, 4,  5,  1,  2,  3,  4,  5}
// contrib_0 = {1, 2, 4, 6,  9,  9, 11, 12, 15,  0}

// a         = {1, 2, 3, 4,  5,  1,  2,  3,  4,  5}
// contrib_n = {0, 2, 3, 6,  8,  8, 10, 11, 14, 16}


// contrib_n = {0, 2, 3, 5, 0,  10, 10, 12, 13, 16, 18}