import java.util.Scanner;

import model.Humain;
import model.Joueur;
import model.Plateau;
import model.RobotMCTS;
import view.VuePlateau;

public class Main {

	public static void main(String[] args) {

		int rep;
		int rep2;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Selectionner la partie souhaiter :\n\t"
					+ "1. Humain VS Humain\n\t"
					+ "2. Humain VS Robot\n\t"
					+ "3. Robot VS Robot");
			rep = sc.nextInt();
			
		} while (rep != 1 && rep != 2 && rep != 3);		
		
		
		do {
			System.out.println("Qui commence ? 1 ou 2 ?");		

			rep2 = sc.nextInt();
		} while (rep2 != 1 && rep2 != 2);
		

		Joueur j1 = null, j2 = null;
		
		switch(rep){
		case 1:
			j1 = new Humain(1);
			j2 = new Humain(2);
			break;
		case 2:
			j1 = new Humain(1);
			j2 = new RobotMCTS(2);
			
			do {
				System.out.println("Donner le temps (en miliseconde) qu'aura le " + j2.toString() + " pour jouer chaque coup.");
				rep = sc.nextInt();				
			} while (rep < 0);
			
			((RobotMCTS)j2).setTimeToPlay(rep);
			
			break;
		case 3:
			j1 = new RobotMCTS(1);
			do {
				System.out.println("Donner le temps (en miliseconde) qu'aura le " + j1.toString() + " pour jouer chaque coup.");
				rep = sc.nextInt();				
			} while (rep < 0);
			
			((RobotMCTS)j1).setTimeToPlay(rep);
			
			j2 = new RobotMCTS(2);
			do {
				System.out.println("Donner le temps (en miliseconde) qu'aura le " + j2.toString() + " pour jouer chaque coup.");
				rep = sc.nextInt();				
			} while (rep < 0);
			
			((RobotMCTS)j2).setTimeToPlay(rep);
			break;
		}

		Plateau p = new Plateau(6, 7, j1, j2, rep2==1?j1:j2);
		VuePlateau vp = new VuePlateau(p);
		p.addObserver(vp);

		p.lancerPartie();
	}

}
