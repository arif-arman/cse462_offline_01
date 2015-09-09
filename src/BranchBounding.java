import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BranchBounding {
	int V;
	double [][] graph;
	int [] vertices;
	ArrayList<Integer> S;	
	double minCost;
	ArrayList<Integer> minTSP;
	
	public BranchBounding(double [][] graph, int V) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
		this.V = V;
		S = new ArrayList<Integer>();
		minCost = Double.MAX_VALUE;
		minTSP = new ArrayList<Integer>();
		S.add(0);
		TSP(0);
	}
	
	void TSP(int v) {
		//System.out.print(v + " ");
		ArrayList<Integer> child = new ArrayList<Integer>();
		for (int i = 0; i < V; i++) {
			if (i==v) continue;
			if (!S.contains(i)) {
				child.add(i);
			}
		}
		for (int i = 0; i < child.size(); i++) {
			S.add(child.get(i));
			if (lowerBound(S) > minCost) return;
			TSP(child.get(i));
			S.remove(child.get(i));
		}
	}
	
	double lowerBound(ArrayList<Integer> S) {
		System.out.println(S);
		int a = S.get(0);
		int b = S.get(S.size()-1);
		int size = V - S.size();
		int [] vertices = new int[size];
		int iter = 0;
		for (int i = 0; i < V; i++) {
			if (!S.contains(i)) vertices[iter++] = i;
		}
		for (int i = 0; i < size; i++) {
			System.out.print(vertices[i] + " ");
		}
		double [][] subgraph = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = i+1; j < size; j++) {
				
			}
		}
		return 0;
	}
	

}
