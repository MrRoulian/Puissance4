package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Plateau;
import model.Robot;

public class PanelDetails extends JPanel {
	
	private GridLayout layout;
	private Plateau plateau;
	private JLabel nbSimu, pourcentageWin, joueurCourrant, victoire;

	public PanelDetails(Plateau p) {
		this.plateau = p;
		initFrame();
	}

	private void initFrame() {		
		layout = new GridLayout(10,1);
		
		nbSimu = new JLabel("Nombre de simulation : ");
		pourcentageWin = new JLabel("Mu :");
		joueurCourrant = new JLabel("Joueur courant");
		joueurCourrant.setOpaque(true);
		victoire = new JLabel();
		
		joueurCourrant.setBackground(plateau.getNumJoueurCourrant() == 1 ? Color.RED : Color.YELLOW);
		
		this.setLayout(layout);
		
		this.add("nbSimu", nbSimu);
		this.add("%win", pourcentageWin);
		this.add("joueur", joueurCourrant);
		this.add(victoire);
	}
	
	public void updateLabels() {
		if (plateau.joueurCourantIsRobot()){
			nbSimu.setText("Nombre de simulation : "+((Robot) plateau.getJoueurCourant()).getN());
			pourcentageWin.setText("Mu : "+(int) (((Robot)plateau.getJoueurCourant()).getMu()*100)+"%");
		}
		joueurCourrant.setBackground(plateau.getNumJoueurCourrant() == 1 ? Color.RED : Color.YELLOW);
		
		if (plateau.isEnded()){
			switch(plateau.verifState()){
			case 0:
				victoire.setText("Match null");
				break;
			case 1:
				victoire.setText("Joueur 1 a gagne !");
				break;
			case 2:
				victoire.setText("Joueur 2 a gagne !");
				break;
			}
		}
	}

}
