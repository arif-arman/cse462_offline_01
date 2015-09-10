import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Library.Prim;

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
		System.out.println("--- TSP ---");
		for (int i = 0; i < minTSP.size(); i++) {
			System.out.print(minTSP.get(i) + " ");
		}
		System.out.println(0);
		System.out.println("Cost : " + Main.two.format(minCost));
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
			if (lowerBound(S) <= minCost) 
				TSP(child.get(i));
			S.remove(child.get(i));
			
		}
	}
	
	double lowerBound(ArrayList<Integer> S) {
		//System.out.print(S);
		//System.out.println();
		double bound = 0;
		for (int i = 0; i < S.size()-1; i++) {
			bound += graph[S.get(i)][S.get(i+1)];
		}
		
		int size = V - S.size();
		if (size == 0) {
			//System.out.println("Store cost");
			bound += graph[S.get(0)][S.get(S.size()-1)];
			if (bound < minCost) {
				minCost = bound;
				minTSP = null;
				minTSP = new ArrayList<Integer>();
				for (int i = 0; i < S.size(); i++) {
					minTSP.add(S.get(i));
				}
			}
		}
		else {
			if (size == 1) {
				//System.out.println("Cost zero");
				bound += 0;
			}
			else {
				int [] vertices = new int[size];
				int iter = 0;
				for (int i = 0; i < V; i++) {
					if (!S.contains(i)) vertices[iter++] = i;
				}
				//for (int i = 0; i < size; i++) 
				//	System.out.print(vertices[i] + " ");
				
				//System.out.println();
				double [][] subgraph = new double[size][size];
				for (int i = 0; i < size; i++) {
					for (int j = i+1; j < size; j++) {
						subgraph[i][j] = graph[vertices[i]][vertices[j]];
						subgraph[j][i] = subgraph[i][j];
					}
				}
				// subgraph adj matrix print
				/*
				for (int i=0;i<size;i++) {
					for (int j=0;j<size;j++) 
						System.out.print(subgraph[i][j] + " ");
					System.out.println();
				}
				*/
				Prim prim = new Prim(subgraph, size);
				int [] parent = prim.primMST();
				double mstCost = 0;
				for (int i = 1; i < size; i++) {
					//System.out.printf("%d - %d    %f \n", parent[i], i, subgraph[i][parent[i]]);
					mstCost += subgraph[i][parent[i]];
				}
				bound += mstCost;
					
			}
			int a = S.get(0);
			int b = S.get(S.size()-1);
			double min = Double.MAX_VALUE;
			for (int i = 0; i < V; i++) 
				if (graph[a][i] < min && !S.contains(i)) min = graph[a][i];
			//System.out.println(min);
			bound += min;
			min = Double.MAX_VALUE;
			for (int i = 0; i < V; i++) 
				if (graph[b][i] < min && !S.contains(i)) min = graph[b][i];
			//System.out.println(min);
			bound += min;
		}
		
		
		//System.out.println("Lower Bound: " + bound);
		return bound;
	}
	
	public double getMinCost() {
		return minCost;
	}


}
