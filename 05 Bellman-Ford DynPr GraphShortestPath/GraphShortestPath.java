import sedgewick_lib.*;

public class GraphShortestPath {
    private WeightedGraph graph;
    private int[][] dynamicProgrammingSubsolutionsPathLengths;
    private final int MAX_EDGE_WEIGHT = 100000;

    public GraphShortestPath(String graphFilename) {
        In in = new In(graphFilename);
        this.graph = new WeightedGraph(in);

        dynamicProgrammingSubsolutionsPathLengths = new int[this.graph.getVertexCount()][this.graph.getVertexCount()];
        for (int e=0; e<this.graph.getVertexCount(); e++) {
            for (int v=0; v<this.graph.getVertexCount(); v++) {
                dynamicProgrammingSubsolutionsPathLengths[e][v] = MAX_EDGE_WEIGHT;
            }
        }
    }

    public void shortestPathDynamicProgrammingBellmanFord() {
        //1. outer loop increases amount of edges that are allowed for shortest path
        //from 1 edge to amount of vertices in graph 
        //(since no shortest path can be longer than total amount of vertices in the graph)
        //2. inner loop: path-ending vertex id

        int startVertex = 0;
        for (int e=0; e<this.graph.getVertexCount(); e++) {
            dynamicProgrammingSubsolutionsPathLengths[e][startVertex] = 0;
        }

        for(int i=1; i<this.graph.getVertexCount(); i++){
            for(int v=0; v<this.graph.getVertexCount(); v++){ // endVertex of the path
                if(startVertex == v) continue;
                int minPathLengthWithOneLessEdge = dynamicProgrammingSubsolutionsPathLengths[i-1][v];

                int minPathLengthWithINumberOfEdges = MAX_EDGE_WEIGHT;
                for(Object edge : this.graph.adj(v)){
                    WeightedGraph.Edge lastEdgeOfPath = (WeightedGraph.Edge) edge;
                    int adjacent_vertex = lastEdgeOfPath.otherVertex(v);
                    int pathLengthThroughThisAdjacentVertex = dynamicProgrammingSubsolutionsPathLengths[i-1][adjacent_vertex] + lastEdgeOfPath.getWeight();
                    minPathLengthWithINumberOfEdges = minimum(minPathLengthWithINumberOfEdges, pathLengthThroughThisAdjacentVertex);
                }
                dynamicProgrammingSubsolutionsPathLengths[i][v] = minimum(minPathLengthWithOneLessEdge, minPathLengthWithINumberOfEdges);
            }
        }
    }

    public WeightedGraph getGraph(){
        return this.graph;
    }

    private int minimum(int left, int right){
        return (left <= right) ? left : right;
    }

    public void printDynamicProgrammingSubsolutionsMatrix(){
        for (int e=0; e<this.graph.getVertexCount(); e++) {
            for (int v=0; v<this.graph.getVertexCount(); v++) {
                Integer value = this.dynamicProgrammingSubsolutionsPathLengths[e][v];
                String valueString = (value == MAX_EDGE_WEIGHT) ? "MAX" : value.toString();
                System.out.print(valueString + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        GraphShortestPath shortestPath = new GraphShortestPath(args[0]);
        StdOut.println(shortestPath.graph);
        shortestPath.shortestPathDynamicProgrammingBellmanFord();
        shortestPath.printDynamicProgrammingSubsolutionsMatrix();
    }
}
