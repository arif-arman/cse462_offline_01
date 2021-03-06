import Library.*;
public class GreedyApproximation_1005046 {
	int V;
	double [][] graph;
	int[] hamiltonian;
	int hamiltonian_index;	// index for hamiltonian array
	double totalCost;
	int [] mst;
	
	public GreedyApproximation_1005046(double [][] graph, int V) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
		this.V = V;
		hamiltonian = new int[V+1];
		hamiltonian_index = 0;
		Prim_1005046 prim = new Prim_1005046(graph,V);
		printMST(prim.primMST());
		
	}
	
	void preOrder(int root, int parent[]) {
		//System.out.println(hamiltonian_index);
		hamiltonian[hamiltonian_index++] = root;
		int[] child = new int[V];
		int index = 0;
		for (int i = 1; i < V; i++) {
			if (parent[i] == root)
				child[index++] = i;
		}
		for (int i = 0; i < index; i++) {
			preOrder(child[i], parent);
		}

	}
	
	double tspCost() {
		double cost = 0;
		for (int i = 0; i < V; i++) {
			cost += graph[hamiltonian[i]][hamiltonian[i+1]];
		}
		return cost;
	}
	
	void printMST(int parent[]) {
		//System.out.println("Edge Weight");
		for (int i = 1; i < V; i++)
			System.out.printf("%d - %d    %f \n", parent[i], i, graph[i][parent[i]]);
		mst = parent;
		preOrder(0, parent);
		hamiltonian[hamiltonian_index] = 0;
		System.out.println("--- TSP --- ");
		for (int i = 0; i <= V; i++)
			System.out.print(hamiltonian[i] + " ");
		System.out.println();
		totalCost = tspCost();
		System.out.println("Cost: " + Main_1005046.two.format(totalCost));

	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public int [] getMST() {
		return mst;
	}
	public int [] getHamiltonian() {
		return hamiltonian;
	}


}
