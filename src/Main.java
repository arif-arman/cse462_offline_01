import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
	
	public static DecimalFormat two = new DecimalFormat("#0.00");

	public static void main(String[] args) {
		int V = 0;					// number of vertices
		double [][] graph = null;	// adj matrix
		double [] X = null;			// x co-ordinates of points
		double [] Y = null;			// y co-ordinates of points
		
		PrintWriter exp = null, bnb = null, gapx = null;
		try {
			exp = new PrintWriter(new BufferedWriter(new FileWriter("outputs/exponential.txt", false)));
			bnb = new PrintWriter(new BufferedWriter(new FileWriter("outputs/branch&bound.txt", false)));
			gapx = new PrintWriter(new BufferedWriter(new FileWriter("outputs/greedyapproximation.txt", false)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
		int counter = 0;
		File file = new File("inputs/input.txt");
		while(true) {
			if (counter > 0) {
				file = new File("inputs/input" + counter +".txt");
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
			
			// output streams initialization
			
			
			// display adj matrix
			System.out.println("n : " + V );
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
				
				System.out.println("--- Exact Exponentiation ---");
				double elapsedTime = 0;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();
					ExactExponential ee = new ExactExponential(graph, V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
				}
				elapsedTime /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				exp.printf("%d \t %.4f\n", V, elapsedTime);
			}
			//else if (choice == 2)
			{
				System.out.println("--- Branch & Bounding ---");
				double elapsedTime = 0;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();
					BranchBounding bb = new BranchBounding(graph, V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
				}
				elapsedTime /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				bnb.printf("%d \t %.4f\n", V, elapsedTime);
			}
			//else if (choice == 3) 
			{
				// for measuring time complexity
				System.out.println("--- Greedy Approximation ---");
				double elapsedTime = 0;
				for(int k=0;k<3;k++) {
					double startTime = System.nanoTime();				
					GreedyApproximation ga = new GreedyApproximation(graph,V);
					double stopTime = System.nanoTime();
					elapsedTime += (stopTime - startTime) / 1000000;
					System.out.println("Time : " + two.format(elapsedTime) + "ms");
				}
				elapsedTime /= 3;
				System.out.println("Time : " + two.format(elapsedTime) + "ms");
				gapx.printf("%d \t %.4f\n", V, elapsedTime);
			}
			//}
			//userInput.close();
			if (++counter > 10) {
				break;
			}
			System.out.println();
			System.out.println();
		}
		exp.close();
		bnb.close();
		gapx.close();

		

	}
}
