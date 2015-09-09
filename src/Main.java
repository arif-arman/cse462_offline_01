import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
	
	public static DecimalFormat two = new DecimalFormat("#0.00");

	public static void main(String[] args) {
		int V = 0;					// number of vertices
		double [][] graph = null;	// adj matrix
		double [] X = null;			// x co-ordinates of points
		double [] Y = null;			// y co-ordinates of points
		
		// TODO Auto-generated method stub
		
		int counter = 0;
		File file = new File("input.txt");
		while(true) {
			if (counter > 0) {
				file = new File("input" + counter +".txt");
			}
			Scanner input = null;
			try {
				input = new Scanner(file);
				int i = 0, iter = 0;	// first line is number of vertices
				while (input.hasNext()) {
					if (iter++ == 0) {
						// get number of vertices and instantiate arrays
						V = input.nextInt();
						graph = new double[V][V];
						X = new double[V];
						Y = new double[V];
						continue;
					}
					X[i] = input.nextDouble();
					Y[i++] = input.nextDouble();
					
				}
				// calculate adj matrix from co-ordinates
				for (i = 0; i < V; i++) {
					for (int j = i+1; j < V; j++) {
						double cost = Math.sqrt((X[j]-X[i])*(X[j]-X[i]) + (Y[j]-Y[i])*(Y[j]-Y[i]));
						graph[i][j] = cost;
						graph[j][i] = cost;
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			input.close();
			
			// file input ends
			
			// display adj matrix
			
			System.out.println("--- Adjacency Matrix ---");
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					System.out.print(two.format(graph[i][j]) + " ");
				}
				System.out.println();
			}
			
			//Scanner userInput = new Scanner(System.in);
			//while (true) {
			//System.out.println("\n1. Exact Expoentnial\n2. Branch & Bounding\n3. Greedy 2-Aproximation\n");
			//int choice = userInput.nextInt();
			//if (choice == 1) 
			
			{
			//	double startTime = System.nanoTime();
				//ExactExponential ee = new ExactExponential(graph, V);
				//double stopTime = System.nanoTime();
				//double elapsedTime = (stopTime - startTime) / 1000000;
				//System.out.println("Time " + two.format(elapsedTime) + "ms");
			}
			//else if (choice == 2)
			{
				double startTime = System.nanoTime();
				BranchBounding bb = new BranchBounding(graph, V);
				double stopTime = System.nanoTime();
				double elapsedTime = (stopTime - startTime) / 1000000;
				System.out.println("Time " + two.format(elapsedTime) + "ms");
			}
			//else if (choice == 3) 
			{
				// for measuring time complexity
			//	double startTime = System.nanoTime();				
			//	GreedyApproximation ga = new GreedyApproximation(graph,V);
			//	double stopTime = System.nanoTime();
			//	double elapsedTime = (stopTime - startTime) / 1000000;
			//	System.out.println("Time " + two.format(elapsedTime) + "ms");
			}
			//}
			//userInput.close();
			if (++counter > 0) {
				break;
			}
			System.out.println();
		}

		

	}
}
