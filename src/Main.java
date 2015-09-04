import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int V = 0;
		int[][] graph = null;
		// TODO Auto-generated method stub

		File file = new File("input3.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
			int i = 0, iter = 0;
			while (input.hasNext()) {
				if (iter++ == 0) {
					V = input.nextInt();
					graph = new int[V][V];
					continue;
				}
				for (int j = 0; j < V; j++) {
					graph[i][j] = input.nextInt();
				}
				i++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("--- Adjacency Matrix ---");
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
		input.close();
		Scanner userInput = new Scanner(System.in);
		while (true) {
			System.out.println("\n1. Exact Expoentnial\n2. Branch & Bounding\n3. Greedy 2-Aproximation\n");
			int choice = userInput.nextInt();
			if (choice == 1) {
				double startTime = System.nanoTime();
				ExactExponential ee = new ExactExponential(graph, V);
				double stopTime = System.nanoTime();
				double elapsedTime = (stopTime - startTime) / 1000000;
				System.out.println("Time " + elapsedTime + "ms");
			}
			else if (choice == 3) {
				// for measuring time complexity
				double startTime = System.nanoTime();				
				GreedyApproximation ga = new GreedyApproximation(graph,V);
				ga.primMST();
				double stopTime = System.nanoTime();
				double elapsedTime = (stopTime - startTime) / 1000000;
				System.out.println("Time " + elapsedTime + "ms");
			}
			else break;

		}
		userInput.close();

	}
}
