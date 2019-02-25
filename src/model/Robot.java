package model;

import java.awt.Point;

public class Robot extends Joueur{

	public Robot(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public Point jouer(int colonne, Plateau p) {
		
		return new Point(0,colonne);		
	}

}
