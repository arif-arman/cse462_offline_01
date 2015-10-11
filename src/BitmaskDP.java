import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BitmaskDP {
	public static  final double INF = 10000;
	public static DecimalFormat two = new DecimalFormat("#0.00");
	double [][] graph = null;	// adj matrix
	int V;				// number of vertices
	int vPow;
	double [][] dp;
	int [][] direction;
	int solution;
	
	public BitmaskDP(double [][] graph, int V) {
		// TODO Auto-generated constructor stub
		this.graph = graph;
		this.V = V;
		vPow = (int) (Math.pow(2, V));
		dp = new double[V][vPow];
		direction = new int[V][vPow];
		solution = 0;
		
		for (int i=0;i<V;i++) {
			for (int j=0;j<vPow;j++) {
				dp[i][j] = -1;
				direction[i][j] = -1;
			}
		}
		
		
		
		//testPrint();
	}
	
	public double tsp(int current, int bitmask, int depth) {		
		
		if (depth == V-1 && bitmask != (1<<V)-1) return BitmaskDP.INF;
		else if (depth == V-1 && bitmask == (1<<V)-1) return graph[current][0];
		else {
			double [] costs = new double[V];
			double ans;
			for (int i=0;i<V;i++) costs[i] = INF;
			for (int i=0;i<V;i++) {
				if (i==current) continue;
				int mask = 1<<i;
				if ((bitmask & mask) == 1) {
					costs[i] = INF;
					continue;
				}
				double cost = tsp(i, bitmask | mask, depth+1);
				costs[i] = cost + graph[current][i];
			}
			ans = INF;
			int minIndex = 0;
			for (int i=0;i<V;i++) {
				
				if (costs[i] < ans) {
					ans = costs[i];
					minIndex = i;
				}
			}
			direction[current][bitmask] = minIndex;
			dp[current][bitmask] = ans;
			return ans;
		}
				
	}
	
	
	
	public void testPrint() {
		System.out.println("n : " + V );
		System.out.println("--- Adjacency Matrix ---");
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				System.out.print(two.format(graph[i][j]) + " ");
			}
			System.out.println();
		}
		
		
		System.out.println("--- DP ---");
		for (int i=0;i<V;i++) {
			for (int j=0;j<vPow;j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("--- Direction ---");
		for (int i=0;i<V;i++) {
			for (int j=0;j<vPow;j++) {
				System.out.print(direction[i][j] + " ");
			}
			System.out.println();
		}
		
		
		
	}
	
	public void solutionSets() {
		int current = 0;
		int count = 0;
		System.out.print(0 + " ");
		while(count < V-1) {
			for (int i=0;i<vPow;i++) {
				if (direction[current][i] != -1) {
					current = direction[current][i];
					break;
				}
			}
			System.out.print(current + " ");
			count++;
			
		}
	}
	
	public void solutionPrint() {
		System.out.println("--- DP ---");
		tsp(0, 1,0);
		
		//testPrint();
		if (dp[0][1] == BitmaskDP.INF){
			
		}
		else {
			System.out.println("Min Cost: " + dp[0][1]);
			//System.out.println("Subsets: " + Integer.toBinaryString(solution));
			System.out.print("Subsets: ");
			solutionSets();
		}
		System.out.println();
	}
	
	
	
	
	
	public static void main(String[] args) {
		int V = 0;					// number of vertices
		double [][] graph = null;	// adj matrix
		double [] X = null;			// x co-ordinates of points
		double [] Y = null;			// y co-ordinates of points
		
		
		// TODO Auto-generated method stub
		
		int counter = 0;
		int test = 0;
		File file = new File("inputs/1005046_input.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
			if (input.hasNext()) 
				test = input.nextInt();	
			for(int l=0;l<test;l++) {
				if (input.hasNext()) {
					V = input.nextInt();
					graph = new double[V][V];
					X = new double[V];
					Y = new double[V];
					for(int i=0;i<V;i++) {
						if (input.hasNext()) {
							X[i] = input.nextDouble();
							Y[i] = input.nextDouble();
						}
						
					}
					for (int i = 0; i < V; i++) {
						for (int j = i+1; j < V; j++) {
							double cost = Math.sqrt((X[j]-X[i])*(X[j]-X[i]) + (Y[j]-Y[i])*(Y[j]-Y[i]));
							graph[i][j] = cost;
							graph[j][i] = cost;
						}
					}
				}
				
				BitmaskDP bdp = new BitmaskDP(graph, V);
				bdp.solutionPrint();
				/*
				System.out.println("--- Exact Exponentiation ---");
				double elapsedTime = 0;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();
					ExactExponential_1005046 ee = new ExactExponential_1005046(graph, V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
					if (k==0 && elapsedTime >= 5000 && V>=13) {
						elapsedTime = elapsedTime * 3;
						break;
					}
				}
				elapsedTime /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				exp.printf("%d \t %.4f\n", V, elapsedTime);
			
				//else if (choice == 2)
				double bbcost = 0;
				System.out.println("--- Branch & Bounding ---");
				elapsedTime = 0;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();
					BranchBounding_1005046 bb = new BranchBounding_1005046(graph, V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					bbcost += bb.getMinCost();
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
				}
				elapsedTime /= 3;
				bbcost /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				bnb.printf("%d \t %.4f\n", V, elapsedTime);
			
				//else if (choice == 3) 
			
				// for measuring time complexity
				double gacost = 0;
				System.out.println("--- Greedy Approximation ---");
				elapsedTime = 0;
				int [] mst = null;
				int [] hamiltonian = null;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();				
					GreedyApproximation_1005046 ga = new GreedyApproximation_1005046(graph,V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					gacost += ga.getTotalCost();
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
					mst = ga.getMST();
					hamiltonian = ga.getHamiltonian();
				}
				elapsedTime /= 3;
				gacost /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				gapx.printf("%d \t %.4f\n", V, elapsedTime);
			
				ratio.printf("%.4f\t%.4f\n", bbcost, gacost);
				
				drawGraph(X,Y,mst,hamiltonian,V);
				//}
				//userInput.close();
				
				System.out.println();
				System.out.println();

				*/
				
			}
				
			
			// calculate adj matrix from co-ordinates
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		input.close();
		

		

	}

}
