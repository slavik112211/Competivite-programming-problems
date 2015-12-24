import java.lang.Math;
import java.util.Arrays;
public class ATaleOfThreeCities {
    public double findEucleadianDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
    }

    public double findShortestPathPointsBetweenTwoClusters(int[] ax, int[] ay, int[] bx, int[] by) {
        double shortestDistance = Double.POSITIVE_INFINITY;
        int shortestA, shortestB;
        double currentDistance;

        for(int i=0; i<ax.length; i++){
            for(int j=0; j<bx.length; j++){
                currentDistance = this.findEucleadianDistance(ax[i], ay[i], bx[j], by[j]);
                if(currentDistance <= shortestDistance){
                    shortestDistance = currentDistance;
                	shortestA = i;
                	shortestB = j;
                }
            }
        }
        return shortestDistance;
    }

    public double connect(int[] ax, int[] ay, int[] bx, int[] by, int[] cx, int[] cy) {
        double[] ABCdistances = new double[3];
        //1. Find shortest distance between clusters A and B
        ABCdistances[0] = this.findShortestPathPointsBetweenTwoClusters(ax, ay, bx, by); 

        //2. Find shortest distance between clusters B and C
        ABCdistances[1] = this.findShortestPathPointsBetweenTwoClusters(cx, cy, bx, by);

        //3. Find shortest distance between clusters A and C
        ABCdistances[2] = this.findShortestPathPointsBetweenTwoClusters(ax, ay, cx, cy);

        Arrays.sort(ABCdistances); //asc
        return ABCdistances[0]+ABCdistances[1];
    }

    public static void main(String[] args){
    	ATaleOfThreeCities triCities = new ATaleOfThreeCities();
    	int[] ax = {0,0,0};
		int[] ay = {0,1,2};
		int[] bx = {2,3};
		int[] by = {1,1};
		int[] cx = {1,5};
		int[] cy = {3,28};
    	double distance = triCities.connect(ax, ay, bx, by, cx, cy);
    	if(distance == 3.414213562373095) {
    		System.out.println("1st example correct: " + distance);
    	};

    	int[] ax1 = {-2,-1,0,1,2};
		int[] ay1 = {0,0,0,0,0};
		int[] bx1 = {-2,-1,0,1,2};
		int[] by1 = {1,1,1,1,1};
		int[] cx1 = {-2,-1,0,1,2};
		int[] cy1 = {2,2,2,2,2};
    	distance = triCities.connect(ax1, ay1, bx1, by1, cx1, cy1);
    	if(distance == 2.0) {
    		System.out.println("2nd example correct: " + distance);
    	};

		int[] ax2 = {4,5,11,21,8,10,3,9,5,6};
		int[] ay2 = {12,8,9,12,2,3,5,7,10,13};
		int[] bx2 = {34,35,36,41,32,39,41,37,39,50};
		int[] by2 = {51,33,41,45,48,22,33,51,41,40};
		int[] cx2 = {86,75,78,81,89,77,83,88,99,77};
		int[] cy2 = {10,20,30,40,50,60,70,80,90,100};
    	distance = triCities.connect(ax2, ay2, bx2, by2, cx2, cy2);
    	if(distance == 50.32339777661102) {
    		System.out.println("3rd example correct: " + distance);
    	};
    }
}