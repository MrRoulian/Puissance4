package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class RobotMCTS extends Robot {

	long temps;
	int timeToPlay = 3000;
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
		while (System.currentTimeMillis() - temps < timeToPlay) {
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
		// System.out.println(racine.N);

		// On recupere le meilleur coup (recompense "robuste") 
		Node max = racine.fils.get(0);
		for (Node node : racine.fils) {
			if (node.N > max.N) {
				max = node;
			}
		}
		nodeAttribut = max;
		return p.findDifference(max.plateau);
	}
	
	public void setTimeToPlay(int ttp){
		this.timeToPlay=ttp;
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
			// On recupere la liste des colonnes jouables 
			for (Integer indice : plateau.getIndicesColonnesJouables()) {
				// On copie le plateau
				Plateau p = new Plateau(plateau);
				// On joue dans la colonne jouable
				p.jouerColonne(indice);
				// On ajoute le fils a la liste de fils 
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
			// Le noeud est terminal 
			if (fils.size() == 0) {
				return this;		
			}
			// Le noeud n'a pas tout ses fils visite
			for (Node node : fils) {
				if (node.N == 0) {
					return this;					
				}
			}
			// Recherche de la meilleur B-valeur
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
			System.err.println("Pas cense arrive, on recupere un fils non visite d'un noeud avec ses fils tous visites");
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
