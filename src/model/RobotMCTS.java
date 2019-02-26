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
		
		public int signe;
		
		private final double C = Math.sqrt(2);
		public int N = 0;
		public double mu;
		
		public Node() {
			signe = 1;
			fils = new ArrayList<Node>();
		}
		
		public Node(Node parent) {
			this.parent = parent;
			fils = new ArrayList<Node>();
			this.signe = parent.signe * -1;
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
			return mu + signe * (C * Math.sqrt(Math.log10(parent.N) / N));
		}
		
		public Node nodeMax() {
			if (fils.size() == 0)
				return this;
			for (Node node : fils)
				if (node.N == 0)
					return this;
			Node nodeMax = this;
			for (Node node : fils)
				if (nodeMax.getBvalue() < node.nodeMax().getBvalue()) 
					nodeMax = node;
			return nodeMax;
		}
	}


	@Override
	public Joueur clone() {
		return new RobotMCTS(numJoueur);
	}
}
