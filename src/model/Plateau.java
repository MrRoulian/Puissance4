package model;

import java.awt.Point;
import java.util.Observable;

public class Plateau extends Observable{

	private int nbColonnes;
	private int nbLignes;
	private boolean end = false;

	private int[][] plateau;
	private Joueur joueurCourant;
	private Joueur joueur1, joueur2;

	public Plateau(int lignes, int colonnes){

		joueur1 = new Humain(1);
		joueur2 = new Humain(2);

		joueurCourant = joueur2;

		nbLignes = lignes;
		nbColonnes = colonnes;
		plateau = new int[lignes][colonnes];
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

	public void jouer(int colonne) {

		Point endroitJoue = joueurCourant.jouer(colonne,this);
		
		//si il a pu jouer
		if (endroitJoue != null) {

			plateau[endroitJoue.x][endroitJoue.y] = joueurCourant.getNumJoueur();
			joueurCourant = joueurCourant.getNumJoueur() == 1 ? joueur2 : joueur1;

			verifState();

			this.setChanged();
			this.notifyObservers();
			
			if (end) {
				return;
			}
			
			if (joueurCourant.isRobot()){
				jouer(0);
			}
		}
	}

	/*
	 * @return -1 si la partie n'est pas fini, 0 si il y a match null, et le numéro du vainceur si il y en a un
	 */
	private int verifState() {
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

		if (win) {
			System.out.println("Joueur " + winner + " à gagné !");
		}

		if (end) {
			winner = 0;
			System.out.println("Match null");
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
}
