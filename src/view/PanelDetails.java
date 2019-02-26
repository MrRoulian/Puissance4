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
	private JLabel nbSimu, pourcentageWin, joueurCourrant;

	public PanelDetails(Plateau p) {
		this.plateau = p;
		initFrame();
	}

	private void initFrame() {		
		layout = new GridLayout(10,1);
		
		nbSimu = new JLabel("5 simu");
		pourcentageWin = new JLabel("80%");
		joueurCourrant = new JLabel("Joueur courant");
		joueurCourrant.setOpaque(true);
		
		joueurCourrant.setBackground(plateau.getNumJoueurCourrant() == 1 ? Color.RED : Color.YELLOW);
		
		this.setLayout(layout);
		
		this.add("nbSimu", nbSimu);
		this.add("%win", pourcentageWin);
		this.add("joueur", joueurCourrant);
	}
	
	public void updateLabels() {
		if (plateau.joueurCourantIsRobot()){
			nbSimu.setText("Nombre de simulation : "+((Robot) plateau.getJoueurCourant()).getN());
			pourcentageWin.setText(((int)((Robot)plateau.getJoueurCourant()).getMu()*100)+"%");
		}
		joueurCourrant.setBackground(plateau.getNumJoueurCourrant() == 1 ? Color.RED : Color.YELLOW);
	}

}
