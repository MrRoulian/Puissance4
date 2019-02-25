package model;

import java.awt.Point;

public class Humain extends Joueur {

	public Humain(int numJoueur) {
		super(numJoueur);
	}

	@Override
	public Point jouer(int colonne, Plateau p) {
		int ligne = -1;

		for (int i = p.getNbLignes()-1; i >= 0; i--){
			if (p.getCase(i,colonne) == 0){
				ligne = i;
				break;
			}
		}

		if (ligne < 0) {
			System.out.println("Impossible de jouer sur cette colonne");
			return null;
		}
		
		return new Point(ligne,colonne);
	}

	@Override
	public boolean isRobot() {
		return false;
	}

}
