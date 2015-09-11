package Library;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class LinesComponent_1005046 extends JComponent {
	
	private static class Line {
		final int x1;
		final int y1;
		final int x2;
		final int y2;
		final Color color;

		public Line(int x1, int y1, int x2, int y2, Color color) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
		}
	}

	private final LinkedList<Line> lines = new LinkedList<Line>();

	public void addLine(int x1, int x2, int x3, int x4) {
		addLine(x1, x2, x3, x4, Color.black);
	}

	public void addLine(int x1, int x2, int x3, int x4, Color color) {
		lines.add(new Line(x1, x2, x3, x4, color));
		repaint();
	}

	public void clearLines() {
		lines.clear();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Line line : lines) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLACK);
			g2.fillOval(line.x1-5, line.y1-5, 10, 10);
			g2.fillOval(line.x2-5, line.y2-5, 10, 10);
			g2.setColor(line.color);	
			g2.setStroke(new BasicStroke(3));
			g2.drawLine(line.x1, line.y1, line.x2, line.y2);
			
		}
	}

	

}