import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExactExponential {
	int V;
	double [][] graph;
	double minCost;	
	List<Integer> minTSP;	// final TSP

	public ExactExponential(double [][] graph, int V) {
		// TODO Auto-generated constructor stub
		this.V = V;
		this.graph = graph;
		minCost = Integer.MAX_VALUE;
		minTSP = new ArrayList<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < V; i++) {
			list.add(i);
		}
		permute(list, 0);
		System.out.println("--- TSP ---");
		System.out.print(0 + " ");
		for (int i = 0; i < minTSP.size(); i++) {
			System.out.print(minTSP.get(i) + " ");
		}
		System.out.println(0);
		System.out.println("Cost : " + Main.two.format(minCost));
		
	}
	
	/*
	 * generate all permutation of list
	 */
		
	void permute(List<Integer> arr, int k) {
		for (int i = k; i < arr.size(); i++) {
			//System.out.println(arr);
			java.util.Collections.swap(arr, i, k);
			permute(arr, k + 1);
			java.util.Collections.swap(arr, k, i);
		}
		if (k == arr.size() - 1) {
			//System.out.println(Arrays.toString(arr.toArray()));
			double cost = tspCost(arr);
			//System.out.println(cost);
			if (cost < minCost) {
				minCost = cost;
				minTSP.clear();
				minTSP.addAll(arr);
			}
		}
	}
	
	/*
	 * calculate cost of tsp created with vertices of arr & root
	 */
	
	double tspCost(List<Integer> arr) {
		
		double cost = graph[0][arr.get(0)];
		int i = 0;
		for (; i < arr.size()-1; i++) {
			cost += graph[arr.get(i)][arr.get(i+1)];
		}
		return cost + graph[arr.get(i)][0];
		
	}
}
