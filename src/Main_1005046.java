import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Library.*;

public class Main_1005046 {
	
	public static DecimalFormat two = new DecimalFormat("#0.00");

	public static void main(String[] args) {
		int V = 0;					// number of vertices
		double [][] graph = null;	// adj matrix
		double [] X = null;			// x co-ordinates of points
		double [] Y = null;			// y co-ordinates of points
		
		PrintWriter exp = null, bnb = null, gapx = null, ratio = null;
		try {
			exp = new PrintWriter(new BufferedWriter(new FileWriter("outputs/1005046_exponential.txt", false)));
			bnb = new PrintWriter(new BufferedWriter(new FileWriter("outputs/1005046_branch&bound.txt", false)));
			gapx = new PrintWriter(new BufferedWriter(new FileWriter("outputs/1005046_greedyapproximation.txt", false)));
			ratio = new PrintWriter(new BufferedWriter(new FileWriter("outputs/1005046_approxratio.txt", false)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
				System.out.println("n : " + V );
				System.out.println("--- Adjacency Matrix ---");
				for (int i = 0; i < V; i++) {
					for (int j = 0; j < V; j++) {
						System.out.print(two.format(graph[i][j]) + " ");
					}
					System.out.println();
				}
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

				
				
			}
				
			
			// calculate adj matrix from co-ordinates
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		input.close();
		exp.close();
		bnb.close();
		gapx.close();
		ratio.close();

		

	}
	static void drawGraph(double [] X, double [] Y, int [] mst, int [] hamiltonian, int V) {
		JFrame testFrame = new JFrame("Graph with Number of Vertices: " + V);
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final LinesComponent_1005046 comp = new LinesComponent_1005046();
		comp.setPreferredSize(new Dimension(600, 400));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		
		double maxX = -1;
		for(int i=0;i<V;i++) {
			if (X[i] > maxX) maxX = X[i];
		}
		int scaleX = (int)(600/maxX);
		double maxY = -1;
		for(int i=0;i<V;i++) {
			if (Y[i] > maxY) maxY = Y[i];
		}
		int scaleY = (int)(400/maxY);
		//System.out.println(scaleX + " " + scaleY);
		for(int i=0;i<V;i++) {
			X[i] *= scaleX;
			Y[i] *= scaleY;
			//System.out.println(X[i] + " " + Y[i]);
		}
		
		for (int i = 0; i < V; i++) {
			for (int j = i+1; j < V; j++) {
				//System.out.println(X[i] + " " + Y[i] + " " + X[j] + " " + Y[j]);
				comp.addLine((int)X[i], (int)Y[i], (int)X[j], (int)Y[j], Color.BLUE);
			}
		}
		
		testFrame.pack();
		testFrame.setVisible(true);
		JOptionPane pane = new JOptionPane("Graph with " + V + " vertices");
		JDialog dialog = pane.createDialog(null, "Message");
		dialog.setLocation(700, 0);
		dialog.setVisible(true);
		
		//for(int i=0;i<V+1;i++) System.out.print(mst[i] + " ");
		comp.clearLines();
		
		for (int i = 1; i < V; i++) {
			//System.out.println(X[mst[i]] + " " + Y[mst[i]] + " " + X[mst[i+1]] + " " + Y[mst[i+1]]);
			comp.addLine((int)X[mst[i]], (int)Y[mst[i]], (int)X[i], (int)Y[i], Color.RED);
	
		}
		testFrame.pack();
		testFrame.setVisible(true);
		pane = new JOptionPane("MST");
		dialog = pane.createDialog(null, "Message");
		dialog.setLocation(700, 0);
		dialog.setVisible(true);
		
		comp.clearLines();
		
		for (int i = 0; i < V; i++) {
			//System.out.println(X[hamiltonian[i]] + " " + Y[hamiltonian[i]] + " " + X[hamiltonian[i+1]] + " " + Y[hamiltonian[i+1]]);
			comp.addLine((int)X[hamiltonian[i]], (int)Y[hamiltonian[i]], (int)X[hamiltonian[i+1]], (int)Y[hamiltonian[i+1]], Color.GREEN);
	
		}
		
		testFrame.pack();
		testFrame.setVisible(true);
		pane = new JOptionPane("TSP Circuit");
		dialog = pane.createDialog(null, "Message");
		dialog.setLocation(700, 0);
		dialog.setVisible(true);
		testFrame.dispose();
	}
}
