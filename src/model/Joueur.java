package model;

import java.awt.Point;

public abstract class Joueur {
	
	int numJoueur;
	
	public Joueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}
	
	public abstract Point jouer(int colonne, Plateau p);
	
	public int getNumJoueur(){
		return numJoueur;
	}

}
