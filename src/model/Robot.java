package model;

import java.awt.Point;

public abstract class Robot extends Joueur{

	public Robot(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public abstract Point jouer(int colonne, Plateau p);

	@Override
	public boolean isRobot() {
		return true;
	}
	
	public abstract int getN();
	
	public abstract double getMu();

}
