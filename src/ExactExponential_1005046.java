import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExactExponential_1005046 {
	int V;
	double [][] graph;
	double minCost;	
	List<Integer> minTSP;	// final TSP
	double start;
	double check;

	public ExactExponential_1005046(double [][] graph, int V) {
		// TODO Auto-generated constructor stub
		start = System.currentTimeMillis();
		this.V = V;
		this.graph = graph;
		minCost = Integer.MAX_VALUE;
		minTSP = new ArrayList<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < V; i++) {
			list.add(i);
		}
		if (permute(list, 0)) {
			System.out.println("--- TSP ---");
			System.out.print(0 + " ");
			for (int i = 0; i < minTSP.size(); i++) {
				System.out.print(minTSP.get(i) + " ");
			}
			System.out.println(0);
			System.out.println("Cost : " + Main_1005046.two.format(minCost));
			
		}
		else {
			System.out.println("No solution found in 5s");
		}
		
	}
	
	/*
	 * generate all permutation of list
	 */
		
	boolean permute(List<Integer> arr, int k) {
		check = System.currentTimeMillis();
		if (check - start > 5000) return false;
		boolean flag = true;
		for (int i = k; i < arr.size(); i++) {
			//System.out.println(arr);
			java.util.Collections.swap(arr, i, k);
			if (permute(arr, k + 1)) {
				java.util.Collections.swap(arr, k, i);
				flag = true;
			}
			else flag = false;
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
		return flag;
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
