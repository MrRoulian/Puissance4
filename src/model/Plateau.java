package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

public class Plateau extends Observable{

	private int nbColonnes;
	private int nbLignes;
	private boolean end = false;

	private int[][] plateau;
	private Joueur joueurCourant;
	private Joueur joueur1, joueur2;

	public Plateau(int lignes, int colonnes) {
		joueur1 = new Humain(1);
		joueur2 = new RobotMCTS(2);

		joueurCourant = joueur1;

		nbLignes = lignes;
		nbColonnes = colonnes;
		plateau = new int[lignes][colonnes];
	}

	public Plateau(Plateau p) {
		joueur1 = p.joueur1;
		joueur2 = p.joueur2;

		joueurCourant = p.joueurCourant;

		nbLignes = p.nbLignes;
		nbColonnes = p.nbColonnes;
		plateau = clone(p.plateau);
	}

	private int[][] clone(int[][] plateau2) {
		int[][] res = new int[plateau2.length][plateau2[0].length];
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				res[i][j] = plateau2[i][j];
			}
		}
		
		return res;
	}

	public void lancerPartie() {
		if (joueurCourant.isRobot()) jouer(0);
	}	

	public boolean joueurCourantIsRobot() {
		return joueurCourant.isRobot();
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public int getCase(int ligne, int colonne){
		return plateau[ligne][colonne];
	}

	public ArrayList<Integer> getIndicesColonnesJouables(){
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i = 0 ; i < nbColonnes ; i++){
			if (plateau[0][i] == 0){
				res.add(i);
			}
		}
		return res;
	}

	public void jouer(int colonne) {
		Point endroitJoue = joueurCourant.jouer(colonne,this);		
		//si il a pu jouer
		if (endroitJoue != null) {
			plateau[endroitJoue.x][endroitJoue.y] = joueurCourant.getNumJoueur();
			verifState();

			//update mu et N et joueurCourant 
			this.setChanged();
			this.notifyObservers();

			if (end) {
				return;
			}

			switchJoueurCourant();

			if (joueurCourant.isRobot()){
				jouer(0);
			}
		}
	}

	public void switchJoueurCourant() {
		joueurCourant = joueurCourant.getNumJoueur() == 1 ? joueur2 : joueur1;		
	}

	public void jouerColonne(int colonne){
		int ligne = -1;

		for (int i = nbLignes-1; i >= 0; i--){
			if (plateau[i][colonne] == 0){
				ligne = i;
				break;
			}
		}

		if (ligne >= 0){
			plateau[ligne][colonne] = joueurCourant.getNumJoueur();
		}

		verifState();
	}

	/*
	 * @return -1 si la partie n'est pas fini, 0 si il y a match null, et le numéro du vainqueur si il y en a un
	 */
	public int verifState() {
		boolean win = false;
		int winner = -1;

		end = true;

		//Vérif end
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				//Si tous est à différent de 0 end restera à true
				end &= plateau[i][j] != 0;
			}
		}

		//Vérif win
		for (int i = 0; i < nbLignes; i++) {
			if (!win && !end) {
				for (int j = 0; j < nbColonnes; j++) {
					if (i + 3 < nbLignes) {
						//Vérif colonne vers le bas
						if (plateau[i][j] != 0 &&
								plateau[i][j] == plateau[i + 1][j] &&
								plateau[i + 1][j] == plateau[i + 2][j] &&
								plateau[i + 2][j] == plateau[i + 3][j]) {
							win = true;
							winner = plateau[i][j];
							break;
						}
					}

					if (j + 3 < nbColonnes)	{
						//Vérif ligne vers la droite
						if (plateau[i][j] != 0 &&
								plateau[i][j] == plateau[i][j + 1] &&
								plateau[i][j + 1] == plateau[i][j + 2] &&
								plateau[i][j + 2] == plateau[i][j + 3]) {
							win = true;
							winner = plateau[i][j];
							break;
						}
					}

					if (j + 3 < nbColonnes && i + 3 < nbLignes)	{
						//Vérif diago bas-droite
						if (plateau[i][j] != 0 &&
								plateau[i][j] == plateau[i + 1][j + 1] &&
								plateau[i + 1][j + 1] == plateau[i + 2][j + 2] &&
								plateau[i + 2][j + 2] == plateau[i + 3][j + 3]) {
							win = true;
							winner = plateau[i][j];
							break;
						}
					}

					if (j - 3 >= 0 && i + 3 < nbLignes)	{
						//Vérif diago bas-gauche
						if (plateau[i][j] != 0 &&
								plateau[i][j] == plateau[i + 1][j - 1] &&
								plateau[i + 1][j - 1] == plateau[i + 2][j - 2] &&
								plateau[i + 2][j - 2] == plateau[i + 3][j - 3]) {
							win = true;
							winner = plateau[i][j];
							break;
						}
					}
				}
			}
		}

		if (end) {
			winner = 0;
		}

		if (end || win)	{
			end = true;
		}

		return winner;
	}

	public boolean isEnded() {
		return end;
	}

	public int getNumJoueurCourrant(){
		return joueurCourant.getNumJoueur();
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public Point findDifference(Plateau p){
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				if (p.getCase(i, j) != plateau[i][j])
					return new Point(i,j);
			}
		}
		return null;
	}
}
