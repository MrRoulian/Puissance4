package model;

import java.awt.Point;
import java.util.ArrayList;

public class RobotMCTS extends Robot {
	
	long temps = 3;

	public RobotMCTS(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public Point jouer(int colonne, Plateau p) {
		temps = System.currentTimeMillis();		
		int nbOperation = 0;
		while (System.currentTimeMillis() - temps < 3000) {
			nbOperation++;
		}
		System.out.println(nbOperation);
		return new Point(0, 0);
	}

	
	private class Node {
		private Node parent;
		private ArrayList<Node> fils;
		
		private final double C = Math.sqrt(2);
		public int N = 0;
		public double mu;
		
		public Node(Node parent) {
			this.parent = parent;
			fils = new ArrayList<Node>();
		}
		
		public void addFils(Node fils) {
			this.fils.add(fils);
		}
		
		public void update(double newProba) {
			if (N == 0) {
				mu = newProba;
			} else {
				mu = (mu * N + newProba) / (N + 1);
			}
			N++;
		}
		
		public double getBvalue() {
			return mu + C * Math.sqrt(Math.log10(parent.N) / N);
		}
	}
}
