package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class RobotMCTS extends Robot {

	long temps;
	private Node nodeAttribut;

	public RobotMCTS(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public Point jouer(int colonne, Plateau p) {
		temps = System.currentTimeMillis();		
		// On cree la racine de l'arbre 
		Node racine = new Node(p);
		racine.genererFils();
		// Tant qu'il reste du temps on applique l'algo 
		while (System.currentTimeMillis() - temps < 3000) {
			// On prend le noeud qui est terminal ou qui a des fils non developpe en prenant les meilleurs Bvalue 
			Node node = racine.nodeMax();
			// Si la partie n'est pas finie, on prend un fils non developpe
			if (node.plateau.verifState() == -1) {
				node = node.randomFilsNonVisite();				
				// On simule jusqu'a finir la partie 
				while (node.plateau.verifState() == -1) {
					if (node.fils.size() == 0)
						node.genererFils();
					node = node.randomFils();
				}
			}
			// On recupere le score de la partie (1 si victoire 0 sinon)
			int score = 0;
			if (node.plateau.verifState() == p.getNumJoueurCourrant()) {
				score = 1;
			}
			// On met a jours les N et les mu 
			node.update(score);
		}
		System.out.println(racine.N);

		// On recupere le meilleur coup (recompense "max") 
		Node max = racine.fils.get(0);
		for (Node node : racine.fils) {
			if (node.mu > max.mu) {
				max = node;
			}
		}
		nodeAttribut = max;
		return p.findDifference(max.plateau);
	}


	private class Node {
		private Node parent;
		private ArrayList<Node> fils;

		public int signe;
		public Plateau plateau;

		private final double C = Math.sqrt(2);
		public int N = 0;
		public double mu;

		public Node(Plateau p) {
			signe = -1;
			plateau = new Plateau(p);
			fils = new ArrayList<Node>();
		}

		public Node(Plateau p, Node parent) {
			this.parent = parent;
			plateau = new Plateau(p);
			plateau.switchJoueurCourant();
			fils = new ArrayList<Node>();
			this.signe = parent.signe * -1;
		}

		public void addFils(Node fils) {
			this.fils.add(fils);
		}

		public void genererFils() {
			for (Integer indice : plateau.getIndicesColonnesJouables()) {
				Plateau p = new Plateau(plateau);
				p.jouerColonne(indice);
				fils.add(new Node(p, this));
			}
		}

		public void update(double newProba) {
			if (parent != null) {
				mu = (mu * N + newProba) / (N + 1);
				parent.update(newProba);
			}
			N++;
		}

		public double getBvalue() {
			return mu * signe + (C * Math.sqrt(Math.log10(parent.N) / N));
		}

		public Node nodeMax() {
			if (fils.size() == 0) {
				return this;		
			}
			for (Node node : fils) {
				if (node.N == 0) {
					return this;					
				}
			}
			Node nodeMax = fils.get(0);
			for (Node node : fils) {
				if (nodeMax.getBvalue() < node.getBvalue()) {
					nodeMax = node;					
				}	
			}
			return nodeMax.nodeMax();
		}

		public Node randomFilsNonVisite() {
			Collections.shuffle(fils);
			for (Node node : fils) {
				if (node.N == 0) {
					return node;
				}
			}
			System.err.println("Pas censé arrivé, on récup un fils non visité d'un noeud avec ses fils tous visités");
			return null;
		}

		public Node randomFils() {
			Collections.shuffle(fils);
			return fils.get(0);
		}
	}


	@Override
	public int getN() {
		if(nodeAttribut != null)
			return nodeAttribut.N;
		return 0;
	}

	@Override
	public double getMu() {
		if(nodeAttribut != null)
			return nodeAttribut.mu;
		return 0;
	}

	@Override
	public String toString() {
		return "RobotMCTS "+numJoueur;
	}
}
