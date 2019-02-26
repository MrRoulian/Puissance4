package model;

import java.awt.Point;

public class RobotAleatoire extends Robot {

	public RobotAleatoire(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public Point jouer(int colonne, Plateau p) {

		int ligne = -1;		

		while (ligne < 0){
			colonne = (int)(Math.random()*p.getNbColonnes());
			for (int i = p.getNbLignes()-1; i >= 0; i--){
				if (p.getCase(i,colonne) == 0){
					ligne = i;
					break;
				}
			}			
		}

		return new Point(ligne,colonne);	
	}

}
