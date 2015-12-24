import sedgewick_lib.*;

public class WeightedGraph extends Graph {

    class Edge {
        private int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int otherVertex(int vertex) {
            if (vertex != v && vertex != u) throw new IllegalArgumentException("Edge isn't connecting this vertex");
            return (vertex == u) ? v : u; 
        }

        public String toString(int v){
            return otherVertex(v) + " (w: " + weight + "); ";
        }

        public int getWeight(){
            return this.weight;
        }
    }

    private Bag<Edge>[] adj;

    public int getVertexCount(){
        return this.V;
    }

    public WeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    public WeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            int weight = in.readInt();
            addEdge(v, w, weight);
        }
    }

    public void addEdge(int v, int w, int weight) {
        super.validateVertex(v);
        super.validateVertex(w);
        Edge edge = new Edge(v, w, weight);
        E++;
        adj[v].add(edge);
        adj[w].add(edge);
    }

    public Iterable adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e.toString(v));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        WeightedGraph G = new WeightedGraph(in);
        StdOut.println(G);
    }
}
